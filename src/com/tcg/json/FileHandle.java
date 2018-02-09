package com.tcg.json;

import java.io.InputStream;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class FileHandle {
	
	/** All encoded in UCS-2, 2-bytes Unicode **/
/*
	public static void saveTextFileFromString(String text, String pathAndFileName) {
		// Write primitives to an output file
		
		//Testing 
		//System.out.println("Path : " + pathAndFileName);
		
		try (DataOutputStream out = new DataOutputStream( new BufferedOutputStream (new FileOutputStream(pathAndFileName)))){
			out.writeChars(text);
			out.flush();
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}*/
	
	public static String getTextStringFromPath(String path) {
		String temp = "";
		
		// Read primitives
		try (DataInputStream in =
			new DataInputStream(
	            new BufferedInputStream(
	                new FileInputStream(path)))) {
	 
			//System.out.print("chars:   ");
	 
	    	// Check file length
	    	File fileIn = new File(path);
	    	//System.out.println("File size is " + fileIn.length() + " bytes");
	     
	    	for (int i = 0; i < fileIn.length()/2; ++i) {
	    		temp = temp + (char)in.readChar();
	    	}
	    	
	    	//System.out.println(temp);
	    	return temp;
		} catch (IOException ex) {
	        ex.printStackTrace();
	    }
		return temp;
	}
}
