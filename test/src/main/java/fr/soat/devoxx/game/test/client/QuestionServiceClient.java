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
package fr.soat.devoxx.game.test.client;

import java.net.URI;
import java.util.Collections;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriBuilder;

import com.google.common.collect.Lists;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.api.json.JSONWithPadding;
import fr.soat.devoxx.game.pojo.*;

/**
 * User: khanh
 * Date: 23/12/11
 * Time: 11:34
 */
public class QuestionServiceClient {

    public static void main(String[] args) {
        QuestionServiceClient client = new QuestionServiceClient();
        System.out.println("is creating an user");
        UserResponseDto res = client.testCreateUser();
        System.out.println("RESULT: is creating an user: " + res);

        System.out.println("is asking for a question");
        QuestionResponseDto responseDto = client.testGetQuestion();
        System.out.println("RESULT: is creating an user: " + responseDto);

        System.out.println("is responding to a question");
        ResponseResponseDto resp = client.testGetReplyForQuestion1();
        System.out.println("RESULT: is responding to a question: " + resp);

        System.out.println("is responding to a question");
        resp = client.testGetReplyForQuestion3();
        System.out.println("RESULT: is responding to a question: " + resp);

        System.out.println("is responding to a question");
        resp = client.testGetReplyForQuestion3WithFalseResponse();
        System.out.println("RESULT: is responding to a question: " + resp);

        System.out.println("is responding to a question");
        resp = client.testGetReplyForQuestion3WithInvalidResponse();
        System.out.println("RESULT: is responding to a question: " + resp);


        System.out.println("is getting results");
        System.out.println("RESULT: is responding to a question: " + client.testGetResult());

//        System.out.println("is deleting the user");
//        client.testDeleteUser();
    }


    private static URI getBaseURI() {
//        return UriBuilder.fromUri("http://localhost:9090/").build();
        return UriBuilder.fromUri("http://localhost:8080/webapp-1.0.0-SNAPSHOT/").build();
//        return UriBuilder.fromUri("http://devoxxfr.jetoile.cloudbees.net/").build();
    }

    
    public ResponseResponseDto testGetReplyForQuestion1() {
        ResponseRequestDto requestDto = new ResponseRequestDto();
        requestDto.setUserName("toto");
        requestDto.setId(1);
        requestDto.setResponses(Lists.newArrayList("toto"));

        ClientConfig config = new DefaultClientConfig();
        Client client = Client.create(config);
        WebResource service = client.resource(getBaseURI());
        ResponseResponseDto res = service.path("services").path("/admin/question/reply").type(MediaType.APPLICATION_JSON).post(ResponseResponseDto.class, requestDto);
        return res;
    }


    public ResponseResponseDto testGetReplyForQuestion3() {
        ResponseRequestDto requestDto = new ResponseRequestDto();
        requestDto.setUserName("toto");
        requestDto.setId(3);
        requestDto.setResponses(Lists.newArrayList("toto", "titi"));

        ClientConfig config = new DefaultClientConfig();
        Client client = Client.create(config);
        WebResource service = client.resource(getBaseURI());
        ResponseResponseDto res = service.path("services").path("/admin/question/reply").type(MediaType.APPLICATION_JSON).post(ResponseResponseDto.class, requestDto);
        return res;
    }

    public ResponseResponseDto testGetReplyForQuestion3WithFalseResponse() {
        ResponseRequestDto requestDto = new ResponseRequestDto();
        requestDto.setUserName("toto");
        requestDto.setId(3);
        requestDto.setResponses(Lists.newArrayList("toto", "titi", "tutu"));

        ClientConfig config = new DefaultClientConfig();
        Client client = Client.create(config);
        WebResource service = client.resource(getBaseURI());
        ResponseResponseDto res = service.path("services").path("/admin/question/reply").type(MediaType.APPLICATION_JSON).post(ResponseResponseDto.class, requestDto);
        return res;
    }

    public ResponseResponseDto testGetReplyForQuestion3WithInvalidResponse() {
        ResponseRequestDto requestDto = new ResponseRequestDto();
        requestDto.setUserName("toto");
        requestDto.setId(3);
        requestDto.setResponses(Collections.EMPTY_LIST);

        ClientConfig config = new DefaultClientConfig();
        Client client = Client.create(config);
        WebResource service = client.resource(getBaseURI());
        ResponseResponseDto res = service.path("services").path("/admin/question/reply").type(MediaType.APPLICATION_JSON).post(ResponseResponseDto.class, requestDto);
        return res;
    }
    
    public UserResponseDto testCreateUser() {
        UserRequestDto requestDto = new UserRequestDto();
        requestDto.setName("toto");
        requestDto.setMail("toto@gmail.com");

        ClientConfig config = new DefaultClientConfig();
        Client client = Client.create(config);
        WebResource service = client.resource(getBaseURI());
        UserResponseDto res = service.path("services").path("/admin/user/user").type(MediaType.APPLICATION_JSON).post(UserResponseDto.class, requestDto);
        return  res;
    }

    public QuestionResponseDto testGetQuestion() {
//        UserRequestDto requestDto = new UserRequestDto();
//        requestDto.setName("toto");
//        requestDto.setMail("toto@gmail.com");

        ClientConfig config = new DefaultClientConfig();
        Client client = Client.create(config);
        WebResource service = client.resource(getBaseURI());
        QuestionResponseDto res = service.path("services").path("/admin/question/question").type(MediaType.APPLICATION_JSON).get(QuestionResponseDto.class);
        return res;
    }

    public void testDeleteUser() {
//        ResponseRequestDto requestDto = new ResponseRequestDto();
//        requestDto.setId(1);
//        requestDto.setResponses(Lists.newArrayList("toto"));

        ClientConfig config = new DefaultClientConfig();
        Client client = Client.create(config);
        WebResource service = client.resource(getBaseURI());
        service.path("services").path("/user/user/toto").delete();
    }

    public ResultResponseDto testGetResult() {
        ClientConfig config = new DefaultClientConfig();
        Client client = Client.create(config);
        WebResource service = client.resource(getBaseURI());
        ResultResponseDto res = service.path("services").path("/admin/result/result/toto").type(MediaType.APPLICATION_JSON).get(ResultResponseDto.class);
//        JSONWithPadding res = service.path("services").path("/result/result/toto").type(MediaType.APPLICATION_JSON).get(JSONWithPadding.class);
        return res;
    }

}
