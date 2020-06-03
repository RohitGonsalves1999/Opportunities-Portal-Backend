package com.accolite.opportunitiesportal.testutils;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.TestComponent;

import com.accolite.opportunitiesportal.auth.security.SessionCrypt;




@TestComponent
@RunWith(MockitoJUnitRunner.class)
class SessionCryptTest {
	
	
	SessionCrypt sessionCrypt = new SessionCrypt();

	
	String token = "CFDEF3D683CB4322F3C018F5D03E73B8D9EB5346AFD2AB28B316D19CD58DE7BD6BCCA62C757EFCDB3FBD7FA3013CBDD0AE6B32B6C3BBD592AA1BF81A84F07C2BFDEDA653";
	String emailId = "rohit.michaelgonsalves@accoliteindia.com";
	@Test
	void testEncrypt() {
		Assert.assertEquals(token, sessionCrypt.encrypt(emailId));
	}

	@Test
	void testDecrypt() {
		Assert.assertEquals(emailId, sessionCrypt.decrypt(token));
	}
	
	@Test
	void testDecryptFail() {
		Assert.assertEquals(null, sessionCrypt.decrypt(null));
	}

}
