package com.duicaishijiao.base.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.Data;



@Data
@Entity
@Table(name = MovieInfo.TABLE_NAME,indexes = @Index(columnList = "movieId"))
public class MovieInfo implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1157599291482127259L;

	public static final String TABLE_NAME = "tb_movieinfo";
	
	@Id
	@GeneratedValue
	private Integer id;
	
	private String movieId;
	
	@Column(name = "`name`")
	private String name;
	
	private String alias;
	
	private String img;
	
	private Double score;
	
	private String type;
	
	private String actors;
	
	private String time;
	
	@Column(name = "`status`")
	private String status;
	
	private String area;
	
	@Column(name = "`desc`",length = 1000)
	private String desc;
	
	private String author;
	
	@OneToOne
	private SourceMetric metric;
	
	private Date createTime;
	
	private Date updateTime;
	
}
