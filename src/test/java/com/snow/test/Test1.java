package com.snow.test;

import java.io.File;

public class Test1 {
	
	public static void main(String[] args) {
		String path = "E:\\ShareFile\\3.28";
		
		File file = new File(path);
		for (String f : file.list()) {
			System.out.println(f);
		}
	}
	
}
