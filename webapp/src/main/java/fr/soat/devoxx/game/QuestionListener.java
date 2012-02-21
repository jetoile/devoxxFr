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

import fr.soat.devoxx.game.business.listener.IQuestionListener;
import fr.soat.devoxx.game.business.question.Question;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.AsyncContext;
import javax.servlet.AsyncEvent;
import javax.servlet.AsyncListener;
import java.io.IOException;
import java.io.Writer;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * User: khanh
 * Date: 17/02/12
 * Time: 16:41
 */
public class QuestionListener extends Thread implements IQuestionListener, AsyncListener {

    private static final Logger LOGGER = LoggerFactory.getLogger(QuestionListener.class);

    AsyncContext asyncContext;

    @Override
    public BlockingQueue<Question> getQueue() {
        return queue;
    }

    private final BlockingQueue<Question> queue = new ArrayBlockingQueue<Question>(800);

    public QuestionListener(AsyncContext asyncContext) {
        this.asyncContext = asyncContext;
        this.asyncContext.addListener(this);
    }

    @Override
    public void callback(Question question) {
        Writer out = null;
        try {
            out = this.asyncContext.getResponse().getWriter();
            out.append(question.toString());

            out.flush();
            out.close();

        } catch (IOException e) {
            LOGGER.error("{}", e);
        }
    }


    @Override
    public void onComplete(AsyncEvent event) throws IOException {
        LOGGER.error("============================== onComplete {}", event);

    }

    @Override
    public void onTimeout(AsyncEvent event) throws IOException {
        LOGGER.error("============================== onTimeout {}", event);

    }

    @Override
    public void onError(AsyncEvent event) throws IOException {
        LOGGER.error("============================== onError {}", event);

    }

    @Override
    public void onStartAsync(AsyncEvent event) throws IOException {
        LOGGER.error("============================== onStartAsync {}", event);

    }


    @Override
    public void run() {
//        while (true) {
            Question question = null;
            try {
                question = queue.take();
                callback(question);
            } catch (InterruptedException e) {
            }
//        }
    }
}
