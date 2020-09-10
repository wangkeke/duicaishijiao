package com.duicaishijiao.base.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.duicaishijiao.base.entity.Anonymity;

public interface AnonymityRepository extends JpaRepository<Anonymity, Integer>, JpaSpecificationExecutor<Anonymity> {
	
	int countByAnonymity(String anonymity);
	
}
