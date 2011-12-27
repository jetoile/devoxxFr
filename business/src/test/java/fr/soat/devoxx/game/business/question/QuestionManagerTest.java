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
package fr.soat.devoxx.game.business.question;

import static org.junit.Assert.*;

import fr.soat.devoxx.game.pojo.question.QuestionType;
import org.junit.Test;

/**
 * User: khanh
 * Date: 21/12/11
 * Time: 14:44
 */
public class QuestionManagerTest {
    @Test
    public void getNbQuestionsShouldReturn5() throws Exception {
        QuestionManager instance = QuestionManager.INSTANCE;
        instance.setConfiguration("question-test.properties");
        assertTrue(instance.getNbQuestions() == 6);
    }

    @Test
    public void getNbQuestionsShouldReturnDefaultValueIfFileIsNotFound() throws Exception {
        QuestionManager instance = QuestionManager.INSTANCE;
        instance.setConfiguration("question-test-unknown.properties");
        assertTrue(instance.getNbQuestions() == 0);
    }

    @Test
    public void getNbQuestionsShouldReturnDefaultValueIfKeyIsNotFound() throws Exception {
        QuestionManager instance = QuestionManager.INSTANCE;
        instance.setConfiguration("question-test2.properties");
        assertTrue(instance.getNbQuestions() == 0);
    }

    @Test
    public void getLoadQuestionsShouldReturnRightQuestions() throws Exception {
        QuestionManager instance = QuestionManager.INSTANCE;
        instance.setConfiguration("question-test.properties");
        Questions questions = instance.loadQuestions();

        assertTrue(questions.size() == 5);
        assertTrue(questions.getQuestionById(1).getId() == 1);
        assertTrue(questions.getQuestionById(1).getLabel().equals("Test?"));
        assertTrue(questions.getQuestionById(1).getQuestionType() == QuestionType.FREE);
        assertTrue(questions.getQuestionById(1).getQuestions().size() == 0);
        assertTrue(questions.getQuestionById(1).getAnswers().get(0).equals("toto"));

        assertTrue(questions.getQuestionById(2).getId() == 2);
        assertTrue(questions.getQuestionById(2).getLabel().equals("Test2?"));
        assertTrue(questions.getQuestionById(2).getQuestionType() == QuestionType.MULTIPLE_CHOICE);
        assertTrue(questions.getQuestionById(2).getQuestions().size() == 2);
        assertTrue(questions.getQuestionById(2).getAnswers().get(0).equals("toto"));

        assertTrue(questions.getQuestionById(3).getId() == 3);
        assertTrue(questions.getQuestionById(3).getLabel().equals("Test3?"));
        assertTrue(questions.getQuestionById(3).getQuestionType() == QuestionType.MULTIPLE_CHOICE);
        assertTrue(questions.getQuestionById(3).getQuestions().size() == 3);
        assertTrue(questions.getQuestionById(3).getAnswers().size() == 2);
        assertTrue(questions.getQuestionById(3).getAnswers().get(0).equals("toto"));
        assertTrue(questions.getQuestionById(3).getAnswers().get(1).equals("titi"));

        assertTrue(questions.getQuestionById(5).getId() == 5);
        assertTrue(questions.getQuestionById(5).getLabel().equals("Test5?"));
        assertTrue(questions.getQuestionById(5).getQuestionType() == QuestionType.MULTIPLE_SINGLE_CHOICE);
        assertTrue(questions.getQuestionById(5).getQuestions().size() == 4);
        assertTrue(questions.getQuestionById(5).getQuestions().get(0).equals("toto"));
        assertTrue(questions.getQuestionById(5).getQuestions().get(3).equals("tyty"));
        assertTrue(questions.getQuestionById(5).getAnswers().size() == 1);
        assertTrue(questions.getQuestionById(5).getAnswers().get(0).equals("toto"));
    }
}
