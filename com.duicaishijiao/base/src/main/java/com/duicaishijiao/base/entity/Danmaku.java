package com.duicaishijiao.base.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.Data;

@Data
@Entity
@Table(name = Danmaku.TABLE_NAME)
public class Danmaku {
	
	public static final String TABLE_NAME = "tb_danmaku";
	
	@Id
	@GeneratedValue
	private Integer id;
	
	@ManyToOne(fetch = FetchType.LAZY)
	private User user;
	
	/**
	 * 匿名用户，sessionId
	 */
	private String anonymity;
	
	@ManyToOne(fetch = FetchType.LAZY)
	private MovieInfo movieInfo;
	
	@ManyToOne(fetch = FetchType.LAZY)
	private VideoInfo videoInfo;
	
	/**
	 * 弹幕内容
	 */
	private String content;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date createTime;
	
}
