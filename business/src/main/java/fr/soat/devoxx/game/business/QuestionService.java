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
package fr.soat.devoxx.game.business;

import java.util.List;

import javax.ws.rs.DefaultValue;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sun.jersey.api.json.JSONWithPadding;

import fr.soat.devoxx.game.business.admin.AdminQuestionService;
import fr.soat.devoxx.game.business.types.CustomMediaType;
import fr.soat.devoxx.game.pojo.AllQuestionResponseDto;
import fr.soat.devoxx.game.pojo.QuestionResponseDto;
import fr.soat.devoxx.game.pojo.ResponseRequestDto;
import fr.soat.devoxx.game.pojo.ResponseResponseDto;

/**
 * User: khanh
 * Date: 21/12/11
 * Time: 15:55
 */
@Path("/question")
public class QuestionService {
    private static final Logger LOGGER = LoggerFactory.getLogger(QuestionService.class);

    private AdminQuestionService delegate = new AdminQuestionService();

    @Path("/")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public QuestionResponseDto getQuestion(@QueryParam("jsoncallback") @DefaultValue("fn") String callback) {
        QuestionResponseDto result = delegate.getQuestion();
        //return new JSONWithPadding(result, callback);
        return result;
    }

    @Path("/reply")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public ResponseResponseDto giveResponse(@QueryParam("jsoncallback") @DefaultValue("fn") String callback, 
                                        @FormParam("userName") String userName,
                                        @FormParam("id") Integer questionId,
                                        @FormParam("responses") List responses) {
        //JERSEY-569 - http://java.net/jira/browse/JERSEY-569
        ResponseRequestDto responseDto = new ResponseRequestDto();
        responseDto.setUserName(userName);
        responseDto.setId(questionId);
        responseDto.setResponses(responses);

        ResponseResponseDto result = delegate.giveResponse(responseDto);
        //return new JSONWithPadding(result, callback);
        return result;
    }
    
    @Path("/{username}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public AllQuestionResponseDto getAllQuestions(@QueryParam("jsoncallback") @DefaultValue("fn") String callback, @PathParam("username") String username) {
        AllQuestionResponseDto result = delegate.getAllQuestions(username);
        //return new JSONWithPadding(result, callback);        
        return result;
    }

}
