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

import javax.ws.rs.DefaultValue;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sun.jersey.api.json.JSONWithPadding;

import fr.soat.devoxx.game.business.admin.AdminUserService;
import fr.soat.devoxx.game.business.exception.InvalidUserException;
import fr.soat.devoxx.game.business.types.CustomMediaType;
import fr.soat.devoxx.game.pojo.UserRequestDto;
import fr.soat.devoxx.game.pojo.UserResponseDto;

/**
 * User: khanh
 * Date: 20/12/11
 * Time: 14:12
 */
@Path("/user")
public class UserService {
    private final static Logger LOGGER = LoggerFactory.getLogger(UserService.class);

    private AdminUserService delegate = new AdminUserService();
    
    @Path("/")
    @POST
//    @GET
    @Produces(CustomMediaType.APPLICATION_XJAVASCRIPT)
    public JSONWithPadding createUser(@QueryParam("jsoncallback") @DefaultValue("fn") String callback, @FormParam("username") String name, @FormParam("mail") String mail) throws InvalidUserException {
//    public JSONWithPadding createUser(@QueryParam("jsoncallback") @DefaultValue("fn") String callback, @PathParam("username") String name, @PathParam("mail") String mail) throws InvalidUserException {
        UserRequestDto userRequestDto = new UserRequestDto();
        userRequestDto.setName(name);
        userRequestDto.setMail(mail);

        UserResponseDto result = delegate.createUser(userRequestDto);
        return new JSONWithPadding(result, callback);
    }

    @Path("/{username}")
    @GET
    @Produces(CustomMediaType.APPLICATION_XJAVASCRIPT)
    public JSONWithPadding getUser(@QueryParam("jsoncallback") @DefaultValue("fn") String callback, @PathParam("username") String userName) {
        UserResponseDto result = delegate.getUser(userName);
        return new JSONWithPadding(result, callback);
    }
}
