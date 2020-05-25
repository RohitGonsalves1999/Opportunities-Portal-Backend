package com.accolite.opportunitiesportal.jobs.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.accolite.opportunitiesportal.jobs.dao.JobsDao;
import com.accolite.opportunitiesportal.jobs.model.DropDownItem;

@Service
public class JobsService {
	
	@Autowired
	JobsDao jobsDao;

	
	public Map<String, List<DropDownItem>> getAttributes(){
		return jobsDao.getAttributes();
	}
}
