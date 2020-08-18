package com.duicaishijiao.crawler;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.duicaishijiao.base.entity.MovieInfo;
import com.duicaishijiao.base.repository.MovieInfoRepository;

@EnableJpaRepositories(basePackageClasses = MovieInfoRepository.class)
@EntityScan(basePackageClasses = MovieInfo.class)
@Configuration
public class JpaConfig {

}
