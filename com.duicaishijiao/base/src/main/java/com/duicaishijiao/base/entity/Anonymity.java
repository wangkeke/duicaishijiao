package com.duicaishijiao.base.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.Data;

/**
 * 匿名用户表
 * @author k
 *
 */
@Data
@Entity
@Table(name = Anonymity.TABLE_NAME,indexes = @Index(columnList = "anonymity",unique = true))
public class Anonymity {
	
	public static final String TABLE_NAME = "tb_anonymity";
	
	@Id
	@GeneratedValue
	private Long id;
	
	/**
	 * 匿名名称，一般为sessionId
	 */
	@Column(nullable = false,unique = true)
	private String anonymity;
	
	/**
	 * 客户端设备信息
	 */
	private String device;
	
	/**
	 * 客户端IP地址
	 */
	private String ip;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date createTime = new Date();
	
}
