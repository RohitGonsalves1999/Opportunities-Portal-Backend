package com.accolite.opportunitiesportal.jobs.model;

import lombok.Setter;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class JobDescription {

	int id;
	int profile;
	int location;
	int employmentType;
	int hiringManager;
	int openings;
	Date postedOn; 
	int postedBy;
	int lastUpdated;
   	Date lastUpdatedBy ;
	String description;
	
	
}
