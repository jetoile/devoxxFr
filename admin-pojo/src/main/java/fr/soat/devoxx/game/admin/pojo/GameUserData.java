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
package fr.soat.devoxx.game.admin.pojo;

import com.google.code.morphia.annotations.Embedded;
import com.google.code.morphia.annotations.Entity;
import com.google.code.morphia.annotations.Id;
import com.mongodb.BasicDBObject;
import org.bson.types.ObjectId;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;

/**
 * User: khanh
 * Date: 23/12/11
 * Time: 20:14
 */
@Entity("gameUserData")
public class GameUserData {
    @Id
    private String name;

//    private ObjectId id;
    private int score = 0;

    @Embedded("games")
    private ArrayList<Game> games = new ArrayList<Game>();

    public int getScore() {

        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public ArrayList<Game> getGames() {
        return games;
    }

    public void addGame(Game game) {
        this.games.add(game);
    }
    
    public void addOrReplace(Game game) {
        int index = this.games.indexOf(game);
        if (index != -1) {
            this.games.set(index, game);
        } else {
            this.games.add(game);
        }
    }
    
    public void setGames(ArrayList<Game> games) {
        this.games = games;
    }

    public String getName() {

        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public GameUserData() {

    }

    @Override
    public String toString() {
        return "GameUserData{" +
                "name='" + name + '\'' +
                ", score=" + score +
                ", games=" + games +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        GameUserData that = (GameUserData) o;

        if (score != that.score) return false;
        if (games != null ? !games.equals(that.games) : that.games != null) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + score;
        result = 31 * result + (games != null ? games.hashCode() : 0);
        return result;
    }
}
