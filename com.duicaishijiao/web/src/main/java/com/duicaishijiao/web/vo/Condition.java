package com.duicaishijiao.web.vo;

import lombok.Data;

@Data
public class Condition {
	
	public static final int PAGE_SIZE = 24;
	
	/**
	 * 分类，动作|恐怖
	 */
	private String type;
	
	/**
	 * 国家
	 */
	private String country;
	
	/**
	 * 年代
	 */
	private String years;
	
	/**
	 * 1:最近热播，2:最新上架，3:高分好评
	 */
	private Integer order;
	
	/**
	 * 搜索关键词
	 */
	private String[] keyword;
	
	/**
	 * 时长
	 */
	private String duration;
	
	/**
	 * 第几页
	 */
	private Integer page;
	
	/**
	 * 每页显示数量
	 */
	private Integer size;
	
	
	public Integer getPage(){
		if(this.page==null || this.page<=0)
			this.page = null;
		return this.page;
	}
	
	public Integer getSize(){
		if(this.size==null || this.size<=0) {
			this.size = PAGE_SIZE;
		}
		return this.size;
	}
	
	public Integer startYear() {
		if(years!=null) {
			if(years.indexOf("-")>-1) {
				return Integer.parseInt(years.substring(years.indexOf("-")+1));
			}else {
				return Integer.parseInt(years);
			}
		}
		return null;
	}
	
	public Integer endYear() {
		if(years!=null) {
			if(years.indexOf("-")>-1) {
				return Integer.parseInt(years.substring(0, years.indexOf("-")));
			}else if(Integer.parseInt(years)<2000) {
				return Integer.parseInt(years)+9;
			}else {
				return Integer.parseInt(years);
			}
		}
		return null;
	}
	
	public String startDuration() {
		if(duration!=null) {
			if(duration.indexOf("-")>-1) {
				return duration.substring(0,duration.indexOf("-"));
			}else {
				return duration;
			}
		}
		return null;
	}
	
	public String endDuration() {
		if(duration!=null) {
			if(duration.indexOf("-")>-1) {
				return duration.substring(duration.indexOf("-")+1);
			}
		}
		return null;
	}
	
}
