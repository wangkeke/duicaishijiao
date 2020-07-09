package com.imtest.core;

import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.TreeMap;
import java.util.TreeSet;

import com.imtest.core.test.SonTest;

public class MainTest {

	public static void main(String[] args) {
		SonTest sonTest = new SonTest();
		TreeSet<String> set = new TreeSet<String>();
		sonTest.testload();
		set.add("c");
		set.add("a");
		set.add("b");
//		set.add("a");
		Iterator<String> it = set.iterator();
		System.out.println(it.next());
		System.out.println(it.next());
		System.out.println(it.next());
		System.out.println("dasda");
		
	}

}
