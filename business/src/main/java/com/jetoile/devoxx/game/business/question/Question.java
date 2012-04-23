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

import com.jetoile.devoxx.game.pojo.question.QuestionType;
import org.apache.commons.lang.StringUtils;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * User: khanh
 * Date: 21/12/11
 * Time: 08:51
 */
@XmlRootElement(name = "question")
public class Question {

    public final static Question EMPTY = new EmptyQuestion();

    private int id;
    
    private QuestionType questionType;
    
    private String label;
    
    private List<String> questions = new ArrayList<String>();
    
    private List<String> answers = new ArrayList<String>();

    public int getId() {

        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public QuestionType getQuestionType() {
        return questionType;
    }

    public void setQuestionType(QuestionType questionType) {
        this.questionType = questionType;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public List<String> getQuestions() {
        return questions;
    }

    public void setQuestions(List<String> questions) {
        this.questions = questions;
    }

    public List<String> getAnswers() {
        return answers;
    }

    public void setAnswers(List<String> answers) {
        this.answers = answers;
    }

    @Override
    public String toString() {
        return "Question{" +
                "id=" + id +
                ", questionType=" + questionType +
                ", label='" + label + '\'' +
                ", questions=" + questions +
                ", answers=" + answers +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Question question = (Question) o;

        if (id != question.id) return false;
        if (answers != null ? !answers.equals(question.answers) : question.answers != null) return false;
        if (label != null ? !label.equals(question.label) : question.label != null) return false;
        if (questionType != question.questionType) return false;
        if (questions != null ? !questions.equals(question.questions) : question.questions != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (questionType != null ? questionType.hashCode() : 0);
        result = 31 * result + (label != null ? label.hashCode() : 0);
        result = 31 * result + (questions != null ? questions.hashCode() : 0);
        result = 31 * result + (answers != null ? answers.hashCode() : 0);
        return result;
    }

    private static class EmptyQuestion extends Question {
        @Override
        public int getId() {
            return 0;
        }

        @Override
        public String getLabel() {
            return StringUtils.EMPTY;
        }

        @Override
        public List<String> getQuestions() {
            return Collections.EMPTY_LIST;
        }

        @Override
        public List<String> getAnswers() {
            return Collections.EMPTY_LIST;
        }
    }
}
