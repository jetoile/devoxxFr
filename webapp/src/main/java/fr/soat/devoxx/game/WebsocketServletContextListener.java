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
package fr.soat.devoxx.game;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.DefaultHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.net.InetAddress;

/**
 * User: khanh
 * Date: 15/02/12
 * Time: 12:18
 */
public class WebsocketServletContextListener implements ServletContextListener {

    private static final Logger LOGGER = LoggerFactory.getLogger(WebsocketServletContextListener.class);

    @Inject
    QuestionsNotifier questionsNotifier;

    private Server server = null;

    /**
     * Start Embedding Jetty server when WEB Application is started.
     *
     */
    public void contextInitialized(ServletContextEvent event) {
        try {
            // 1) Create a Jetty server with the 8081 port.
            this.server = new Server(8081);
            // 2) Register ChatWebSocketHandler in the Jetty server instance.
            WebsocketRequestHandler webSocketHandler = new WebsocketRequestHandler(questionsNotifier);
            webSocketHandler.setHandler(new DefaultHandler());
            server.setHandler(webSocketHandler);
            // 2) Start the Jetty server.
            server.start();
        } catch (Throwable e) {
            LOGGER.error("unable to create jetty server for websocket handling: {}", e);
        }
    }

    /**
     * Stop Embedding Jetty server when WEB Application is stopped.
     */
    public void contextDestroyed(ServletContextEvent event) {
        if (server != null) {
            try {
                // stop the Jetty server.
                server.stop();
            } catch (Exception e) {
                LOGGER.error("unable to destroy jetty server for websocket handling: {}", e);
            }
        }
    }

}


