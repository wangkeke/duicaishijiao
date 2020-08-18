package com.duicaishijiao.base.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.duicaishijiao.base.entity.VideoSource;

public interface VideoSourceRepository extends JpaRepository<VideoSource, Integer> , JpaSpecificationExecutor<VideoSource> {

}
