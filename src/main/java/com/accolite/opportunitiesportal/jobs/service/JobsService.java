package com.accolite.opportunitiesportal.jobs.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.accolite.opportunitiesportal.jobs.dao.JobsDao;
import com.accolite.opportunitiesportal.jobs.model.JobDescription;
import com.accolite.opportunitiesportal.jobs.model.JobDescriptionWithSkills;

@Service
public class JobsService {
	
	@Autowired
	JobsDao jobsDao;

	
	

	public JobDescriptionWithSkills addJobDescription(JobDescriptionWithSkills desc) {
		
		int id;
		
		id = jobsDao.saveJobDescription(desc.getJobDescription());
		
		desc.getJobDescription().setId(id);
		
		jobsDao.saveJobSkills(id, desc.getSkillList());
		
		return desc;
	}


	public JobDescriptionWithSkills findJobDescriptionbyId(int id) {
		
		JobDescriptionWithSkills descriptionWithSkills = new JobDescriptionWithSkills();
		JobDescription  jobDescription = jobsDao.findJobDescriptionbyId(id);
		descriptionWithSkills.setJobDescription(jobDescription);
		descriptionWithSkills.setSkillList(jobsDao.getSkillsById(jobDescription.getId()));
		
		return descriptionWithSkills;
	}


	public List<JobDescriptionWithSkills> getAllJobDescriptions() {
	
		return jobsDao.getAllJobDescriptions();
	
	}
	
	
	public JobDescriptionWithSkills updateJobDescriptionWithSkills(JobDescriptionWithSkills desc) {
		JobDescription oldDesc = jobsDao.findJobDescriptionbyId(desc.getJobDescription().getId());
		JobDescription newDesc = desc.getJobDescription();
		
		newDesc.setPostedBy(oldDesc.getPostedBy());
		newDesc.setPostedOn(oldDesc.getPostedOn());
		newDesc.setLastUpdated(new Date());
		
		jobsDao.updateJobDescription(desc);
		
		return desc;
	}


	public boolean deleteJobDescription(int id) {
		jobsDao.deleteJobDescription(id);
		return true;
		
	}
	
	public boolean resolveJobDescription(int id) {
		jobsDao.resolveJobDescription(id);
		return true;
		
	}
	
	
	
}
	