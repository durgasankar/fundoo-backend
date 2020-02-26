package com.bridgelabz.fundoonotes.configuration;

import static springfox.documentation.builders.PathSelectors.regex;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Swagger is a set of open-source tools built around the OpenAPI Specification
 * that can help you design, build, document and consume REST APIs. ... Swagger
 * UI – renders OpenAPI specks as interactive API documentation.
 * 
 * @author Durgasankar Mishra
 * @created 2020-01-22
 * @version 1.0
 * @see {@link Docket} swagger SwaggerConfiguration class
 */
@Configuration
@EnableSwagger2
public class SwaggerConfiguration {
	@Bean
	public Docket productApi() {
		return new Docket(DocumentationType.SWAGGER_2).select()
				.apis(RequestHandlerSelectors.basePackage("com.bridgelabz.fundoonotes.controller")).paths(regex("/.*"))
				.build();
//				.apiInfo(new ApiInfo("Fundoo Notes API",
//						"Note making API for registered user. where user can register, login, change password and after login he has the options of creating note, updating,deleting and much more operations",
//						"1.0", "  ->  https://github.com/durgasankar/fundooNote",
//						new Contact("Durgasankar Mishra", "https://github.com/durgasankar/fundooNote",
//								"durgasankar.raja500@gmail.com"),
//						"Apache-2.0", "http://www.apache.org/licenses/LICENSE-2.0"));
	}

}
