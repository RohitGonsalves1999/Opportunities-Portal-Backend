package com.accolite.opportunitiesportal.jobs.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.accolite.opportunitiesportal.jobs.model.DropDownItem;
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

}
