 package com.luv2code.springsecurity.demo.config;


import org.springframework.beans.factory.annotation.Autowired;  
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import com.mchange.v2.c3p0.ComboPooledDataSource;

import java.beans.PropertyVetoException;
import java.util.logging.Logger;

import javax.sql.DataSource;

@Configuration
@EnableWebMvc
@ComponentScan(basePackages = "com.luv2code.springsecurity.demo")
@PropertySource("classpath:persistence-mysql.properties")
public class DemoAppConfig 
{
	
	//set up a variable to hold the properties
	@Autowired
	private Environment env; // will hold and read the data from properties files
	
	
	
	//set up a logger for diagnostics
	private Logger logger = Logger.getLogger(getClass().getName());
	
	
	
	//define a bean for the ViewResolver
	@Bean
	public ViewResolver viewResolver()
	{
		InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
		
		viewResolver.setPrefix("/WEB-INF/view/");
		viewResolver.setSuffix(".jsp");
		
		return viewResolver;
	}
	
	//define a bean for security datasource
	@Bean
	public DataSource securityDataSource()
	{
		//CREATE A CONNECTION POOL
		ComboPooledDataSource securityDataSource = 
						new ComboPooledDataSource();
		
		
		//SET THE JDBC DRIVER
		try 
		{
			securityDataSource
			.setDriverClass(env.getProperty("jdbc.driver")); // read the db  configs
			//from the properties file
		}
		catch (PropertyVetoException exc) 
		{	
			throw new RuntimeException(exc);
		}
		
		
		//LOG THE CONNECTION PROPS
		
		//for sanity's sake log this info just to make 
		//sure we are actually reading the data from
		//the properties file
		logger.info(">>jdbc.url=" + env.getProperty("jdbc.url"));
		logger.info(">>jdbc.user=" + env.getProperty("jdbc.user"));
		
		
		//SET THE DATABASE CONNECTION PROPS
		securityDataSource.setJdbcUrl(env.getProperty("jdbc.url"));
		securityDataSource.setUser(env.getProperty("jdbc.user"));
		securityDataSource.setPassword(env.getProperty("jdbc.password"));

		
		
		//SET THE CONNECTION POOL PROPS
		securityDataSource.setInitialPoolSize(
				getIntProperty("connection.pool.initialPoolSize"));
		
		securityDataSource.setMinPoolSize(
				getIntProperty("connection.pool.minPoolSize"));
		
		securityDataSource.setMaxPoolSize(
				getIntProperty("connection.pool.maxPoolSize"));
		
		securityDataSource.setMaxIdleTime(
				getIntProperty("connection.pool.maxIdleTime"));
		
		return securityDataSource; 
	}
	
	//NEED A HELPER METHOD
	//READ THE ENV PROPERTY AND CONVERT TO INT
	private int getIntProperty(String propName)
	{
		String propVal = env.getProperty(propName);
		//now convert to int
		
		int intPropVal = Integer.parseInt(propVal);
		return intPropVal;
	}
	
	
}
