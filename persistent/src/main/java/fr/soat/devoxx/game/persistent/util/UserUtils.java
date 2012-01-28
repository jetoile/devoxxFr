package fr.soat.devoxx.game.persistent.util;

import java.util.NoSuchElementException;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.lang.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * User: aurelien
 * Date: 27/01/12
 * Time: 13:00
 */
public enum UserUtils {
    INSTANCE;

    private static final Logger LOGGER = LoggerFactory.getLogger(UserUtils.class);
    
    private static final String USER_PROPERTIES_FILENAME = "user.properties";    
    private static final String USER_TOKEN_LENGHT = "user.token.lenght";    
    private static final int USER_TOKEN_LENGHT_DEFAULT_VALUE = 15;

    private PropertiesConfiguration configuration;

    private UserUtils() {
        try {
            this.configuration = new PropertiesConfiguration(USER_PROPERTIES_FILENAME);
            this.configuration.setThrowExceptionOnMissing(true);
        } catch (ConfigurationException e) {
            //NOTHING TO DO
        }
    }

    public void reload() {
        setConfiguration(USER_PROPERTIES_FILENAME);
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
            LOGGER.warn("unable to get {} in {}: will use default value {}", new String[] {USER_TOKEN_LENGHT, USER_PROPERTIES_FILENAME, Integer.toString(USER_TOKEN_LENGHT_DEFAULT_VALUE)});
            return USER_TOKEN_LENGHT_DEFAULT_VALUE;
        }
    }
    
    public String generateToken() {
        return RandomStringUtils.randomAlphanumeric(getUserTokenLenght()).toLowerCase();
    }
}
