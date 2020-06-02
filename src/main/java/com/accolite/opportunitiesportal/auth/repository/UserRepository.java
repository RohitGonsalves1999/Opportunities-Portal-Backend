package com.accolite.opportunitiesportal.auth.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

import com.accolite.opportunitiesportal.auth.model.UserDetails;
import com.accolite.opportunitiesportal.auth.queries.UserQueries;
import com.accolite.opportunitiesportal.auth.rowmapper.UserDetailsRowMapper;
import com.accolite.opportunitiesportal.jobs.constants.JobsConstants;

@Repository
public class UserRepository {
	
	@Autowired
	JdbcTemplate jdbcTemplate;
	
	
	public UserDetails getUserById(int id) {
		return jdbcTemplate.queryForObject(UserQueries.GET_USER_BY_ID, new Object[] {id}, new UserDetailsRowMapper());
	}
	
	
	public UserDetails getUserById(String email) {
		return jdbcTemplate.queryForObject(UserQueries.GET_USER_BY_EMAIL, new Object[] {email}, new UserDetailsRowMapper());
	}
	
	
	public int saveUserDetails(UserDetails details){
		GeneratedKeyHolder holder = new GeneratedKeyHolder();
		jdbcTemplate.update( 
				new PreparedStatementCreator()  {
			@Override
			public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
			PreparedStatement ps  = con.prepareStatement(UserQueries.SAVE_USER, Statement.RETURN_GENERATED_KEYS);
			ps.setString(1, details.getName());
			ps.setString(2, details.getEmail());
			ps.setString(3, details.getAuthToken());
			return ps;		
		
		}
				},holder);
		
		
		return (int)holder.getKeys().get(JobsConstants.ID);
	}
	
	public boolean saveUserTokenByEmail(String authToken, String email) {
		
		jdbcTemplate.update(UserQueries.SAVE_USER_TOKEN_BY_EMAIL, authToken, email);
		
		return true;
	}
	
	
	public boolean isUserWithTokenPresent(String token) {
		List<UserDetails> usersList = jdbcTemplate.query(UserQueries.GET_USER_BY_TOKEN, new Object [] {token}, new UserDetailsRowMapper());
		
		return usersList.size() == 1;
	}
	
	public boolean isUserWithEmailPresent(String email) {
		List<UserDetails> usersList = jdbcTemplate.query(UserQueries.GET_USER_BY_EMAIL, new Object [] {email}, new UserDetailsRowMapper());
		
		return usersList.size() == 1;
	}

}
