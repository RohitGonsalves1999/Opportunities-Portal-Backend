package com.accolite.opportunitiesportal.jobs.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.accolite.opportunitiesportal.jobs.dao.JobsAttributeDao;
import com.accolite.opportunitiesportal.jobs.model.ChartDataObject;
import com.accolite.opportunitiesportal.jobs.model.DropDownItem;

@Service
public class JobAttributeService {

	
	@Autowired
	JobsAttributeDao jobsDao;

	
	public Map<String, List<DropDownItem>> getAttributes(){
		return jobsDao.getAttributes();
	}

	public Map<String,Map<Integer, String>> getAttributesMap(){
		return jobsDao.getAttributesMap();
	}
	
	public Map<String, ChartDataObject> getInsightMap(){
		return jobsDao.getInsightMap();
	}
}
