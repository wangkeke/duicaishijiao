package com.duicaishijiao.base.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

@Data
@Entity
@Table(name = User.TABLE_NAME,
	indexes = {
			@Index(columnList = "phone",unique = true),
			@Index(columnList = "email",unique = true)
		}
)
@JsonIgnoreProperties({"phone","email","password","disable","reason","updateTime"})
public class User implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3683912575680220541L;


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
	private String nickname;
	
	/**
	 * 用户类型
	 */
	private UserType type;
	
	/**
	 * 所在城市
	 */
	private String city;
	
	/**
	 * 详细位置
	 */
	private String location;
	
	/**
	 * 是否启用
	 */
	private boolean enable = true;
	
	/**
	 * enable=false时，被禁用原因
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
