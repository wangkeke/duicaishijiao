package com.duicaishijiao.crawler.repositories;

import org.springframework.data.elasticsearch.repository.ElasticsearchCrudRepository;

import com.duicaishijiao.crawler.domain.MovieInfo;

public interface MovieInfoRepository extends ElasticsearchCrudRepository<MovieInfo,Integer> {

}
