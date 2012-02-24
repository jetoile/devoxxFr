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
package fr.soat.devoxx.game.business.listener;

import fr.soat.devoxx.game.business.question.Question;

import javax.inject.Singleton;
import java.util.HashMap;
import java.util.Map;

/**
 * User: khanh
 * Date: 22/02/12
 * Time: 17:33
 */
@Singleton
public class QuestionsNotifier {
    private static QuestionsNotifier instance = null;


    Map<String, IQuestionListener> listeners = new HashMap<String, IQuestionListener>();

    private QuestionsNotifier() {
    }

    public static QuestionsNotifier getInstance() {
        if (instance == null) {
            instance = new QuestionsNotifier();
        }
        return instance;
    }

    public void addListeners(String userName, IQuestionListener listener) {
        this.listeners.put(userName, listener);
    }

    public void removeListener(String userName) {
        this.listeners.remove(userName);
    }

    public void notify(String userName, Question question) {
        IQuestionListener listener = this.listeners.get(userName);
        if (listener != null) {
            listener.callback(question);
        }
    }
}
