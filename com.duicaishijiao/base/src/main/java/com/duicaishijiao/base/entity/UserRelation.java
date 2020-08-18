package com.duicaishijiao.base.entity;

public enum UserRelation {
	
	ATTENTION("关注"),IGNORE("屏蔽");
	
	private String name;
	
	UserRelation(String name){
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	
}
