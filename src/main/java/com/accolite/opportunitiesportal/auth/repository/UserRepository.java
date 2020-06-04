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

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Repository
@Slf4j
@AllArgsConstructor
public class UserRepository {
	
	@Autowired
	JdbcTemplate jdbcTemplate;
	
	
	public UserDetails getUserById(int id) {
		log.info("Get User Details By Id");
		return jdbcTemplate.queryForObject(UserQueries.GET_USER_BY_ID, new Object[] {id}, new UserDetailsRowMapper());
	}
	
	
	public UserDetails getUserById(String email) {
		log.info("Get User Details By Email");
		return jdbcTemplate.queryForObject(UserQueries.GET_USER_BY_EMAIL, new Object[] {email}, new UserDetailsRowMapper());
	}
	
	
	public int saveUserDetails(UserDetails details){
		log.info("Saving User details");
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
		
		log.info("Save Successful! returning Generated ID");
		return (int)holder.getKeys().get(JobsConstants.ID);
	}
	
	public boolean saveUserTokenByEmail(String authToken, String email) {
		log.info("Persisting User Token");
		jdbcTemplate.update(UserQueries.SAVE_USER_TOKEN_BY_EMAIL, authToken, email);
		log.info("Persisting User Token Successful");
		return true;
	}
	
	
	public boolean isUserWithTokenPresent(String token) {
		log.debug(String.format("Checking User With Token: %s", token));
		List<UserDetails> usersList = jdbcTemplate.query(UserQueries.GET_USER_BY_TOKEN, new Object [] {token}, new UserDetailsRowMapper());
		log.debug(String.format("Checking User With Token Done: %s", token));
		return usersList.size() == 1;
	}
	
	public boolean isUserWithEmailPresent(String email) {
		log.debug(String.format("Checking User With Email: %s", email));
		List<UserDetails> usersList = jdbcTemplate.query(UserQueries.GET_USER_BY_EMAIL, new Object [] {email}, new UserDetailsRowMapper());
		log.debug(String.format("Done Checking email: %s", email));
		return usersList.size() == 1;
	}

}
