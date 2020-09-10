package com.duicaishijiao.web.common;

/**
 * redis存储key
 * @author 小张张的老头儿
 *
 */
public class KeyConstants {
	
	
	public static final String USER_ANONYMITY_COUNT = "cache:anonymity:count";
	
	/**
	 * 电影ID
	 */
	public static final String MOVIE_ID = "cache:movie:id";
	
	/**
	 * 电影在线观看数量统计
	 */
	public static final String MOVIE_WATCHS = "cache:movie:watchs";
	
	/**
	 * 默认计数器
	 */
	public static final String DEFAULT_COUNT = "default:count";
	
}
