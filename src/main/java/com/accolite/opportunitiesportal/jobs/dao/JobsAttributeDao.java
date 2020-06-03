package com.accolite.opportunitiesportal.jobs.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.accolite.opportunitiesportal.jobs.constants.JobsConstants;
import com.accolite.opportunitiesportal.jobs.model.ChartDataObject;
import com.accolite.opportunitiesportal.jobs.model.ChartObject;
import com.accolite.opportunitiesportal.jobs.model.DropDownItem;
import com.accolite.opportunitiesportal.jobs.repository.JobsRepository;

import lombok.extern.slf4j.Slf4j;

@Repository
@Slf4j
public class JobsAttributeDao {
	
	@Autowired
	JobsRepository jobsRepository;
	
	
	public Map<String,List<DropDownItem>> getAttributes(){
		Map<String, List<DropDownItem>> attributeMap = new HashMap<>();
		
		
		
		for(String i : JobsConstants.getAttributeList()) {
			attributeMap.put(i, jobsRepository.getItemList(i));
		}
		
		String mapString =  attributeMap.toString();
		log.info(String.format("Attribute Map List: %s", mapString));
		
		return attributeMap;
	}
	
	public Map<String,Map<Integer, String>> getAttributesMap(){
		Map<String, Map<Integer, String>> attributeMap = new HashMap<>();
		
		for(String i : JobsConstants.getAttributeList()) {
			List<DropDownItem> attributeList = jobsRepository.getItemList(i);
			Map<Integer, String> attributeSubMap = new HashMap<>();
			for(DropDownItem it : attributeList) {
				attributeSubMap.put(it.getId(), it.getName());
			}
			attributeMap.put(i, attributeSubMap);
		}
		
		
		String mapString =  attributeMap.toString();
		log.info(String.format("Attribute Map Map: %s", mapString));
		
		return attributeMap;
	}

	public Map<String, ChartDataObject> getInsightMap(){
		
		Map<String, ChartDataObject> chartMap = new HashMap<>();
		chartMap.put(JobsConstants.SKILLS, constructChartDataObject(jobsRepository.getSkillCounts()));
		chartMap.put(JobsConstants.LOCATION, constructChartDataObject(jobsRepository.getLocationCounts()));
		chartMap.put(JobsConstants.HIRING_MANAGERS, constructChartDataObject(jobsRepository.getHiringManagerCCounts()));
		chartMap.put(JobsConstants.PROFILE, constructChartDataObject(jobsRepository.getProfileCounts()));
		chartMap.put(JobsConstants.EMPLOYMENT_TYPE	, constructChartDataObject(jobsRepository.getEmploymentTypeCounts()));
		chartMap.put(JobsConstants.RESOLVED_SKILLS, constructChartDataObject(jobsRepository.getResolvedSkillCounts()));
		String mapString =  chartMap.toString();
		log.info(String.format("Chart Map: %s", mapString));
		return chartMap;
	}
	
	
	private ChartDataObject constructChartDataObject(List<ChartObject> objects) {
		
		List<Integer> values = new ArrayList<>();
		List<String> labels = new ArrayList<>();
		
		log.debug("ChartObjects: %s", objects.toString());
		
		values.addAll(objects.stream().map(x -> x.getValue()).collect(Collectors.toList()));
		labels.addAll(objects.stream().map(x -> x.getName()).collect(Collectors.toList()));
		
		return new ChartDataObject(values, labels);
	}
	
}
