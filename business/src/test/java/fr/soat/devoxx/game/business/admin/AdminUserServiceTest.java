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
import fr.soat.devoxx.game.pojo.UserRequestDto;
import fr.soat.devoxx.game.pojo.UserResponseDto;

import org.apache.commons.lang.StringUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

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

//        EntityManagerFactory emf = Persistence.createEntityManagerFactory("devoxx-test");
//        adminUserService.em = emf.createEntityManager();

//        adminUserService = new UserService("devoxx");
        adminUserService.deleteUser("toto");
    }

    @Test
    public void createUserShouldReturnAToken() throws InvalidUserException {
		UserRequestDto userRequestDto = new UserRequestDto();
		userRequestDto.setName("toto");
		userRequestDto.setMail("toto@gmail.com");
		UserResponseDto user = adminUserService.createUser(userRequestDto);
		assertNotNull(user);
		assertFalse(StringUtils.isEmpty(user.getToken()));
    }


    @Test(expected = InvalidUserException.class)
    public void createUserWithInvalidMailShouldThrowException() throws InvalidUserException {
        UserRequestDto user = new UserRequestDto();
        user.setName("toto");
        user.setMail("toto@gmailcom");
        adminUserService.createUser(user);
    }

    @Test(expected = InvalidUserException.class)
    public void createUserWithInvalidMail2ShouldThrowException() throws InvalidUserException {
		UserRequestDto user = new UserRequestDto();
		user.setName("toto");
		user.setMail("totogmailcom");
		adminUserService.createUser(user);
    }

    @Test(expected = InvalidUserException.class)
    public void createUserWithInvalidMail3ShouldThrowException() throws InvalidUserException {
        UserRequestDto user = new UserRequestDto();
        user.setName("toto");
        user.setMail(null);
        adminUserService.createUser(user);
    }

    @Test(expected = InvalidUserException.class)
    public void createUserWithInvalidNameShouldThrowException() throws InvalidUserException {
        UserRequestDto user = new UserRequestDto();
        user.setName("to");
        user.setMail("toto@gmailcom");
        adminUserService.createUser(user);
    }

    @Test(expected = InvalidUserException.class)
    public void createUserWithInvalidName2ShouldThrowException() throws InvalidUserException {
        UserRequestDto user = new UserRequestDto();
        user.setName(null);
        user.setMail("toto@gmailcom");
        adminUserService.createUser(user);
    }

    @Test
    public void generateAValidUserShouldSuccessAndBePersist() throws InvalidUserException {
        UserRequestDto userRequest = new UserRequestDto();
        userRequest.setName("toto");
        userRequest.setMail("toto@gmail.com");
        UserResponseDto user = adminUserService.createUser(userRequest);
        assertNotNull(user);
//        assertTrue(user.getToken().length() == 10);
        assertEquals("toto", adminUserService.getUser("toto").getName());
    }
}

