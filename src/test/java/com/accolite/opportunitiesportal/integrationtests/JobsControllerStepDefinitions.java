package com.accolite.opportunitiesportal.integrationtests;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.mail.Session;

import org.apache.tomcat.JarScanType;
import org.junit.Assert;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.accolite.opportunitiesportal.OpportunitiesPortalApplicationTests;
import com.accolite.opportunitiesportal.auth.model.SessionStatus;
import com.accolite.opportunitiesportal.auth.model.SessionUser;
import com.accolite.opportunitiesportal.auth.model.UserDetails;
import com.accolite.opportunitiesportal.jobs.model.JobDescription;
import com.accolite.opportunitiesportal.jobs.model.JobDescriptionWithSkills;

import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class JobsControllerStepDefinitions extends OpportunitiesPortalApplicationTests {
	private ResponseEntity<List> response;
	private ResponseEntity<Map> mapResponse;
	private ResponseEntity<JobDescriptionWithSkills> jdResponse;
	private ResponseEntity<Boolean> boolResponseEntity;
	private ResponseEntity<SessionUser> sessionUserResponseEntity;
	private ResponseEntity<SessionStatus> sessionStatusResponseEntity;
	List<JobDescriptionWithSkills> joblist;

	static final String BASE_URL = "http://localhost:8081/api/jobs/";
	static final String SUB_URL = "JobDescription/";
	static final String VERSION_SUB_URL = "JobVersions/";

	String idToken = "eyJhbGciOiJSUzI1NiIsImtpZCI6ImZiOGNhNWI3ZDhkOWE1YzZjNjc4ODA3MWU4NjZjNmM0MGYzZmMxZjkiLCJ0eXAiOiJKV1QifQ.eyJpc3MiOiJhY2NvdW50cy5nb29nbGUuY29tIiwiYXpwIjoiMzQzNjAwODM4NzM4LThxcDFrbzM4YmVzYmpsaXJjN292M2NhOTA4czQ3ZzRzLmFwcHMuZ29vZ2xldXNlcmNvbnRlbnQuY29tIiwiYXVkIjoiMzQzNjAwODM4NzM4LThxcDFrbzM4YmVzYmpsaXJjN292M2NhOTA4czQ3ZzRzLmFwcHMuZ29vZ2xldXNlcmNvbnRlbnQuY29tIiwic3ViIjoiMTE2MTAzNTk2MTg2MDY4MzY3NTMxIiwiaGQiOiJhY2NvbGl0ZWluZGlhLmNvbSIsImVtYWlsIjoicm9oaXQubWljaGFlbGdvbnNhbHZlc0BhY2NvbGl0ZWluZGlhLmNvbSIsImVtYWlsX3ZlcmlmaWVkIjp0cnVlLCJhdF9oYXNoIjoiSkU4WXozWTJJMjBfMUFvZFJjRDdrdyIsIm5hbWUiOiJSb2hpdCBNaWNoYWVsIEdvbnNhbHZlcyIsInBpY3R1cmUiOiJodHRwczovL2xoNC5nb29nbGV1c2VyY29udGVudC5jb20vLWlVR0hFZG1nMTVnL0FBQUFBQUFBQUFJL0FBQUFBQUFBQUFBL0FNWnV1Y21rc0xzcXlBSjdwUkhXX1BWS180UzFkaVZna3cvczk2LWMvcGhvdG8uanBnIiwiZ2l2ZW5fbmFtZSI6IlJvaGl0IiwiZmFtaWx5X25hbWUiOiJNaWNoYWVsIEdvbnNhbHZlcyIsImxvY2FsZSI6ImVuIiwiaWF0IjoxNTkxMDQ0NTk5LCJleHAiOjE1OTEwNDgxOTksImp0aSI6ImY4OGI1NjNiNDEzYmM4MWNiNzFkODU5ZTJkYWJmNzRjYjJhOGYzODkifQ.hxRgs1grhjHiSGwxnQyJZn-4FPhr8qB39mfQjK2OEQkQ08VjV9IQiw23woQJ3LJu6OQwbOTmaKKBwjKChNrDfjjcz9ANEajqyZbp4bDG328-kUHGhieLf1UHBLghuQwP2uOW5hf80L4wfwffFujki1JARuKzM1g40FK8__Y2KlP2P5J86pp9tl0GCTZ21rUASHAKKKGl1rbyV0qwVn4TWXX0nGt_rTL57Y0oajUmtsoLMckAw4Wk-GWUPPdysWf7YuzE1v80DiRdJm0fcm_c_CY00uqeo9-JkrdIR-wsNmu4lRWtwlV_2Yt9FmWw2XmE_rqtglwiKKFdPL2MVGBJoQ";

	UserDetails validUserDetails = new UserDetails(0, "rohit.michaelgonsalves@accoliteindia.com", "Rohit Gonsalves",
			idToken);

	UserDetails invlidUserDetails = new UserDetails(0, "rohit.michaelgonsalves@gmail.com", "Rohit Gonsalves",
			"jddfjkdjgkojvx");

	SessionUser validUser = new SessionUser(0, "rohit.michaelgonsalves@accoliteindia.com", idToken);

	SessionUser invalidUser = new SessionUser(0, "rohit.michaelgonsalves@gmail.com", "jddfjkdjgkojvx");

	SessionStatus validSession = new SessionStatus(true, 200, "jddfjkdjgkojvx");
	SessionStatus invalidSession = new SessionStatus(false, 401, "jddfjkdjgkojvx");

	private JobDescription jobDesc = new JobDescription(9, 1, 1, 1, 1, 10, new Date(), 1, null, 0, "BEST JOB EVER");

	private List<Integer> skillList = Arrays.asList(new Integer[] { 1, 2, 3, 4, 5 });

	private JobDescriptionWithSkills jdWithSkills = new JobDescriptionWithSkills(jobDesc, skillList);
	private JobDescriptionWithSkills jdResponseObject;

	@When("client calls \\/JobDescription")
	public void client_calls_JobDescription() {
		// Write code here that turns the phrase above into concrete actions
		response = template.getForEntity(BASE_URL + SUB_URL, List.class);
	}

	@Then("the client receives a response status code {int}")
	public void the_client_receives_a_response_status_code(int int1) {
		HttpStatus responseCode = response.getStatusCode();
		Assert.assertEquals((int) responseCode.value(), int1);
	}

	@When("client calls \\/JobDescription\\/{int}")
	public void client_calls_JobDescription(Integer int1) {
		jdResponse = template.getForEntity(BASE_URL + SUB_URL + int1, JobDescriptionWithSkills.class);
	}

	@Then("the client receives JobDescription with a response status code {int}")
	public void the_client_receives_JobDescription_with_a_response_status_code(int int1) {
		HttpStatus responseCode = jdResponse.getStatusCode();
		Assert.assertEquals((int) responseCode.value(), int1);
	}

	@When("client calls \\/resolve\\/{int}")
	public void client_calls_resolve(Integer int1) {
		boolResponseEntity = template.getForEntity(BASE_URL + "resolve/" + int1, Boolean.class);
	}

	@Then("the job is resolved and client receives a response status code {int}")
	public void the_job_is_resolved_and_client_receives_a_response_status_code(int int1) {
		HttpStatus responseCode = boolResponseEntity.getStatusCode();
		Assert.assertEquals((int) responseCode.value(), int1);
	}

	@When("client calls \\/DropDownItems")
	public void client_calls_DropDownItems() {
		mapResponse = template.getForEntity(BASE_URL + "/DropDownItems", Map.class);
	}

	@Then("the ListMap is received and client receives a response status code {int}")
	public void the_ListMap_is_received_and_client_receives_a_response_status_code(int int1) {
		HttpStatus responseCode = mapResponse.getStatusCode();
		Assert.assertEquals((int) responseCode.value(), int1);
	}

	@When("client calls \\/JobVersions\\/{int}")
	public void client_calls_JobVersions(Integer int1) {
		response = template.getForEntity(BASE_URL + VERSION_SUB_URL + int1, List.class);
	}

	@Then("the client receives JobDescriptionWithSkills with response status code {int}")
	public void the_client_receives_JobDescriptionWithSkills_with_response_status_code(int int1) {
		HttpStatus responseCode = response.getStatusCode();
		Assert.assertEquals((int) responseCode.value(), int1);
	}

	@When("client calls \\/DropDownMap")
	public void client_calls_DropDownMap() {
		mapResponse = template.getForEntity(BASE_URL + "/DropDownMap", Map.class);
	}

	@Then("the MapMap is received and client receives a response status code {int}")
	public void the_MapMap_is_received_and_client_receives_a_response_status_code(int int1) {
		HttpStatus responseCode = mapResponse.getStatusCode();
		Assert.assertEquals((int) responseCode.value(), int1);

	}

	@When("client calls \\/JobVersions\\/version\\/{int}")
	public void client_calls_JobVersions_version(Integer int1) {
		jdResponse = template.getForEntity(BASE_URL + VERSION_SUB_URL + "version/" + int1,
				JobDescriptionWithSkills.class);
	}

	@Then("the client receives JobDescriptionWithSkills object with response status code {int}")
	public void the_client_receives_JobDescriptionWithSkills_object_with_response_status_code(int int1) {
		HttpStatus responseCode = jdResponse.getStatusCode();
		JobDescriptionWithSkills jdws = jdResponse.getBody();
		Assert.assertEquals(31, jdws.getJobDescription().getId());
		Assert.assertEquals((int) responseCode.value(), int1);
	}

	@When("client calls \\/jobInsights")
	public void client_calls_jobInsights() {
		mapResponse = template.getForEntity(BASE_URL + "/DropDownMap", Map.class);
	}

	@Then("the InsightMap is received and client receives a response status code {int}")
	public void the_InsightMap_is_received_and_client_receives_a_response_status_code(int int1) {
		HttpStatus responseCode = mapResponse.getStatusCode();

		Assert.assertEquals(int1, (int) responseCode.value());
	}

	@When("client calls \\/verifySession")
	public void client_calls_verifySession() {
		sessionStatusResponseEntity = template.postForEntity(BASE_URL + "verifySession", invalidUser.getToken(),
				SessionStatus.class);
	}

	@Then("the client gets a session object and receives a response status code {int}")
	public void the_client_gets_a_session_object_and_receives_a_response_status_code(Integer int1) {
		SessionStatus ss = sessionStatusResponseEntity.getBody();
		Assert.assertFalse(ss.isValid());
	}

	@When("client calls \\/signin\\/google")
	public void client_calls_signin_google() {
		sessionUserResponseEntity = template.postForEntity(BASE_URL + "signin/google", invlidUserDetails,
				SessionUser.class);
	}

	@Then("the client gets a SessionUser object and receives a response status code {int}")
	public void the_client_gets_a_SessionUser_object_and_receives_a_response_status_code(int int1) {
		Assert.assertEquals(int1, sessionUserResponseEntity.getStatusCode().value());
		Assert.assertEquals(-1, sessionUserResponseEntity.getBody().getId());
	}

	@When("client calls \\/delete\\/{int}")
	public void client_calls_delete(Integer int1) {
		boolResponseEntity = template.getForEntity(BASE_URL + "delete/9", Boolean.class);
	}

	@Then("the job is deleted and client receives a response status code {int}")
	public void the_job_is_deleted_and_client_receives_a_response_status_code(int int1) {
		// Write code here that turns the phrase above into concrete actions
		Assert.assertEquals(int1, boolResponseEntity.getStatusCode().value());
		Assert.assertTrue(boolResponseEntity.getBody());
	}

//	@When("client calls UPDATE \\/JobDescription")
//	public void client_calls_UPDATE_JobDescription() {
//	   jdResponseObject = template.postForObject(BASE_URL + SUB_URL + "update/", jdWithSkills, JobDescriptionWithSkills.class);
//	}
//
//	@Then("the job is updated and client receives a response status code {int}")
//	public void the_job_is_updated_and_client_receives_a_response_status_code(Integer int1) {
//		Assert.assertEquals(jdWithSkills.getJobDescription().getId(), jdResponseObject.getJobDescription().getId());
//	}
	
	
//	@When("client calls Post \\/JobDescription")
//	public void client_calls_Post_JobDescription() {
//	    jdResponse = template.postForEntity(BASE_URL + SUB_URL, jdWithSkills, JobDescriptionWithSkills.class);
//	}
//
//	@Then("the job is added and client receives a response status code {int}")
//	public void the_job_is_added_and_client_receives_a_response_status_code(int int1) {
//	    Assert.assertEquals(int1,jdResponse.getStatusCode().value());
//	    Assert.assertTrue(jdResponse.getBody().getJobDescription().getId() != 0);
//	}


}
