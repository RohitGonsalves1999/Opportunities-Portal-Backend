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

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/jobs")
@Slf4j
public class JobAttributesController {
	
	
	@Autowired
	JobAttributeService jobsService;
	
		

	@GetMapping("/DropDownItems")
	public Map<String, List<DropDownItem>> getDropDownItems() {
		log.debug("Entered getDropDownItems");
		return jobsService.getAttributes();
	}
	
	@GetMapping("/DropDownMap")
	public Map<String,Map<Integer, String>> getAttributesMap(){
		log.debug("Entered getDropDownMap");
		return jobsService.getAttributesMap();
	}
	
	@GetMapping("/jobInsights")
	public Map<String, ChartDataObject> getInsightMap(){
		log.debug("Entered getDropDownInsights");
		return jobsService.getInsightMap();
	}
}
