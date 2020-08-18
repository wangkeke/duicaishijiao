package com.duicaishijiao.base.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.Data;

@Data
@Entity
@Table(name = UserActivity.TABLE_NAME)
public class UserActivity {
	
	public static final String TABLE_NAME = "tb_user_activity";
	
	@Id
	@GeneratedValue
	private Long id;
	
	@ManyToOne(fetch = FetchType.LAZY)
	private User user;
	
	/**
	 * 匿名用户保存session id
	 */
	private String anonymity;
	
	/**
	 * 是否观看
	 */
	private boolean watch;
	
	/**
	 * 是否评论
	 */
	private boolean comment;
	
	/**
	 * 是否发送了弹幕
	 */
	private boolean danmaku;
	
	/**
	 * 是否点赞
	 */
	@Column(name = "`like`")
	private boolean like;
	
	/**
	 * 是否反对
	 */
	private boolean dislike;
	
	/**
	 * 是否收藏
	 */
	private boolean star;
	
	/**
	 * 是否分享
	 */
	@Column(name = "`share`")
	private boolean share;
	
	/**
	 * 操作的资源信息
	 */
	@OneToOne
	private SourceRecord record;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date createTime;
	
}
