package com.imtest.core.test;

public class ParentTest {
	static {
		System.out.println("I am Parent");
	}

	public ParentTest() {
		// TODO Auto-generated constructor stub
		System.out.println("I am Parent Init");
	}
	
	public Number testload() {
		System.out.println("I am Parent testload");
		return 1;
	}
	
}
