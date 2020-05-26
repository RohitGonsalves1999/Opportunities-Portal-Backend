package com.accolite.opportunitiesportal.jobs.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.accolite.opportunitiesportal.jobs.model.DropDownItem;
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
	
	@GetMapping("/getDropDownItems")
	public Map<String, List<DropDownItem>> getDropDownItems() {
		return jobsService.getAttributes();
	}
	
	
	@PostMapping("/addJobDescription")
	public JobDescriptionWithSkills addJobDescription(@RequestBody JobDescriptionWithSkills desc) {
		
		System.out.println("Description At controller:" + desc.getJobDescription());
		return jobsService.addJobDescription(desc);
	}
	
	@GetMapping("/getAllJobDescriptions/")
	public List<JobDescriptionWithSkills> findJobDesccriptionById() {
		return jobsService.getAllJobDescriptions();
	}
	
	@GetMapping("/getAllJobDescriptions/{id}")
	public JobDescriptionWithSkills findJobDesccriptionById(@PathVariable int id) {
		return jobsService.findJobDescriptionbyId(id);
	}
	
	
	@PutMapping("/updateJobDescription")
	public  JobDescriptionWithSkills updateJobDescription(@RequestBody JobDescriptionWithSkills desc) {
		return jobsService.updateJobDescriptionWithSkills(desc);
	}

}
