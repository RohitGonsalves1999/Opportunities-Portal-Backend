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

import com.accolite.opportunitiesportal.jobs.model.ChartDataObject;
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
	
	@GetMapping("/getDropDownMap")
	public Map<String,Map<Integer, String>> getAttributesMap(){
		return jobsService.getAttributesMap();
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
	
	@GetMapping("/resolveJobDescription?{id}")
	public void resolveJobDescription(@PathVariable int id) {
		jobsService.resolveJobDescription(id);
		
	}
	
	
	@PutMapping("/updateJobDescription")
	public  JobDescriptionWithSkills updateJobDescription(@RequestBody JobDescriptionWithSkills desc) {
		return jobsService.updateJobDescriptionWithSkills(desc);
	}
	
	@GetMapping("/deleteJobDescription/{id}")
	public void deleteJobDescription(@PathVariable int id) {
		jobsService.deleteJobDescription(id);
	}
	
	@GetMapping("/jobInsights")
	public Map<String, ChartDataObject> getInsightMap(){
		return jobsService.getInsightMap();
	}

}
