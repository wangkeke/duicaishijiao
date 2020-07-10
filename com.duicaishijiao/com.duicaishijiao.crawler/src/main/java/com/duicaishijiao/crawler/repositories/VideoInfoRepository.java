package com.duicaishijiao.crawler.repositories;

import org.springframework.data.elasticsearch.repository.ElasticsearchCrudRepository;

import com.duicaishijiao.crawler.domain.VideoInfo;

public interface VideoInfoRepository extends ElasticsearchCrudRepository<VideoInfo, String> {

}
