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
import fr.soat.devoxx.game.admin.pojo.dto.AllUserResponseDto;
import fr.soat.devoxx.game.business.exception.InvalidUserException;
import fr.soat.devoxx.game.persistent.User;
import fr.soat.devoxx.game.pojo.UserRequestDto;
import fr.soat.devoxx.game.pojo.UserResponseDto;
import org.dozer.DozerBeanMapper;
import org.dozer.Mapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response.Status;
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

    @Inject
    private GameUserDataManager gameUserDataManager;

    private final Validator validator;

    {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    private final Mapper dozerMapper = new DozerBeanMapper();

    private EntityManagerFactory emf;

    @javax.enterprise.inject.Produces
    @PersistenceContext
    private EntityManager em;

    private void init() {
//            emf = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
//            em = emf.createEntityManager();
    }

    public AdminUserService() {
//        this.gameUserDataManager = fr.soat.devoxx.game.business.admin.GameUserDataManager.INSTANCE;
    }

//    AdminUserService(String persistenceUnitName,
//                     GameUserDataManager gameUserDataManager) {
//        this.PERSISTENCE_UNIT_NAME = persistenceUnitName;
//        this.gameUserDataManager = gameUserDataManager;
//    }

    private void close() {
//        if (em != null) {
//            em.close();
//        }
    }

    @Path("/")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public AllUserResponseDto getAllUsers() {
        AllUserResponseDto allUsersDto = new AllUserResponseDto();
        try {
            init();
            @SuppressWarnings("unchecked")
            List<User> users = em.createQuery("from User u").getResultList();
            for (User user : users) {
                allUsersDto.addUserResponse(this.dozerMapper.map(user, UserResponseDto.class));
            }
        } finally {
            close();
        }
        return allUsersDto;
    }

    @Path("/")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public UserResponseDto createUser(UserRequestDto userRequestDto)
            throws InvalidUserException {
        try {
            init();
            User user = dozerMapper.map(userRequestDto, User.class);

            Set<ConstraintViolation<User>> constraintViolations = validator
                    .validate(user);

            if (constraintViolations.size() != 0) {
                LOGGER.error("Invalid input for user creation {}",
                        userRequestDto);
                throw new InvalidUserException(constraintViolations);
            }
            /*
                * final String token = generateToken(); user.setToken(token);
                */

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

    @Path("/{username}")
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public UserResponseDto getUser(@PathParam("username") String userName) {
        try {
            init();
//			List<User> users = getUsers(em, userName);
            User user = getUserByName(userName);

            if (null != user) {
                LOGGER.debug("get user {} successful", userName);
                UserResponseDto response = dozerMapper.map(user, UserResponseDto.class);
                response.setToken(null);
                return response;
            } else {
                LOGGER.debug("get user {} failed: not found", userName);
                throw new WebApplicationException(Status.NOT_FOUND);
            }
        } catch (PersistenceException e) {
            LOGGER.debug("get user failed: PersistenceException", e);
            throw new WebApplicationException(Status.NOT_FOUND);
        } finally {
            close();
        }
    }

    @Path("/{username}/games")
    @DELETE
    public void cleanUserGames(@PathParam("username") String userName) {
        this.gameUserDataManager.cleanUser(userName);
    }

    @Path("/{username}")
    @DELETE
    public void deleteUser(@PathParam("username") String userName) {
        try {
            init();
//			List<User> users = getUsers(em, userName);
            User user = getUserByName(userName);

            if (null != user) {
                em.getTransaction().begin();
                em.remove(user);
                em.getTransaction().commit();
            } else {
                LOGGER.debug("delete user {} failed: not found", userName);
                throw new WebApplicationException(Status.NOT_FOUND);
            }
            this.gameUserDataManager.destroyUser(userName);

            LOGGER.debug("delete user {} successful", userName);
        } catch (PersistenceException e) {
            LOGGER.debug("delete user failed: PersistenceException", e);
//			throw new WebApplicationException(Status.NOT_FOUND);
        } finally {
            close();
        }
    }

    /*private List<User> getUsers(EntityManager em, String userName) {
         // CriteriaQuery<User> criteriaQuery = createSimpleUserCriteriaQuery(em,
         // userName);
         // return em.createQuery(criteriaQuery).setParameter("name",
         // userName).getResultList();
         return em.createQuery("select g from User g where g.name = :name")
                 .setParameter("name", userName).getResultList();
     }*/

    private User getUserByName(String userName) throws PersistenceException {
        return (User) em.createQuery("select g from User g where g.name = :name")
                .setParameter("name", userName).getSingleResult();
//        CriteriaQuery<User> criteriaQuery = createSimpleUserCriteriaQuery(em,
//                userName);
//                return em.createQuery(criteriaQuery).setParameter("name",
//                userName).getSingleResult();
    }

    private CriteriaQuery<User> createSimpleUserCriteriaQuery(EntityManager em,
                                                              String userName) {
        // List<User> users = em.createQuery(
        // "select g from User g where g.name = :name")
        // .setParameter("name", userName).getResultList();

        CriteriaBuilder queryBuilder = em.getCriteriaBuilder();
        CriteriaQuery<User> criteriaQuery = queryBuilder
                .createQuery(User.class);

        Root<User> root = criteriaQuery.from(User.class);

        criteriaQuery.select(root).where(
                queryBuilder.equal(root.get("name"), userName));
        return criteriaQuery;
    }
}
