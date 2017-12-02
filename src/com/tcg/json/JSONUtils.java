package com.tcg.json;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Scanner;

import org.json.JSONObject;

public class JSONUtils {
	
	public static String getJSONStringFromFile(String path) {
		Scanner scanner;
		
		//Testing
		//System.out.println("path = " + path);
		
		InputStream in = FileHandle.inputStreamFromFile(path);
		scanner = new Scanner(in);
		
		String json = "";
		while(scanner.hasNext()) {
			json = json + scanner.next();
		}
		
		scanner.close();
		try {
			in.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return json;
	}
	
	public static JSONObject getJSONObjectFromFile(String path) {
		return new JSONObject(getJSONStringFromFile(path));
	}
	
	// This function should allow us to return multiple JSONOnjects from the same file.
	public static JSONObject[] getMultipleJSONObjectsFromFile(String path) {
		JSONObject[] Objs;
		ArrayList<String> ObjStrings = new ArrayList<String>();
		
		String json = getJSONStringFromFile(path);
		
		int isObj = 0;
		int StartIdx = 0;
		for(int i = 0; i<json.length();i++) {
			if( json.charAt(i) == '{'  && isObj == 0) {
				isObj++;
				StartIdx = i;
			}else if(json.charAt(i) == '{') {
				isObj++;
			}else if(json.charAt(i) == '}') {
				isObj--;
			}else {}
			
			if(isObj==0 && json.charAt(i) == '}') {
				ObjStrings.add(json.substring(StartIdx, i+1));
				StartIdx = i+1;
			}
		}
		
		Objs = new JSONObject[ObjStrings.size()];
		for(int i=0; i < ObjStrings.size() ; i++) {
			//Testing
			//System.out.println(i+"th String = \n");
			//System.out.println(ObjStrings.get(i));
			
			Objs[i] = new JSONObject(ObjStrings.get(i));
		}
		
		return Objs;
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
