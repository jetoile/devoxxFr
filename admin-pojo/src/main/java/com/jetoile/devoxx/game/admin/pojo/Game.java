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
package com.jetoile.devoxx.game.admin.pojo;

import com.google.code.morphia.annotations.Embedded;
import com.jetoile.devoxx.game.pojo.question.ResponseType;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * User: khanh
 * Date: 23/12/11
 * Time: 20:19
 */
@Embedded
public class Game implements Serializable {
//    @Transient
//    public final static Game EMPTY = new EmptyGame();

    private int id;

    private List<String> givenAnswers = new ArrayList<String>();

    private ResponseType type;

    public Game() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setType(ResponseType type) {
        this.type = type;
    }

    public void setGivenAnswers(List<String> answers) {
        this.givenAnswers = answers;
    }

    public List<String> getGivenAnswers() {
        return givenAnswers;
    }

    public ResponseType getType() {
        return type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Game game = (Game) o;

        if (id != game.id) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return id;
    }

    @Override
    public String toString() {
        return "Game{" +
                "id=" + id +
                ", givenAnswers=" + givenAnswers +
                ", type=" + type +
                '}';
    }

//    private static class EmptyGame extends Game {
//        @Override
//        public int getId() {
//            return 0;
//        }
//
//        @Override
//        public List<String> getGivenAnswers() {
//            return Collections.EMPTY_LIST;
//        }
//
//        @Override
//        public ResponseType getType() {
//            return ResponseType.INVALID;
//        }
//    }
}
