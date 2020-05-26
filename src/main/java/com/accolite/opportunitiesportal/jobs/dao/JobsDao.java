package com.accolite.opportunitiesportal.jobs.dao;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.accolite.opportunitiesportal.jobs.constants.JobsConstants;
import com.accolite.opportunitiesportal.jobs.model.DropDownItem;
import com.accolite.opportunitiesportal.jobs.model.JobDescription;
import com.accolite.opportunitiesportal.jobs.model.JobDescriptionWithSkills;
import com.accolite.opportunitiesportal.jobs.queries.JobsQueries;
import com.accolite.opportunitiesportal.jobs.repository.JobsRepository;
import com.accolite.opportunitiesportal.jobs.rowmapper.AttributeMapper;

@Repository
public class JobsDao {

	JdbcTemplate jdbcTemplate;
	
	
	@Autowired
	public JobsDao(DataSource dataSource) {
		jdbcTemplate = new JdbcTemplate(dataSource);
	}
	
	
	@Autowired
	JobsRepository jobsRepository;
	
	
	public Map<String,List<DropDownItem>> getAttributes(){
		Map<String, List<DropDownItem>> attributeMap = new HashMap<>();
		
		for(String i : JobsConstants.ATTRIBUTES_LIST) {
			attributeMap.put(i, jdbcTemplate.query(JobsQueries.getAttribute(i), new AttributeMapper()));
		}
		
		return attributeMap;
	}


	public int saveJobDescription(JobDescription jobDescription) {
		
		return jobsRepository.saveJobdescription(jobDescription);
	}
	
	public void saveJobSkills(int jobId, List<Integer> skillList) {
		jobsRepository.saveJobDescriptionSkills(jobId, skillList);
	}


	public JobDescription findJobDescriptionbyId(int id) {
		
		return jobsRepository.findById(id);
	}
	
	
	public List<JobDescriptionWithSkills> getAllJobDescriptions() {
		List<JobDescriptionWithSkills> resultList;
		List<JobDescription> jobList;
		jobList = jobsRepository.findAllJobDescriptions();
		
		resultList = jobList.stream().map(job -> {
			JobDescriptionWithSkills descriptionWithSkills = new JobDescriptionWithSkills();
			descriptionWithSkills.setJobDescription(job);
			descriptionWithSkills.setSkillList(jobsRepository.getSkillListById(job.getId()));
			return descriptionWithSkills;
		}).collect(Collectors.toList());
		
		return resultList;
	}
	
	public void updateJobDescription(JobDescriptionWithSkills desc) {
		jobsRepository.updateJobDescription(desc.getJobDescription());
		jobsRepository.deleteSkillsByJd(desc.getJobDescription().getId());
		jobsRepository.saveJobDescriptionSkills(desc.getJobDescription().getId(), desc.getSkillList());
	}
	
}
