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
package fr.soat.devoxx.game.admin.pojo.dto;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlRootElement;

import org.apache.commons.lang.StringUtils;

import fr.soat.devoxx.game.pojo.UserResponseDto;

@XmlRootElement(name = "allUserResponse")
public class AllUserResponseDto {
	
	private List<UserResponseDto> userResponses = new ArrayList<UserResponseDto>();

	public List<UserResponseDto> getUserResponses() {
		return userResponses;
	}

	/**
	 * @param userResponses the userResponses to set
	 */
	public void setUserResponses(List<UserResponseDto> userResponses) {
		this.userResponses = userResponses;
	}
	
	/**
	 * @param usersResponseDto the UserResponseDto to add
	 */
	public void addUserResponse(UserResponseDto usersResponseDto) {
		this.userResponses.add(usersResponseDto);
	}
	
	/**
	 * Unmarshal Fix
	 * See :
	 * http://stackoverflow.com/questions/4181120/transforming-empty-element-into-null-when-unmarshalling-with-jaxb/4197817#4197817
	 * 
	 * @param aUnmarshaller
	 * @param aParent
	 */
	void afterUnmarshal(Unmarshaller aUnmarshaller, Object aParent) {
		if (userResponses != null) {
			Iterator<UserResponseDto> iterator = userResponses.iterator();
			while (iterator.hasNext()) {
				UserResponseDto userResponse = iterator.next();
				if (StringUtils.isEmpty(userResponse.getName())) {
					// a user without name is considered invalid
					iterator.remove();
				}
			}
		}
	}

	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "AllUserResponseDto [userResponses=" + userResponses + "]";
	}
}
