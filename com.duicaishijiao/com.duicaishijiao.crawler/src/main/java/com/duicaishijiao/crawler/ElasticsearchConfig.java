package com.duicaishijiao.crawler;

import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.client.ClientConfiguration;
import org.springframework.data.elasticsearch.client.RestClients;
import org.springframework.data.elasticsearch.config.AbstractElasticsearchConfiguration;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

@Configuration
@EnableElasticsearchRepositories(basePackages = "com.duicaishijiao.crawler.repositories")
public class ElasticsearchConfig extends AbstractElasticsearchConfiguration{

	@Override
	public RestHighLevelClient elasticsearchClient() {
		final ClientConfiguration clientConfiguration = ClientConfiguration.builder()  
	            .connectedTo("192.168.186.128:9200")
	            .build();
	        return RestClients.create(clientConfiguration).rest();                       
	}

}
