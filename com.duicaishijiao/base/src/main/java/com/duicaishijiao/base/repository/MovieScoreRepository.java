package com.duicaishijiao.base.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.duicaishijiao.base.entity.MovieInfo;
import com.duicaishijiao.base.entity.MovieScore;
import com.duicaishijiao.base.entity.User;

public interface MovieScoreRepository extends JpaRepository<MovieScore, Integer>, JpaSpecificationExecutor<MovieScore> {
	
	int countByMovieInfoAndUserAndAnonymity(MovieInfo movieInfo , User user , String anonymity);
	
}
