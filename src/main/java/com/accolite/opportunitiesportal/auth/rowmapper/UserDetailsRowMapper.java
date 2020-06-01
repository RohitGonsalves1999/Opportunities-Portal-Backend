package com.accolite.opportunitiesportal.auth.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.accolite.opportunitiesportal.auth.constants.UserColumns;
import com.accolite.opportunitiesportal.auth.model.UserDetails;

public class UserDetailsRowMapper implements RowMapper<UserDetails> {

	@Override
	public UserDetails mapRow(ResultSet rs, int rowNum) throws SQLException {
		return new UserDetails(
				rs.getInt(UserColumns.ID),
				rs.getString(UserColumns.EMAIL),
				rs.getString(UserColumns.NAME),
				rs.getString(UserColumns.AUTH_TOKEN)
				);
	}

}
