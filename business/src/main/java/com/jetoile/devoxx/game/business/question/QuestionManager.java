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

import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import com.jetoile.devoxx.game.business.GameUtils;
import com.jetoile.devoxx.game.pojo.question.QuestionType;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

/**
 * User: khanh
 * Date: 21/12/11
 * Time: 08:41
 */
public enum QuestionManager {
    INSTANCE;

    private static final Logger LOGGER = LoggerFactory.getLogger(QuestionManager.class);
    private static final int NB_QUESTIONS_DEFAULT = 0;
    public static final String QUESTION_LABEL = ".question.label";
    public static final String SEPARATOR = ";";

    private final String QUESTION_FILE_PATH = GameUtils.INSTANCE.getQuestionFilePath();

    private PropertiesConfiguration configuration;

    private Questions questions = null;

    private QuestionManager() {
        try {
            this.configuration = new PropertiesConfiguration(QUESTION_FILE_PATH);
        } catch (ConfigurationException e) {
            //NOTHING TO DO
        }
    }

    public void reload() {
        setConfiguration(QUESTION_FILE_PATH);
        this.questions = null;
    }

    //TODO : allegement des contraintes de visibilités pour permettre le test
    public void setConfiguration(String fileName) {
        this.configuration.clear();
        this.questions = null;
        try {
            this.configuration.load(fileName);
        } catch (ConfigurationException e) {
            //NOTHING TO DO
        }
    }

    public int getNbQuestions() {
        return this.configuration.getInt("nb.questions", NB_QUESTIONS_DEFAULT);
    }

    public Questions loadQuestions() {
        if (this.questions == null) {
        int nbQuestions = getNbQuestions();

        Questions result = new Questions();
        addQuestions(nbQuestions, result);
        questions = result;
        }
        return questions;
    }

    private void addQuestions(int nbQuestions, Questions result) {
        Question question;
        for (int i = 1; i <= nbQuestions; i++) {
            try {
                question = new Question();
                question.setId(i);
                question.setLabel(getQuestionLabel(i));
                question.setQuestionType(getQuestionType(i));
                question.setQuestions(getQuestionQuestions(question, i));
                question.setAnswers(getQuestionAnswers(i));
                if (question.getQuestionType() == QuestionType.MULTIPLE_SINGLE_CHOICE && question.getAnswers().size() != 1) {
                    throw new InvalidQuestionException("multiple single choice should have an only answer");
                }
            } catch (NoSuchElementException e) {
                LOGGER.error("error while reading question: {}", e.getMessage());
                continue;
            } catch (InvalidQuestionException e) {
                LOGGER.error("error while reading question: {}", e.getMessage());
                continue;
            }
            result.addQuestion(question);
        }
    }

    private ArrayList<String> getQuestionAnswers(int i) {
        String answers = this.configuration.getString(i + ".question.answer");
        Iterable<String> answersList = Splitter.on(SEPARATOR).trimResults().omitEmptyStrings().split(answers);
        return Lists.newArrayList(answersList);
    }

    private List<String> getQuestionQuestions(Question question, int i) {
        if (question.getQuestionType() != QuestionType.FREE) {
            String questions = this.configuration.getString(i + ".question.question");
            Iterable<String> questionsList = Splitter.on(SEPARATOR).trimResults().omitEmptyStrings().split(questions);
            return Lists.newArrayList(questionsList);
        } else {
            return Collections.EMPTY_LIST;
        }
    }

    private String getQuestionLabel(int i) {
        return this.configuration.getString(i + QUESTION_LABEL);
    }

    private QuestionType getQuestionType(int i) throws InvalidQuestionException {
        String type = this.configuration.getString(i + ".question.type");
        if (QuestionType.FREE.name().equals(type)) {
            return QuestionType.FREE;
        } else if (QuestionType.MULTIPLE_CHOICE.name().equals(type)) {
            return QuestionType.MULTIPLE_CHOICE;
        } else if (QuestionType.MULTIPLE_SINGLE_CHOICE.name().equals(type)) {
            return QuestionType.MULTIPLE_SINGLE_CHOICE;
        } else {
            throw new InvalidQuestionException("wrong type");
        }

//        switch (type) {
//            case "FREE" :
//            return QuestionType.FREE;
//        case "MULTIPLE_CHOICE" :
//            return QuestionType.MULTIPLE_CHOICE;
//        case "MULTIPLE_SINGLE_CHOICE" :
//            return QuestionType.MULTIPLE_SINGLE_CHOICE;
//        default:
//            throw new InvalidQuestionException("wrong type");
//        }
    }

    private class InvalidQuestionException extends Exception {
        InvalidQuestionException(String cause) {
            super(cause);
        }
    }

}
