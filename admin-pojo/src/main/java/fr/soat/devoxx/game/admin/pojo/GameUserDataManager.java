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
import com.google.code.morphia.Morphia;
import com.mongodb.Mongo;
import fr.soat.devoxx.game.admin.pojo.exception.StorageException;
import fr.soat.devoxx.game.pojo.question.ResponseType;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

/**
 * User: khanh
 * Date: 23/12/11
 * Time: 20:25
 */
public enum GameUserDataManager {
    INSTANCE;

    private static final Logger LOGGER = LoggerFactory.getLogger(GameUserDataManager.class);
    public static final String MONGODB_PROPERTIES = "mongodb.properties";

    private PropertiesConfiguration configuration;
    
    Datastore ds = null;

    private GameUserDataManager() {
        try {
            this.configuration = new PropertiesConfiguration(MONGODB_PROPERTIES);
        } catch (ConfigurationException e) {
            //NOTHING TO DO
        }

        String host = this.configuration.getString("mongodb.host", "localhost");
        int port = this.configuration.getInt("mongodb.port", 27017);
        String dbName = this.configuration.getString("mongodb.dbname", "devoxx");
        String login = this.configuration.getString("mongodb.login", "");
        String password = this.configuration.getString("mongodb.password", "");
        
        Mongo mongo = null;
        try {
            mongo = new Mongo(host, port);
            if (!StringUtils.isEmpty(login) || !StringUtils.isEmpty(password)) {
                mongo.getDB(dbName).authenticate(login, password.toCharArray());
            }
            ds = new Morphia().createDatastore(mongo, dbName);

        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }

    public GameUserData getGames(String userName) {
        return ds.find(GameUserData.class).field("name").equal(userName).get();
    }
    
    public List<Game> getGamesByResultType(String userName, ResponseType responseType) {
        List<Game>  result = new ArrayList<Game>();
        if (ds != null) {
            List<GameUserData> gameUserDatas = ds.find(GameUserData.class).field("name").equal(userName).field("games.type").contains(responseType.name()).asList();
            for (GameUserData gameUserData : gameUserDatas) {
                for (Game game : gameUserData.getGames()) {
                    if (game.getType() == responseType) {
                        result.add(game);
                    }
                }
            }
        } else {
            LOGGER.error("unable to access to mongoDb: datastore is unknown: ");
        }
        return result;
    }

    public void registerUser(String name) {
        GameUserData gameUserData = new GameUserData();
        gameUserData.setName(name);
        if (ds != null) {
            ds.save(gameUserData);
            LOGGER.debug("user {} registered in mongoDb", name);
        } else {
            LOGGER.error("unable to save user {}: datastore is unknown: ", name);
        }
    }

    public void destroyUser(String name) {
        if (ds != null) {
            GameUserData entity = ds.get(GameUserData.class, name);
            if (entity != null) {
                ds.delete(entity);
            }
            LOGGER.debug("user {} is destroyed from mongoDb", name);
        } else {
            LOGGER.error("unable to destroy user {}: datastore is unknown: ", name);
        }
    }

    public void addGame(String userName, Game game) throws StorageException {
        if (ds != null) {
            GameUserData gameUserData = ds.find(GameUserData.class).field("name").equal(userName).get();
            if (gameUserData == null) {
                LOGGER.error("insertion error: unable to find the user {}... the game {} will not be inserted", userName, game);
                throw new StorageException("unable to find user " + userName);
            }
            gameUserData.addGame(game);
            ds.save(gameUserData);
        } else {
            LOGGER.error("unable to save game {} for user {}: datastore is unknown: ", game, userName);
        }

        //UpdateOperations<User> ops = datastore.createUpdateOperations(User.class).set("lastLogin", now);
//        ds.update(queryToFindMe(), ops);
    }

    public GameResult getResult(String userName) {
        GameResult result = new GameResult();
        result.setNbSuccess(getGamesByResultType(userName, ResponseType.SUCCESS).size());
        result.setNbFail(getGamesByResultType(userName, ResponseType.FAIL).size());
        result.setNbInvalid(getGamesByResultType(userName, ResponseType.INVALID).size());
        return result;
    }

}
