package com.accolite.opportunitiesportal.jobs.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.accolite.opportunitiesportal.jobs.dao.JobsDao;
import com.accolite.opportunitiesportal.jobs.model.JobDescription;
import com.accolite.opportunitiesportal.jobs.model.JobDescriptionWithSkills;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;


/**
 * The Class JobsService.
 */
@Service

/** The Constant log. */
@Slf4j

/**
 * Instantiates a new jobs service.
 *
 * @param jobsDao the jobs dao
 */
@AllArgsConstructor

/**
 * Instantiates a new jobs service.
 */
@NoArgsConstructor
public class JobsService {
	
	/** The jobs dao. */
	@Autowired
	JobsDao jobsDao;
	
	/**
	 * Adds the job description.
	 *
	 * @param desc the JobDescription
	 * @return the job description with skills
	 */
	public JobDescriptionWithSkills addJobDescription(JobDescriptionWithSkills desc) {
		log.debug("Add JobDescription : %s", desc.toString());
		int id;
		
		id = jobsDao.saveJobDescription(desc.getJobDescription());
		
		desc.getJobDescription().setId(id);
		
		jobsDao.saveJobSkills(id, desc.getSkillList());
		log.debug("Added JobDescription is : %s", desc.toString());
		return desc;
	}


	/**
	 * Find job description by id.
	 *
	 * @param id the JobId
	 * @return the job description with skills
	 */
	public JobDescriptionWithSkills findJobDescriptionbyId(int id) {
		log.debug("find JobDescription : %d", id);
		JobDescriptionWithSkills descriptionWithSkills = new JobDescriptionWithSkills();
		JobDescription  jobDescription = jobsDao.findJobDescriptionbyId(id);
		descriptionWithSkills.setJobDescription(jobDescription);
		descriptionWithSkills.setSkillList(jobsDao.getSkillsById(jobDescription.getId()));
		log.debug("Constructed Object: %s", descriptionWithSkills.toString());
		return descriptionWithSkills;
	}
	
	/**
	 * Find job description version by id.
	 *
	 * @param id the id
	 * @return the job description with skills
	 */
	public JobDescriptionWithSkills findJobDescriptionVersionById(int id) {
		log.debug("find JobDescription version : %d", id);
		JobDescriptionWithSkills descriptionWithSkills = new JobDescriptionWithSkills();
		JobDescription  jobDescription = jobsDao.findJobDescriptionVersionbyId(id);
		descriptionWithSkills.setJobDescription(jobDescription);
		descriptionWithSkills.setSkillList(jobsDao.getVersionSkillsById(id));
		log.debug("Constructed Version Object: %s", descriptionWithSkills.toString());
		return descriptionWithSkills;
	}


	/**
	 * Gets the all job descriptions.
	 *
	 * @return the all job descriptions
	 */
	public List<JobDescriptionWithSkills> getAllJobDescriptions() {
		log.debug("Find all JobDescriptions");
		return jobsDao.getAllJobDescriptions();
	
	}
	
	/**
	 * Gets the all job descrption versions.
	 *
	 * @param jobId the job id
	 * @return the all job descrption versions
	 */
	public List<JobDescriptionWithSkills> getallJobDescrptionVersions(int jobId){
		log.debug("Find all Versions JobDescriptions");
		return jobsDao.getAllJobDescriptionsVersions(jobId);
	}
	
	
	/**
	 * Update job description with skills.
	 *
	 * @param desc the desc
	 * @return the job description with skills
	 */
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


	/**
	 * Delete job description.
	 *
	 * @param id the JobId
	 * @return true, if successful
	 */
	public boolean deleteJobDescription(int id) {
		log.debug("Delete JobDescriptionByID: %d", id);
		jobsDao.deleteJobDescription(id);
		return true;
		
	}
	
	/**
	 * Resolve job description.
	 *
	 * @param id the JobId
	 * @return true, if successful
	 */
	public boolean resolveJobDescription(int id) {
		log.debug("Resolve JobDescriptionByID: %d", id);
		jobsDao.resolveJobDescription(id);
		return true;
		
	}
	
	
	
}
	