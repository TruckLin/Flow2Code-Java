package gui.manager;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.DirectoryIteratorException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import com.tcg.json.JSONUtils;

public class SaveAndLoadTest {
	
	public static void saveTextFileFromString(String text, String pathAndFileName) {
		// Write primitives to an output file
		try (DataOutputStream out = new DataOutputStream( new BufferedOutputStream (new FileOutputStream(pathAndFileName)))){
			out.writeChars(text);
			out.flush();
		} catch (IOException ex) {
       ex.printStackTrace();
		}
	}
	
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
	
	
	public static void main(String[] args) {
	/*	String text = "{Hello World: \"¦n\"}\n";
		SaveAndLoadTest.saveTextFileFromString(text, "testing.json");
		//System.out.println(File.separator);
		//String temp1 = SaveAndLoadTest.getTextStringFromPath("Demo-If.json");
		//String temp2 = JSONUtils.getJSONStringFromFile("Demo-If.json");
		String temp1 = SaveAndLoadTest.getTextStringFromPath("testing.json");
		String temp2 = JSONUtils.getJSONStringFromFile("testing.json");
		System.out.println(temp1);
		System.out.println(temp2);
	*/
		
		// List the contents of a directory
		Path dir = Paths.get(".//assets");
		try (DirectoryStream<Path> dirStream = Files.newDirectoryStream(dir)) {
		   for (Path entry : dirStream) {
		      System.out.println(entry.getFileName());  // Filename only
		      System.out.println(entry.toString());     // Full-path name
		      
		   }
		} catch (IOException | DirectoryIteratorException ex) {
		   ex.printStackTrace();
		}
	}
}
