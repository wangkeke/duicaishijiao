package com.duicaishijiao.base.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.Data;

/**
 * 用户举报信息表
 * @author 小张张的老头儿
 *
 */
@Data
@Entity
@Table(name = Accuse.TABLE_NAME)
public class Accuse {
	
	public static final String TABLE_NAME = "tb_accuse";
	
	@Id
	@GeneratedValue
	private Integer id;
	
	/**
	 * 举报人，必须是已注册的用户
	 */
	@ManyToOne
	private User user;
	
	/**
	 * 被举报人
	 */
	@ManyToOne
	private User accused;
	
	/**
	 * 原因
	 */
	private String reason;
	
	/**
	 * 来源地址
	 */
	private String url;
	
	/**
	 * 状态，0：待审核；1：审核通过；2：审核未通过；3：已处理
	 */
	private int status;
	
	/**
	 * 处理结果文字描述
	 */
	private String result;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date updateTime;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date createTime;
	
}
