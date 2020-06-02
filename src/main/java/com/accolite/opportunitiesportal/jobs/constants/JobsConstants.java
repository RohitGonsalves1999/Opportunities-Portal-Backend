package com.accolite.opportunitiesportal.jobs.constants;

import java.util.Arrays;
import java.util.List;

public class JobsConstants {
	
	private JobsConstants() {}
	
	public static final String SKILLS = "skillSet";
	public static final String RESOLVED_SKILLS = "resolvedSkillSet";
	public static final String HIRING_MANAGERS = "hiringManager";
	public static final String EMPLOYMENT_TYPE = "employmentType";
	public static final String LOCATION = "locationdetails";
	public static final String PROFILE = "profile";
	
	
	public static final String NAME = "name";
	public static final String ID = "id";
	public static final String JOB_ID = "jobId";
	public static final String SKILL_ID = "skillId";
	public static final String ENTRY_ID = "entryid";
	
	public static final String CHART_OBJECT_NAME = "name";
	public static final String CHART_OBJECT_VALUE = "count";
	
	protected static final List<String> ATTRIBUTES_LIST = Arrays.asList( 
			SKILLS, 
			HIRING_MANAGERS, 
			EMPLOYMENT_TYPE, 
			LOCATION, 
			PROFILE 
			);
	
	public static List<String> getAttributeList(){
		return ATTRIBUTES_LIST;
	}
	                                                 
}
