package com.duicaishijiao.base.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Index;
import javax.persistence.OneToOne;
import javax.persistence.Table;

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
@Entity
@Table(name = VideoInfo.TABLE_NAME,indexes = {@Index(columnList = "videoId"),@Index(columnList = "title")})
public class VideoInfo implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -8828417740995792396L;

	public final static String TABLE_NAME = "tb_videoinfo";
	
	@javax.persistence.Id
	@GeneratedValue
	private Integer id;
	
	private String videoId;
	
	private String title;

	private String img;

	private String src;
	
	@OneToOne
	private SourceMetric metric;
	
	/**
	 * 标签
	 */
	private String tags;
	
	/**
	 * 时长
	 */
	private  String duration;
	
	private Date createTime;
	
	private Date updateTime;
	
}
