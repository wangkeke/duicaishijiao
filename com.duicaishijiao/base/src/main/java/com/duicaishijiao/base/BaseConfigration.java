package com.duicaishijiao.base;

import java.util.TimeZone;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

import com.duicaishijiao.base.entity.MovieInfo;
import com.duicaishijiao.base.repository.MovieInfoRepository;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.hibernate5.Hibernate5Module;

@EntityScan(basePackageClasses = MovieInfo.class)
@EnableJpaRepositories(basePackageClasses = MovieInfoRepository.class)
@Configuration
public class BaseConfigration {
	
	@Bean
	@Primary
	public ObjectMapper customObjectMapper(Jackson2ObjectMapperBuilder builder) {
		return builder.indentOutput(true)
		.serializationInclusion(Include.NON_NULL)
		.timeZone(TimeZone.getDefault())
		.failOnUnknownProperties(false)
		.modulesToInstall(new Hibernate5Module())
		.build();
	}
	
}
