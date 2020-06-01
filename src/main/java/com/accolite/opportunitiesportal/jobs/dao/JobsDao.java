package com.accolite.opportunitiesportal.jobs.dao;


import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.accolite.opportunitiesportal.jobs.model.JobDescription;
import com.accolite.opportunitiesportal.jobs.model.JobDescriptionWithSkills;
import com.accolite.opportunitiesportal.jobs.repository.JobsRepository;

@Repository
public class JobsDao {

	@Autowired
	JdbcTemplate jdbcTemplate;
	
	@Autowired
	JobsRepository jobsRepository;
	
	
	


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
	
	
	
	
}
