package com.accolite.opportunitiesportal.jobs.dao;


import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
	
	Logger logger = LoggerFactory.getLogger(JobsDao.class);
	


	public int saveJobDescription(JobDescription jobDescription) {
		
		return jobsRepository.saveJobdescription(jobDescription);
	}
	
	public int saveJobSkills(int jobId, List<Integer> skillList) {
		return jobsRepository.saveJobDescriptionSkills(jobId, skillList);
	}


	public JobDescription findJobDescriptionbyId(int id) {
		
		return jobsRepository.findById(id);
	}
	
	
	public JobDescription findJobDescriptionVersionbyId(int id) {
		return jobsRepository.findVersionById(id);
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
	
	public List<JobDescriptionWithSkills> getAllJobDescriptionsVersions(int jobId) {
		List<JobDescriptionWithSkills> resultList;
		List<JobDescription> jobList;
		jobList = jobsRepository.findAllJobDescriptionVersions(jobId);
		
		resultList = jobList.stream().map(job -> {
			JobDescriptionWithSkills descriptionWithSkills = new JobDescriptionWithSkills();
			descriptionWithSkills.setJobDescription(job);
			logger.error(job.getId() + "");
			descriptionWithSkills.setSkillList(jobsRepository.getVersionSkillListById(job.getId()));
			return descriptionWithSkills;
		}).collect(Collectors.toList());
		
		return resultList;
	}
	
	
	public List<Integer> getSkillsById (int id){
		return jobsRepository.getSkillListById(id);
	}
	
	public List<Integer> getVersionSkillsById(int id){
		return jobsRepository.getVersionSkillListById(id);
	}
	

	
	public boolean updateJobDescription(JobDescriptionWithSkills desc) {
		JobDescription oldJd = jobsRepository.findById(desc.getJobDescription().getId());
		List<Integer> oldSkillList = jobsRepository.getSkillListById(desc.getJobDescription().getId());
		int entryId = jobsRepository.saveJobdescriptionVersion(oldJd);
		jobsRepository.saveJobDescriptionVersionSkills(entryId, oldSkillList);
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
