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

import fr.soat.devoxx.game.admin.pojo.GameUserDataManager;
import fr.soat.devoxx.game.business.PropertiesUtils;
import fr.soat.devoxx.game.business.exception.InvalidUserException;
import fr.soat.devoxx.game.persistent.User;
import fr.soat.devoxx.game.pojo.UserRequestDto;
import fr.soat.devoxx.game.pojo.UserResponseDto;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.StringUtils;
import org.dozer.DozerBeanMapper;
import org.dozer.Mapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;
import java.util.Set;

/**
 * User: khanh
 * Date: 20/12/11
 * Time: 14:12
 */
@Path("/admin/user")
public class AdminUserService {
    private final static Logger LOGGER = LoggerFactory.getLogger(AdminUserService.class);

    private String PERSISTENCE_UNIT_NAME = "devoxx";

    private GameUserDataManager gameUserDataManager;

    private final Validator validator;
    {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    private final Mapper dozerMapper = new DozerBeanMapper();
    private EntityManagerFactory emf;
    private EntityManager em;

    private void init() {
            emf = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
            em = emf.createEntityManager();
    }

    public AdminUserService() {
        this.gameUserDataManager = GameUserDataManager.INSTANCE;
    }

    AdminUserService(String persistenceUnitName, GameUserDataManager gameUserDataManager) {
        this.PERSISTENCE_UNIT_NAME = persistenceUnitName;
        this.gameUserDataManager = gameUserDataManager;
    }

    private void close() {
        if (em != null) {
            em.close();
        }
    }

    @Path("/user")
    @POST
//    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public UserResponseDto createUser(UserRequestDto userRequestDto) throws InvalidUserException {
        try {
            init();
            User user = dozerMapper.map(userRequestDto, User.class);

            Set<ConstraintViolation<User>> constraintViolations = validator.validate(user);

            if (constraintViolations.size() != 0) {
                LOGGER.error("Invalid input for user creation {}", userRequestDto);
                throw new InvalidUserException(constraintViolations);
            }
            final String token = generateToken();
            user.setToken(token);

            em.getTransaction().begin();
            em.persist(user);
            em.getTransaction().commit();
            LOGGER.debug("User creation successful: {}", userRequestDto);

            this.gameUserDataManager.registerUser(userRequestDto.getName());

            return dozerMapper.map(user, UserResponseDto.class);
        } finally {
            close();
        }
    }

    @Path("/user/{username}")
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public UserResponseDto getUser(@PathParam("username") String userName) {
        try {
            init();

            List<User> users = getUsers(em, userName);
                    
            if (users.size() == 1) {
                LOGGER.debug("get user {} successful", userName);
                UserResponseDto response = dozerMapper.map(users.get(0), UserResponseDto.class);
                response.setToken(null);
                return response;
            } else if (users.size() > 1) {
                LOGGER.debug("get user {} failed: too many response", userName);
                return null;
            } else {
                LOGGER.debug("get user {} failed: not found", userName);
                return null;
            }
        } finally {
            close();
        }
    }

    @Path("/games/{username}")
    @DELETE
//    @POST
    public void cleanUserGames(@PathParam("username") String userName) {
            this.gameUserDataManager.cleanUser(userName);
    }

    @Path("/user/{username}")
    @DELETE
//    @POST
    public void deleteUser(@PathParam("username") String userName) {
        try {
            init();

            List<User> users = getUsers(em, userName);

            em.getTransaction().begin();
            for (User user : users) {
                em.remove(user);
            }
            em.getTransaction().commit();

            this.gameUserDataManager.destroyUser(userName);

            LOGGER.debug("delete all user {} successful", userName);
        } finally {
            close();
        }
    }

    private List<User> getUsers(EntityManager em, String userName) {
        CriteriaQuery<User> criteriaQuery = createSimpleUserCriteriaQuery(em, userName);
        return em.createQuery(criteriaQuery).setParameter("name", userName).getResultList();
    }

    String generateToken() {
        return RandomStringUtils.randomAlphanumeric(PropertiesUtils.INSTANCE.getUserTokenLenght()).toLowerCase();
    }
    
    private CriteriaQuery<User> createSimpleUserCriteriaQuery(EntityManager em, String userName) {
        //                    List<User> users = em.createQuery(
        //                    "select g from User g where g.name = :name")
        //                    .setParameter("name", userName).getResultList();

        CriteriaBuilder queryBuilder = em.getCriteriaBuilder();
        CriteriaQuery<User> criteriaQuery = queryBuilder.createQuery(User.class);

        Root<User> root = criteriaQuery.from(User.class);

        criteriaQuery
                .select(root)
                .where(
                        queryBuilder.equal(
                                root.get("name"),
                                userName)
                );
        return criteriaQuery;
    }
}
