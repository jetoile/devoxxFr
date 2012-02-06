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
package fr.soat.devoxx.game.pojo;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * User: khanh
 * Date: 27/12/11
 * Time: 21:36
 */
@XmlRootElement(name = "resultResponse")
public class ResultResponseDto {
	private String username;
    private int nbSuccess = 0;
    private int nbFail = 0;
    private int nbInvalid = 0;

    public ResultResponseDto() {
    }

    public int getNbSuccess() {

        return nbSuccess;
    }

    public void setNbSuccess(int nbSuccess) {
        this.nbSuccess = nbSuccess;
    }

    public int getNbFail() {
        return nbFail;
    }

    public void setNbFail(int nbFail) {
        this.nbFail = nbFail;
    }

    public int getNbInvalid() {
        return nbInvalid;
    }

    public void setNbInvalid(int nbInvalid) {
        this.nbInvalid = nbInvalid;
    }

    @Override
    public String toString() {
        return "ResultResponseDto{" +
        		"username=" + username +
                "nbSuccess=" + nbSuccess +
                ", nbFail=" + nbFail +
                ", nbInvalid=" + nbInvalid +
                '}';
    }

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
}
