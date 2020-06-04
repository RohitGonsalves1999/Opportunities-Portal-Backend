package com.accolite.opportunitiesportal.testrepository;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.test.context.junit4.SpringRunner;

import com.accolite.opportunitiesportal.auth.repository.UserRepository;
import com.accolite.opportunitiesportal.jobs.constants.JobsConstants;
import com.accolite.opportunitiesportal.jobs.repository.JobsRepository;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestComponent;
import org.springframework.context.annotation.Import;

@SpringBootTest
@TestComponent
class JobsRepositoryTestActual {
	
	JobsRepository repo = new JobsRepository(getJdbcTemplate());

	UserRepository userRepo = new UserRepository(getJdbcTemplate());
	@Test
	void testFindAllJobDescriptions() {
		Assert.assertTrue(repo.findAllJobDescriptions().size() > 0);
	}

	@Test
	void testFindAllJobDescriptionVersions() {
		Assert.assertTrue(repo.findAllJobDescriptionVersions(31).size() > 0);
	}

	@Test
	void testDeleteJobDescription() {
		Assert.assertTrue(repo.deleteJobDescription(9));
	}

	@Test
	void testMarkSkillInactive() {
		Assert.assertTrue(0 < repo.markSkillInactive(9));
	}

	@Test
	void testFindById() {
		Assert.assertEquals(9, repo.findById(9).getId());
	}
	
	
	@Test
	void testGetSkillListById() {
		Assert.assertTrue(repo.getSkillListById(9).size() > 0);
	}

	@Test
	void testGetVersionSkillListById() {
		Assert.assertTrue(repo.getVersionSkillListById(10).size() > 0);
	}

	@Test
	void testGetSkillCounts() {
		Assert.assertTrue(repo.getSkillCounts().size() > 0);
	}

	@Test
	void testGetItemList() {
		Assert.assertTrue(repo.getItemList(JobsConstants.LOCATION).size() > 0);
	}
	
	@Test
	public void testUserExistsWithEmail()
	{
		Assert.assertEquals(true, userRepo.isUserWithEmailPresent("rohit.michaelgonsalves@accoliteindia.com"));
	}
	
	
	static JdbcTemplate getJdbcTemplate() {
		DriverManagerDataSource driverManagerDataSource = new DriverManagerDataSource();
		driverManagerDataSource.setUrl("jdbc:postgresql://127.0.0.1:5432/opportunityPortal");
		driverManagerDataSource.setUsername("postgres");
		driverManagerDataSource.setPassword("postgres");
		driverManagerDataSource.setDriverClassName("org.postgresql.Driver");
		return new JdbcTemplate(driverManagerDataSource);
	}

}
