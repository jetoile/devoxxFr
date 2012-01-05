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
package fr.soat.devoxx.game.business.admin;

import com.google.common.collect.Lists;
import fr.soat.devoxx.game.admin.pojo.Game;
import fr.soat.devoxx.game.admin.pojo.GameUserDataManager;
import fr.soat.devoxx.game.business.question.QuestionManager;
import fr.soat.devoxx.game.pojo.AllQuestionResponseDto;
import fr.soat.devoxx.game.pojo.QuestionResponseDto;
import fr.soat.devoxx.game.pojo.ResponseRequestDto;
import fr.soat.devoxx.game.pojo.ResponseResponseDto;
import fr.soat.devoxx.game.pojo.question.QuestionType;
import fr.soat.devoxx.game.pojo.question.ResponseType;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;

/**
 * User: khanh
 * Date: 21/12/11
 * Time: 19:34
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest(GameUserDataManager.class)
@PowerMockIgnore("javax.management.*")
public class AdminQuestionServiceTest {
    private AdminQuestionService adminQuestionService;
    private GameUserDataManager gameUserDataManager;

    @Before
    public void setUp() {
        gameUserDataManager = PowerMockito.mock(GameUserDataManager.class);
//        when(gameUserDataManager.getResult())

        QuestionManager questionManager = QuestionManager.INSTANCE;
        questionManager.setConfiguration("question-test.properties");
        adminQuestionService = new AdminQuestionService(gameUserDataManager);
        adminQuestionService.questionManager = questionManager;
    }

    @Test
    public void getQuestionShouldReturnAValidResult() {
        QuestionResponseDto questionDto = adminQuestionService.getQuestion();
        assertNotNull(questionDto);
        assertNotNull(questionDto.getLabel());
        assertFalse(questionDto.getLabel().isEmpty());

        assertNotNull(questionDto.getId());
        assertNotNull(questionDto.getQuestionType());
        assertNotNull(questionDto.getQuestions());

        if (questionDto.getQuestionType() != QuestionType.FREE) {
            assertFalse(questionDto.getQuestions().isEmpty());
        } else {
            assertTrue(questionDto.getQuestions().isEmpty());
        }

    }

    @Test
    public void giveResponseForQuestion1ShouldReturnSuccessWithValidResponse() {
        Game game = new Game();
        game.setId(1);
        game.setType(ResponseType.NEED_RESPONSE);
        
        when(gameUserDataManager.getGameById(anyString(), anyInt())).thenReturn(game);
        
        ResponseRequestDto responseDto = new ResponseRequestDto();
        responseDto.setId(1);
        ArrayList<String> responses = new ArrayList<String>();
        responses.add("toto");
        responseDto.setResponses(responses);
        ResponseResponseDto responseResponseDto = adminQuestionService.giveResponse(responseDto);
        assertNotNull(responseResponseDto);
        assertEquals(ResponseType.SUCCESS, responseResponseDto.getResponseType());
    }

    @Test
    public void giveResponseForQuestion1ShouldReturnFailWithFalseResponse() {
        Game game = new Game();
        game.setId(1);
        game.setType(ResponseType.NEED_RESPONSE);

        when(gameUserDataManager.getGameById(anyString(), anyInt())).thenReturn(game);

        ResponseRequestDto responseDto = new ResponseRequestDto();
        responseDto.setId(1);
        ArrayList<String> responses = new ArrayList<String>();
        responses.add("toto1");
        responseDto.setResponses(responses);
        ResponseResponseDto responseResponseDto = adminQuestionService.giveResponse(responseDto);
        assertNotNull(responseResponseDto);
        assertEquals(ResponseType.FAIL, responseResponseDto.getResponseType());

        responses = new ArrayList<String>();
        responses.add("toto");
        responses.add("tata");
        responseDto.setResponses(responses);
        responseResponseDto = adminQuestionService.giveResponse(responseDto);
        assertNotNull(responseResponseDto);
        assertEquals(ResponseType.FAIL, responseResponseDto.getResponseType());

        responses = new ArrayList<String>();
        responseDto.setResponses(responses);
        responseResponseDto = adminQuestionService.giveResponse(responseDto);
        assertNotNull(responseResponseDto);
        assertEquals(ResponseType.FAIL, responseResponseDto.getResponseType());
    }

    @Test
    public void giveResponseForQuestion1ShouldReturnFailWithInvalidResponse() {
        ResponseRequestDto responseDto = new ResponseRequestDto();
        responseDto.setId(1);
        ArrayList<String> responses = null;
        responseDto.setResponses(responses);
        ResponseResponseDto responseResponseDto = adminQuestionService.giveResponse(responseDto);
        assertNotNull(responseResponseDto);
        assertEquals(ResponseType.INVALID, responseResponseDto.getResponseType());
    }


    @Test
    public void giveResponseForQuestion2ShouldReturnSuccessWithValidResponse() {
        Game game = new Game();
        game.setId(2);
        game.setType(ResponseType.NEED_RESPONSE);

        when(gameUserDataManager.getGameById(anyString(), anyInt())).thenReturn(game);

        ResponseRequestDto responseDto = new ResponseRequestDto();
        responseDto.setId(2);
        ArrayList<String> responses = new ArrayList<String>();
        responses.add("toto");
        responseDto.setResponses(responses);
        ResponseResponseDto responseResponseDto = adminQuestionService.giveResponse(responseDto);
        assertNotNull(responseResponseDto);
        assertEquals(ResponseType.SUCCESS, responseResponseDto.getResponseType());
    }

    @Test
    public void giveResponseForQuestion2ShouldReturnFailWithFalseResponse() {
        Game game = new Game();
        game.setId(2);
        game.setType(ResponseType.NEED_RESPONSE);

        when(gameUserDataManager.getGameById(anyString(), anyInt())).thenReturn(game);

        ResponseRequestDto responseDto = new ResponseRequestDto();
        responseDto.setId(2);
        ArrayList<String> responses = new ArrayList<String>();
        responses.add("toto1");
        responseDto.setResponses(responses);
        ResponseResponseDto responseResponseDto = adminQuestionService.giveResponse(responseDto);
        assertNotNull(responseResponseDto);
        assertEquals(ResponseType.FAIL, responseResponseDto.getResponseType());

        responses = new ArrayList<String>();
        responses.add("toto");
        responses.add("tata");
        responseDto.setResponses(responses);
        responseResponseDto = adminQuestionService.giveResponse(responseDto);
        assertNotNull(responseResponseDto);
        assertEquals(ResponseType.FAIL, responseResponseDto.getResponseType());

        responses = new ArrayList<String>();
        responseDto.setResponses(responses);
        responseResponseDto = adminQuestionService.giveResponse(responseDto);
        assertNotNull(responseResponseDto);
        assertEquals(ResponseType.FAIL, responseResponseDto.getResponseType());
    }


    @Test
    public void giveResponseForQuestion3ShouldReturnSuccessWithValidResponse() {
        Game game = new Game();
        game.setId(3);
        game.setType(ResponseType.NEED_RESPONSE);

        when(gameUserDataManager.getGameById(anyString(), anyInt())).thenReturn(game);

        ResponseRequestDto responseDto = new ResponseRequestDto();
        responseDto.setId(3);
        ArrayList<String> responses = new ArrayList<String>();
        responses.add("toto");
        responses.add("titi");
        responseDto.setResponses(responses);
        ResponseResponseDto responseResponseDto = adminQuestionService.giveResponse(responseDto);
        assertNotNull(responseResponseDto);
        assertEquals(ResponseType.SUCCESS, responseResponseDto.getResponseType());
    }

    @Test
    public void giveResponseForQuestion3ShouldReturnFailWithFalseResponse() {
        Game game = new Game();
        game.setId(3);
        game.setType(ResponseType.NEED_RESPONSE);

        when(gameUserDataManager.getGameById(anyString(), anyInt())).thenReturn(game);

        ResponseRequestDto responseDto = new ResponseRequestDto();
        responseDto.setId(3);
        ArrayList<String> responses = new ArrayList<String>();
        responses.add("toto");
        responseDto.setResponses(responses);
        ResponseResponseDto responseResponseDto = adminQuestionService.giveResponse(responseDto);
        assertNotNull(responseResponseDto);
        assertEquals(ResponseType.FAIL, responseResponseDto.getResponseType());

        responses = new ArrayList<String>();
        responses.add("toto");
        responses.add("tata");
        responseDto.setResponses(responses);
        responseResponseDto = adminQuestionService.giveResponse(responseDto);
        assertNotNull(responseResponseDto);
        assertEquals(ResponseType.FAIL, responseResponseDto.getResponseType());


        responses = new ArrayList<String>();
        responses.add("toto");
        responses.add("titi");
        responses.add("tutu");
        responseDto.setResponses(responses);
        responseResponseDto = adminQuestionService.giveResponse(responseDto);
        assertNotNull(responseResponseDto);
        assertEquals(ResponseType.FAIL, responseResponseDto.getResponseType());

        responses = new ArrayList<String>();
        responseDto.setResponses(responses);
        responseResponseDto = adminQuestionService.giveResponse(responseDto);
        assertNotNull(responseResponseDto);
        assertEquals(ResponseType.FAIL, responseResponseDto.getResponseType());
    }

    @Test
    public void getAllQuestionsShouldReturnOnlyValidType() {
        //given
        Game game1 = new Game();
        game1.setType(ResponseType.NEED_RESPONSE);
        game1.setGivenAnswers(Collections.EMPTY_LIST);
        game1.setId(1);

        Game game2 = new Game();
        game2.setType(ResponseType.FAIL);
        game2.setGivenAnswers(Collections.EMPTY_LIST);
        game2.setId(2);

        Game game3 = new Game();
        game3.setType(ResponseType.SUCCESS);
        game3.setGivenAnswers(Lists.newArrayList("titi", "tutu"));
        game3.setId(3);

        when(gameUserDataManager.getGamesByResultType("toto", ResponseType.NEED_RESPONSE)).thenReturn(Lists.newArrayList(game1));

        //wwhen
        AllQuestionResponseDto results = adminQuestionService.getAllQuestions("toto");

        //then
        assertNotNull(results);
        assertTrue(results.getQuestions().size() == 1);
        assertEquals(results.getQuestions().get(0).getId(), 1);

    }


}
