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
import fr.soat.devoxx.game.business.exception.InvalidUserException;
import fr.soat.devoxx.game.pojo.UserResponseDto;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import static org.mockito.Mockito.*;

import static org.junit.Assert.*;

/**
 * User: khanh
 * Date: 20/12/11
 * Time: 15:28
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest(GameUserDataManager.class)
@PowerMockIgnore("javax.management.*")
public class AdminUserServiceTest {
    private AdminUserService adminUserService;

    @Before
    public void init() {
        GameUserDataManager gameUserDataManager = PowerMockito.mock(GameUserDataManager.class);
//        when(gameUserDataManager.)

        adminUserService = new AdminUserService("devoxx-test", gameUserDataManager);
//        adminUserService = new UserService("devoxx");
        adminUserService.deleteUser("toto");
    }

    @Test
    public void generateTokenShouldReturnDifferentResults() {
        assertNotSame(adminUserService.generateToken(), adminUserService.generateToken());
    }

    @Test
    public void createUserShouldReturnAToken() throws InvalidUserException {

        UserResponseDto user = adminUserService.createUser("toto", "toto@gmail.com");
        assertNotNull(user);
        assertNotNull(user.getToken());
    }


    @Test(expected = InvalidUserException.class)
    public void createUserWithInvalidMailShouldThrowException() throws InvalidUserException {
        adminUserService.createUser("toto", "toto@gmailcom");
    }

    @Test(expected = InvalidUserException.class)
    public void createUserWithInvalidMail2ShouldThrowException() throws InvalidUserException {
        adminUserService.createUser("toto", "totogmailcom");
    }

    @Test(expected = InvalidUserException.class)
    public void createUserWithInvalidMail3ShouldThrowException() throws InvalidUserException {
        adminUserService.createUser("toto", null);
    }

    @Test(expected = InvalidUserException.class)
    public void createUserWithInvalidNameShouldThrowException() throws InvalidUserException {
        adminUserService.createUser("to", "toto@gmailcom");
    }

    @Test(expected = InvalidUserException.class)
    public void createUserWithInvalidName2ShouldThrowException() throws InvalidUserException {
        adminUserService.createUser(null, "toto@gmailcom");
    }

    @Test
    public void generateAValidUserShouldSuccessAndBePersist() throws InvalidUserException {
        UserResponseDto user = adminUserService.createUser("toto", "toto@gmail.com");
        assertNotNull(user);
//        assertTrue(user.getToken().length() == 10);
        assertEquals("toto", adminUserService.getUser("toto").getName());
    }
}

