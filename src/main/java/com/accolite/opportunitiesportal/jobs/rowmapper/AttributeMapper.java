package com.accolite.opportunitiesportal.jobs.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.accolite.opportunitiesportal.jobs.constants.JobsConstants;
import com.accolite.opportunitiesportal.jobs.model.DropDownItem;

public class AttributeMapper implements RowMapper<DropDownItem> {

	@Override
	public DropDownItem mapRow(ResultSet rs, int rowNum) throws SQLException {
		return new DropDownItem(
				rs.getInt(JobsConstants.ID),
				rs.getString(JobsConstants.NAME)
				);
	}

}
