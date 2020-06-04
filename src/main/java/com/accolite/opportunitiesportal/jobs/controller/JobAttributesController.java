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

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * The Class JobAttributesController.
 */
@RestController
@RequestMapping("/api/jobs")

/** The Constant log. */
@Slf4j

/**
 * Instantiates a new job attributes controller.
 *
 * @param jobsService the jobs service
 */
@AllArgsConstructor

/**
 * Instantiates a new job attributes controller.
 */
@NoArgsConstructor
public class JobAttributesController {
	
	
	/** The jobs service. */
	@Autowired
	JobAttributeService jobsService;
	
		

	/**
	 * Gets the drop down items.
	 *
	 * @return the drop down items
	 */
	@GetMapping("/DropDownItems")
	public Map<String, List<DropDownItem>> getDropDownItems() {
		log.debug("Entered getDropDownItems");
		return jobsService.getAttributes();
	}
	
	/**
	 * Gets the Drop Down attributes map.
	 *
	 * @return the drop down attributes map
	 */
	@GetMapping("/DropDownMap")
	public Map<String,Map<Integer, String>> getAttributesMap(){
		log.debug("Entered getDropDownMap");
		return jobsService.getAttributesMap();
	}
	
	/**
	 * Gets the insight map.
	 *
	 * @return the insight map
	 */
	@GetMapping("/jobInsights")
	public Map<String, ChartDataObject> getInsightMap(){
		log.debug("Entered getDropDownInsights");
		return jobsService.getInsightMap();
	}
}
