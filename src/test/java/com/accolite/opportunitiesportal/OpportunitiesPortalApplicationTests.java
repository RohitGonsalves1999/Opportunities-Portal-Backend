package com.accolite.opportunitiesportal;

import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.client.RestTemplate;

import com.accolite.opportunitiesportal.auth.model.SessionStatus;
import com.accolite.opportunitiesportal.auth.model.SessionUser;
import com.accolite.opportunitiesportal.auth.model.User;
import com.accolite.opportunitiesportal.auth.model.UserConnection;
import com.accolite.opportunitiesportal.auth.model.UserDetails;
import com.accolite.opportunitiesportal.jobs.controller.JobAttributesController;
import com.accolite.opportunitiesportal.jobs.controller.JobController;
import com.accolite.opportunitiesportal.jobs.controller.JobDescriptionVersioningController;
import com.accolite.opportunitiesportal.jobs.model.ChartDataObject;
import com.accolite.opportunitiesportal.jobs.model.ChartObject;
import com.accolite.opportunitiesportal.jobs.model.DropDownItem;
import com.accolite.opportunitiesportal.jobs.model.JobDescription;
import com.accolite.opportunitiesportal.jobs.model.JobDescriptionWithSkills;
import com.accolite.opportunitiesportal.jobs.repository.JobsRepository;
import com.accolite.opportunitiesportal.jobs.service.JobAttributeService;
import com.accolite.opportunitiesportal.jobs.service.JobsService;
import com.accolite.opportunitiesportal.testutils.PojoTestUtils;



@SpringBootTest
public class OpportunitiesPortalApplicationTests {
	
	public RestTemplate template = new RestTemplate();
	
	@InjectMocks
	private JobController jobController;
	
	@InjectMocks
	private JobAttributesController jaController;
	
	
	@Mock
	private JobsService jobsService;
	
	
	@Mock JobAttributeService attirbuteService;
	
	
	private static Map<String, List<DropDownItem>>  dropDownAttributesList = new HashMap<>();
	
	private static Map<String, Map<Integer, String>> attributeMap = new HashMap<>();
	
	private JobDescription jobDesc = new JobDescription(1, 1, 1, 1, 1, 10, new Date(), 1, null, 0, "BEST JOB EVER");
	
	private List<Integer> skillList = Arrays.asList(new Integer[] {1,2,3,4,5});
	
	private JobDescriptionWithSkills jdWithSkills= new JobDescriptionWithSkills(jobDesc, skillList);
	
	private List<JobDescriptionWithSkills> jdList = Arrays.asList(new JobDescriptionWithSkills[] {jdWithSkills});
	

	@Test
	void contextLoads() {
	}
	
	
	@Test
    public void testAccesors_shouldAccessProperField() {
		PojoTestUtils.validateAccessors(JobDescription.class);
		PojoTestUtils.validateAccessors(JobDescriptionWithSkills.class);
		PojoTestUtils.validateAccessors(ChartDataObject.class);
		PojoTestUtils.validateAccessors(ChartObject.class);
		PojoTestUtils.validateAccessors(DropDownItem.class);
		PojoTestUtils.validateAccessors(User.class);
		PojoTestUtils.validateAccessors(SessionUser.class);
		PojoTestUtils.validateAccessors(UserDetails.class);
		PojoTestUtils.validateAccessors(SessionStatus.class);
		PojoTestUtils.validateAccessors(UserConnection.class);
		PojoTestUtils.validateAccessors(JobController.class);
		PojoTestUtils.validateAccessors(JobDescriptionVersioningController.class);
		PojoTestUtils.validateAccessors(JobsRepository.class);
	}
	
	
	// Test Job Controller
	@Test
	public void testHelloWorld() {
		Assert.assertEquals("HELLO WORLD", jobController.helloWorld());
	}
	
	
	@Test
	public void testAddJobPost() {
		when(jobsService.addJobDescription(jdWithSkills)).thenReturn(jdWithSkills);
		Assert.assertEquals(jobController.addJobDescription(jdWithSkills).getSkillList(), skillList);
	}

	
	@Test
	public void testGetAllJobPost(){
		when(jobsService.getAllJobDescriptions()).thenReturn(jdList);
		Assert.assertEquals(jdList, jobController.findJobDesccriptionById());
	}
	
	@Test
	public void testGetJobPostById() {
		when(jobsService.findJobDescriptionbyId(1)).thenReturn(jdWithSkills);
		Assert.assertEquals(jdWithSkills, jobController.findJobDesccriptionById(1));
	}
	
	
	@Test
	public void testResolveJobDescription() {
		when(jobsService.resolveJobDescription(1)).thenReturn(true);
		Assert.assertEquals(true, jobController.resolveJobDescription(1));
	}
	
	@Test
	public void testDeleteJobDescription() {
		when(jobsService.deleteJobDescription(1)).thenReturn(true);
		Assert.assertEquals(true, jobController.deleteJobDescription(1));
	}
	
	@Test
	public void testUpdateJobDescription() {
		when(jobsService.updateJobDescriptionWithSkills(jdWithSkills)).thenReturn(jdWithSkills);
		Assert.assertEquals(jdWithSkills, jobController.updateJobDescription(jdWithSkills));
	}
	
	@Test
	public void testAttributeList() {
		when(attirbuteService.getAttributes()).thenReturn(dropDownAttributesList);
		Assert.assertEquals(dropDownAttributesList, jaController.getDropDownItems());
	}
	
	@Test
	public void testAttributeMap() {
		when(attirbuteService.getAttributesMap()).thenReturn(attributeMap);
		Assert.assertEquals(attributeMap, jaController.getAttributesMap());
	}
}
