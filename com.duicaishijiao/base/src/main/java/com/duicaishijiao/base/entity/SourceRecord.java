package com.duicaishijiao.base.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = SourceRecord.TABLE_NAME)
public class SourceRecord {
	
	public static final String TABLE_NAME = "tb_source_record";
	
	@Id
	@GeneratedValue
	private Integer id;
	
	/**
	 * 标题
	 */
	private String title;
	
	/**
	 * 展示图片地址
	 */
	private String img;
	
	
	/**
	 * 资源页面url
	 */
	private String url;
}
