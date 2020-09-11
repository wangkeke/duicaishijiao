package com.duicaishijiao.base.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.duicaishijiao.base.entity.UserShare;

public interface UserShareRepository extends JpaRepository<UserShare, Integer>, JpaSpecificationExecutor<UserShare> {

}
