package com.accolite.opportunitiesportal.auth.controller;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.NativeWebRequest;

import com.accolite.opportunitiesportal.auth.model.AuthProvider;
import com.accolite.opportunitiesportal.auth.model.SessionStatus;
import com.accolite.opportunitiesportal.auth.model.SessionUser;
import com.accolite.opportunitiesportal.auth.model.UserDetails;
import com.accolite.opportunitiesportal.auth.repository.UserRepository;
import com.accolite.opportunitiesportal.auth.security.SessionCrypt;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeTokenRequest;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleTokenResponse;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;

@RestController
@RequestMapping("/api/jobs")
public class MobileProviderSignInController {
	
	private static final Logger logger=LoggerFactory.getLogger(MobileProviderSignInController.class);
	
	private SessionCrypt sessionCrypt = new SessionCrypt();
	
	@Autowired
	UserRepository userRepo;

	
	@RequestMapping(value="/signin/{providerId}", method=RequestMethod.POST)
	public SessionUser oauth2Callback(@PathVariable String providerId, @RequestBody UserDetails sessionUser) {
		
		try {
			String decodedCode = URLDecoder.decode(sessionUser.getAuthToken(), StandardCharsets.UTF_8.toString());
			logger.info("google authentication code " + sessionUser.getAuthToken() + "\n email is => " + sessionUser.getEmail() + "\n decoded tok =>" + decodedCode);
			
			if(!(sessionUser.getEmail().contains("@accoliteindia.com"))) {
				throw new Exception();
			}
			
			String result = sessionCrypt.encrypt(sessionUser.getEmail());
			UserDetails user = new UserDetails(0, sessionUser.getEmail(), sessionUser.getName(), result);
			int userId = 0;
			if(!userRepo.isUserWithEmailPresent(sessionUser.getEmail())) {
				userId  = userRepo.saveUserDetails(user);
			}
			else {
				userId = userRepo.getUserById(sessionUser.getEmail()).getId();
			}
			
			
			
			
			return new SessionUser(userId, sessionUser.getEmail(), result);
		
		} catch (Exception e) {
			
			logger.error("Exception while completing OAuth 2 connection: ", e);
			return new SessionUser(-1, "", "");	}
	}
	
	
	@PostMapping("/verifySession")
	public SessionStatus verifySession(@RequestBody String authToken) {
		 	
		if(userRepo.isUserWithTokenPresent(authToken)) {
			return new SessionStatus(true, 200, authToken);
		}
		else {
			return new SessionStatus(false,401,authToken);
		}
		
	}
}
