package com.bridgeLabz.fundooNotes.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * This class has the additional configuration in the project.
 * 
 * @author Durgasankar Mishra
 * @created 2020-01-21
 * @version 1.0
 * @see {@link BCryptPasswordEncoder}
 */
@Configuration
public class ApplicationConfiguration {
	/**
	 * creates the object of BCryptPasswordEncoder
	 * 
	 * @return object of BCryptPasswordEncoder
	 */
	@Bean
	public BCryptPasswordEncoder getPasswordEncription() {
		return new BCryptPasswordEncoder();
	}
	
	
}
