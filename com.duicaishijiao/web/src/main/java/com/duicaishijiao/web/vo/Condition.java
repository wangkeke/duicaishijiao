package com.duicaishijiao.web.vo;

import javax.validation.constraints.Min;

import lombok.Data;

@Data
public class Condition {
	
	public static final int PAGE_SIZE = 24;
	
	public static final int TOTAL = 12;
	
	/**
	 * ID参数
	 */
	@Min(1)
	private Integer id;
	
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
	@Min(1)
	private Integer order;
	
	/**
	 * 搜索关键词
	 */
	private String keyword;
	
	/**
	 * 时长
	 */
	private String duration;
	
	/**
	 * 第几页
	 */
	@Min(1)
	private Integer page;
	
	/**
	 * 每页显示数量
	 */
	@Min(1)
	private Integer size;
	
	/**
	 * 查询的总数
	 */
	@Min(0)
	private Integer total;
	
	
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
			}else if(years.matches("\\d+")) {
				Integer startYear = Integer.parseInt(years);
				if(startYear>=1990 && startYear<2000) {
					return 1990;
				}else if(startYear>=1980 && startYear<1990) {
					return 1980;
				}
				return startYear;
			}
		}
		return null;
	}
	
	public Integer endYear() {
		if(years!=null) {
			if(years.indexOf("-")>-1) {
				return Integer.parseInt(years.substring(0, years.indexOf("-")));
			}else if(years.matches("\\d+")) {
				Integer endYear = Integer.parseInt(years);
				if(endYear>=1990 && endYear<2000) {
					return 1999;
				}else if(endYear>=1980 && endYear<1990) {
					return 1989;
				}
				return endYear;
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
	
	public Integer getTotal(){
		if(this.total==null) {
			this.total = TOTAL;
		}else if(this.total<=0 || this.total>TOTAL) {
			this.total = TOTAL;
		}
		return this.total;
	}
	
}
