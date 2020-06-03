package com.accolite.opportunitiesportal.jobs.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.accolite.opportunitiesportal.jobs.model.JobDescriptionWithSkills;
import com.accolite.opportunitiesportal.jobs.service.JobsService;

@RestController
@RequestMapping("/api/jobs")
public class JobDescriptionVersioningController {
	
	@Autowired
	JobsService jobsService;
	
	@GetMapping("/JobVersions/{jobId}")
	public List<JobDescriptionWithSkills> getAllJobDescriptionVersions(@PathVariable int jobId){
		return jobsService.getallJobDescrptionVersions(jobId);
		
	}
	
	
	@GetMapping("/JobVersions/version/{id}")
	public JobDescriptionWithSkills getSpecificJobDescriptionVersion(@PathVariable int id) {
		return jobsService.findJobDescriptionVersionById(id);
	}

}
