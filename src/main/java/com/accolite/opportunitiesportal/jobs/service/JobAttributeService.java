package com.accolite.opportunitiesportal.jobs.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.accolite.opportunitiesportal.jobs.dao.JobsAttributeDao;
import com.accolite.opportunitiesportal.jobs.model.ChartDataObject;
import com.accolite.opportunitiesportal.jobs.model.DropDownItem;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class JobAttributeService {

	
	@Autowired
	JobsAttributeDao jobsDao;

	
	public Map<String, List<DropDownItem>> getAttributes(){
		log.debug("Service Method for Attributes");
		return jobsDao.getAttributes();
	}

	public Map<String,Map<Integer, String>> getAttributesMap(){
		log.debug("Service Method for AttributesMap");
		return jobsDao.getAttributesMap();
	}
	
	public Map<String, ChartDataObject> getInsightMap(){
		log.debug("Service Method for InsightMap");
		return jobsDao.getInsightMap();
	}
}
