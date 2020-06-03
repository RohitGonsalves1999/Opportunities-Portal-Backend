package com.accolite.opportunitiesportal.testrepository;

import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
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
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestComponent;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;

import com.accolite.opportunitiesportal.jobs.constants.JobsConstants;
import com.accolite.opportunitiesportal.jobs.model.ChartDataObject;
import com.accolite.opportunitiesportal.jobs.model.ChartObject;
import com.accolite.opportunitiesportal.jobs.model.DropDownItem;
import com.accolite.opportunitiesportal.jobs.model.JobDescription;
import com.accolite.opportunitiesportal.jobs.model.JobDescriptionWithSkills;
import com.accolite.opportunitiesportal.jobs.repository.JobsRepository;
import com.accolite.opportunitiesportal.jobs.rowmapper.AttributeMapper;
import com.accolite.opportunitiesportal.jobs.rowmapper.ChartObjectMapper;
import com.accolite.opportunitiesportal.jobs.rowmapper.JobDescriptionMapper;



@SpringBootTest
@TestComponent
@RunWith(MockitoJUnitRunner.class)
class JobsRepositoryTest {
	
	@InjectMocks
	JobsRepository repo;
	
	@Mock
	JdbcTemplate template;
	
	private static Map<String, List<DropDownItem>>  dropDownAttributesList = new HashMap<>();
	
	private static Map<String, Map<Integer, String>> attributeMap = new HashMap<>();
	
	
	private static Map<String, ChartDataObject> insightObject = new HashMap<>();
	
	private JobDescription jobDesc = new JobDescription(1, 1, 1, 1, 1, 10, new Date(), 1, null, 0, "BEST JOB EVER");
	
	private List<Integer> skillList = Arrays.asList(new Integer[] {1,2,3,4,5});
	
	private JobDescriptionWithSkills jdWithSkills= new JobDescriptionWithSkills(jobDesc, skillList);
	
	private List<JobDescriptionWithSkills> jdList = Arrays.asList(new JobDescriptionWithSkills[] {jdWithSkills});
	
	private List<JobDescription> jdList2 = Arrays.asList(new JobDescription[] {jobDesc});
	
	public List<DropDownItem> ddItems = Arrays.asList(new DropDownItem[] { 
			new DropDownItem(1,"DDItem 1"),
			new DropDownItem(2,"DDItem 2")
	});
	
	
	public Map<String, ChartDataObject> insightMap = new HashMap<>();
	
	public List<String> mockLabels = Arrays.asList(new String[] { "Mock Label 1", "Mock Label2" });
	public List<Integer> mockData = Arrays.asList(new Integer[] { 1,2 });
	
	public List<ChartObject> mockObjects = Arrays.asList(new ChartObject[] { new ChartObject("Mock Label 1", 1),
			new ChartObject("Mock Label 2", 2)});
	
	
	GeneratedKeyHolder holder = new GeneratedKeyHolder();

	@Test
	void testSaveJobDescriptionSkills() {
		when(template.update(Mockito.anyString(), Mockito.any(BatchPreparedStatementSetter.class))).thenReturn(skillList.size());
		
		Assert.assertEquals(skillList.size(), repo.saveJobDescriptionSkills(1, skillList));
	}
	
	@Test 
	void testsaveJobDescriptionVersionSkills(){
		when(template.update(Mockito.anyString(), Mockito.any(BatchPreparedStatementSetter.class))).thenReturn(skillList.size());
		Assert.assertEquals(skillList.size(), repo.saveJobDescriptionVersionSkills(1, skillList));
	}

	@Test
	void testFindAllJobDescriptions() {
		when(template.query(Mockito.anyString(), Mockito.any(JobDescriptionMapper.class))).thenReturn(jdList2);
		Assert.assertEquals(jdList2, repo.findAllJobDescriptions());
	}
	
	@Test
	void testfindAllJobDescriptionVersions() {
		when(template.query(Mockito.anyString(), any(Object[].class) ,Mockito.any(JobDescriptionMapper.class))).thenReturn(jdList2);
		Assert.assertEquals(jdList2, repo.findAllJobDescriptionVersions(1));
	}

	@Test
	void testUpdateJobDescription() {
		when(template.update(anyString(), any(Object[].class))).thenReturn(1);
		Assert.assertEquals(true, repo.updateJobDescription(jobDesc));
	}

	@Test
	void testDeleteSkillsByJd() {
		when(template.update(anyString(), any(Object.class))).thenReturn(5);
		Assert.assertEquals(true, repo.deleteSkillsByJd(1));
	}

	@Test
	void testDeleteJobDescription() {
		when(template.update(anyString(), any(Object.class))).thenReturn(5);
		Assert.assertEquals(true, repo.deleteJobDescription(1));
	}

	@Test
	void testMarkSkillInactive() {
		when(template.update(anyString(), any(Object.class))).thenReturn(5);
		Assert.assertEquals(5, repo.markSkillInactive(1));
	}

	@Test
	void testFindById() {
		when(template.queryForObject(anyString(), Mockito.any(Object[].class), any(JobDescriptionMapper.class))).thenReturn(jobDesc);
		Assert.assertEquals(jobDesc, repo.findById(1));
	}
	
	@Test
	void testFindVersionById() {
		when(template.queryForObject(anyString(), Mockito.any(Object[].class), any(JobDescriptionMapper.class))).thenReturn(jobDesc);
		Assert.assertEquals(jobDesc, repo.findVersionById(1));
	}

	@Test
	void testGetSkillListById() {
		when(template.query(anyString(), any(Object[].class), any(RowMapper.class))).thenReturn(skillList);
		
		Assert.assertEquals(skillList, repo.getSkillListById(1));
	}
	
	@Test
	void testGetVersionSkillListById() {
		when(template.query(anyString(), any(Object[].class), any(RowMapper.class))).thenReturn(skillList);
		
		Assert.assertEquals(skillList, repo.getVersionSkillListById(1));
	}

	@Test
	void testGetSkillCounts() {
		when(template.query(anyString(), any(ChartObjectMapper.class))).thenReturn(mockObjects);
		Assert.assertEquals(mockObjects, repo.getSkillCounts());
	}

	@Test
	void testGetResolvedSkillCounts() {
		when(template.query(anyString(), any(ChartObjectMapper.class))).thenReturn(mockObjects);
		Assert.assertEquals(mockObjects, repo.getResolvedSkillCounts());
	}

	@Test
	void testGetLocationCounts() {
		when(template.query(anyString(), any(ChartObjectMapper.class))).thenReturn(mockObjects);
		Assert.assertEquals(mockObjects, repo.getLocationCounts());
	}

	@Test
	void testGetHiringManagerCCounts() {
		when(template.query(anyString(), any(ChartObjectMapper.class))).thenReturn(mockObjects);
		Assert.assertEquals(mockObjects, repo.getHiringManagerCCounts());
	}

	@Test
	void testGetEmploymentTypeCounts() {
		when(template.query(anyString(), any(ChartObjectMapper.class))).thenReturn(mockObjects);
		Assert.assertEquals(mockObjects, repo.getEmploymentTypeCounts());
	}

	@Test
	void testGetProfileCounts() {
		when(template.query(anyString(), any(ChartObjectMapper.class))).thenReturn(mockObjects);
		Assert.assertEquals(mockObjects, repo.getProfileCounts());
	}

	@Test
	void testGetItemList() {
		when(template.query(anyString(), any(AttributeMapper.class))).thenReturn(ddItems);
		Assert.assertEquals(ddItems, repo.getItemList(JobsConstants.LOCATION));
	}
	

}
