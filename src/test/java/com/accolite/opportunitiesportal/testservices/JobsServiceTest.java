package com.accolite.opportunitiesportal.testservices;

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
class JobsServiceTest {
	
	@InjectMocks
	public JobsService jobsService;
	
	@InjectMocks
	public JobAttributeService attributeService;
	
	@Mock
	public JobsDao jobsDao;
	
	@Mock
	public JobsAttributeDao attrDao;
	
	
	private static Map<String, List<DropDownItem>>  dropDownAttributesList = new HashMap<>();
	
	private static Map<String, Map<Integer, String>> attributeMap = new HashMap<>();
	
	
	private static Map<String, ChartDataObject> insightObject = new HashMap<>();
	
	private JobDescription jobDesc = new JobDescription(1, 1, 1, 1, 1, 10, new Date(), 1, null, 0, "BEST JOB EVER");
	
	private List<Integer> skillList = Arrays.asList(new Integer[] {1,2,3,4,5});
	
	private JobDescriptionWithSkills jdWithSkills= new JobDescriptionWithSkills(jobDesc, skillList);
	
	private List<JobDescriptionWithSkills> jdList = Arrays.asList(new JobDescriptionWithSkills[] {jdWithSkills});

	@Test
	void testGetAttributes() {
		when(attrDao.getAttributes()).thenReturn(dropDownAttributesList);
		Assert.assertEquals(dropDownAttributesList, attributeService.getAttributes());
	}

	@Test
	void testGetAttributesMap() {
		when(attrDao.getAttributesMap()).thenReturn(attributeMap);
		Assert.assertEquals(attributeMap, attributeService.getAttributesMap());
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
	void testfindJobDescriptionVersionById() {
		when(jobsDao.findJobDescriptionVersionbyId(1)).thenReturn(jobDesc);
		when(jobsDao.getVersionSkillsById(1)).thenReturn(skillList);
		Assert.assertEquals(jobDesc.getId(), jobsService.findJobDescriptionVersionById(1).getJobDescription().getId());
		Assert.assertEquals(jobDesc.getLocation(), jobsService.findJobDescriptionVersionById(1).getJobDescription().getLocation());
		Assert.assertEquals(skillList.size(), jobsService.findJobDescriptionVersionById(1).getSkillList().size());
	}

	@Test
	void testGetAllJobDescriptions() {
		when(jobsDao.getAllJobDescriptions()).thenReturn(jdList);
		Assert.assertEquals(jdList, jobsService.getAllJobDescriptions());
	}
	
	@Test
	void testgetallJobDescrptionVersions() {
		when(jobsDao.getAllJobDescriptionsVersions(Mockito.anyInt())).thenReturn(jdList);
		Assert.assertEquals(jdList, jobsService.getallJobDescrptionVersions(1));
	}

	@Test
	void testUpdateJobDescriptionWithSkills() {
		when(jobsDao.updateJobDescription(jdWithSkills)).thenReturn(true);
		when(jobsDao.findJobDescriptionbyId(1)).thenReturn(jobDesc);
		Assert.assertEquals(jdWithSkills, jobsService.updateJobDescriptionWithSkills(jdWithSkills));
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
		when(attrDao.getInsightMap()).thenReturn(insightObject);
		Assert.assertEquals(insightObject, attributeService.getInsightMap());
	}

}
