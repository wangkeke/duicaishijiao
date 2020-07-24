package com.duicaishijiao.crawler.domain;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import lombok.Data;

/**
 * data example:
 * {"id":"67616","href":"/play-67616-1-1/","name":"1944",
 * 	"alias":"一九四四/1944铁甲连(台)/我们的1944/1944: Forced to Fight",
 * 	"img":"https://tu.tianzuida.com/pic/upload/vod/2020-07-08/202007081594184263.jpg",
 * "score":"8.1","type":"战争片",
 * "actors":"卡斯帕·威尔伯格,克里斯蒂安·乌克斯库拉,麦肯·施密特,格特·劳塞,亨德里克·图姆佩勒,卡尔·安德烈",
 * "time":"2015","status":"BD高清中字","area":"其它",
 * "desc":" 1944：1944年……德军与苏联列宁格勒方面军为争夺纳尔瓦地峡进行了一连串战斗。7月，苏军攻占了纳尔瓦，这座古城九百年来一直是俄罗斯和西方世界的分界线。纳尔瓦身后的坦能堡防线是爱沙尼亚首都塔林的最后防线，一群20岁出头的爱沙尼亚青年人志愿加入武装党卫军亲卫队第20师，开始在东线迎头痛击苏军装甲部队，为反抗苏联的占领而战。 这不是爱沙尼亚第一次沦为东方和西方、德意志人和斯拉夫人交战的战场。从楚德湖的冰上大战算起，这一战斗已持续了七百多年，夹在中间的爱沙尼亚人也不可避免地被双方裹挟其中，成为历史的牺牲品。…… 到了9月，由于芬兰宣布停止同德国的军事合作并签订莫斯科停战协定，腹背受敌的德军决定撤出爱沙尼亚。苏军尾随着德军一路前进直至爱沙尼亚的首都塔林，红军中的叶沙尼亚（俄罗斯化的爱沙尼亚人）士兵也亲眼见证了，那面象征着爱沙尼亚民族独立的蓝、黑、白三色旗，于德军撤离的9月18日在塔林市的象征——赫尔曼塔悬挂了四天之后，终于在9月22日被苏联的红旗所取代。…… 占领塔林，并非苏德双方在爱沙尼亚的最后一战。……11月，在萨雷马岛泥泞、茂密的森林中，叶沙尼亚士兵顶着德军舰艇的炮火，驱逐了爱沙尼亚最后一批德军",
 * "author":"埃尔莫·纽加农"}
 * @author Administrator
 *
 */

@Data
@Document(indexName = "movieinfo")
public class MovieInfo {
	
	@Id
	private Integer id;
	
	private String name;
	
	private String alias;
	
	private String img;
	
	private Double score;
	
	private String type;
	
	private String actors;
	
	private String time;
	
	private String status;
	
	private String area;
	
	@Field(type = FieldType.Text)
	private String desc;
	
	private String author;
	
	@Field(type = FieldType.Date)
	private Date createTime;
	
	@Field(type = FieldType.Date)
	private Date updateTime;
	
}
