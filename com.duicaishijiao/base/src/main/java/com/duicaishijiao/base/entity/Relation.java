package com.duicaishijiao.base.entity;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Data;

/**
 * 用户关系
 * @author k
 *
 */
@Data
@Entity
@Table(name = Relation.TABLE_NAME)
public class Relation {
	
	public static final String TABLE_NAME = "tb_relation";
	
	@Id
	@GeneratedValue
	private Integer id;
	
	/**
	 * 来自用户
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	private User from;
	
	/**
	 * 关系
	 */
	@Enumerated(EnumType.ORDINAL)
	private UserRelation relation;
	
	/**
	 * 目标用户
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	private User to;
	
}
