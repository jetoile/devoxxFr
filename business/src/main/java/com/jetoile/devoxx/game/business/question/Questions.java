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
package com.jetoile.devoxx.game.business.question;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * User: khanh
 * Date: 21/12/11
 * Time: 08:50
 */
public class Questions {

    private final Map<Integer, Question> questions = new HashMap<Integer, Question>();

    public void addQuestion(Question question) {
        this.questions.put(question.getId(), question);
    }

    public int size() {
        return questions.size();
    }

    public Question getQuestionById(int i) {
        Question question = this.questions.get(i);
        if (question != null) {
            return question;
        } else {
            return Question.EMPTY;
        }
    }
    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Question question : questions.values()) {
            sb.append(question.toString() + "\n");
        }
        return sb.toString();
    }

    public Question getRandomQuestion() {
//        int randomIndex = Integer.parseInt(RandomStringUtils.randomNumeric(size()));

        Random rand = new Random();
        int randomIndex = rand.nextInt(size());
        return getQuestionById(randomIndex + 1);
    }
}
