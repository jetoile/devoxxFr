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

import org.eclipse.jetty.websocket.WebSocket;
import org.eclipse.jetty.websocket.WebSocketHandler;

import javax.servlet.http.HttpServletRequest;

/**
 * User: khanh
 * Date: 15/02/12
 * Time: 12:42
 */
public class WebsocketRequestHandler extends WebSocketHandler {

    QuestionsNotifier questionsNotifier;

    public WebsocketRequestHandler(QuestionsNotifier questionsNotifier) {
        this.questionsNotifier = questionsNotifier;
    }

    public WebSocket doWebSocketConnect(HttpServletRequest request,
                                        String protocol) {
        return new RequestWebSocket();
    }

    private class RequestWebSocket implements WebSocket.OnTextMessage {

        private Connection connection;

        @Override
        public void onOpen(Connection connection) {
            // Client (Browser) WebSockets has opened a connection.
            // 1) Store the opened connection
             this.connection = connection;
        }

        @Override
        public void onMessage(String data) {

            QuestionListener questionListener = new QuestionListener(this.connection);
            String userName = data;

            questionsNotifier.addListener(userName, questionListener);

//            questionsNotifier.addListener(userName, questionListener);


//            // Loop for each instance of RequestWebSocket to send message server to
//            // each client WebSockets.
//            try {
//                for (RequestWebSocket webSocket : webSockets) {
//                    // send a message to the current client WebSocket.
//                    webSocket.connection.sendMessage(data);
//                }
//            } catch (IOException x) {
//                // Error was detected, close the RequestWebSocket client side
//                this.connection.disconnect();
//            }
//
        }

        @Override
        public void onClose(int closeCode, String message) {
            // Remove RequestWebSocket in the global list of RequestWebSocket instance.
//            String userName = "titi2";
//            questionsNotifier.removeListener(userName);
        }
    }
}

