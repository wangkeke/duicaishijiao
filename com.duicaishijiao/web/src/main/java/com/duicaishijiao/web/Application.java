package com.duicaishijiao.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.duicaishijiao.base.entity.MovieInfo;
import com.duicaishijiao.base.repository.MovieInfoRepository;

@EnableCaching
@SpringBootApplication
@EnableJpaRepositories(basePackageClasses = MovieInfoRepository.class)
@EntityScan(basePackageClasses = MovieInfo.class)
public class Application {
	
	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
	
}
