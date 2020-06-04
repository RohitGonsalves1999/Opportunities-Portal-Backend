package com.accolite.opportunitiesportal.jobs.queries;

public class JobsQueries {
	
	private JobsQueries() {}
	
	private static final String ATTRIBUTE_SKELETON = "select * from %s";
	
	public static  String getAttribute(String attribute) {
		return String.format(ATTRIBUTE_SKELETON, attribute);
	}
	
	public static final String SAVE_JOB_DESCRIPTION = "INSERT INTO jobdescription(\r\n" + 
			"	profile, \r\n" + 
			"	description, \r\n" + 
			"	location, \r\n" + 
			"	employmenttype,\r\n" + 
			"	hiringmanager, \r\n" + 
			"	openings, \r\n" + 
			"	postedon, \r\n" + 
			"	postedby, \r\n" + 
			"	lastupdated, \r\n" + 
			"	lastupdatedby)\r\n" + 
			"	VALUES ( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";

	public static final String SAVE_JOB_SKILL = 
			"INSERT INTO jobpostskillset(\r\n" + 
			"	jobid, skillid)\r\n" + 
			"	VALUES (?, ?);";
	
	public static final String GET_JOB_DESCRIPTION_BY_ID =  "SELECT *\r\n" + 
			"	FROM jobdescription where id = ?;";
	
	public static final String GET_SKILLSET_BY_JOB_ID = 
		"SELECT jobid, skillid\r\n" + 
		"	FROM jobpostskillset where jobId = ?;";
	
	public static final String DELETE_SKILLS_BY_JD = 
			"DELETE FROM jobpostskillset\r\n" + 
			"	WHERE jobId = ?;";
	
	public static final String MARK_SKILL_INACTIVE_BY_JD = 
			"UPDATE jobpostskillset as js\r\n" + 
			"	SET isactive=false\r\n" + 
			"	WHERE js.jobid = ?;";
	
	public static final String MARK_SKILL_ACTIVE_BY_JD = 
			"UPDATE jobpostskillset as js\r\n" + 
			"	SET isactive=true\r\n" + 
			"	WHERE js.jobid = ?;";
	
	public static final String GET_ALL_JOB_DESCRIPTIONS = 
			"SELECT *\r\n" + 
					"	FROM jobdescription where isActive = true";
	
	public static final String UPDATE_JOB_DESCRIPTIONS = "UPDATE jobdescription\r\n" + 
			"	SET profile=?, \r\n" + 
			"	description=?, \r\n" + 
			"	location=?, \r\n" + 
			"	employmenttype=?, \r\n" + 
			"	hiringmanager=?, \r\n" + 
			"	openings=?, \r\n" + 
			"	postedon=?, \r\n" + 
			"	postedby=?,\r\n" + 
			"	lastupdated=?, \r\n" + 
			"	lastupdatedby=?\r\n" + 
			"	WHERE id = ?;";
	
	public static final String DELETE_JOB_DESCRIPTION_BY_ID = 
			"update jobdescription set isActive = false\r\n" + 
			"	WHERE id = ?;";
	
	
	public static final String SAVE_NEW_JD_VERSION = 
			"INSERT INTO outdatedjobdescription(\r\n" + 
			"	id, profile, description, location, employmenttype, hiringmanager, openings, postedon, postedby, lastupdated, lastupdatedby)\r\n" + 
			"	VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";
	
	public static final String SAVE_JD_VERSION_SKILLS = 
			"INSERT INTO outdatedjobpostskillset(\r\n" + 
			"	jobId, skillId)\r\n" + 
			"	VALUES (?, ?);";
	
	public static final String GET_JD_VERSION_SKILLS = 
			"SELECT skillid\r\n" + 
			"	FROM outdatedjobpostskillset where jobid = ?;";
	
	public static final String GET_ALL_JD_VERSIONS = "SELECT * FROM outdatedjobdescription where id=?;";
	
	public static final String GET_JD_VERSION_BY_ENTRY_ID = "SELECT * FROM outdatedjobdescription where entryId=?;";
}

	
