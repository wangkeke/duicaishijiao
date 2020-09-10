package com.duicaishijiao.base.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import com.duicaishijiao.base.entity.Comment;
import com.duicaishijiao.base.entity.MovieInfo;

public interface CommentRepository extends JpaRepository<Comment, Integer> ,JpaSpecificationExecutor<Comment>{
	
	@Query("select c from Comment c left join c.user u where c.movieInfo = ?1")
	Page<Comment> findByMovieInfo(MovieInfo movieInfo , Pageable pageable);
	
}
