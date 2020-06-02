package com.accolite.opportunitiesportal.jobs.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.accolite.opportunitiesportal.jobs.constants.JobDescriptionColumnNames;
import com.accolite.opportunitiesportal.jobs.model.JobDescription;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
public class JobDescriptionMapper implements RowMapper<JobDescription> {
	
	private boolean isRetrievingVersion = false;

	@Override
	public JobDescription mapRow(ResultSet rs, int rowNum) throws SQLException {
		JobDescription description = new JobDescription();
		description.setId(rs.getInt(isRetrievingVersion?JobDescriptionColumnNames.ENTRY_ID:JobDescriptionColumnNames.ID));
		description.setDescription(rs.getString(JobDescriptionColumnNames.DESCRIPTION));
		description.setEmploymentType(rs.getInt(JobDescriptionColumnNames.EMPLOYMENT_TYPE));
		description.setHiringManager(rs.getInt(JobDescriptionColumnNames.HIRING_MANAGER));
		description.setProfile(rs.getInt(JobDescriptionColumnNames.PROFILE));
		description.setLocation(rs.getInt(JobDescriptionColumnNames.LOCATION));
		description.setOpenings(rs.getInt(JobDescriptionColumnNames.OPENINGS));
		description.setLastUpdated(rs.getDate(JobDescriptionColumnNames.LAST_UPDATED));
		description.setLastUpdatedBy(rs.getInt(JobDescriptionColumnNames.LAST_UPDATED_BY));
		description.setPostedBy(rs.getInt(JobDescriptionColumnNames.POSTED_BY));
		description.setPostedOn(rs.getDate(JobDescriptionColumnNames.POSTED_ON));
		return description;
	}

}
