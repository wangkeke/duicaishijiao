package com.duicaishijiao.base.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.duicaishijiao.base.entity.MovieInfo;
import com.duicaishijiao.base.entity.User;
import com.duicaishijiao.base.entity.UserStar;

public interface UserStarRepository extends JpaRepository<UserStar, Integer>, JpaSpecificationExecutor<UserStar> {
	
	int countByMovieInfoAndUserAndFlagNot(MovieInfo movieInfo , User user , int flag);
	
	UserStar findFirstByMovieInfoAndUserAndFlagNot(MovieInfo movieInfo , User user , int flag);
	
}
