package fr.soat.devoxx.game.persistent.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotSame;

import org.junit.Test;

public class UserUtilsTest {
	
	@Test
    public void generateTokenShouldReturnDifferentResults() {
        assertNotSame(UserUtils.INSTANCE.generateToken(), UserUtils.INSTANCE.generateToken());
    }
	
	@Test
    public void getUserTokenShouldReturnAnEmptySizeIfPropertiesAreNotFound() {
		UserUtils userUtils = UserUtils.INSTANCE;
        userUtils.setConfiguration("nothing");
        assertEquals(15, userUtils.getUserTokenLenght());
    }

    @Test
    public void getUserTokenReturnSizeIfPropertiesAreFound() {
    	UserUtils userUtils = UserUtils.INSTANCE;
    	userUtils.reload();
        assertEquals(10, userUtils.getUserTokenLenght());
    }
}
