package com.duicaishijiao.crawler.repositories;

import org.springframework.data.elasticsearch.repository.ElasticsearchCrudRepository;

import com.duicaishijiao.crawler.domain.MovieSource;

public interface MovieSourceRepository extends ElasticsearchCrudRepository<MovieSource, Integer> {

}
