package com.accolite.opportunitiesportal.testcontrollers;

import static org.mockito.Mockito.when;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestComponent;

import com.accolite.opportunitiesportal.auth.controller.MobileProviderSignInController;
import com.accolite.opportunitiesportal.auth.model.SessionStatus;
import com.accolite.opportunitiesportal.auth.model.SessionUser;
import com.accolite.opportunitiesportal.auth.model.UserDetails;
import com.accolite.opportunitiesportal.auth.repository.UserRepository;



@SpringBootTest
@TestComponent
@RunWith(MockitoJUnitRunner.class)
class MobileProviderSignInControllerTest {
	
	
	@InjectMocks
	MobileProviderSignInController controller;
	
	
	@Mock
	UserRepository repo;
	
	String idToken = "eyJhbGciOiJSUzI1NiIsImtpZCI6ImZiOGNhNWI3ZDhkOWE1YzZjNjc4ODA3MWU4NjZjNmM0MGYzZmMxZjkiLCJ0eXAiOiJKV1QifQ.eyJpc3MiOiJhY2NvdW50cy5nb29nbGUuY29tIiwiYXpwIjoiMzQzNjAwODM4NzM4LThxcDFrbzM4YmVzYmpsaXJjN292M2NhOTA4czQ3ZzRzLmFwcHMuZ29vZ2xldXNlcmNvbnRlbnQuY29tIiwiYXVkIjoiMzQzNjAwODM4NzM4LThxcDFrbzM4YmVzYmpsaXJjN292M2NhOTA4czQ3ZzRzLmFwcHMuZ29vZ2xldXNlcmNvbnRlbnQuY29tIiwic3ViIjoiMTE2MTAzNTk2MTg2MDY4MzY3NTMxIiwiaGQiOiJhY2NvbGl0ZWluZGlhLmNvbSIsImVtYWlsIjoicm9oaXQubWljaGFlbGdvbnNhbHZlc0BhY2NvbGl0ZWluZGlhLmNvbSIsImVtYWlsX3ZlcmlmaWVkIjp0cnVlLCJhdF9oYXNoIjoiSkU4WXozWTJJMjBfMUFvZFJjRDdrdyIsIm5hbWUiOiJSb2hpdCBNaWNoYWVsIEdvbnNhbHZlcyIsInBpY3R1cmUiOiJodHRwczovL2xoNC5nb29nbGV1c2VyY29udGVudC5jb20vLWlVR0hFZG1nMTVnL0FBQUFBQUFBQUFJL0FBQUFBQUFBQUFBL0FNWnV1Y21rc0xzcXlBSjdwUkhXX1BWS180UzFkaVZna3cvczk2LWMvcGhvdG8uanBnIiwiZ2l2ZW5fbmFtZSI6IlJvaGl0IiwiZmFtaWx5X25hbWUiOiJNaWNoYWVsIEdvbnNhbHZlcyIsImxvY2FsZSI6ImVuIiwiaWF0IjoxNTkxMDQ0NTk5LCJleHAiOjE1OTEwNDgxOTksImp0aSI6ImY4OGI1NjNiNDEzYmM4MWNiNzFkODU5ZTJkYWJmNzRjYjJhOGYzODkifQ.hxRgs1grhjHiSGwxnQyJZn-4FPhr8qB39mfQjK2OEQkQ08VjV9IQiw23woQJ3LJu6OQwbOTmaKKBwjKChNrDfjjcz9ANEajqyZbp4bDG328-kUHGhieLf1UHBLghuQwP2uOW5hf80L4wfwffFujki1JARuKzM1g40FK8__Y2KlP2P5J86pp9tl0GCTZ21rUASHAKKKGl1rbyV0qwVn4TWXX0nGt_rTL57Y0oajUmtsoLMckAw4Wk-GWUPPdysWf7YuzE1v80DiRdJm0fcm_c_CY00uqeo9-JkrdIR-wsNmu4lRWtwlV_2Yt9FmWw2XmE_rqtglwiKKFdPL2MVGBJoQ";
	
	
	UserDetails validUserDetails = new UserDetails(0, "rohit.michaelgonsalves@accoliteindia.com", "Rohit Gonsalves", idToken);
	
	UserDetails invlidUserDetails = new UserDetails(0, "rohit.michaelgonsalves@gmail.com", "Rohit Gonsalves", "jddfjkdjgkojvx");
	
	SessionUser validUser = new SessionUser(0, "rohit.michaelgonsalves@accoliteindia.com", idToken);
	
	SessionUser invalidUser = new SessionUser(0, "rohit.michaelgonsalves@gmail.com", "jddfjkdjgkojvx");

	SessionStatus validSession = new SessionStatus(true,200,"jddfjkdjgkojvx");
	SessionStatus invalidSession = new SessionStatus(false,401,"jddfjkdjgkojvx");
	
	@Test
	void testOauth2Callback() {
		when(repo.isUserWithEmailPresent("rohit.michaelgonsalves@accoliteindia.com")).thenReturn(true);
		when(repo.getUserById("rohit.michaelgonsalves@accoliteindia.com")).thenReturn(validUserDetails);
		Assert.assertEquals(validUser.getEmail(), controller.oauth2Callback("google", validUserDetails).getEmail());
	}
	
	
	
	@Test
	void testOauth2CallbackInvalid() {
		when(repo.isUserWithEmailPresent("rohit.michaelgonsalves@accoliteindia.com")).thenReturn(false);
		when(repo.getUserById("rohit.michaelgonsalves@accoliteindia.com")).thenReturn(validUserDetails);
		Assert.assertEquals(validUser.getEmail(), controller.oauth2Callback("google", validUserDetails).getEmail());
	}

	@Test
	void testVerifySessionValid() {
		when(repo.isUserWithTokenPresent("jddfjkdjgkojvx")).thenReturn(true);
		Assert.assertEquals(validSession.getStatus(), controller.verifySession("jddfjkdjgkojvx").getStatus());
	}
	
	
	@Test
	void testVerifySessionInValid() {
		when(repo.isUserWithTokenPresent("jddfjkdjgkojvx")).thenReturn(false);
		Assert.assertEquals(invalidSession.getStatus(), controller.verifySession("jddfjkdjgkojvx").getStatus());
	}

}
