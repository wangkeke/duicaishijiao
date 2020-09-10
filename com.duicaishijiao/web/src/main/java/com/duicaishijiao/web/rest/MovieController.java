package com.duicaishijiao.web.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.duicaishijiao.web.service.MovieInfoService;
import com.duicaishijiao.web.vo.Condition;
import com.duicaishijiao.web.vo.ScorePut;
import com.duicaishijiao.web.vo.StarPut;

@RestController
@RequestMapping("movie")
public class MovieController {
	
	@Autowired
	private MovieInfoService service;
	
	
	@Cacheable("cache:movie:query")
	@GetMapping("query")
	public Object query(Condition condition) {
		return service.query(condition);
	}
	
	@Cacheable(value = "cache:movie:detail",key = "#p0",condition = "#p0!=null")
	@GetMapping("detail")
	public Object detail(Integer id) {
		return service.detail(id);
	}
	
	@Cacheable(value = "cache:movie:recommend")
	@GetMapping("recommend")
	public Object recommend(Condition condition) {
		return service.recommend(condition);
	}
	
	@Cacheable("cache:movie:comment")
	@GetMapping("comment")
	public Object comment(Condition condition) {
		return service.comment(condition);
	}
	
	@PutMapping("score")
	public void score(@RequestBody ScorePut score) {
		service.addScore(score);
	}
	
	
	@Secured({"USER"})
	@PutMapping("star")
	public void star(@RequestBody StarPut star) {
		service.addStar(star);
	}
	
	public void share() {
		
	}
	
}
