package com.duicaishijiao.base.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.Data;

@Data
@Entity
@Table(name = MovieScore.TABLE_NAME,indexes = @Index(columnList = "anonymity"))
public class MovieScore {
	
	public static final String TABLE_NAME = "tb_moviescore";
	
	@Id
	@GeneratedValue
	private Integer id;
	
	/**
	 * 分数
	 */
	private Double score;
	
	@ManyToOne(fetch = FetchType.LAZY)
	private MovieInfo movieInfo;
	
	@ManyToOne(fetch = FetchType.LAZY)
	private User user;
	
	/**
	 * 匿名用户session ID
	 */
	private String anonymity;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date updateTime;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date createTime;
	
}
