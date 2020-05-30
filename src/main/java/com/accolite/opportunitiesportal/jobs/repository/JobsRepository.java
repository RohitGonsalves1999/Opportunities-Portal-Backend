package com.accolite.opportunitiesportal.jobs.repository;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.sql.DataSource;

import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

import com.accolite.opportunitiesportal.jobs.constants.JobDescriptionColumnNames;
import com.accolite.opportunitiesportal.jobs.constants.JobsConstants;
import com.accolite.opportunitiesportal.jobs.model.ChartDataObject;
import com.accolite.opportunitiesportal.jobs.model.ChartObject;
import com.accolite.opportunitiesportal.jobs.model.JobDescription;
import com.accolite.opportunitiesportal.jobs.queries.InsightQueries;
import com.accolite.opportunitiesportal.jobs.queries.JobsQueries;
import com.accolite.opportunitiesportal.jobs.rowmapper.ChartObjectMapper;
import com.accolite.opportunitiesportal.jobs.rowmapper.JobDescriptionMapper;

@Repository
public class JobsRepository {
	
JdbcTemplate jdbcTemplate;
	

	@Autowired
	Logger logger;
	
	@Autowired
	public JobsRepository(DataSource dataSource) {
		jdbcTemplate = new JdbcTemplate(dataSource);
	}
	
	
	
	public int saveJobdescription(JobDescription desc) {
		GeneratedKeyHolder holder = new GeneratedKeyHolder();
		jdbcTemplate.update( 
				new PreparedStatementCreator()  {
			@Override
			public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
			PreparedStatement ps  = con.prepareStatement(JobsQueries.SAVE_JOB_DESCRIPTION, Statement.RETURN_GENERATED_KEYS);
			System.out.println("Description:" + desc);
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
		
		
		return (int)holder.getKeys().get(JobsConstants.ID);
	}
	
	
	public void saveJobDescriptionSkills(int jobId, List<Integer> skillList) {
		
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
		
	}
	
	public List<JobDescription> findAllJobDescriptions(){
		return jdbcTemplate.query(JobsQueries.GET_ALL_JOB_DESCRIPTIONS, new JobDescriptionMapper());
	}

	
	public void updateJobDescription(JobDescription jobDescription) {
		jdbcTemplate.update(JobsQueries.UPDATE_JOB_DESCRIPTIONS, 
				
				jobDescription.getProfile(),
				jobDescription.getDescription(),
				jobDescription.getLocation(),
				jobDescription.getEmploymentType(),
				jobDescription.getHiringManager(),
				jobDescription.getOpenings(),
				jobDescription.getPostedOn(),
				jobDescription.getPostedBy(),
				jobDescription.getLastUpdated(),
				jobDescription.getLastUpdatedBy(),
				jobDescription.getId()
				);
	}
	
	
	public void deleteSkillsByJd(int id) {
		jdbcTemplate.update(JobsQueries.DELETE_SKILLS_BY_JD, id);
	}
	
	public void deleteJobDescription(int id) {
		jdbcTemplate.update(JobsQueries.DELETE_JOB_DESCRIPTION_BY_ID, id);
	}
	
	
	
	public void markSkillInactive(int id) {
		jdbcTemplate.update(JobsQueries.MARK_SKILL_INACTIVE_BY_JD, id);
	}


	public JobDescription findById(int id) {
		
		return jdbcTemplate.queryForObject(JobsQueries.GET_JOB_DESCRIPTION_BY_ID,new Object[] {id} , new JobDescriptionMapper());
	}
	
	
	public List<Integer> getSkillListById(int id){
		return jdbcTemplate.query(JobsQueries.GET_SKILLSET_BY_JOB_ID, new Object[] { id }, (ResultSet rs,int rowNum) -> {
			return rs.getInt("skillid");
		});
	}
	
	public ChartDataObject getSkillCounts() {
		List<ChartObject> objects = jdbcTemplate.query(InsightQueries.FETCH_SKILL_COUNT, new ChartObjectMapper());
		System.out.println(objects.size());
		return constructChartDataObject(objects);
	}
	
	
	public ChartDataObject getLocationCounts() {
		List<ChartObject> objects = jdbcTemplate.query(
				InsightQueries.FETCH_INSIGHT(JobsConstants.LOCATION, JobDescriptionColumnNames.LOCATION)
				, new ChartObjectMapper());
		return constructChartDataObject(objects);
	}
	
	
	public ChartDataObject getHiringManagerCCounts() {
		List<ChartObject> objects = jdbcTemplate.query(
				InsightQueries.FETCH_INSIGHT(
						JobsConstants.HIRING_MANAGERS, JobsConstants.HIRING_MANAGERS), 
						new ChartObjectMapper()
						);
		return constructChartDataObject(objects);
	}
	
	public ChartDataObject getEmploymentTypeCounts() {
		List<ChartObject> objects = jdbcTemplate.query(InsightQueries.FETCH_INSIGHT(JobsConstants.EMPLOYMENT_TYPE, JobsConstants.EMPLOYMENT_TYPE), new ChartObjectMapper());
		return constructChartDataObject(objects);
	}
	
	public ChartDataObject getProfileCounts() {
		List<ChartObject> objects = jdbcTemplate.query(InsightQueries.FETCH_INSIGHT(
				JobsConstants.PROFILE, 
				JobsConstants.PROFILE
				), new ChartObjectMapper());
		
		
		return constructChartDataObject(objects);
	}
	
	private ChartDataObject constructChartDataObject(List<ChartObject> objects) {
		
		List<Integer> values = new ArrayList<>();
		List<String> labels = new ArrayList<>();
		
		values.addAll(objects.stream().map(x -> x.getValue()).collect(Collectors.toList()));
		labels.addAll(objects.stream().map(x -> x.getName()).collect(Collectors.toList()));
		
		return new ChartDataObject(values, labels);
	}

}