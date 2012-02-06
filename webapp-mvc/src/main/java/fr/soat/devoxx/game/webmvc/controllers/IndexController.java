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

import javax.ws.rs.core.MediaType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import fr.soat.devoxx.game.pojo.QuestionResponseDto;
import fr.soat.devoxx.game.webmvc.delegate.HttpRestException;
import fr.soat.devoxx.game.webmvc.delegate.RequesterDelegate;
import fr.soat.devoxx.game.webmvc.utils.TilesUtil;

@Controller
@RequestMapping(value = "/")
public class IndexController {

	private static Logger logger = LoggerFactory.getLogger(IndexController.class);

	@RequestMapping(value = "index", method = RequestMethod.GET)
	public String index() {
		RequesterDelegate service = new RequesterDelegate("/admin/question", MediaType.APPLICATION_JSON);
		try {
			QuestionResponseDto questions = service.get(QuestionResponseDto.class);
			System.out.println(questions.toString());
		} catch (HttpRestException e) {
			logger.error("Error while loading question", e);
		}

		return TilesUtil.DFR_INDEX_PAGE;
	}
}
