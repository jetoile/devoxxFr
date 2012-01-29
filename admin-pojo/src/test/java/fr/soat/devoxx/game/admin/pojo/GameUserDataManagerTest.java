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

import com.google.code.morphia.Datastore;
import com.google.code.morphia.query.FieldEnd;
import com.google.code.morphia.query.Query;
import com.google.common.collect.Lists;
import fr.soat.devoxx.game.admin.pojo.exception.StorageException;
import fr.soat.devoxx.game.pojo.question.ResponseType;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;

/**
 * User: khanh
 * Date: 27/12/11
 * Time: 14:08
 */
public class GameUserDataManagerTest {
    @Before
    public void setUp() {
//        try {
//            Mongo mongo = new Mongo("localhost", 27017);
//            Datastore ds = new Morphia().createDatastore(mongo, "foo");
//            GameUserDataManager.INSTANCE.ds = ds;
//        } catch (UnknownHostException e) {
//            e.printStackTrace();
//        }

    }

    @Test
    public void registerUserShouldSuccess() throws Exception {
        //given
        Datastore ds = mock(Datastore.class);
        GameUserDataManager.INSTANCE.ds = ds;

        //when
        GameUserDataManager.INSTANCE.registerUser("user1");
        //then
        verify(ds).save(any(GameUserData.class));
    }

    @Test(expected = StorageException.class)
    public void addGameWithAnUnknownUserShouldFail() throws Exception {
        //given
        GameUserData gameUserData = new GameUserData();
        gameUserData.setName("use");

        Datastore ds = mock(Datastore.class);
        Query<GameUserData> query = mock(Query.class);
        FieldEnd fieldEnd = mock(FieldEnd.class);

        GameUserDataManager.INSTANCE.ds = ds;        
        when(query.get()).thenReturn(null);
        when(query.field("name")).thenReturn(fieldEnd);
        when(fieldEnd.equal(anyString())).thenReturn(query);
        when(ds.find(GameUserData.class)).thenReturn(query);

        Game game = new Game();
        game.setGivenAnswers(Lists.newArrayList("toto", "titi"));
        game.setType(ResponseType.SUCCESS);

        //when
        GameUserDataManager.INSTANCE.addOrUpdateGame("use", game);
//        verify(ds).save();
    }

    @Test
    public void addGameShouldSuccess() throws StorageException {
        //given
        GameUserData gameUserData = mock(GameUserData.class);
        when(gameUserData.getName()).thenReturn("user1");

        Datastore ds = mock(Datastore.class);
        Query<GameUserData> query = mock(Query.class);
        FieldEnd fieldEnd = mock(FieldEnd.class);

        GameUserDataManager.INSTANCE.ds = ds;
        when(query.get()).thenReturn(gameUserData);
        when(query.field("name")).thenReturn(fieldEnd);
        when(fieldEnd.equal(anyString())).thenReturn(query);
        when(ds.find(GameUserData.class)).thenReturn(query);

        Game game = new Game();
        game.setId(1);
        game.setGivenAnswers(Lists.newArrayList("toto", "titi"));
        game.setType(ResponseType.SUCCESS);

        //when
        GameUserDataManager.INSTANCE.addOrUpdateGame("user1", game);

        //then
        verify(gameUserData, times(1)).addOrReplace(game);
        verify(ds, times(1)).save(gameUserData);
    }

    @Test
    public void getGamesShouldReturnAllPlayedGamesForASpecificUser() throws StorageException {
        //given
        GameUserData gameUserData = mock(GameUserData.class);
        when(gameUserData.getName()).thenReturn("user1");

        Datastore ds = mock(Datastore.class);
        Query<GameUserData> query = mock(Query.class);
        Query<GameUserData> query2 = mock(Query.class);
        FieldEnd fieldEnd = mock(FieldEnd.class);
        FieldEnd fieldEnd2 = mock(FieldEnd.class);

        GameUserDataManager.INSTANCE.ds = ds;
        when(query.field("name")).thenReturn(fieldEnd);
        when(fieldEnd.equal("user1")).thenReturn(query2);
        when(query2.get()).thenReturn(gameUserData);
        when(ds.find(GameUserData.class)).thenReturn(query);

        Game game1 = new Game();
        game1.setId(1);
        game1.setGivenAnswers(Lists.newArrayList("toto", "titi"));
        game1.setType(ResponseType.SUCCESS);

        Game game2 = new Game();
        game2.setId(2);
        game2.setGivenAnswers(Lists.newArrayList("tutu1"));
        game2.setType(ResponseType.FAIL);

        Game game3 = new Game();
        game3.setId(3);
        game3.setGivenAnswers(Lists.newArrayList("tutu"));
        game3.setType(ResponseType.SUCCESS);

        //when
        when(gameUserData.getGames()).thenReturn(Lists.newArrayList(game1, game2, game3));

        //then
        List<Game> games = GameUserDataManager.INSTANCE.getGames("user1");
        assertTrue(games.size() == 3);
        assertTrue(games.get(0).getId() == 1);
        assertTrue(games.get(1).getId() == 2);
        assertTrue(games.get(2).getId() == 3);
    }

    @Test
    public void getGamesByResponseTypeShouldSuccess() throws StorageException {
        //given
        GameUserData gameUserData = mock(GameUserData.class);
        when(gameUserData.getName()).thenReturn("user1");

//        GameUserData gameUserData2 = mock(GameUserData.class);
//        when(gameUserData2.getName()).thenReturn("user2");

        Datastore ds = mock(Datastore.class);
        Query<GameUserData> query = mock(Query.class);
        Query<GameUserData> query2 = mock(Query.class);
        FieldEnd fieldEnd = mock(FieldEnd.class);
        FieldEnd fieldEnd2 = mock(FieldEnd.class);

        GameUserDataManager.INSTANCE.ds = ds;
        when(query.field("name")).thenReturn(fieldEnd);
        when(fieldEnd.equal("user1")).thenReturn(query2);
        when(query2.field("games.type")).thenReturn(fieldEnd2);
        when(fieldEnd2.contains("SUCCESS")).thenReturn(query2);
        when(query2.asList()).thenReturn(Lists.newArrayList(gameUserData));
        when(ds.find(GameUserData.class)).thenReturn(query);

        Query<GameUserData> query3 = mock(Query.class);
        when(fieldEnd2.contains("FAIL")).thenReturn(query3);
        when(query3.asList()).thenReturn(Lists.newArrayList(gameUserData));

        Query<GameUserData> query4 = mock(Query.class);
        when(fieldEnd2.contains("INVALID")).thenReturn(query4);
        when(query4.asList()).thenReturn(Lists.newArrayList(gameUserData));

        Game game1 = new Game();
        game1.setId(1);
        game1.setGivenAnswers(Lists.newArrayList("toto", "titi"));
        game1.setType(ResponseType.SUCCESS);

        Game game2 = new Game();
        game2.setId(2);
        game2.setGivenAnswers(Lists.newArrayList("tutu1"));
        game2.setType(ResponseType.FAIL);


        Game game3 = new Game();
        game3.setId(3);
        game3.setGivenAnswers(Lists.newArrayList("tutu"));
        game3.setType(ResponseType.SUCCESS);

        //when
        when(gameUserData.getGames()).thenReturn(Lists.newArrayList(game1, game2, game3));

        //then
        List<Game> games = GameUserDataManager.INSTANCE.getGamesByResultType("user1", ResponseType.SUCCESS);
        assertTrue(games.size() == 2);
        assertTrue(games.get(0).getId() == 1);
        assertTrue(games.get(1).getId() == 3);

        games = GameUserDataManager.INSTANCE.getGamesByResultType("user1", ResponseType.FAIL);
        assertTrue(games.size() == 1);
        assertTrue(games.get(0).getId() == 2);

        games = GameUserDataManager.INSTANCE.getGamesByResultType("user1", ResponseType.INVALID);
        assertTrue(games.size() == 0);
    }
}
