package com.accolite.opportunitiesportal.integrationtests;

import java.util.List;
import java.util.Map;

import org.apache.tomcat.JarScanType;
import org.junit.Assert;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.accolite.opportunitiesportal.OpportunitiesPortalApplicationTests;
import com.accolite.opportunitiesportal.jobs.model.JobDescriptionWithSkills;

import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class JobsControllerStepDefinitions extends OpportunitiesPortalApplicationTests {
	private ResponseEntity<List> response;
	private ResponseEntity<Map> mapResponse;
	private ResponseEntity<JobDescriptionWithSkills> jdResponse;
	private ResponseEntity<Boolean> boolResponseEntity;
	List<JobDescriptionWithSkills> joblist;

	static final String BASE_URL = "http://localhost:8081/api/jobs/";
	static final String SUB_URL = "JobDescription/";
	static final String VERSION_SUB_URL = "JobVersions/";

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
		jdResponse = template.getForEntity(BASE_URL + VERSION_SUB_URL + "version/" + int1, JobDescriptionWithSkills.class);
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
		
		Assert.assertEquals(int1,(int) responseCode.value());
	}

}
