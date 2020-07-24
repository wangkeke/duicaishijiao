package com.duicaishijiao.crawler;

import java.util.concurrent.atomic.AtomicLong;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class KeyGenerator {
	
	@Value("${server.node.name}")
	private String nodeName;
	
	private static final AtomicLong atomCount = new AtomicLong(1);
	
	public String getKey() {
		String name = Integer.toString(nodeName.hashCode(),Character.MAX_RADIX);
		if(name.startsWith("-")) {
			name = "v" + name.substring(1);
		}
		Long n = atomCount.getAndIncrement();
		return name+"_"+ Long.toString(n, Character.MAX_RADIX);
	}
	
}
