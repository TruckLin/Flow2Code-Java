package com.tcg.json;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Scanner;

import org.json.JSONObject;

public class JSONUtils {
	
	public static JSONObject getJSONObjectFromFile(String path) {
		return new JSONObject(FileHandle.getTextStringFromPath(path));
	}
	
	
	public static boolean objectExists(JSONObject jsonObject, String key) {
		Object o;
		
		try {
			o = jsonObject.get(key);
		}catch(Exception e) {
			return false;
		}
		
		return o != null;
	}
	

}
