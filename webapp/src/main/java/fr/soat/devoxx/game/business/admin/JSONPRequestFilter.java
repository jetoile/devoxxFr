package fr.soat.devoxx.game.business.admin;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

public class JSONPRequestFilter implements Filter {
    private final static Logger LOGGER = LoggerFactory.getLogger(JSONPRequestFilter.class);

    protected String jsonp = "jsoncallback";
    protected String[] jsonMimeTypes = new String[]{
            "application/json",
            "application/x-json",
            "text/json",
            "text/x-json"
    };

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        //NOtHING TO DO
    }


    public void doFilter(final ServletRequest req, final ServletResponse resp,
                         FilterChain chain) throws IOException, ServletException {

        if (resp instanceof HttpServletResponse && isJsonp(req)) {

            System.out.println(((HttpServletRequest)req).getMethod());
            HttpServletResponseContentWrapper wrapper = new HttpServletResponseContentWrapper(
                    (HttpServletResponse) resp) {

                private boolean wrapContentType = false;

                public String getContentType() {
                    return wrapContentType ? "text/javascript; charset=utf-8" :
                            super.getContentType();
                }

                @Override
                public byte[] wrap(byte[] content) throws UnsupportedEncodingException {
                    wrapContentType = true;
                    String contentstr = new String(content, getCharacterEncoding());
                    boolean isJson = isJson(req, super.getResponse());
//                    return (getCallback(req) + "(" +
//                            (isJson ? contentstr : quote(contentstr)) +
//                            ");").getBytes(getCharacterEncoding());
                    return contentstr.getBytes(getCharacterEncoding());
                }
            };

            wrapper.addHeader("Access-Control-Allow-Origin", "*");
            wrapper.setContentType("text/javascript");
            wrapper.setCharacterEncoding("UTF-8");
            chain.doFilter(req, wrapper);
            wrapper.flushWrapper();

        } else {
            HttpServletResponse response = (HttpServletResponse)resp;
            response.addHeader("Access-Control-Allow-Origin", "*");
            response.setContentType("text/javascript");
            response.setCharacterEncoding("UTF-8");
            chain.doFilter(req, resp);

        }
    }


    protected boolean isJsonp(ServletRequest req) {
        if (req instanceof HttpServletRequest) {
            return req.getParameterMap().containsKey(jsonp);
        } else {
            return false;
        }
    }

    protected boolean isJson(ServletRequest req, ServletResponse resp) {
        String ctype = resp.getContentType();
        if (ctype == null || ctype.equals("")) {
            return false;
        }
        for (String jsonMimeType : jsonMimeTypes) {
            if (ctype.indexOf(jsonMimeType) >= 0) {
                return true;
            }
        }
        return false;
    }

    protected String getCallback(ServletRequest req) {
        return req.getParameterValues(jsonp)[0];
    }

//    public void doFilter(ServletRequest request, ServletResponse response,
//                         FilterChain chain) throws IOException, ServletException {
//        if (!(request instanceof HttpServletRequest)) {
//            LOGGER.warn("This filter can only process HttpServletRequest requests");
//            throw new ServletException("This filter can only process HttpServletRequest requests");
//        }
//
//        HttpServletRequest httpRequest = (HttpServletRequest) request;
//
//        if (isJSONPRequest(httpRequest)) {
//            ServletOutputStream out = response.getOutputStream();
//
//            out.println(getCallbackMethod(httpRequest) + "(");
//            chain.doFilter(request, response);
//            out.println(");");
//
//            HttpServletResponse res = (HttpServletResponse) response;
//            res.addHeader("Access-Control-Allow-Origin", "*");
//
//            response.setContentType("text/javascript");
//            out.flush();
//
//        } else {
//            chain.doFilter(request, response);
//        }
//    }

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


    /**
     * copy from: org.json, org.json.JSONObject.quote(String string)
     */
    private static String quote(String string) {
        if (string == null || string.length() == 0) {
            return "\"\"";
        }

        char b;
        char c = 0;
        int i;
        int len = string.length();
        StringBuffer sb = new StringBuffer(len + 4);
        String t;

        sb.append('"');
        for (i = 0; i < len; i += 1) {
            b = c;
            c = string.charAt(i);
            switch (c) {
                case '\\':
                case '"':
                    sb.append('\\');
                    sb.append(c);
                    break;
                case '/':
                    if (b == '<') {
                        sb.append('\\');
                    }
                    sb.append(c);
                    break;
                case '\b':
                    sb.append("\\b");
                    break;
                case '\t':
                    sb.append("\\t");
                    break;
                case '\n':
                    sb.append("\\n");
                    break;
                case '\f':
                    sb.append("\\f");
                    break;
                case '\r':
                    sb.append("\\r");
                    break;
                default:
                    if (c < ' ' || (c >= '\u0080' && c < '\u00a0')
                            || (c >= '\u2000' && c < '\u2100')) {
                        t = "000" + Integer.toHexString(c);
                        sb.append("\\u" + t.substring(t.length() - 4));
                    } else {
                        sb.append(c);
                    }
            }
        }
        sb.append('"');
        return sb.toString();
    }
}