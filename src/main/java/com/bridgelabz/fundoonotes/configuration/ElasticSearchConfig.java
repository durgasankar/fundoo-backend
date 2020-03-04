package com.bridgelabz.fundoonotes.configuration;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.bridgelabz.fundoonotes.utility.Util;

/**
 * Configuration bean of elastic search.which takes httpClient which takes
 * httpClientBuilder which takes host-name , port and schema as input parameter.
 * 
 * @author Durgasankar Mishra
 * @created 2020-03-03
 * @version 1.0
 */
@Configuration
public class ElasticSearchConfig {

	@Bean(destroyMethod = "close")
	public RestHighLevelClient client() {
		return new RestHighLevelClient(
				RestClient.builder(new HttpHost("localhost", Util.ELASTIC_SEARCH_PORT_NUMBER, "http")));

	}

}