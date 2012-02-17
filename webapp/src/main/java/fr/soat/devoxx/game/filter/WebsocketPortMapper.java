/*
 * Copyright (c) 2011 Khanh Tuong Maudoux <kmx.petals@gmail.com>
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 * 1. Redistributions of source code must retain the above copyright
 *    notice, this list of conditions and the following disclaimer.
 * 2. The name of the author may not be used to endorse or promote products
 *    derived from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE AUTHOR ``AS IS'' AND ANY EXPRESS OR
 * IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES
 * OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED.
 * IN NO EVENT SHALL THE AUTHOR BE LIABLE FOR ANY DIRECT, INDIRECT,
 * INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT
 * NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,
 * DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY
 * THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF
 * THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package fr.soat.devoxx.game.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;

/**
 * User: khanh
 * Date: 16/02/12
 * Time: 16:03
 */
public class WebsocketPortMapper implements Filter {

    private final static Logger LOGGER = LoggerFactory.getLogger(WebsocketPortMapper.class);

    private Socket socket;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        try {
            socket = new Socket("localhost", 8081);
        } catch (IOException e) {
            LOGGER.error("unable to create the socket adapter: {}", e);
        }
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
//        PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

//        PrintWriter writer = servletResponse.getWriter();
//        writer.print(out);

        ServletInputStream inputStream = servletRequest.getInputStream();

        byte[] buffer = new byte[2048];
        int nRead;

        OutputStream socketOutputStream = socket.getOutputStream();


        while ((nRead = inputStream.read(buffer, 0, buffer.length)) != -1) {
            socketOutputStream.write(buffer, 0, nRead);
            System.out.println("write");

        }
        buffer = new byte[2048];
        ServletOutputStream servletOutputStream = servletResponse.getOutputStream();

        while ((nRead = socket.getInputStream().read(buffer, 0, buffer.length)) != -1) {
            servletOutputStream.write(buffer, 0, nRead);
            System.out.println("lllll");
        }

        servletOutputStream.flush();
        socketOutputStream.flush();


    }

    @Override
    public void destroy() {
        try {
            socket.close();
        } catch (IOException e) {
            LOGGER.error("unable to close the socket adapter: {}", e);
        }
    }
}
