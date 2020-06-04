package com.accolite.opportunitiesportal.jobs.model;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
	Date lastUpdated;
   	int lastUpdatedBy ;
	String description;
	
	
}
