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

@RestController
@RequestMapping("/api/jobs")
@Slf4j
@AllArgsConstructor
@NoArgsConstructor
public class JobController {
	
	@Autowired
	JobsService jobsService;
	
	@GetMapping("/hellos")
	public String helloWorld() {
		return "HELLO WORLD";
	}
	
	@PostMapping("/JobDescription")
	public JobDescriptionWithSkills addJobDescription(@RequestBody JobDescriptionWithSkills desc) {
		log.debug("Save JobDescription: %s", desc.toString());
		return jobsService.addJobDescription(desc);
	}
	
	@GetMapping("/JobDescription/")
	public List<JobDescriptionWithSkills> findJobDesccriptionById() {
		log.debug("Find All jobDescriptions");
		return jobsService.getAllJobDescriptions();
	}
	
	@GetMapping("/JobDescription/{id}")
	public JobDescriptionWithSkills findJobDesccriptionById(@PathVariable int id) {
		log.debug("Find All jobDescriptions by id: %d", id);
		return jobsService.findJobDescriptionbyId(id);
	}
	
	@GetMapping("/resolve/{id}")
	public boolean resolveJobDescription(@PathVariable int id) {
		log.debug("Resolve  by id: %d", id);
		jobsService.resolveJobDescription(id);
		return true;
		
	}
	
	@PostMapping("/JobDescription/update")
	public JobDescriptionWithSkills updateJobDescription(@RequestBody JobDescriptionWithSkills desc) {
		log.debug("Update: %d", desc.toString());
		return jobsService.updateJobDescriptionWithSkills(desc);
	}
	
	@GetMapping("/delete/{id}")
	public boolean deleteJobDescription(@PathVariable int id) {
		log.debug("Delete by id: %d", id);
		jobsService.deleteJobDescription(id);
		return true;
	}

}
