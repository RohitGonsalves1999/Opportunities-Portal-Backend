package com.accolite.opportunitiesportal.jobs.queries;

public class InsightQueries {
	
	private InsightQueries() {}

	public static final String FETCH_SKILL_COUNT = 
			"select s.name, count(js.skillId) as count\r\n" + 
			"from skillset as s, \r\n" + 
			"jobpostskillset as js,\r\n" + 
			"jobdescription as jd\r\n" + 
			"where s.id = js.skillid and js.jobid = jd.id and jd.isActive = true\r\n" + 
			"group by (s.name) order by count DESC;";
	
	public static final String FETCH_RESOLVED_SKILL_COUNT = 
			"select s.name, count(js.skillId) as count\r\n" + 
			"from skillset as s, \r\n" + 
			"jobpostskillset as js,\r\n" + 
			"jobdescription as jd\r\n" + 
			"where s.id = js.skillid and js.jobid = jd.id and jd.isActive = false and js.isActive = true\r\n" + 
			"group by (s.name) order by count DESC;";

	
	private static final String FETCH_INSIGHT= 
			"select s.name, count(jd.%s) as count\r\n" + 
			"from %s as s, \r\n" + 
			"jobdescription as jd\r\n" + 
			"where s.id = jd.%s and jd.isActive = true\r\n" + 
			"group by (s.name) order by count DESC;";
	
	public static final String FETCH_RESOLVED_INSIGHT = 
			"select distinct jd.id, l.name\r\n" + 
			"			from skillset as s, \r\n" + 
			"			jobpostskillset as js,\r\n" + 
			"			locationdetails as l,\r\n" + 
			"			jobdescription as jd\r\n" + 
			"			where s.id = js.skillid \r\n" + 
			"			and js.jobid = jd.id \r\n" + 
			"			and jd.location = l.id \r\n" + 
			"			and jd.isActive = false and js.isactive = true;\r\n" + 
			"			";
	
	public static final String FETCH_INSIGHT(String table, String column) {
		return String.format(FETCH_INSIGHT, column, table, column);
	}
}
