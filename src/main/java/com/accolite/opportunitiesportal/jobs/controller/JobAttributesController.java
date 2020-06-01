package com.accolite.opportunitiesportal.jobs.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.accolite.opportunitiesportal.jobs.model.ChartDataObject;
import com.accolite.opportunitiesportal.jobs.model.DropDownItem;
import com.accolite.opportunitiesportal.jobs.service.JobAttributeService;

@RestController
@RequestMapping("/api/jobs")
public class JobAttributesController {
	
	
	@Autowired
	JobAttributeService jobsService;

	@GetMapping("/DropDownItems")
	public Map<String, List<DropDownItem>> getDropDownItems() {
		return jobsService.getAttributes();
	}
	
	@GetMapping("/DropDownMap")
	public Map<String,Map<Integer, String>> getAttributesMap(){
		return jobsService.getAttributesMap();
	}
	
	@GetMapping("/jobInsights")
	public Map<String, ChartDataObject> getInsightMap(){
		return jobsService.getInsightMap();
	}
}
