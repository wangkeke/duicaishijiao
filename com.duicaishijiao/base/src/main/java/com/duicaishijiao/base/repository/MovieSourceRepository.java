package com.duicaishijiao.base.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.duicaishijiao.base.entity.MovieInfo;
import com.duicaishijiao.base.entity.MovieSource;

public interface MovieSourceRepository extends JpaRepository<MovieSource, Integer> {
	
	List<MovieSource> findByMovieIdOrMovieInfo(String movieId , MovieInfo movieInfo);
	
}
