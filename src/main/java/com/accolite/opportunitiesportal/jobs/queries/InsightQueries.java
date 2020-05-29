package com.accolite.opportunitiesportal.jobs.queries;

public class InsightQueries {

	public static final String FETCH_SKILL_COUNT = 
			"select s.name, count(js.skillId) as count\r\n" + 
			"from skillset as s, \r\n" + 
			"jobpostskillset as js,\r\n" + 
			"jobdescription as jd\r\n" + 
			"where s.id = js.skillid and js.jobid = jd.id and jd.isActive = true\r\n" + 
			"group by (s.name) order by count DESC;";

	
	private static final String FETCH_INSIGHT_ = 
			"select s.name, count(jd.%s) as count\r\n" + 
			"from %s as s, \r\n" + 
			"jobdescription as jd\r\n" + 
			"where s.id = jd.%s and jd.isActive = true\r\n" + 
			"group by (s.name) order by count DESC;";
	
	public static final String FETCH_INSIGHT(String table, String column) {
		return String.format(FETCH_INSIGHT_, column, table, column);
	}
}
