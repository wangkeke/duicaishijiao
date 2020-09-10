package com.duicaishijiao.base.entity;

/**
 *身份类型
 * @author 小张张的老头儿
 *
 */
public enum UserType {
	
	USER("已注册的用户"),ADMIN("管理员");
	
	private String name;
	
	UserType(String name){
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	
}
