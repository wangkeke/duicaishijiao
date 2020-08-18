package com.duicaishijiao.web.restful;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.duicaishijiao.web.service.MovieInfoService;
import com.duicaishijiao.web.vo.Condition;

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
	
}
