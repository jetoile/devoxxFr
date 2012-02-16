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

import fr.soat.devoxx.game.QuestionsNotifier;
import fr.soat.devoxx.game.admin.pojo.Game;
import fr.soat.devoxx.game.admin.pojo.GameUserDataManager;
import fr.soat.devoxx.game.admin.pojo.dto.QuestionRequestDto;
import fr.soat.devoxx.game.admin.pojo.exception.StorageException;
import fr.soat.devoxx.game.business.question.Question;
import fr.soat.devoxx.game.business.question.QuestionManager;
import fr.soat.devoxx.game.business.question.Response;
import fr.soat.devoxx.game.pojo.AllQuestionResponseDto;
import fr.soat.devoxx.game.pojo.QuestionResponseDto;
import fr.soat.devoxx.game.pojo.ResponseRequestDto;
import fr.soat.devoxx.game.pojo.ResponseResponseDto;
import fr.soat.devoxx.game.pojo.question.ResponseType;
import org.apache.commons.lang.StringUtils;
import org.dozer.DozerBeanMapper;
import org.dozer.Mapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;
import java.util.Random;
import java.util.Set;

/**
 * User: khanh
 * Date: 21/12/11
 * Time: 15:55
 */
@Path("/admin/question")
public class AdminQuestionService {
    private static final Logger LOGGER = LoggerFactory.getLogger(AdminQuestionService.class);

    private static Random RANDOM_GENERATOR = new Random();

    private final Mapper dozerMapper = new DozerBeanMapper();

    @Inject
    QuestionsNotifier questionsNotifier;

    @Inject
    QuestionManager questionManager;

    @Inject
    GameUserDataManager gameUserDataManager;

    private final Validator validator;

    {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    public AdminQuestionService() {
    }

    AdminQuestionService(GameUserDataManager gameUserDataManager) {
        this.gameUserDataManager = gameUserDataManager;
    }

    @Path("/")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public QuestionResponseDto getQuestion() {
        return dozerMapper.map(questionManager.loadQuestions().getRandomQuestion(), QuestionResponseDto.class);
    }

    @Path("/")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public javax.ws.rs.core.Response addQuestion(QuestionRequestDto questionRequest) {
        //TODO add question processing
        return javax.ws.rs.core.Response.ok().build();
    }

    @Path("/{questionId}")
    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    public javax.ws.rs.core.Response updateQuestion(@PathParam("questionId") Integer questionId, QuestionRequestDto questionRequest) {
        //TODO update question processing
        return javax.ws.rs.core.Response.ok().build();
    }

    @Path("/{questionId}")
    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    public javax.ws.rs.core.Response updateQuestion(@PathParam("questionId") Integer questionId) {
        //TODO delete question processing
        return javax.ws.rs.core.Response.ok().build();
    }

    @Path("/{username}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public AllQuestionResponseDto getAllQuestions(@PathParam("username") String username) {
        List<Game> games = gameUserDataManager.getGamesByResultType(username, ResponseType.NEED_RESPONSE);
        AllQuestionResponseDto results = new AllQuestionResponseDto();
        for (Game game : games) {
            results.addQuestion(dozerMapper.map(questionManager.loadQuestions().getQuestionById(game.getId()), QuestionResponseDto.class));
        }
        return results;
    }

    @Path("/{username}/reply")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public ResponseResponseDto giveResponse(ResponseRequestDto responseDto) {
        Response res = dozerMapper.map(responseDto, Response.class);

        ResponseResponseDto response = new ResponseResponseDto();

        Set<ConstraintViolation<Response>> constraintViolations = validator.validate(res);
        if (constraintViolations.size() != 0) {
            LOGGER.error("Invalid response from user");
            response.setResponseType(ResponseType.INVALID);
            return response;
        }
        Question question = questionManager.loadQuestions().getQuestionById(res.getId());
        List<String> answers = question.getAnswers();
        List<String> responses = responseDto.getResponses();

        response.setId(res.getId());
        response.setAnswer(responses);
        response.setResponseType(ResponseType.FAIL);

        if (answers.size() == responses.size()) {
            //TODO : gerer le cas des majuscules/minuscules
            if (answers.containsAll(responses)) {
                response.setResponseType(ResponseType.SUCCESS);
            }
        }

        Game game = gameUserDataManager.getGameById(responseDto.getUserName(), res.getId());

        if (game == null) {
            game = new Game();
            game.setId(response.getId());
        }
        game.setGivenAnswers(response.getAnswer());
        game.setType(response.getResponseType());
        try {
            gameUserDataManager.addOrUpdateGame(responseDto.getUserName(), game);
        } catch (StorageException e) {
            LOGGER.error("unable to store result in mongoDb: {}", e.getMessage());
        }

        return response;
    }

    @Path("/{username}/create")
    @PUT
    public javax.ws.rs.core.Response addQuestionForUser(@PathParam("username") String userName) {
        List<Game> gamesAllReadyPlayed = gameUserDataManager.getGames(userName);
        List<Question> allQuestions = questionManager.loadQuestions().getQuestions();

        if (gamesAllReadyPlayed.size() == allQuestions.size()) {
            //NOTHING TO DO : the player allready answered all questions
            return javax.ws.rs.core.Response.ok().build();
        }

        Question randomQuestion = getRandomQuestion(gamesAllReadyPlayed, allQuestions);

        Game game = new Game();
        game.setId(randomQuestion.getId());
        game.setType(ResponseType.NEED_RESPONSE);

        try {
            gameUserDataManager.addOrUpdateGame(userName, game);
        } catch (StorageException e) {
            LOGGER.error("unable to store result in mongoDb: {}", e.getMessage());
        }

        questionsNotifier.notifyListener(userName, randomQuestion);

        return javax.ws.rs.core.Response.ok().build();
    }

    private Question getRandomQuestion(List<Game> gamesAllReadyPlayed, List<Question> allQuestions) {
        Question tmpQuestion = null;
        for (Game game : gamesAllReadyPlayed) {
            tmpQuestion = new Question();
            tmpQuestion.setId(game.getId());
            allQuestions.remove(tmpQuestion);
        }

        return allQuestions.get(getRandomIndex(allQuestions.size()));
    }


    private int getRandomIndex(int size) {
        return RANDOM_GENERATOR.nextInt(size);
    }
}
