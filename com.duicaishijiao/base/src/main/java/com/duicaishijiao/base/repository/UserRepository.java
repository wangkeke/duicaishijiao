package com.duicaishijiao.base.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import com.duicaishijiao.base.entity.User;

public interface UserRepository extends JpaRepository<User, Integer>, JpaSpecificationExecutor<User> {
	
	User findFirstByPhoneOrEmail(String phone , String email);
	
	long countByPhoneOrEmail(String phone , String email);
	
	@Query("update User set password = ?1 , enable = ?2 where phone=?3 or email=?4")
	int updateByPhoneOrEmail(String password , boolean enable , String phone , String email);
	
	@Query("update User set enable = false , reason = ?1  where phone=?2 or email=?3")
	int disableByPhoneOrEmail(String reason , String phone , String email);
	
	@Query("update User set password = ?1 where phone=?2 or email=?3")
	int updatePassword(String password , String phone , String email);
	
}
