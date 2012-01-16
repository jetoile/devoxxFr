package fr.soat.devoxx.game.business.admin;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public class JSONPRequestFilter implements Filter {
    private final static Logger LOGGER = LoggerFactory.getLogger(JSONPRequestFilter.class);

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        //NOtHING TO DO
    }

    public void doFilter(ServletRequest request, ServletResponse response,
                         FilterChain chain) throws IOException, ServletException {
        if (!(request instanceof HttpServletRequest)) {
            LOGGER.warn("This filter can only process HttpServletRequest requests");
            throw new ServletException("This filter can only process HttpServletRequest requests");
        }

        HttpServletRequest httpRequest = (HttpServletRequest) request;

        if (isJSONPRequest(httpRequest)) {
            ServletOutputStream out = response.getOutputStream();

            out.println(getCallbackMethod(httpRequest) + "(");
            chain.doFilter(request, response);
            out.println(");");

            response.setContentType("text/javascript");
        } else {
            chain.doFilter(request, response);
        }
    }

    @Override
    public void destroy() {
        //NOTHING TO DO
    }

    private String getCallbackMethod(HttpServletRequest httpRequest) {
        return httpRequest.getParameter("jsoncallback");
    }

    private boolean isJSONPRequest(HttpServletRequest httpRequest) {
        String callbackMethod = getCallbackMethod(httpRequest);
        return (callbackMethod != null && callbackMethod.length() > 0);
    }
}