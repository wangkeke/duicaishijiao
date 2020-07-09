package com.imtest.core.test;

public class SonTest extends ParentTest {
	
	static {
		System.out.println("I am Son");
	}
	
	public SonTest() {
		// TODO Auto-generated constructor stub
		System.out.println("I am Son Init");
	}
	
	@Override
	public Integer testload() {
		System.out.println("I am Son testload");
		return 2;
	}
	
}