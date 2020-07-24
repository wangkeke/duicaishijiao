package com.duicaishijiao.crawler.domain;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import lombok.Data;

@Data
@Document(indexName = "userlog")
public class UserLog {
	
	@Id
	private String id;
	
	/**
	 * 如何为匿名用户则为空
	 */
	private String userId;
	
	@Field(type = FieldType.Keyword)
	private UserLogType type;
	
	@Field(type = FieldType.Text)
	private String msg;
	
	@Field(type = FieldType.Date)
	private Date createTime;
	
}
