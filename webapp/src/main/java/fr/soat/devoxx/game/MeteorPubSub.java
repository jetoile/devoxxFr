/*
 * Copyright 2011 Jeanfrancois Arcand
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package fr.soat.devoxx.game;

import fr.soat.devoxx.game.business.listener.IQuestionListener;
import fr.soat.devoxx.game.business.listener.QuestionsNotifier;
import org.atmosphere.cpr.*;
import org.atmosphere.websocket.WebSocketEventListenerAdapter;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Writer;

/**
 * Simple PubSub resource that demonstrate many functionality supported by
 * Atmosphere JQuery Plugin and Atmosphere Meteor extension.
 *
 * @author Jeanfrancois Arcand
 */
public class MeteorPubSub extends HttpServlet {


    //    @Inject
    QuestionsNotifier questionsNotifier = QuestionsNotifier.getInstance();

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException {
        // Create a Meteor
        Meteor meteor = Meteor.build(req);

        // Log all events on the console, including WebSocket events.
        meteor.addListener(new WebSocketEventListenerAdapter());

        res.setContentType("text/html;charset=ISO-8859-1");

//        Broadcaster broadcaster = lookupBroadcaster(req.getPathInfo());
//        meteor.setBroadcaster(broadcaster);

        String userName = req.getParameter("userName");
        if (userName == null) {
            Writer out = res.getWriter();
            out.write("invalid user");
            out.flush();
            out.close();
            meteor.destroy();
        } else {
            IQuestionListener questionListener = new QuestionListener(meteor);
            questionsNotifier.addListeners(userName, questionListener);

//            if (req.getHeader(HeaderConfig.X_ATMOSPHERE_TRANSPORT).equalsIgnoreCase(HeaderConfig.LONG_POLLING_TRANSPORT)) {
//                req.setAttribute(ApplicationConfig.RESUME_ON_BROADCAST, Boolean.TRUE);
//                meteor.suspend(-1, false);
//            } else {
                meteor.suspend(-1);
//            }
        }

    }

//    public void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException {
//        Broadcaster broadcaster = lookupBroadcaster(req.getPathInfo());
//
//        String message = req.getReader().readLine();
//
//        if (message != null && message.indexOf("message") != -1) {
//            // We could also have looked up the Broadcaster using the Meteor
//            // m.getBroadcaster().broadcast(message.substring("message=".length()));
//            broadcaster.broadcast(message.substring("message=".length()));
//        }
//    }

//    /**
//     * Retrieve the {@link org.atmosphere.cpr.Broadcaster} based on the request's path info.
//     *
//     * @param pathInfo
//     * @return the {@link org.atmosphere.cpr.Broadcaster} based on the request's path info.
//     */
//    Broadcaster lookupBroadcaster(String pathInfo) {
//        String[] decodedPath = pathInfo.split("/");
//        Broadcaster b = BroadcasterFactory.getDefault().lookup(decodedPath[decodedPath.length - 1], true);
//        return b;
//    }
}
