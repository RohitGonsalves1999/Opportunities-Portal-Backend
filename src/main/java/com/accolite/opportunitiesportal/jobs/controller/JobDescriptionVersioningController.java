package com.accolite.opportunitiesportal.jobs.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.accolite.opportunitiesportal.jobs.model.JobDescriptionWithSkills;
import com.accolite.opportunitiesportal.jobs.service.JobsService;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

/**
 * The Class JobDescriptionVersioningController.
 */
@RestController
@RequestMapping("/api/jobs")

/**
 * Instantiates a new job description versioning controller.
 *
 * @param jobsService the jobs service
 */
@AllArgsConstructor

/**
 * Instantiates a new job description versioning controller.
 */
@NoArgsConstructor
public class JobDescriptionVersioningController {
	
	/** The jobs service. */
	@Autowired
	JobsService jobsService;
	
	/**
	 * Gets the all job description versions.
	 *
	 * @param jobId the Original job description id
	 * @return the all job description versions
	 */
	@GetMapping("/JobVersions/{jobId}")
	public List<JobDescriptionWithSkills> getAllJobDescriptionVersions(@PathVariable int jobId){
		return jobsService.getallJobDescrptionVersions(jobId);
		
	}
	
	
	/**
	 * Gets the specific job description version by entry Id.
	 *
	 * @param id the Job Id
	 * @return the specific job description version
	 */
	@GetMapping("/JobVersions/version/{id}")
	public JobDescriptionWithSkills getSpecificJobDescriptionVersion(@PathVariable int id) {
		return jobsService.findJobDescriptionVersionById(id);
	}

}
