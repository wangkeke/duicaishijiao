package com.duicaishijiao.base.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.duicaishijiao.base.entity.VideoInfo;

public interface VideoInfoRepository extends JpaRepository<VideoInfo, Integer> , JpaSpecificationExecutor<VideoInfo> {
	
	int countByVideoIdOrTitle(String videoId,String title);
	
}
