package com.duicaishijiao.web.common;

import org.springframework.util.DigestUtils;

public class KeyIdConverter {
	
	public static String simpleKey(int id) {
		int place = Integer.MAX_VALUE>>21<<21;
		place = place | id;
		String key = Long.toString(place, Character.MAX_RADIX);
		String md5 =  DigestUtils.md5DigestAsHex(key.getBytes());
		return key+md5;
	}
	
	public static Integer simpleId(String key) {
		Integer id = null;
		if(key.length()<33) {
			return id;
		}
		String k = key.substring(0, key.length()-32);
		String md5 = key.substring(key.length()-32);
		if(!md5.equals(DigestUtils.md5DigestAsHex(k.getBytes()))) {
			return id;
		}
		try {
			id = Integer.parseInt(k, Character.MAX_RADIX);			
		} catch (NumberFormatException e) {  //非法数字字符 
			return id;
		}
		int place = Integer.MAX_VALUE>>21<<21;
		return place ^ id;
	}
	
	public static void main(String[] args) {
		String s = simpleKey(1);
		System.out.println(s);
		System.out.println(simpleId(s));
	}
	
}
