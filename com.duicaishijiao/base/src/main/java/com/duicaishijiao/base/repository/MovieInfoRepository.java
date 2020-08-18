package com.duicaishijiao.base.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.duicaishijiao.base.entity.MovieInfo;

public interface MovieInfoRepository extends JpaRepository<MovieInfo, Integer>,JpaSpecificationExecutor<MovieInfo>{
	
	int countByMovieId(String movieId);
	
}
