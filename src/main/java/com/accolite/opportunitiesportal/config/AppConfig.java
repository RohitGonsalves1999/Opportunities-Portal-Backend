package com.accolite.opportunitiesportal.config;

import javax.servlet.annotation.MultipartConfig;
import javax.sql.DataSource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

/**
 * The Class AppConfig.	
 * Contains configuration for Database and mail service
 *
 * @author Rohit Gonsalves
 */
@Configuration
@MultipartConfig
@EnableWebMvc
@PropertySource("classpath:application.properties")
@ComponentScan(basePackages = "com.*")
public class AppConfig {
	
	/** The Constant URL. */
	private static final String URL = "url";
	
	/** The Constant USER. */
	private static final String USER = "dbuser";
	
	/** The Constant DRIVER. */
	private static final String DRIVER = "driver";
	
	/** The Constant PASSWORD. */
	private static final String PASS = "dbpassword";
	
	/** The Constant MAIL_HOST. */
	private static final String MAIL_HOST = "mail.host";
	
	/** The Constant MAIL_PORT. */
	private static final String MAIL_PORT = "mail.port";
	
	/** The Constant MAIL_PROTOCOL. */
	private static final String MAIL_PROTOCOL =  "mail.protocol";
	
	/** The Constant MAIL_SENDER. */
	private static final String MAIL_SENDER = "mail.sender";
	
	/** The environment. */
	@Autowired
	Environment environment;

	/**
	 * Data source.
	 *	Database Server Configuration
	 * @return the data source
	 */
	
	@Bean
	Logger getLogger() {
		return LogManager.getLogger(LogManager.ROOT_LOGGER_NAME);
	}
	@Bean
	DataSource dataSource() {
		DriverManagerDataSource driverManagerDataSource = new DriverManagerDataSource();
		driverManagerDataSource.setUrl(environment.getProperty(URL));
		driverManagerDataSource.setUsername(environment.getProperty(USER));
		driverManagerDataSource.setPassword(environment.getProperty(PASS));
		driverManagerDataSource.setDriverClassName(environment.getProperty(DRIVER));
		return driverManagerDataSource;
	}
	
	
	@Bean
	JdbcTemplate jdbcTemplate() {
		DriverManagerDataSource driverManagerDataSource = new DriverManagerDataSource();
		driverManagerDataSource.setUrl(environment.getProperty(URL));
		driverManagerDataSource.setUsername(environment.getProperty(USER));
		driverManagerDataSource.setPassword(environment.getProperty(PASS));
		driverManagerDataSource.setDriverClassName(environment.getProperty(DRIVER));
		return new JdbcTemplate(driverManagerDataSource);
	}
	
	
	/**
	 * Java mail service.
	 * Mail Sending Configuration
	 * @return the java mail sender
	 */
	@Bean
    public JavaMailSender javaMailService() {
        JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl();
        javaMailSender.setHost(environment.getProperty(MAIL_HOST));
        javaMailSender.setProtocol(environment.getProperty(MAIL_PROTOCOL));
        javaMailSender.setPort(Integer.parseInt(environment.getProperty(MAIL_PORT)));
        return javaMailSender;
    }

    /**
     * Simple mail message.
     *	Mail Sending Template
     * @return the simple mail message
     */
    @Bean
    public SimpleMailMessage simpleMailMessage() {
       SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
       simpleMailMessage.setFrom(environment.getProperty(MAIL_SENDER));
       return simpleMailMessage;
    }
	
}
	
	
