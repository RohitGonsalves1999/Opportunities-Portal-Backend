package com.accolite.opportunitiesportal.jobs.queries;

public class JobsQueries {
	
	
	private static final String ATTRIBUTE_SKELETON = "select * from %s";
	
	public static  String getAttribute(String attribute) {
		return String.format(ATTRIBUTE_SKELETON, attribute);
	}

}
