package com.accolite.opportunitiesportal.jobs.model;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class JobDescriptionWithSkills {

	private JobDescription jobDescription;
	private List<Integer> skillList;
}
