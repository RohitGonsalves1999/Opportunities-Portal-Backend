package com.accolite.opportunitiesportal.jobs.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.accolite.opportunitiesportal.jobs.constants.JobsConstants;
import com.accolite.opportunitiesportal.jobs.model.ChartObject;

public class ChartObjectMapper implements RowMapper<ChartObject>{

	@Override
	public ChartObject mapRow(ResultSet rs, int rowNum) throws SQLException {
		
		return new ChartObject(
				rs.getString(JobsConstants.CHART_OBJECT_NAME),
				rs.getInt(JobsConstants.CHART_OBJECT_VALUE)
				);
	}

}
