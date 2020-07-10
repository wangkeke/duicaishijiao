package com.duicaishijiao.crawler.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import lombok.Data;

/**
 * data example : 
 * {"id":"67399",
 * "source":"https://www.ooe.la/22tu/?url=https://jingdian.qincai-zuida.com/20200705/9042_e5dab607/index.m3u8"}
 * @author Administrator
 *
 */

@Data
@Document(indexName = "moviesource")
public class MovieSource {
	
	@Id
	private Integer id;
	
	private String source;
	
}
