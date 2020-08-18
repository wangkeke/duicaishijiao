package com.duicaishijiao.base.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.Data;

@Data
@Entity
@Table(name = User.TABLE_NAME,
	indexes = {
			@Index(columnList = "phone",unique = true),
			@Index(columnList = "email",unique = true)
		}
)
public class User {
	
	public static final String TABLE_NAME = "tb_user";
	
	
	@Id
	@GeneratedValue
	private Integer id;
	
	/**
	 * 手机号登录
	 */
	private String phone;
	
	/**
	 * 邮箱登录
	 */
	private String email;
	
	/**
	 * 密码
	 */
	private String password;
	
	/**
	 * 头像地址
	 */
	private String avatar;
	
	/**
	 * 昵称
	 */
	private String nicename;
	
	
	/**
	 * 所在城市
	 */
	private String city;
	
	/**
	 * 详细位置
	 */
	private String location;
	
	/**
	 * 是否被禁用
	 */
	private boolean disable = false;
	
	/**
	 * 被禁用原因
	 */
	private String reason;
	
	/**
	 * 最后登录的时间
	 */
	@Temporal(TemporalType.TIMESTAMP)
	private Date lastLogin;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date updateTime;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date createTime;
	
}
