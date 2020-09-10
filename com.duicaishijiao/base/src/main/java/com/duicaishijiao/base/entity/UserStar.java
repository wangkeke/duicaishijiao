package com.duicaishijiao.base.entity;

import java.util.Date;

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
 * 用户收藏
 * @author k
 *
 */
@Data
@Entity
@Table(name = UserStar.TABLE_NAME)
public class UserStar {
	
	public static final String TABLE_NAME = "tb_user_star";
	
	@Id
	@GeneratedValue
	private Integer id;
	
	@ManyToOne
	private User user;
	
	/**
	 * -1:表示删除，0:表示正常
	 */
	private int flag;
	
	/**
	 * 
	 */
	@JoinColumn(name = "record_id")
	@OneToOne
	private SourceRecord record;
	
	@ManyToOne(fetch = FetchType.LAZY)
	private MovieInfo movieInfo;
	
	@ManyToOne(fetch = FetchType.LAZY)
	private VideoInfo videoInfo;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date updateTime;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date createTime;
	
}
