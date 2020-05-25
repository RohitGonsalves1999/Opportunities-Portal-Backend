package com.accolite.opportunitiesportal.jobs.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.accolite.opportunitiesportal.jobs.constants.JobsConstants;
import com.accolite.opportunitiesportal.jobs.model.DropDownItem;
import com.accolite.opportunitiesportal.jobs.queries.JobsQueries;
import com.accolite.opportunitiesportal.jobs.rowmapper.AttributeMapper;

@Repository
public class JobsDao {

	JdbcTemplate jdbcTemplate;
	
	
	@Autowired
	public JobsDao(DataSource dataSource) {
		jdbcTemplate = new JdbcTemplate(dataSource);
	}
	
	
	public Map<String,List<DropDownItem>> getAttributes(){
		Map<String, List<DropDownItem>> attributeMap = new HashMap<>();
		
		for(String i : JobsConstants.ATTRIBUTES_LIST) {
			attributeMap.put(i, jdbcTemplate.query(JobsQueries.getAttribute(i), new AttributeMapper()));
		}
		
		return attributeMap;
	}
	
}
