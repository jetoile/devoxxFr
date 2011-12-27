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

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.NoSuchElementException;

/**
 * User: khanh
 * Date: 19/12/11
 * Time: 12:03
 */
//@Path("/gridService")
public enum PropertiesUtils {
    INSTANCE;

    private static final Logger LOGGER = LoggerFactory.getLogger(PropertiesUtils.class);
    
    private static final String GAME_PROPERTIES_FILENAME = "game.properties";
    
    private static final String USER_TOKEN_LENGHT = "user.token.lenght";
    private static final String GAME_QUESTION_FILE_PATH = "questions.file.path";
    
    private static final int USER_TOKEN_LENGHT_DEFAULT_VALUE = 15;
    private static final String GAME_QUESTION_FILE_PATH_DEFAULT_VALUE="question.properties";



    private PropertiesConfiguration configuration;

    private PropertiesUtils() {
        try {
            this.configuration = new PropertiesConfiguration(GAME_PROPERTIES_FILENAME);
        } catch (ConfigurationException e) {
            //NOTHING TO DO
        }
    }

    public void reload() {
        setConfiguration(GAME_PROPERTIES_FILENAME);
    }

    void setConfiguration(String fileName) {
        this.configuration.clear();
        try {
            this.configuration.load(fileName);
        } catch (ConfigurationException e) {
            //NOTHING TO DO
        }
    }

    public int getUserTokenLenght() {
        try {
            return this.configuration.getInt(USER_TOKEN_LENGHT);
        } catch (NoSuchElementException e) {
            LOGGER.warn("unable to get {} in {}: will use default value {}", new String[] {USER_TOKEN_LENGHT, GAME_PROPERTIES_FILENAME, Integer.toString(USER_TOKEN_LENGHT_DEFAULT_VALUE)});
            return USER_TOKEN_LENGHT_DEFAULT_VALUE;
        }
    }

    public String getQuestionFilePath() {
        try {
            return this.configuration.getString(GAME_QUESTION_FILE_PATH);
        } catch (NoSuchElementException e) {
            LOGGER.warn("unable to get {} in {}: will use default value {}", new String[] {GAME_QUESTION_FILE_PATH, GAME_PROPERTIES_FILENAME, GAME_QUESTION_FILE_PATH_DEFAULT_VALUE});
            return GAME_QUESTION_FILE_PATH_DEFAULT_VALUE;
        }
    }

    /*@Path("/height")
    @GET
    @Produces("application/json")
    public String getHeight() {
        try {
            return "" + this.configuration.getInt(GRID_HEIGHT_KEY);
        } catch (NoSuchElementException e) {
            LOGGER.warn("unable to get {} in {}: will use default value {}", new String[] {GRID_HEIGHT_KEY, GAME_PROPERTIES_FILENAME, Integer.toString(GRID_HEIGHT_DEFAULT_VALUE)});
            return "" + GRID_HEIGHT_DEFAULT_VALUE;
        }
    }

    @Path("/width")
    @GET
    @Produces("application/json")
    public String getWidth() {
        try {
            return "" + this.configuration.getInt(GRID_WIDTH_KEY);
        } catch (NoSuchElementException e) {
            LOGGER.warn("unable to get {} in {}: will use default value {}", new String[] {GRID_WIDTH_KEY, GAME_PROPERTIES_FILENAME, Integer.toString(GRID_WIDTH_DEFAULT_VALUE)});
            return "" + GRID_WIDTH_DEFAULT_VALUE;
        }
    }*/


}
