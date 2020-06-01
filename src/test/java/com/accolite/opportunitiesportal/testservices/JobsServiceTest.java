package com.accolite.opportunitiesportal.testservices;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.meta.When;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.boot.autoconfigure.batch.BatchProperties.Job;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestComponent;
import org.springframework.test.context.junit4.SpringRunner;

import com.accolite.opportunitiesportal.jobs.dao.JobsDao;
import com.accolite.opportunitiesportal.jobs.model.DropDownItem;
import com.accolite.opportunitiesportal.jobs.model.JobDescription;
import com.accolite.opportunitiesportal.jobs.model.JobDescriptionWithSkills;
import com.accolite.opportunitiesportal.jobs.service.JobsService;

import junit.framework.Assert;

import static org.mockito.Mockito.when;

@SpringBootTest
@TestComponent
@RunWith(MockitoJUnitRunner.class)
class JobsServiceTest {
	
	@InjectMocks
	public JobsService jobsService;
	
	@Mock
	public JobsDao jobsDao;
	
	
	private static Map<String, List<DropDownItem>>  dropDownAttributesList = new HashMap<>();
	
	private static Map<String, Map<Integer, String>> attributeMap = new HashMap<>();
	
	private JobDescription jobDesc = new JobDescription(1, 1, 1, 1, 1, 10, new Date(), 1, null, 0, "BEST JOB EVER");
	
	private List<Integer> skillList = Arrays.asList(new Integer[] {1,2,3,4,5});
	
	private JobDescriptionWithSkills jdWithSkills= new JobDescriptionWithSkills(jobDesc, skillList);
	
	private List<JobDescriptionWithSkills> jdList = Arrays.asList(new JobDescriptionWithSkills[] {jdWithSkills});

	@Test
	void testGetAttributes() {
		when(jobsDao.getAttributes()).thenReturn(dropDownAttributesList);
		Assert.assertEquals(dropDownAttributesList, jobsService.getAttributes());
	}

	@Test
	void testGetAttributesMap() {
		when(jobsDao.getAttributesMap()).thenReturn(attributeMap);
		Assert.assertEquals(attributeMap, jobsService.getAttributesMap());
	}

	@Test
	void testAddJobDescription() {
		when(jobsDao.saveJobDescription(jobDesc)).thenReturn(2);
		Assert.assertEquals(2, jobsService.addJobDescription(jdWithSkills).getJobDescription().getId());
	}

	@Test
	void testFindJobDescriptionbyId() {
		when(jobsDao.findJobDescriptionbyId(1)).thenReturn(jobDesc);
		when(jobsDao.getSkillsById(1)).thenReturn(skillList);
		Assert.assertEquals(jobDesc.getId(), jobsService.findJobDescriptionbyId(1).getJobDescription().getId());
		Assert.assertEquals(jobDesc.getLocation(), jobsService.findJobDescriptionbyId(1).getJobDescription().getLocation());
		Assert.assertEquals(skillList.size(), jobsService.findJobDescriptionbyId(1).getSkillList().size());
	}

	@Test
	void testGetAllJobDescriptions() {
		when(jobsDao.getAllJobDescriptions()).thenReturn(jdList);
		Assert.assertEquals(jdList, jobsService.getAllJobDescriptions());
	}

	@Test
	void testUpdateJobDescriptionWithSkills() {
		when(jobsDao.updateJobDescription(jdWithSkills)).thenReturn(true);
		Assert.assertEquals(true, jobsService.updateJobDescriptionWithSkills(jdWithSkills));
	}

	@Test
	void testDeleteJobDescription() {
		when(jobsDao.deleteJobDescription(1)).thenReturn(true);
		Assert.assertEquals(true, jobsService.deleteJobDescription(1));
	}

	@Test
	void testResolveJobDescription() {
		when(jobsDao.resolveJobDescription(1)).thenReturn(true);
		Assert.assertEquals(true, jobsService.resolveJobDescription(1));
		
	}

	@Test
	void testGetInsightMap() {
		fail("Not yet implemented");
	}

}
