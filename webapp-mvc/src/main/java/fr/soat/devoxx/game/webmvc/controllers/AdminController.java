/*
 * Copyright (c) 2012 Aurélien VIALE
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
package fr.soat.devoxx.game.webmvc.controllers;

import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import fr.soat.devoxx.game.admin.pojo.dto.AllUserResponseDto;
import fr.soat.devoxx.game.pojo.ResultResponseDto;
import fr.soat.devoxx.game.pojo.UserResponseDto;
import fr.soat.devoxx.game.webmvc.delegate.HttpRestException;
import fr.soat.devoxx.game.webmvc.delegate.RequesterDelegate;
import fr.soat.devoxx.game.webmvc.utils.TilesUtil;

@Controller
@RequestMapping(value = "/admin")
public class AdminController {

	private static Logger logger = LoggerFactory.getLogger(AdminController.class);
	private RequesterDelegate service;

	@RequestMapping(value = { "/", "/index" })
	public String index() {
		return TilesUtil.DFR_ADMIN_INDEX_PAGE;
	}

	@RequestMapping(value = "/user")
	public String showAllUser(Model model) {
		String forward = TilesUtil.DFR_ERRORS_ERRORMSG_PAGE;
		try {
			service = new RequesterDelegate("/admin/user");
			AllUserResponseDto allUsers = service.get(AllUserResponseDto.class);
			model.addAttribute("allUserResponses", allUsers.getUserResponses());
			forward = TilesUtil.DFR_ADMIN_SHOWALLUSERS_PAGE;
		} catch (HttpRestException e) {
			model.addAttribute("error", "admin.error.user.getall");
			logger.info("Error while fetching all users", e);
		}
		return forward;
	}

	@RequestMapping(value = "/user/{username}")
	public String showUser(@PathVariable String username, Model model) {
		String forward = TilesUtil.DFR_ERRORS_ERRORMSG_PAGE;
		try {
			service = new RequesterDelegate("/admin/user/" + username);
			UserResponseDto userResponse = service.get(UserResponseDto.class);
			model.addAttribute("userResponse", userResponse);
			model.addAttribute("mailHash", DigestUtils.md5Hex(userResponse.getMail().trim().toLowerCase()));

			service = new RequesterDelegate("/admin/result/" + username);
			ResultResponseDto resultResponse = service.get(ResultResponseDto.class);
			model.addAttribute("resultResponse", resultResponse);

			forward = TilesUtil.DFR_ADMIN_SHOWUSER_PAGE;
		} catch (HttpRestException e) {
			model.addAttribute("error", "admin.error.user.get");
			model.addAttribute("errorParams", username);
			logger.info("Error while fetching user", e);
		}
		return forward;
	}

	@RequestMapping(value = "/user/{username}/delete")
	public String removeUser(@PathVariable String username, Model model) {
		String forward = TilesUtil.DFR_ERRORS_ERRORMSG_PAGE;
		try {
			service = new RequesterDelegate("/admin/user/" + username);
			service.delete();
			forward = "redirect:/admin/index";
		} catch (HttpRestException e) {
			model.addAttribute("error", "admin.error.user.delete");
			model.addAttribute("errorParams", username);
			logger.info("Error while deleting user", e);
		}
		return forward;
	}
}
