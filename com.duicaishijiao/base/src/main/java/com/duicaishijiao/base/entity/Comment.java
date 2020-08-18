package com.duicaishijiao.base.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.Data;

@Data
@Entity
@Table(name = Comment.TABLE_NAME)
public class Comment {
	
	public static final String TABLE_NAME = "tb_comment";
	
	@Id
	@GeneratedValue
	private Long id;
	
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
	 * 评论内容
	 */
	@Lob
	private String content;
	
	/**
	 * 点赞数
	 */
	private int likes;
	
	/**
	 * 反对数
	 */
	private int dislikes;
	
	/**
	 * 设备信息，例如：华为手机，浏览器等
	 */
	private String device;
	
	/**
	 * 要回复的评论
	 */
	@ManyToOne
	private Comment parent;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date updateTime;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date createTime;
	
}
