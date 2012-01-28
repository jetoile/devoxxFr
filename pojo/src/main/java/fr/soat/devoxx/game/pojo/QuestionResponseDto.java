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
package fr.soat.devoxx.game.pojo;

import fr.soat.devoxx.game.pojo.question.QuestionType;

import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.List;

/**
 * User: khanh
 * Date: 21/12/11
 * Time: 16:42
 */
@XmlRootElement(name = "questionReponse")
public class QuestionResponseDto implements Serializable {

    private int id;

    private QuestionType questionType;

    private String label;

    private List<String> questions;

    public int getId() {

        return id;
    }

    public QuestionType getQuestionType() {
        return questionType;
    }

    public String getLabel() {
        return label;
    }

    public List<String> getQuestions() {
        return questions;
    }

    @Override
    public String toString() {
        return "Question{" +
                "id=" + id +
                ", questionType=" + questionType +
                ", label='" + label + '\'' +
                ", questions=" + questions +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        QuestionResponseDto questionDto = (QuestionResponseDto) o;

        if (id != questionDto.id) return false;
        if (label != null ? !label.equals(questionDto.label) : questionDto.label != null) return false;
        if (questionType != questionDto.questionType) return false;
        if (questions != null ? !questions.equals(questionDto.questions) : questionDto.questions != null) return false;

        return true;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setQuestionType(QuestionType questionType) {
        this.questionType = questionType;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public void setQuestions(List<String> questions) {
        this.questions = questions;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (questionType != null ? questionType.hashCode() : 0);
        result = 31 * result + (label != null ? label.hashCode() : 0);
        result = 31 * result + (questions != null ? questions.hashCode() : 0);
        return result;
    }

}
