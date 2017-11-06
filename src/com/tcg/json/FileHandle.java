package com.tcg.json;

import java.io.InputStream;

public class FileHandle {
	public static InputStream inputStreamFromFile(String path) {
		try {
			InputStream inputStream = FileHandle.class.getResourceAsStream(path);
			
			//Testing
			//System.out.println(inputStream.toString());
			
			return inputStream;
		}catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
