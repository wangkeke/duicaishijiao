package com.duicaishijiao.base.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = VideoSource.TABLE_NAME)
public class VideoSource {
	
	public final static String TABLE_NAME = "tb_videosource";
	
	@Id
	@GeneratedValue
	private Integer id;
	
	private String videoId;

	private String source;
	
}
