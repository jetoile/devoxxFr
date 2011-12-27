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

import fr.soat.devoxx.game.business.exception.InvalidUserException;
import fr.soat.devoxx.game.pojo.UserResponseDto;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * User: khanh
 * Date: 20/12/11
 * Time: 15:28
 */
public class UserServiceTest {
    private UserService userService;

    @Before
    public void init() {
        userService = new UserService("devoxx-test");
//        userService = new UserService("devoxx");
        userService.deleteUser("toto");
    }

    @Test
    public void generateTokenShouldReturnDifferentResults() {
        assertNotSame(userService.generateToken(), userService.generateToken());
    }

    @Test
    public void createUserShouldReturnAToken() throws InvalidUserException {

        UserResponseDto user = userService.createUser("toto", "toto@gmail.com");
        assertNotNull(user);
        assertNotNull(user.getToken());
    }


    @Test(expected = InvalidUserException.class)
    public void createUserWithInvalidMailShouldThrowException() throws InvalidUserException {
        userService.createUser("toto", "toto@gmailcom");
    }

    @Test(expected = InvalidUserException.class)
    public void createUserWithInvalidMail2ShouldThrowException() throws InvalidUserException {
        userService.createUser("toto", "totogmailcom");
    }

    @Test(expected = InvalidUserException.class)
    public void createUserWithInvalidMail3ShouldThrowException() throws InvalidUserException {
        userService.createUser("toto", null);
    }

    @Test(expected = InvalidUserException.class)
    public void createUserWithInvalidNameShouldThrowException() throws InvalidUserException {
        userService.createUser("to", "toto@gmailcom");
    }

    @Test(expected = InvalidUserException.class)
    public void createUserWithInvalidName2ShouldThrowException() throws InvalidUserException {
        userService.createUser(null, "toto@gmailcom");
    }

    @Test
    public void generateAValidUserShouldSuccessAndBePersist() throws InvalidUserException {
        UserResponseDto user = userService.createUser("toto", "toto@gmail.com");
        assertNotNull(user);
//        assertTrue(user.getToken().length() == 10);
        assertEquals("toto", userService.getUser("toto").getName());
    }
}

