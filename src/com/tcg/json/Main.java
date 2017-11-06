package com.tcg.json;

import org.json.*;

public class Main {

	public static void main(String[] args) {
		JSONObject obj = JSONUtils.getJSONObjectFromFile("/obj.json");
		String[] names = JSONObject.getNames(obj); 
		for(String string:names) {
			System.out.println(string + ": "+ obj.get(string)); 
			// Important to know that the order in names is not the same as the order in the JSON file
		}
		
		System.out.println("\n");
		
		JSONArray jsonArray = obj.getJSONArray("Array");
		// It is case sensitive, "array" aon't work as it doen't exist in the file.
		for(int i=0; i < jsonArray.length(); i++) {
			System.out.println(jsonArray.get(i));
		}
		
		System.out.println("\n");
		
		int number = obj.getInt("Number");
		System.out.println(number);
		
		System.out.println("\n");
		
		String string = obj.getString("String");
		System.out.println(string);
		
		System.out.println("\n");
		
		boolean bool = obj.getBoolean("Boolean");
		System.out.println(bool);
		
		
	}

}
