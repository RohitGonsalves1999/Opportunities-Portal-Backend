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


import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * The Class JobsDao.
 */
@Repository

@Slf4j

/**
 * Instantiates a new jobs dao.
 *
 * @param jdbcTemplate the jdbc template
 * @param jobsRepository the jobs repository
 * @param logger the logger
 */
@AllArgsConstructor

/**
 * Instantiates a new jobs dao.
 */
@NoArgsConstructor
public class JobsDao {

	/** The jdbc template. */
	@Autowired
	JdbcTemplate jdbcTemplate;
	
	/** The jobs repository. */
	@Autowired
	JobsRepository jobsRepository;
	
	/** The logger. */
	Logger logger = LoggerFactory.getLogger(JobsDao.class);
	
	

	/**
	 * Save job description.
	 *
	 * @param jobDescription the job description
	 * @return the Generated Id
	 */
	public int saveJobDescription(JobDescription jobDescription) {
		log.info("Save JobDescription");
		return jobsRepository.saveJobdescription(jobDescription);
	}
	
	/**
	 * Save job Description skills to the database.
	 *
	 * @param jobId the job id
	 * @param skillList the skill list
	 * @return the int
	 */
	public int saveJobSkills(int jobId, List<Integer> skillList) {
		log.info(String.format("Save Skills: %s", skillList.toString()));
		return jobsRepository.saveJobDescriptionSkills(jobId, skillList);
	}


	/**
	 * Find job description by id.
	 *
	 * @param id the Jobid
	 * @return the job description
	 */
	public JobDescription findJobDescriptionbyId(int id) {
		log.debug(String.format("Find JobDescription By Id: %d", id));
		return jobsRepository.findById(id);
	}
	
	
	/**
	 * Find job description version by id.
	 *
	 * @param id the Jobid
	 * @return the job description
	 */
	public JobDescription findJobDescriptionVersionbyId(int id) {
		log.debug(String.format("Find JobDescription By Id: %d", id));
		return jobsRepository.findVersionById(id);
	}
	
	
	/**
	 * Gets the all job descriptions.
	 *
	 * @return the all job descriptions
	 */
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
	
	/**
	 * Gets the all job descriptions versions.
	 *
	 * @param jobId the job id
	 * @return the all job descriptions versions
	 */
	public List<JobDescriptionWithSkills> getAllJobDescriptionsVersions(int jobId) {
		log.debug("Find All JobDescriptions Versions");
		List<JobDescriptionWithSkills> resultList;
		List<JobDescription> jobList;
		jobList = jobsRepository.findAllJobDescriptionVersions(jobId);
		
		resultList = jobList.stream().map(job -> {
			JobDescriptionWithSkills descriptionWithSkills = new JobDescriptionWithSkills();
			descriptionWithSkills.setJobDescription(job);
			log.info(String.format("Id: %d", job.getId()));
			descriptionWithSkills.setSkillList(jobsRepository.getVersionSkillListById(job.getId()));
			return descriptionWithSkills;
		}).collect(Collectors.toList());
		log.debug(String.format("Jobdescriptions: %s", resultList.toString()));
		log.debug("Success in Retrieving");
		return resultList;
	}
	
	
	/**
	 * Gets the skills by id.
	 *
	 * @param id the id
	 * @return the skills by id
	 */
	public List<Integer> getSkillsById (int id){
		log.debug("Find Skills By Id: %d", id);
		return jobsRepository.getSkillListById(id);
	}
	
	/**
	 * Gets the version skills by id.
	 *
	 * @param id the id
	 * @return the version skills by id
	 */
	public List<Integer> getVersionSkillsById(int id){
		log.debug("Find Version Skills By Id: %d", id);
		return jobsRepository.getVersionSkillListById(id);
	}
	

	
	/**
	 * Update job description.
	 *
	 * @param desc the desc
	 * @return true, if successful
	 */
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


	/**
	 * Delete job description.
	 *
	 * @param id the id
	 * @return true, if successful
	 */
	public boolean deleteJobDescription(int id) {
		log.debug("Delete JobDescription: %d",id);
		jobsRepository.deleteJobDescription(id);
		jobsRepository.markSkillInactive(id);
		log.debug("Success");
		return true;
		
	}
	
	
	/**
	 * Resolve job description.
	 *
	 * @param id the id
	 * @return true, if successful
	 */
	public boolean resolveJobDescription(int id) {
		log.debug("Resolve JobDescription: %d",id);
		jobsRepository.deleteJobDescription(id);
		log.debug("Success");
		return true;
	}
	
	
	
	
}
