package com.accolite.opportunitiesportal.testrepository;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.Assert;
import org.junit.jupiter.api.Test;


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
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestComponent;
import org.springframework.jdbc.core.JdbcTemplate;

import com.accolite.opportunitiesportal.auth.model.UserDetails;
import com.accolite.opportunitiesportal.auth.repository.UserRepository;
import com.accolite.opportunitiesportal.auth.rowmapper.UserDetailsRowMapper;
import com.accolite.opportunitiesportal.jobs.dao.JobsAttributeDao;
import com.accolite.opportunitiesportal.jobs.dao.JobsDao;
import com.accolite.opportunitiesportal.jobs.model.ChartDataObject;
import com.accolite.opportunitiesportal.jobs.model.DropDownItem;
import com.accolite.opportunitiesportal.jobs.model.JobDescription;
import com.accolite.opportunitiesportal.jobs.model.JobDescriptionWithSkills;
import com.accolite.opportunitiesportal.jobs.service.JobAttributeService;
import com.accolite.opportunitiesportal.jobs.service.JobsService;



@SpringBootTest
@TestComponent
@RunWith(MockitoJUnitRunner.class)
class UserRepositoryTest {
	
	@InjectMocks
	UserRepository repo;
	
	@Mock
	JdbcTemplate template;
	
	String idToken = "eyJhbGciOiJSUzI1NiIsImtpZCI6ImZiOGNhNWI3ZDhkOWE1YzZjNjc4ODA3MWU4NjZjNmM0MGYzZmMxZjkiLCJ0eXAiOiJKV1QifQ.eyJpc3MiOiJhY2NvdW50cy5nb29nbGUuY29tIiwiYXpwIjoiMzQzNjAwODM4NzM4LThxcDFrbzM4YmVzYmpsaXJjN292M2NhOTA4czQ3ZzRzLmFwcHMuZ29vZ2xldXNlcmNvbnRlbnQuY29tIiwiYXVkIjoiMzQzNjAwODM4NzM4LThxcDFrbzM4YmVzYmpsaXJjN292M2NhOTA4czQ3ZzRzLmFwcHMuZ29vZ2xldXNlcmNvbnRlbnQuY29tIiwic3ViIjoiMTE2MTAzNTk2MTg2MDY4MzY3NTMxIiwiaGQiOiJhY2NvbGl0ZWluZGlhLmNvbSIsImVtYWlsIjoicm9oaXQubWljaGFlbGdvbnNhbHZlc0BhY2NvbGl0ZWluZGlhLmNvbSIsImVtYWlsX3ZlcmlmaWVkIjp0cnVlLCJhdF9oYXNoIjoiSkU4WXozWTJJMjBfMUFvZFJjRDdrdyIsIm5hbWUiOiJSb2hpdCBNaWNoYWVsIEdvbnNhbHZlcyIsInBpY3R1cmUiOiJodHRwczovL2xoNC5nb29nbGV1c2VyY29udGVudC5jb20vLWlVR0hFZG1nMTVnL0FBQUFBQUFBQUFJL0FBQUFBQUFBQUFBL0FNWnV1Y21rc0xzcXlBSjdwUkhXX1BWS180UzFkaVZna3cvczk2LWMvcGhvdG8uanBnIiwiZ2l2ZW5fbmFtZSI6IlJvaGl0IiwiZmFtaWx5X25hbWUiOiJNaWNoYWVsIEdvbnNhbHZlcyIsImxvY2FsZSI6ImVuIiwiaWF0IjoxNTkxMDQ0NTk5LCJleHAiOjE1OTEwNDgxOTksImp0aSI6ImY4OGI1NjNiNDEzYmM4MWNiNzFkODU5ZTJkYWJmNzRjYjJhOGYzODkifQ.hxRgs1grhjHiSGwxnQyJZn-4FPhr8qB39mfQjK2OEQkQ08VjV9IQiw23woQJ3LJu6OQwbOTmaKKBwjKChNrDfjjcz9ANEajqyZbp4bDG328-kUHGhieLf1UHBLghuQwP2uOW5hf80L4wfwffFujki1JARuKzM1g40FK8__Y2KlP2P5J86pp9tl0GCTZ21rUASHAKKKGl1rbyV0qwVn4TWXX0nGt_rTL57Y0oajUmtsoLMckAw4Wk-GWUPPdysWf7YuzE1v80DiRdJm0fcm_c_CY00uqeo9-JkrdIR-wsNmu4lRWtwlV_2Yt9FmWw2XmE_rqtglwiKKFdPL2MVGBJoQ";
	
	UserDetails user = new UserDetails(1, "rohit.michaelgonsalves@accoliteindia.com", "Rohit Gonsalves", idToken);
	
	List<UserDetails> userList = Arrays.asList(user);

	@Test
	void testGetUserByIdInt() {
		when(template.queryForObject(Mockito.anyString(), Mockito.any(Object[].class), Mockito.any(UserDetailsRowMapper.class))).thenReturn(user);
		Assert.assertEquals(repo.getUserById(1), user);
	}

	@Test
	void testGetUserByIdString() {
		when(template.queryForObject(Mockito.anyString(), Mockito.any(Object[].class), Mockito.any(UserDetailsRowMapper.class))).thenReturn(user);
		Assert.assertEquals(repo.getUserById("rohit.michaelgonsalves@accoliteindia.com"), user);
	}

	@Test
	void testSaveUserDetails() {
		fail("Not yet implemented");
	}

	@Test
	void testSaveUserTokenByEmail() {
		when(template.update(Mockito.anyString(), Mockito.anyObject(), Mockito.anyObject())).thenReturn(1);
		Assert.assertEquals(true, repo.saveUserTokenByEmail(user.getAuthToken(), user.getEmail()));
	}

	@Test
	void testIsUserWithTokenPresent() {
		when(template.query(Mockito.anyString(), Mockito.any(Object[].class), Mockito.any(UserDetailsRowMapper.class))).thenReturn(userList);
		Assert.assertEquals(true, repo.isUserWithTokenPresent(user.getEmail()));
	}
	
	@Test
	void testIsUserWithTokenPresentFalse() {
		when(template.query(Mockito.anyString(), Mockito.any(Object[].class), Mockito.any(UserDetailsRowMapper.class))).thenReturn(Arrays.asList());
		Assert.assertEquals(false, repo.isUserWithTokenPresent(user.getEmail()));
	}

	@Test
	void testIsUserWithEmailPresent() {
		when(template.query(Mockito.anyString(), Mockito.any(Object[].class), Mockito.any(UserDetailsRowMapper.class))).thenReturn(userList);
		Assert.assertEquals(true, repo.isUserWithEmailPresent(user.getAuthToken()));
	}
	
	
	@Test
	void testIsUserWithEmailPresentFalse() {
		when(template.query(Mockito.anyString(), Mockito.any(Object[].class), Mockito.any(UserDetailsRowMapper.class))).thenReturn(Arrays.asList());
		Assert.assertEquals(false, repo.isUserWithEmailPresent(user.getAuthToken()));
	}

}
