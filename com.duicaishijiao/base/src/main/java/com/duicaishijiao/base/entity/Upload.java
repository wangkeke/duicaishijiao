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
@Table(name = Upload.TABLE_NAME)
public class Upload {
	
	public final static String TABLE_NAME = "tb_upload";
	
	@Id
	@GeneratedValue
	private Integer id;
	
	/**
	 * 上传用户
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	private User user;
	
	@ManyToOne
	private MovieInfo movieInfo;
	
	@ManyToOne
	private VideoInfo videoInfo;
	
	
	/**
	 * 状态：0：待审核；1：审核通过；2：审核未通过；3：取消
	 */
	private int status;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date updateTime;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date createTime;
	
}
