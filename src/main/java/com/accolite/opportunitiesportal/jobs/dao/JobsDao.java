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

import lombok.extern.slf4j.Slf4j;

@Repository
@Slf4j
public class JobsDao {

	@Autowired
	JdbcTemplate jdbcTemplate;
	
	@Autowired
	JobsRepository jobsRepository;
	
	Logger logger = LoggerFactory.getLogger(JobsDao.class);
	


	public int saveJobDescription(JobDescription jobDescription) {
		log.info("Save JobDescription");
		return jobsRepository.saveJobdescription(jobDescription);
	}
	
	public int saveJobSkills(int jobId, List<Integer> skillList) {
		log.info(String.format("Save Skills: %s", skillList.toString()));
		return jobsRepository.saveJobDescriptionSkills(jobId, skillList);
	}


	public JobDescription findJobDescriptionbyId(int id) {
		log.debug(String.format("Find JobDescription By Id: %d", id));
		return jobsRepository.findById(id);
	}
	
	
	public JobDescription findJobDescriptionVersionbyId(int id) {
		log.debug(String.format("Find JobDescription By Id: %d", id));
		return jobsRepository.findVersionById(id);
	}
	
	
	public List<JobDescriptionWithSkills> getAllJobDescriptions() {
		log.debug("Find All JobDescriptions");
		List<JobDescriptionWithSkills> resultList;
		List<JobDescription> jobList;
		jobList = jobsRepository.findAllJobDescriptions();
		
		resultList = jobList.stream().map(job -> {
			JobDescriptionWithSkills descriptionWithSkills = new JobDescriptionWithSkills();
			
			descriptionWithSkills.setJobDescription(job);
			descriptionWithSkills.setSkillList(jobsRepository.getSkillListById(job.getId()));
			return descriptionWithSkills;
		}).collect(Collectors.toList());
		log.debug(String.format("Jobdescriptions: %s", resultList.toString()));
		return resultList;
	}
	
	public List<JobDescriptionWithSkills> getAllJobDescriptionsVersions(int jobId) {
		log.debug("Find All JobDescriptions Versions");
		List<JobDescriptionWithSkills> resultList;
		List<JobDescription> jobList;
		jobList = jobsRepository.findAllJobDescriptionVersions(jobId);
		
		resultList = jobList.stream().map(job -> {
			JobDescriptionWithSkills descriptionWithSkills = new JobDescriptionWithSkills();
			descriptionWithSkills.setJobDescription(job);
			logger.info(String.format("Id: %d", job.getId()));
			descriptionWithSkills.setSkillList(jobsRepository.getVersionSkillListById(job.getId()));
			return descriptionWithSkills;
		}).collect(Collectors.toList());
		log.debug(String.format("Jobdescriptions: %s", resultList.toString()));
		log.debug("Success");
		return resultList;
	}
	
	
	public List<Integer> getSkillsById (int id){
		log.debug("Find Skills By Id: %d", id);
		return jobsRepository.getSkillListById(id);
	}
	
	public List<Integer> getVersionSkillsById(int id){
		log.debug("Find Version Skills By Id: %d", id);
		return jobsRepository.getVersionSkillListById(id);
	}
	

	
	public boolean updateJobDescription(JobDescriptionWithSkills desc) {
		log.debug("UpdateJobdescription %s", desc.toString());
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
		log.debug("Delete JobDescription: %d",id);
		jobsRepository.deleteJobDescription(id);
		jobsRepository.markSkillInactive(id);
		log.debug("Success");
		return true;
		
	}
	
	
	public boolean resolveJobDescription(int id) {
		log.debug("Resolve JobDescription: %d",id);
		jobsRepository.deleteJobDescription(id);
		log.debug("Success");
		return true;
	}
	
	
	
	
}
