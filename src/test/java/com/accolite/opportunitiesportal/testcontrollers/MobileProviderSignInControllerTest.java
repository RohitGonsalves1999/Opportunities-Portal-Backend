package com.accolite.opportunitiesportal.testcontrollers;

import static org.junit.Assert.fail;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestComponent;

import com.accolite.opportunitiesportal.auth.controller.MobileProviderSignInController;
import com.accolite.opportunitiesportal.auth.model.SessionStatus;
import com.accolite.opportunitiesportal.auth.model.SessionUser;
import com.accolite.opportunitiesportal.auth.model.UserDetails;
import com.accolite.opportunitiesportal.auth.repository.UserRepository;
import com.accolite.opportunitiesportal.jobs.dao.JobsDao;
import com.accolite.opportunitiesportal.jobs.model.ChartDataObject;
import com.accolite.opportunitiesportal.jobs.model.DropDownItem;
import com.accolite.opportunitiesportal.jobs.model.JobDescription;
import com.accolite.opportunitiesportal.jobs.model.JobDescriptionWithSkills;
import com.accolite.opportunitiesportal.jobs.service.JobsService;

import junit.framework.Assert;

@SpringBootTest
@TestComponent
@RunWith(MockitoJUnitRunner.class)
class MobileProviderSignInControllerTest {
	
	
	@InjectMocks
	MobileProviderSignInController controller;
	
	
	@Mock
	UserRepository repo;
	
	
	UserDetails validUserDetails = new UserDetails(0, "rohit.michaelgonsalves@accoliteindia.com", "Rohit Gonsalves", "jddfjkdjgkojvx");
	
	UserDetails invlidUserDetails = new UserDetails(0, "rohit.michaelgonsalves@gmail.com", "Rohit Gonsalves", "jddfjkdjgkojvx");
	
	SessionUser validUser = new SessionUser(0, "rohit.michaelgonsalves@accoliteindia.com", "jddfjkdjgkojvx");
	
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
