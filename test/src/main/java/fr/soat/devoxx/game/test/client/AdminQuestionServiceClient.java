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

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import fr.soat.devoxx.game.pojo.AllQuestionResponseDto;
import fr.soat.devoxx.game.pojo.QuestionResponseDto;
import fr.soat.devoxx.game.pojo.UserRequestDto;
import fr.soat.devoxx.game.pojo.UserResponseDto;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriBuilder;
import java.net.URI;

/**
 * User: khanh
 * Date: 05/01/12
 * Time: 20:18
 */
public class AdminQuestionServiceClient {

	public static final String BASE_URI = "http://devoxxfrjee.jetoile.cloudbees.net/";
//	public static final String BASE_URI = "http://devoxxfr.jetoile.cloudbees.net/";
//    public static final String BASE_URI = "http://localhost:8080/webapp-1.0.1-SNAPSHOT/";
    public static final String SERVICE_PATH = "services";
    public static final String TEST_USERNAME = "toto";

    public static void main(String[] args) {
        AdminQuestionServiceClient client = new AdminQuestionServiceClient();

        System.out.println("is deleting the user");
        client.testDeleteUser();

        System.out.println("is creating an user");
        UserResponseDto res = client.testCreateUser();
        System.out.println("RESULT: is creating an user: " + res);

        System.out.println("is creating a question");
        client.testCreateForQuestionForUser();

        System.out.println("is asking for a question");
        System.out.println("RESULT: is creating an user: " + client.testGetAllQuestions());
    }

    private static URI getBaseURI() {
//        return UriBuilder.fromUri("http://localhost:9090/").build();
//        return UriBuilder.fromUri("http://localhost:8080/webapp-1.0.1-SNAPSHOT/").build();
//        return UriBuilder.fromUri("http://devoxxfr.jetoile.cloudbees.net/").build();
//        return UriBuilder.fromUri("http://devoxxfrjee.jetoile.cloudbees.net/").build();
        return UriBuilder.fromUri(BASE_URI).build();

    }


    public void testCreateForQuestionForUser() {
        ClientConfig config = new DefaultClientConfig();
        Client client = Client.create(config);
        WebResource service = client.resource(getBaseURI());
//        service.path("services").path("/admin/question/create/toto").type(MediaType.APPLICATION_JSON).put();
        service.path(SERVICE_PATH).path("/admin/question/"+TEST_USERNAME+"/create").type(MediaType.APPLICATION_JSON).put();

    }


    public UserResponseDto testCreateUser() {
        UserRequestDto requestDto = new UserRequestDto();
        requestDto.setName("toto");
        requestDto.setMail("toto@gmail.com");

        ClientConfig config = new DefaultClientConfig();
        Client client = Client.create(config);
        WebResource service = client.resource(getBaseURI());
//        UserResponseDto res = service.path("services").path("/admin/user/user").type(MediaType.APPLICATION_JSON).post(UserResponseDto.class, requestDto);
        UserResponseDto res = service.path(SERVICE_PATH).path("/admin/user").type(MediaType.APPLICATION_JSON).post(UserResponseDto.class, requestDto);

        return  res;
    }

    public AllQuestionResponseDto testGetAllQuestions() {
//        UserRequestDto requestDto = new UserRequestDto();
//        requestDto.setName("toto");
//        requestDto.setMail("toto@gmail.com");

        ClientConfig config = new DefaultClientConfig();
        Client client = Client.create(config);
        WebResource service = client.resource(getBaseURI());
//        AllQuestionResponseDto res = service.path("services").path("/admin/question/allQuestions/toto").type(MediaType.APPLICATION_JSON).get(AllQuestionResponseDto.class);
        AllQuestionResponseDto res = service.path(SERVICE_PATH).path("/admin/question/"+TEST_USERNAME).type(MediaType.APPLICATION_JSON).get(AllQuestionResponseDto.class);

        return res;
    }


    public void testDeleteUser() {
//        ResponseRequestDto requestDto = new ResponseRequestDto();
//        requestDto.setId(1);
//        requestDto.setResponses(Lists.newArrayList("toto"));

        ClientConfig config = new DefaultClientConfig();
        Client client = Client.create(config);
        WebResource service = client.resource(getBaseURI());
//        service.path("services").path("/admin/user/user/toto").delete();
        service.path(SERVICE_PATH).path("/admin/user/"+TEST_USERNAME).delete();

    }

}
