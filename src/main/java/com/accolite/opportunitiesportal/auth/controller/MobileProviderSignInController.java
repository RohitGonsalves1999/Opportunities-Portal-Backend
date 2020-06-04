package com.accolite.opportunitiesportal.auth.controller;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.security.GeneralSecurityException;
import java.util.Collections;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.accolite.opportunitiesportal.auth.model.SessionStatus;
import com.accolite.opportunitiesportal.auth.model.SessionUser;
import com.accolite.opportunitiesportal.auth.model.UserDetails;
import com.accolite.opportunitiesportal.auth.repository.UserRepository;
import com.accolite.opportunitiesportal.auth.security.SessionCrypt;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken.Payload;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.Value;

/**
 * The Class MobileProviderSignInController.
 */
@RestController
@RequestMapping("/api/jobs")
public class MobileProviderSignInController {

	/** The Constant logger. */
	private static final Logger logger = LoggerFactory.getLogger(MobileProviderSignInController.class);

	/** The session crypt. */
	private SessionCrypt sessionCrypt = new SessionCrypt();

	/** The client id. */
	@Value("${google.clientId}")
	private String clientId = "343600838738-8qp1ko38besbjlirc7ov3ca908s47g4s.apps.googleusercontent.com";

	/** The user repo. */
	@Autowired
	UserRepository userRepo;

	/**
	 * Oauth 2 callback.
	 *
	 * @param providerId the provider id (Namely Google)
	 * @param sessionUser the The User details Object
	 * @return the session user Object with token and id
	 */
	@PostMapping(value = "/signin/{providerId}")
	public SessionUser oauth2Callback(@PathVariable String providerId, @RequestBody UserDetails sessionUser) {

		try {

			URLDecoder.decode(sessionUser.getAuthToken(), StandardCharsets.UTF_8.toString());

			HttpTransport transport = GoogleNetHttpTransport.newTrustedTransport();
			JsonFactory jsonFactory = new JacksonFactory();

			GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(transport, jsonFactory)
					.setAudience(Collections.singletonList(clientId)).build();
			
			String logMsg = String.format("IDToken: %s  ClientId: %s", sessionUser.getAuthToken(), clientId);
			logger.info(logMsg);
			
			if (!(sessionUser.getEmail().contains("@accoliteindia.com"))) {
				throw new GeneralSecurityException();
			}

			logMsg = String.format("User: %s", sessionUser.toString());
			logger.info(logMsg);
			GoogleIdToken idToken = verifier.verify(sessionUser.getAuthToken());

			if (idToken != null) {
				Payload payload = idToken.getPayload();
				String message = String.format("%s", payload.toString());
				logger.info(message);

			} else {
				logger.error("Invalid ID token.");
			}
			

			String result = sessionCrypt.encrypt(sessionUser.getEmail());
			UserDetails user = new UserDetails(0, sessionUser.getEmail(), sessionUser.getName(), result);
			int userId = 0;
			if (!userRepo.isUserWithEmailPresent(sessionUser.getEmail())) {
				userId = userRepo.saveUserDetails(user);
			} else {
				userId = userRepo.getUserById(sessionUser.getEmail()).getId();
				userRepo.saveUserTokenByEmail(result, sessionUser.getEmail());
			}
			
			SessionUser sessionUser2 = new SessionUser(userId, sessionUser.getEmail(), result);
			String msg = String.format("Session User: %s", sessionUser2.toString());
			logger.debug(msg);
			return sessionUser2;

		}
		 catch (Exception e) {

			logger.error("Exception while completing OAuth 2 connection: ", e);
			return new SessionUser(-1, "", "");
		}
	}

	/**
	 * Verify session.
	 *
	 * @param authToken the auth token
	 * @return the session status
	 */
	@PostMapping("/verifySession")
	public SessionStatus verifySession(@RequestBody String authToken) {

		String messge;
		if (authToken != null && authToken != "" && userRepo.isUserWithTokenPresent(authToken)) {
			SessionStatus success = new SessionStatus(true, 200, authToken);
			messge = String.format("Session Verification Successful: %s", success.toString());
			logger.debug(messge);
			return success;
		} else {
			SessionStatus faliure = new SessionStatus(false, 401, authToken);
			messge = String.format("Session Verification Failed: %s", faliure.toString());
			logger.debug(messge);
			return new SessionStatus(false, 401, authToken);
		}

	}
}
