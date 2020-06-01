package com.accolite.opportunitiesportal.jobs.dao;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.accolite.opportunitiesportal.jobs.constants.JobsConstants;
import com.accolite.opportunitiesportal.jobs.model.ChartDataObject;
import com.accolite.opportunitiesportal.jobs.model.ChartObject;
import com.accolite.opportunitiesportal.jobs.model.DropDownItem;
import com.accolite.opportunitiesportal.jobs.model.JobDescription;
import com.accolite.opportunitiesportal.jobs.model.JobDescriptionWithSkills;
import com.accolite.opportunitiesportal.jobs.queries.JobsQueries;
import com.accolite.opportunitiesportal.jobs.repository.JobsRepository;
import com.accolite.opportunitiesportal.jobs.rowmapper.AttributeMapper;

@Repository
public class JobsDao {

	@Autowired
	JdbcTemplate jdbcTemplate;
	
	@Autowired
	JobsRepository jobsRepository;
	
	
	public Map<String,List<DropDownItem>> getAttributes(){
		Map<String, List<DropDownItem>> attributeMap = new HashMap<>();
		
		for(String i : JobsConstants.ATTRIBUTES_LIST) {
			attributeMap.put(i, jdbcTemplate.query(JobsQueries.getAttribute(i), new AttributeMapper()));
		}
		
		return attributeMap;
	}
	
	public Map<String,Map<Integer, String>> getAttributesMap(){
		Map<String, Map<Integer, String>> attributeMap = new HashMap<>();
		
		for(String i : JobsConstants.ATTRIBUTES_LIST) {
			List<DropDownItem> attributeList = jdbcTemplate.query(JobsQueries.getAttribute(i), new AttributeMapper());
			Map<Integer, String> attributeSubMap = new HashMap<>();
			for(DropDownItem it : attributeList) {
				attributeSubMap.put(it.getId(), it.getName());
			}
			attributeMap.put(i, attributeSubMap);
		}
		
		return attributeMap;
	}


	public int saveJobDescription(JobDescription jobDescription) {
		
		return jobsRepository.saveJobdescription(jobDescription);
	}
	
	public int saveJobSkills(int jobId, List<Integer> skillList) {
		return jobsRepository.saveJobDescriptionSkills(jobId, skillList);
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
	
	
	public List<Integer> getSkillsById (int id){
		return jobsRepository.getSkillListById(id);
	}
	
	public Map<String, ChartDataObject> getInsightMap(){
		Map<String, ChartDataObject> chartMap = new HashMap<>();
		chartMap.put(JobsConstants.SKILLS, constructChartDataObject(jobsRepository.getSkillCounts()));
		chartMap.put(JobsConstants.LOCATION, constructChartDataObject(jobsRepository.getLocationCounts()));
		chartMap.put(JobsConstants.HIRING_MANAGERS, constructChartDataObject(jobsRepository.getHiringManagerCCounts()));
		chartMap.put(JobsConstants.PROFILE, constructChartDataObject(jobsRepository.getProfileCounts()));
		chartMap.put(JobsConstants.EMPLOYMENT_TYPE	, constructChartDataObject(jobsRepository.getEmploymentTypeCounts()));
		chartMap.put(JobsConstants.RESOLVED_SKILLS, constructChartDataObject(jobsRepository.getResolvedSkillCounts()));
		return chartMap;
	}
	
	public boolean updateJobDescription(JobDescriptionWithSkills desc) {
		jobsRepository.updateJobDescription(desc.getJobDescription());
		jobsRepository.deleteSkillsByJd(desc.getJobDescription().getId());
		jobsRepository.saveJobDescriptionSkills(desc.getJobDescription().getId(), desc.getSkillList());
		return true;
	}


	public boolean deleteJobDescription(int id) {
		jobsRepository.deleteJobDescription(id);
		jobsRepository.markSkillInactive(id);
		return true;
		
	}
	
	
	public boolean resolveJobDescription(int id) {
		jobsRepository.deleteJobDescription(id);
		return true;
	}
	
	
	private ChartDataObject constructChartDataObject(List<ChartObject> objects) {
		
		List<Integer> values = new ArrayList<>();
		List<String> labels = new ArrayList<>();
		
		values.addAll(objects.stream().map(x -> x.getValue()).collect(Collectors.toList()));
		labels.addAll(objects.stream().map(x -> x.getName()).collect(Collectors.toList()));
		
		return new ChartDataObject(values, labels);
	}
	
	
}
