package com.duicaishijiao.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Import;
import org.springframework.scheduling.annotation.EnableScheduling;

import com.duicaishijiao.base.BaseConfigration;

@EnableScheduling
@EnableCaching
@Import(BaseConfigration.class)
@SpringBootApplication
public class WebApplication {
	
	public static void main(String[] args) {
		SpringApplication.run(WebApplication.class, args);
	}
	
}
