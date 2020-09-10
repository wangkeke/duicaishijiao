package com.duicaishijiao.base.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

/**
 * 资源纬度统计
 * @author 小张张的老头儿
 *
 */
@Data
@Entity
@Table(name = SourceMetric.TABLE_NAME)
@JsonIgnoreProperties({"updateTime","createTime"})
public class SourceMetric {
	
	public final static String TABLE_NAME = "tb_sourcemetric";
	
	@Id
	@GeneratedValue
	private Integer id;
	
	/**
	 * 播放数量
	 */
	private Integer plays;
	
	/**
	 * 评分数
	 */
	private Integer scores;
	
	/**
	 * 评论数量
	 */
	private Integer comments;
	
	/**
	 * 弹幕数量
	 */
	private Integer danmakus;
	
	/**
	 * 点赞数
	 */
	private Integer likes;
	
	/**
	 * 反对数
	 */
	private Integer dislikes;
	
	/**
	 * 收藏数
	 */
	private Integer stars;
	
	/**
	 * 分享数量
	 */
	private Integer shares;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date updateTime;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date createTime;
}
