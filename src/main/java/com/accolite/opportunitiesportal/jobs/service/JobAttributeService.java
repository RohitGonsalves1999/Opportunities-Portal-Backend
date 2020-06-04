package com.accolite.opportunitiesportal.jobs.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.accolite.opportunitiesportal.jobs.dao.JobsAttributeDao;
import com.accolite.opportunitiesportal.jobs.model.ChartDataObject;
import com.accolite.opportunitiesportal.jobs.model.DropDownItem;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * The Class JobAttributeService.
 */
@Service

/** The Constant log. */
@Slf4j

/**
 * Instantiates a new job attribute service.
 *
 * @param jobsDao the jobs dao
 */
@AllArgsConstructor

/**
 * Instantiates a new job attribute service.
 */
@NoArgsConstructor
public class JobAttributeService {

	
	/** The jobs dao. */
	@Autowired
	JobsAttributeDao jobsDao;

	
	/**
	 * Gets the Drop Down attributes List.
	 *
	 * @return the attributes
	 */
	public Map<String, List<DropDownItem>> getAttributes(){
		log.debug("Service Method for Attributes");
		return jobsDao.getAttributes();
	}

	/**
	 * Gets the attributes map.
	 *
	 * @return the attributes map
	 */
	public Map<String,Map<Integer, String>> getAttributesMap(){
		log.debug("Service Method for AttributesMap");
		return jobsDao.getAttributesMap();
	}
	
	/**
	 * Gets the insight map.
	 *
	 * @return the insight map
	 */
	public Map<String, ChartDataObject> getInsightMap(){
		log.debug("Service Method for InsightMap");
		return jobsDao.getInsightMap();
	}
}
