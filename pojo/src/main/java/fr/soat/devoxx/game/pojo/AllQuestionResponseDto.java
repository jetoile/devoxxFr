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

import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlRootElement;

import org.apache.commons.lang.StringUtils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * User: khanh
 * Date: 05/01/12
 * Time: 20:10
 */
@XmlRootElement(name = "allQuestionsReponse")
public class AllQuestionResponseDto {

    private List<QuestionResponseDto> questions = new ArrayList<QuestionResponseDto>();

    public List<QuestionResponseDto> getQuestions() {
        return questions;
    }

    public void setQuestions(List<QuestionResponseDto> questions) {
        this.questions = questions;
    }

    public void addQuestion(QuestionResponseDto question) {
        this.questions.add(question);
    }
    
    /**
	 * Unmarshal Fix
	 * See :
	 * http://stackoverflow.com/questions/4181120/transforming-empty-element-into-null-when-unmarshalling-with-jaxb/4197817#4197817
	 * 
	 * @param aUnmarshaller
	 * @param aParent
	 */
	void afterUnmarshal(Unmarshaller aUnmarshaller, Object aParent) {
		if (questions != null) {
			Iterator<QuestionResponseDto> iterator = questions.iterator();
			while (iterator.hasNext()) {
				QuestionResponseDto questionResponse = iterator.next();
				if (StringUtils.isEmpty(questionResponse.getLabel())) {
					// a QuestionResponseDto without label is considered invalid
					iterator.remove();
				}
			}
		}
	}

    @Override
    public String toString() {
        return "AllQuestionResponseDto{" +
                "questions=" + questions +
                '}';
    }
}
