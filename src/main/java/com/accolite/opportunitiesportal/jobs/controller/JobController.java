package com.accolite.opportunitiesportal.jobs.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.accolite.opportunitiesportal.jobs.model.JobDescriptionWithSkills;
import com.accolite.opportunitiesportal.jobs.service.JobsService;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * The Class JobController.
 */
@RestController
@RequestMapping("/api/jobs")

/** The Constant log. */
@Slf4j

/**
 * Instantiates a new job controller.
 *
 * @param jobsService the jobs service
 */
@AllArgsConstructor

/**
 * Instantiates a new job controller.
 */
@NoArgsConstructor
public class JobController {
	
	/** The jobs service. */
	@Autowired
	JobsService jobsService;
	
	/**
	 * Hello world.
	 *
	 * @return the string
	 */
	@GetMapping("/hellos")
	public String helloWorld() {
		return "HELLO WORLD";
	}
	
	/**
	 * Adds the job description to the database.
	 *
	 * @param desc the JobDescrpition with Skills
	 * @return the job description with skills
	 */
	@PostMapping("/JobDescription")
	public JobDescriptionWithSkills addJobDescription(@RequestBody JobDescriptionWithSkills desc) {
		log.debug("Save JobDescription: %s", desc.toString());
		return jobsService.addJobDescription(desc);
	}
	
	/**
	 * Find List of Job Descriptions.
	 *
	 * @return the list of Job Description
	 */
	@GetMapping("/JobDescription/")
	public List<JobDescriptionWithSkills> findJobDesccriptionById() {
		log.debug("Find All jobDescriptions");
		return jobsService.getAllJobDescriptions();
	}
	
	/**
	 * Find job description by id.
	 *
	 * @param id the id
	 * @return the job description with skills
	 */
	@GetMapping("/JobDescription/{id}")
	public JobDescriptionWithSkills findJobDesccriptionById(@PathVariable int id) {
		log.debug("Find All jobDescriptions by id: %d", id);
		return jobsService.findJobDescriptionbyId(id);
	}
	
	/**
	 * Resolve job description.
	 *
	 * @param id the id
	 * @return true, if successful
	 */
	@GetMapping("/resolve/{id}")
	public boolean resolveJobDescription(@PathVariable int id) {
		log.debug("Resolve  by id: %d", id);
		jobsService.resolveJobDescription(id);
		return true;
		
	}
	
	/**
	 * Update job description.
	 *
	 * @param desc the desc
	 * @return the job description with skills
	 */
	@PostMapping("/JobDescription/update")
	public JobDescriptionWithSkills updateJobDescription(@RequestBody JobDescriptionWithSkills desc) {
		log.debug("Update: %d", desc.toString());
		return jobsService.updateJobDescriptionWithSkills(desc);
	}
	
	/**
	 * Delete job description.
	 *
	 * @param id the id
	 * @return true, if successful
	 */
	@GetMapping("/delete/{id}")
	public boolean deleteJobDescription(@PathVariable int id) {
		log.debug("Delete by id: %d", id);
		jobsService.deleteJobDescription(id);
		return true;
	}

}
