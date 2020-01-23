package com.bridgeLabz.fundooNotes.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
import static springfox.documentation.builders.PathSelectors.regex;

/**
 * Swagger is a set of open-source tools built around the OpenAPI Specification
 * that can help you design, build, document and consume REST APIs. ... Swagger
 * UI – renders OpenAPI specs as interactive API documentation.
 * 
 * @author Durgasankar Mishra
 * @created 2020-01-22
 * @version 1.0
 */
@Configuration
@EnableSwagger2
public class SwaggerConfiguration {
	@Bean
	public Docket productApi() {
		return new Docket(DocumentationType.SWAGGER_2).select()
				.apis(RequestHandlerSelectors.basePackage("com.bridgeLabz.fundooNotes.controller")).paths(regex("/.*"))
				.build();
	}
}
