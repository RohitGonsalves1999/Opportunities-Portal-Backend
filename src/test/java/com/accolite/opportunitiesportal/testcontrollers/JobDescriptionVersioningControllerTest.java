package com.accolite.opportunitiesportal.testcontrollers;

import static org.junit.jupiter.api.Assertions.*;

import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestComponent;

import com.accolite.opportunitiesportal.jobs.controller.JobDescriptionVersioningController;
import com.accolite.opportunitiesportal.jobs.dao.JobsAttributeDao;
import com.accolite.opportunitiesportal.jobs.dao.JobsDao;
import com.accolite.opportunitiesportal.jobs.model.ChartDataObject;
import com.accolite.opportunitiesportal.jobs.model.DropDownItem;
import com.accolite.opportunitiesportal.jobs.model.JobDescription;
import com.accolite.opportunitiesportal.jobs.model.JobDescriptionWithSkills;
import com.accolite.opportunitiesportal.jobs.service.JobAttributeService;
import com.accolite.opportunitiesportal.jobs.service.JobsService;

import junit.framework.Assert;

@SpringBootTest
@TestComponent
@RunWith(MockitoJUnitRunner.class)
class JobDescriptionVersioningControllerTest {
	
	
	@InjectMocks
	JobDescriptionVersioningController jobController;
	
	@Mock
	JobsService jobsService;
	
	private JobDescription jobDesc = new JobDescription(1, 1, 1, 1, 1, 10, new Date(), 1, null, 0, "BEST JOB EVER");
	
	private List<Integer> skillList = Arrays.asList(new Integer[] {1,2,3,4,5});
	
	private JobDescriptionWithSkills jdWithSkills= new JobDescriptionWithSkills(jobDesc, skillList);
	
	private List<JobDescriptionWithSkills> jdList = Arrays.asList(new JobDescriptionWithSkills[] {jdWithSkills});

	@Test
	void testGetAllJobDescriptionVersions() {
		when(jobsService.getallJobDescrptionVersions(1)).thenReturn(jdList);
		Assert.assertEquals(jdList, jobController.getAllJobDescriptionVersions(1));
	}

	@Test
	void testGetSpecificJobDescriptionVersion() {
		when(jobsService.findJobDescriptionVersionById(Mockito.anyInt())).thenReturn(jdWithSkills);
		Assert.assertEquals(jdWithSkills, jobController.getSpecificJobDescriptionVersion(1));
	}

}
