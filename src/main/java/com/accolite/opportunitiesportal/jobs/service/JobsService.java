package com.accolite.opportunitiesportal.jobs.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.accolite.opportunitiesportal.jobs.dao.JobsDao;
import com.accolite.opportunitiesportal.jobs.model.JobDescription;
import com.accolite.opportunitiesportal.jobs.model.JobDescriptionWithSkills;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class JobsService {
	
	@Autowired
	JobsDao jobsDao;
	
	public JobDescriptionWithSkills addJobDescription(JobDescriptionWithSkills desc) {
		log.debug("Add JobDescription : %s", desc.toString());
		int id;
		
		id = jobsDao.saveJobDescription(desc.getJobDescription());
		
		desc.getJobDescription().setId(id);
		
		jobsDao.saveJobSkills(id, desc.getSkillList());
		log.debug("Added JobDescription is : %s", desc.toString());
		return desc;
	}


	public JobDescriptionWithSkills findJobDescriptionbyId(int id) {
		log.debug("find JobDescription : %d", id);
		JobDescriptionWithSkills descriptionWithSkills = new JobDescriptionWithSkills();
		JobDescription  jobDescription = jobsDao.findJobDescriptionbyId(id);
		descriptionWithSkills.setJobDescription(jobDescription);
		descriptionWithSkills.setSkillList(jobsDao.getSkillsById(jobDescription.getId()));
		log.debug("Constructed Object: %s", descriptionWithSkills.toString());
		return descriptionWithSkills;
	}
	
	public JobDescriptionWithSkills findJobDescriptionVersionById(int id) {
		log.debug("find JobDescription version : %d", id);
		JobDescriptionWithSkills descriptionWithSkills = new JobDescriptionWithSkills();
		JobDescription  jobDescription = jobsDao.findJobDescriptionVersionbyId(id);
		descriptionWithSkills.setJobDescription(jobDescription);
		descriptionWithSkills.setSkillList(jobsDao.getVersionSkillsById(id));
		log.debug("Constructed Version Object: %s", descriptionWithSkills.toString());
		return descriptionWithSkills;
	}


	public List<JobDescriptionWithSkills> getAllJobDescriptions() {
		log.debug("Find all JobDescriptions");
		return jobsDao.getAllJobDescriptions();
	
	}
	
	public List<JobDescriptionWithSkills> getallJobDescrptionVersions(int jobId){
		log.debug("Find all Versions JobDescriptions");
		return jobsDao.getAllJobDescriptionsVersions(jobId);
	}
	
	
	public JobDescriptionWithSkills updateJobDescriptionWithSkills(JobDescriptionWithSkills desc) {
		log.debug("Update JobDescription: %s", desc.toString());
		JobDescription oldDesc = jobsDao.findJobDescriptionbyId(desc.getJobDescription().getId());
		JobDescription newDesc = desc.getJobDescription();
		
		newDesc.setPostedBy(oldDesc.getPostedBy());
		newDesc.setPostedOn(oldDesc.getPostedOn());
		newDesc.setLastUpdated(new Date());
		
		jobsDao.updateJobDescription(desc);
		
		return desc;
	}


	public boolean deleteJobDescription(int id) {
		log.debug("Delete JobDescriptionByID: %d", id);
		jobsDao.deleteJobDescription(id);
		return true;
		
	}
	
	public boolean resolveJobDescription(int id) {
		log.debug("Resolve JobDescriptionByID: %d", id);
		jobsDao.resolveJobDescription(id);
		return true;
		
	}
	
	
	
}
	