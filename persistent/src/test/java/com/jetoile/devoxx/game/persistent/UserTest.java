package com.jetoile.devoxx.game.persistent;/*
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

import org.junit.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;

import com.jetoile.devoxx.game.persistent.User;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * User: khanh
 * Date: 20/12/11
 * Time: 01:36
 */
public class UserTest {


    static private EntityManagerFactory emf;
    static private EntityManager em;
    static private final String PERSISTENCE_UNIT_NAME = "devoxx-test";

    private static Validator validator;

    @BeforeClass
    public static void initEntityManager() {
        emf = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
        em = emf.createEntityManager();
    }

    @AfterClass
    public static void closeEntityManager() {
        em.close();
        emf.close();
    }

    @Before
    public void initDb() {
        em.getTransaction().begin();
        User user1 = new User("toto", "toto@toto.com");
        User user2 = new User("titi", "titi@titi.com");
        User[] users = new User[]{user1, user2};
        for (User g : users) {
            em.persist(g);
        }
        em.getTransaction().commit();

        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @After
    public void cleanDb() {
        em.getTransaction().begin();
        User userx = em.find(User.class, "toto");
        em.remove(userx);
        userx = em.find(User.class, "titi");
        em.remove(userx);
        em.getTransaction().commit();
    }

    @Test
    public void theDbReadShouldSuccess() {
        User user = (User) em.createQuery(
                "select g from User g where g.name = :name")
                .setParameter("name", "toto").getSingleResult();
        assertNotNull(user);
        assertEquals(user.getMail(), "toto@toto.com");
        assertEquals(user.getName(), "toto");
//        System.out.println("Query returned: " + user);
    }

    @Test
    public void anInvalidEmailShouldBeChecked() {
        User user = new User("toto", "totototo.com");
        Set<ConstraintViolation<User>> constraintViolations = validator.validate(user);
        assertEquals(1, constraintViolations.size());
        assertEquals("{javax.validation.constraints.Pattern.message}", constraintViolations.iterator().next().getMessageTemplate());

        User user1 = new User("toto", null);
        constraintViolations = validator.validate(user1);
        assertEquals(1, constraintViolations.size());
        assertEquals("{javax.validation.constraints.NotNull.message}", constraintViolations.iterator().next().getMessageTemplate());
    }

    @Test
    public void anInvalidNameShouldBeChecked() {
        User user = new User("to", "toto@toto.com");
        Set<ConstraintViolation<User>> constraintViolations = validator.validate(user);
        assertEquals(1, constraintViolations.size());
        assertEquals("{javax.validation.constraints.Size.message}", constraintViolations.iterator().next().getMessageTemplate());

        User user1 = new User(null, "toto@toot.com");
        constraintViolations = validator.validate(user1);
        assertEquals(1, constraintViolations.size());
        assertEquals("{javax.validation.constraints.NotNull.message}", constraintViolations.iterator().next().getMessageTemplate());
    }

    @Test
    public void anValidUserShouldSuccess() {
        User user = new User("toto", "toto@toto.com");
        Set<ConstraintViolation<User>> constraintViolations = validator.validate(user);
        assertEquals(0, constraintViolations.size());
    }


}
