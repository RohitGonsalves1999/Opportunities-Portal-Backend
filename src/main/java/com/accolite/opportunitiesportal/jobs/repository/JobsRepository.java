package com.accolite.opportunitiesportal.jobs.repository;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

import com.accolite.opportunitiesportal.jobs.constants.JobDescriptionColumnNames;
import com.accolite.opportunitiesportal.jobs.constants.JobsConstants;
import com.accolite.opportunitiesportal.jobs.model.ChartObject;
import com.accolite.opportunitiesportal.jobs.model.DropDownItem;
import com.accolite.opportunitiesportal.jobs.model.JobDescription;
import com.accolite.opportunitiesportal.jobs.queries.InsightQueries;
import com.accolite.opportunitiesportal.jobs.queries.JobsQueries;
import com.accolite.opportunitiesportal.jobs.rowmapper.AttributeMapper;
import com.accolite.opportunitiesportal.jobs.rowmapper.ChartObjectMapper;
import com.accolite.opportunitiesportal.jobs.rowmapper.JobDescriptionMapper;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * The Class JobsRepository.
 */
@Repository

/**
 * Instantiates a new jobs repository.
 *
 * @param jdbcTemplate the jdbc template
 */
@AllArgsConstructor

/**
 * Instantiates a new jobs repository.
 */
@NoArgsConstructor

@Slf4j
public class JobsRepository {
	
	/** The Constant logger. */
	private static final org.slf4j.Logger logger =LoggerFactory.getLogger(JobsRepository.class);
	
	
	/** The jdbc template. */
	@Autowired
	JdbcTemplate jdbcTemplate;
	
	
	
	
	/**
	 * Save jobdescription Object to database.
	 *
	 * @param desc the JobDescription Object
	 * @return the Generated Id
	 */
	public int saveJobdescription(JobDescription desc) {
		log.debug("Adding JobDescription");
		GeneratedKeyHolder holder = new GeneratedKeyHolder();
		jdbcTemplate.update( 
				new PreparedStatementCreator()  {
			@Override
			public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
			PreparedStatement ps  = con.prepareStatement(JobsQueries.SAVE_JOB_DESCRIPTION, Statement.RETURN_GENERATED_KEYS);
			logger.info(String.format("Description: %s",  desc.toString()));
			ps.setInt(1, desc.getProfile());
			ps.setString(2, desc.getDescription());
			ps.setInt(3, desc.getLocation());
			ps.setInt(4, desc.getEmploymentType());
			ps.setInt(5, desc.getHiringManager());
			ps.setInt(6, desc.getOpenings());
			ps.setDate(7, new Date(new java.util.Date().getTime()));
			ps.setInt(8	, desc.getPostedBy());
			ps.setDate(9, null);
			ps.setInt(10, -1);
			return ps;		
		
		}
				},holder);
		log.info("Addition Succesful");
		
		return (int)holder.getKeys().get(JobsConstants.ID);
	}
	
	/**
	 * Save job description version to DB.
	 *
	 * @param desc the JobDescription Object
	 * @return the Generated Id
	 */
	public int saveJobdescriptionVersion(JobDescription desc) {
		log.debug("Adding JobDescription");
		GeneratedKeyHolder holder = new GeneratedKeyHolder();
		jdbcTemplate.update( 
				new PreparedStatementCreator()  {
			@Override
			public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
			PreparedStatement ps  = con.prepareStatement(JobsQueries.SAVE_NEW_JD_VERSION, Statement.RETURN_GENERATED_KEYS);
			logger.info(String.format("Description: %s",  desc.toString()));
			ps.setInt(1, desc.getId());
			ps.setInt(2, desc.getProfile());
			ps.setString(3, desc.getDescription());
			ps.setInt(4, desc.getLocation());
			ps.setInt(5, desc.getEmploymentType());
			ps.setInt(6, desc.getHiringManager());
			ps.setInt(7, desc.getOpenings());
			ps.setDate(8, new Date(desc.getPostedOn().getTime()));
			ps.setInt(9	, desc.getPostedBy());
			ps.setDate(10, desc.getLastUpdated() == null?null:new Date(desc.getLastUpdated().getTime()));
			ps.setInt(11, desc.getLastUpdatedBy());
			return ps;		
		
		}
				},holder);
		
		log.info("Successful");
		
		return (int)holder.getKeys().get(JobsConstants.ENTRY_ID);
	}
	
	
	/**
	 * Save job description skills.
	 *
	 * @param jobId the job id
	 * @param skillList the skill list
	 * @return the number of skills stored 
	 */
	public int saveJobDescriptionSkills(int jobId, List<Integer> skillList) {
		String skills = skillList.toString();
		log.debug(String.format("Saving Skill List: %s", skills));
		
		jdbcTemplate.batchUpdate(JobsQueries.SAVE_JOB_SKILL,
				
			new BatchPreparedStatementSetter() 
			
			 {

            public void setValues(PreparedStatement ps, int i)
				throws SQLException {
                ps.setInt(1, jobId);
                ps.setInt(2, skillList.get(i));
            }

            public int getBatchSize() {
                return skillList.size();
            }

        });
		log.info(String.format("Saving Skill List: %s Successful", skills));
		return skillList.size();
	}
	
	
	/**
	 * Save job description version skills to the DB.
	 *
	 * @param outdatedJobId the Original job id
	 * @param outdatedSkillList the out dated skill list 
	 * @return the Number of Skills Inserted in the DB
	 */
	public int saveJobDescriptionVersionSkills(int outdatedJobId, List<Integer> outdatedSkillList) {
		
		log.debug(String.format("Saving Skill List: %s", outdatedSkillList.toString()));
		
		jdbcTemplate.batchUpdate(JobsQueries.SAVE_JD_VERSION_SKILLS,
				
			new BatchPreparedStatementSetter() 
			
			 {

            public void setValues(PreparedStatement ps, int i)
				throws SQLException {
                ps.setInt(1, outdatedJobId);
                ps.setInt(2, outdatedSkillList.get(i));
            }

            public int getBatchSize() {
                return outdatedSkillList.size();
            }

        });
		logger.info("Save List Successful");
		return outdatedSkillList.size();
	}
	
	/**
	 * Find all job descriptions.
	 *
	 * @return the list of JobDescriptions
	 */
	public List<JobDescription> findAllJobDescriptions(){
		logger.debug("Get All Job Descriptions");
		return jdbcTemplate.query(JobsQueries.GET_ALL_JOB_DESCRIPTIONS, new JobDescriptionMapper());
	}
	
	/**
	 * Find all job description versions.
	 *
	 * @param jobID the job ID
	 * @return the list
	 */
	public List<JobDescription> findAllJobDescriptionVersions(int jobID){
		logger.debug("Get All Job Descriptions Versions");
		return jdbcTemplate.query(JobsQueries.GET_ALL_JD_VERSIONS , new Object[] { jobID }, new JobDescriptionMapper(true));
	}

	
	/**
	 * Update job description in the main table.
	 *
	 * @param jobDescription the job description
	 * @return true, if successful
	 */
	public boolean updateJobDescription(JobDescription jobDescription) {
		String message = String.format("Update JobDescriptions: %s", jobDescription);
		logger.debug(message);
		jdbcTemplate.update(JobsQueries.UPDATE_JOB_DESCRIPTIONS, 
				new Object [] {
				jobDescription.getProfile(),
				jobDescription.getDescription(),
				jobDescription.getLocation(),
				jobDescription.getEmploymentType(),
				jobDescription.getHiringManager(),
				jobDescription.getOpenings(),
				jobDescription.getPostedOn(),
				jobDescription.getPostedBy(),
				new Date(System.currentTimeMillis()),
				jobDescription.getLastUpdatedBy(),
				jobDescription.getId()}
				);
		message = String.format("Update JobDescriptions: %s successful", jobDescription);
		logger.debug(message);
		return true;
	}
	
	
	/**
	 * Delete skills by jd.
	 *
	 * @param id the id
	 * @return true, if successful
	 */
	public boolean deleteSkillsByJd(int id) {
		log.debug("Deleting Skills");
		jdbcTemplate.update(JobsQueries.DELETE_SKILLS_BY_JD, id);
		log.info("Delete Successful");
		return true;
	}
	
	/**
	 * Delete job description.
	 *
	 * @param id the id
	 * @return true, if successful
	 */
	public boolean deleteJobDescription(int id) {
		log.debug(String.format("Deactivating JobDescription: %d", id));
		jdbcTemplate.update(JobsQueries.DELETE_JOB_DESCRIPTION_BY_ID, id);
		log.debug(String.format("Deactivating JobDescription: %d Successful", id));
		return true;
	}
	
	
	
	/**
	 * Mark skills of a JobDescription inactive.
	 *
	 * @param id the JobId
	 * @return the Number of skills Updated
	 */
	public int markSkillInactive(int id) {
		log.debug(String.format("Marking Skill for jobId %d Inactive", id));
		return jdbcTemplate.update(JobsQueries.MARK_SKILL_INACTIVE_BY_JD, id);
	}


	/**
	 * Find by id.
	 *
	 * @param id the id
	 * @return the job description
	 */
	public JobDescription findById(int id) {
		log.debug(String.format("Find job By Id: %d", id));
		return jdbcTemplate.queryForObject(JobsQueries.GET_JOB_DESCRIPTION_BY_ID,new Object[] {id} , new JobDescriptionMapper());
	}
	
	
	/**
	 * Find version by id.
	 *
	 * @param id the id
	 * @return the job description
	 */
	public JobDescription findVersionById(int id) {
		log.debug(String.format("Find job Version By Id: %d", id));
		return jdbcTemplate.queryForObject(JobsQueries.GET_JD_VERSION_BY_ENTRY_ID,new Object[] {id} , new JobDescriptionMapper());
	}
	
	/**
	 * Gets the skill list by id.
	 *
	 * @param id the id
	 * @return the skill list by id
	 */
	public List<Integer> getSkillListById(int id){
		log.debug(String.format("Find job Skill List By Id: %d", id));
		return jdbcTemplate.query(JobsQueries.GET_SKILLSET_BY_JOB_ID, new Object[] { id }, (ResultSet rs,int rowNum) -> 
			rs.getInt("skillid")
		);
	}
	
	/**
	 * Gets the version skill list by id.
	 *
	 * @param id the id
	 * @return the version skill list by id
	 */
	public List<Integer> getVersionSkillListById(int id){
		log.debug(String.format("Find job Version Skill List By Id: %d", id));
		return jdbcTemplate.query(JobsQueries.GET_JD_VERSION_SKILLS, new Object[] { id }, (ResultSet rs,int rowNum) -> 
			rs.getInt("skillid")
		);
	}
	
	/**
	 * Gets the skill counts.
	 *
	 * @return the skill counts for insights
	 */
	public List<ChartObject> getSkillCounts() {
		return jdbcTemplate.query(InsightQueries.FETCH_SKILL_COUNT, new ChartObjectMapper());
	}
	
	
	/**
	 * Gets the resolved skill counts.
	 *
	 * @return the resolved skill counts for insights 
	 */
	public List<ChartObject> getResolvedSkillCounts() {
		log.debug("Find Resolved Skill Counts");
		return jdbcTemplate.query(InsightQueries.FETCH_RESOLVED_SKILL_COUNT, new ChartObjectMapper());
	}
	
	/**
	 * Gets the location counts.
	 *
	 * @return the location counts for insights
	 */
	public List<ChartObject> getLocationCounts() {
		log.debug("Find Location Counts");
		return jdbcTemplate.query(
				InsightQueries.fetchInsight(JobsConstants.LOCATION, JobDescriptionColumnNames.LOCATION)
				, new ChartObjectMapper());
	}
	
	
	/*
	 * Gets the hiring manager counts.
	 *
	 * @return the hiring manager counts for insights
	 */
	public List<ChartObject> getHiringManagerCCounts() {
		log.debug("Find Hiring Manager Counts");
		return jdbcTemplate.query(
				InsightQueries.fetchInsight(
						JobsConstants.HIRING_MANAGERS, JobsConstants.HIRING_MANAGERS), 
						new ChartObjectMapper()
						);
	}
	
	/**
	 * Gets the employment type counts.
	 *
	 * @return the employment type counts
	 */
	public List<ChartObject> getEmploymentTypeCounts() {
		log.debug("Find Employment Type Counts");
		return jdbcTemplate.query(InsightQueries.fetchInsight(JobsConstants.EMPLOYMENT_TYPE, JobsConstants.EMPLOYMENT_TYPE), new ChartObjectMapper());
	}
	
	/**
	 * Gets the profile counts.
	 *
	 * @return the profile counts
	 */
	public List<ChartObject> getProfileCounts() {
		log.debug("Find Profile Counts");
		return jdbcTemplate.query(InsightQueries.fetchInsight(
				JobsConstants.PROFILE, 
				JobsConstants.PROFILE
				), new ChartObjectMapper());
	}
	
	/**
	 * Gets the item list.
	 *
	 * @param i the Parameter 
	 * @return the item list
	 */
	public List<DropDownItem> getItemList(String i){
		log.debug(String.format("Find The list of: %s", i));
		return jdbcTemplate.query(JobsQueries.getAttribute(i), new AttributeMapper());
	}
	

}
