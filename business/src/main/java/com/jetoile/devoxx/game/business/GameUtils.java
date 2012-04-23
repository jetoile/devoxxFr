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
package com.jetoile.devoxx.game.business;

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
public enum GameUtils {
    INSTANCE;

    private static final Logger LOGGER = LoggerFactory.getLogger(GameUtils.class);
    
    private static final String GAME_PROPERTIES_FILENAME = "game.properties";
    private static final String GAME_QUESTION_FILE_PATH = "questions.file.path";    
    private static final String GAME_QUESTION_FILE_PATH_DEFAULT_VALUE="question.properties";

    private PropertiesConfiguration configuration;

    private GameUtils() {
        try {
            this.configuration = new PropertiesConfiguration(GAME_PROPERTIES_FILENAME);
            this.configuration.setThrowExceptionOnMissing(true);
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

    public String getQuestionFilePath() {
        try {
            return this.configuration.getString(GAME_QUESTION_FILE_PATH);
        } catch (NoSuchElementException e) {
            LOGGER.warn("unable to get {} in {}: will use default value {}", new String[] {GAME_QUESTION_FILE_PATH, GAME_PROPERTIES_FILENAME, GAME_QUESTION_FILE_PATH_DEFAULT_VALUE});
            return GAME_QUESTION_FILE_PATH_DEFAULT_VALUE;
        }
    }
}
