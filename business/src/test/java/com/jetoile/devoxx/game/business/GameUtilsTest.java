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
package com.jetoile.devoxx.game.business;

import com.jetoile.devoxx.game.business.GameUtils;

import static org.junit.Assert.assertEquals;

import org.junit.Test;


/**
 * User: khanh
 * Date: 19/12/11
 * Time: 11:58
 */
public class GameUtilsTest {
	
	private static final String GAME_QUESTION_FILE_PATH_TEST="question.properties";

	@Test
    public void getQuestionFilePathShouldReturnAnEmptySizeIfPropertiesAreNotFound() {
		GameUtils propertiesUtils = GameUtils.INSTANCE;
        propertiesUtils.setConfiguration("nothing");
        assertEquals(GAME_QUESTION_FILE_PATH_TEST, propertiesUtils.getQuestionFilePath());
    }

    @Test
    public void getQuestionFilePathReturnSizeIfPropertiesAreFound() {
    	GameUtils propertiesUtils = GameUtils.INSTANCE;
        propertiesUtils.reload();
        assertEquals(GAME_QUESTION_FILE_PATH_TEST, propertiesUtils.getQuestionFilePath());
    }
	
}
