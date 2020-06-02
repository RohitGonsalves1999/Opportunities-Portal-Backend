package com.accolite.opportunitiesportal.jobs.model;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ChartDataObject {

	List<Integer> values;
	List<String> labels;
}
