package com.duicaishijiao.base.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.Data;

/**
 * 用户分享
 * @author k
 *
 */
@Data
@Entity
@Table(name = UserStar.TABLE_NAME)
public class UserShare {
	
	public static final String TABLE_NAME = "tb_user_share";
	
	@Id
	@GeneratedValue
	private Integer id;
	
	@ManyToOne
	private User user;
	
	/**
	 * 匿名用户
	 */
	private String anonymity;
	
	/**
	 * 
	 */
	@JoinColumn(name = "record_id")
	@OneToOne
	private SourceRecord record;
	
	/**
	 * 分享目标用户的设备信息
	 */
	@Column(length = 500)
	private String device;
	
	/**
	 * 分享目标用户的设备IP地址
	 */
	private String ip;
	
	@ManyToOne(fetch = FetchType.LAZY)
	private MovieInfo movieInfo;
	
	@ManyToOne(fetch = FetchType.LAZY)
	private VideoInfo videoInfo;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date updateTime;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date createTime;
}
