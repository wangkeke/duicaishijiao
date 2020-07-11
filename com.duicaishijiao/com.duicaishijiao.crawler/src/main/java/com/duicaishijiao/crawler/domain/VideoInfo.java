package com.duicaishijiao.crawler.domain;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.DateFormat;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import lombok.Data;

/**
{"title":"王牌：代沟有多大？欧阳娜娜跳街舞，沈腾看半天：霹雳贝贝",
"img":"https://ib11.go2yd.com/corpimage.php?url=V_0979jBOwGT&type=webp_324x211&source=mibrowser&net=wifi&docid=V_0979jBaN",
"src":"https://video.yidianzixun.com/video/get-url?key=user_upload%2F1594001616858d247061847ddbea9713280a8b60c3b4a.mp4&need_https=1",
"id":"V_0979jBaN"}
 * @author Administrator
 *
 */
@Data
@Document(indexName = "videoinfo")
public class VideoInfo {
	
	@Id
	private String id;
	
	/**
	 * 视频类型
	 */
	@Field(type = FieldType.Text)
	private String type;
	
	@Field(type = FieldType.Text)
	private String title;

	private String img;

	private String src;

	/**
	 * 时长
	 */
	private  String duration;
	
	@Field(type = FieldType.Date)
	private Date createTime;
	
	@Field(type = FieldType.Date)
	private Date updateTime;
	
}
