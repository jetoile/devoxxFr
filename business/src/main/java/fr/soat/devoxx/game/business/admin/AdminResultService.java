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

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.dozer.DozerBeanMapper;
import org.dozer.Mapper;

import fr.soat.devoxx.game.admin.pojo.GameResult;
import fr.soat.devoxx.game.admin.pojo.GameUserDataManager;
import fr.soat.devoxx.game.admin.pojo.dto.AllResultResponseDto;
import fr.soat.devoxx.game.pojo.ResultResponseDto;

/**
 * User: khanh
 * Date: 27/12/11
 * Time: 21:31
 */
@Path("/admin/result")
public class AdminResultService {

	private final Mapper dozerMapper = new DozerBeanMapper();
	
    @Path("/{username}")
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public ResultResponseDto getResultForUser(@PathParam("username") String userName) {
    	GameResult gameResult = GameUserDataManager.INSTANCE.getResult(userName);
        return dozerMapper.map(gameResult, ResultResponseDto.class);
    }
    
	@Path("/")
	@GET
	@Produces({ MediaType.APPLICATION_JSON })
	public AllResultResponseDto getAllResult() {
		AllResultResponseDto allResultResp = new AllResultResponseDto();
		List<GameResult> allGameResult = GameUserDataManager.INSTANCE.getAllResult();
		for (GameResult gameResult : allGameResult) {
			allResultResp.addGameResult(this.dozerMapper.map(gameResult, ResultResponseDto.class));
		}
		return allResultResp;
	}
}
