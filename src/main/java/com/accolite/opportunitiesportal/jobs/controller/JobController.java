package com.accolite.opportunitiesportal.jobs.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.accolite.opportunitiesportal.jobs.model.JobDescriptionWithSkills;
import com.accolite.opportunitiesportal.jobs.service.JobsService;

@RestController
@RequestMapping("/api/jobs")

public class JobController {
	
	@Autowired
	JobsService jobsService;
	
	@GetMapping("/hellos")
	public String helloWorld() {
		return "HELLO WORLD";
	}
	
	@PostMapping("/JobDescription")
	public JobDescriptionWithSkills addJobDescription(@RequestBody JobDescriptionWithSkills desc) {
		
		return jobsService.addJobDescription(desc);
	}
	
	@GetMapping("/JobDescription/")
	public List<JobDescriptionWithSkills> findJobDesccriptionById() {
		return jobsService.getAllJobDescriptions();
	}
	
	@GetMapping("/JobDescription/{id}")
	public JobDescriptionWithSkills findJobDesccriptionById(@PathVariable int id) {
		return jobsService.findJobDescriptionbyId(id);
	}
	
	@GetMapping("/resolve/{id}")
	public boolean resolveJobDescription(@PathVariable int id) {
		jobsService.resolveJobDescription(id);
		return true;
		
	}
	
	@PutMapping("/JobDescription")
	public  JobDescriptionWithSkills updateJobDescription(@RequestBody JobDescriptionWithSkills desc) {
		return jobsService.updateJobDescriptionWithSkills(desc);
	}
	
	@GetMapping("/delete/{id}")
	public boolean deleteJobDescription(@PathVariable int id) {
		jobsService.deleteJobDescription(id);
		return true;
	}

}
