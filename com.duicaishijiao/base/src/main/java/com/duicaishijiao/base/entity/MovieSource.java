package com.duicaishijiao.base.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Table;

import lombok.Data;

/**
 * data example : 
 * {"id":"67399",
 * "source":"https://www.ooe.la/22tu/?url=https://jingdian.qincai-zuida.com/20200705/9042_e5dab607/index.m3u8"}
 * @author Administrator
 *
 */

@Data
@Entity
@Table(name = MovieSource.TABLE_NAME)
public class MovieSource {
	
	public final static String TABLE_NAME = "tb_moviesource";
	
	@javax.persistence.Id
	@GeneratedValue
	private Integer id;
	
	private String movieId;

	private  String name;

	private String source;
	
}
