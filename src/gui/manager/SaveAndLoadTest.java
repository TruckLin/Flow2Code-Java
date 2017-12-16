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
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.json.JSONObject;

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
	
	
	public static void loadZippedProject(String path) {
		
	}
	
	public static void copyTextFilesIntoZippedFile(String[] textFilePath, String[] textFilePathInZip ,String zipFilePath) {
		byte[] textFileInBytes = null;
		File f = new File(zipFilePath);
		
		if(textFilePath.length != textFilePathInZip.length) {
			System.err.println("number if textFilePath != number of textFilePathInZip.");
			return;
		}
		
		
		
		try(ZipOutputStream out = new ZipOutputStream(new FileOutputStream(f)) ){
			for(int i = 0; i < textFilePath.length; i++) {
				
				try (FileInputStream in = new FileInputStream(textFilePath[i])) {
					 
					// Check file length
					File fileIn = new File(textFilePath[i]);
					textFileInBytes = new byte[(int)fileIn.length()];
					in.read(textFileInBytes);
				} catch (IOException ex) {
					ex.printStackTrace();
				}
				
				ZipEntry e = new ZipEntry(textFilePathInZip[i]);
				out.putNextEntry(e);
			
				out.write(textFileInBytes, 0, textFileInBytes.length);
				out.closeEntry();
			}
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		
		
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
	/*	Path dir = Paths.get(".//assets");
		try (DirectoryStream<Path> dirStream = Files.newDirectoryStream(dir)) {
		   for (Path entry : dirStream) {
		      System.out.println(entry.getFileName());  // Filename only
		      System.out.println(entry.toString());     // Full-path name
		      
		   }
		} catch (IOException | DirectoryIteratorException ex) {
		   ex.printStackTrace();
		}
	*/
		String textFilePath, textFilePath2,textFilePathInZip ,textFilePathInZip2, zipFilePath;
		textFilePath = ".\\assets\\Demo-If.json";
		textFilePath2 = ".\\assets\\Demo-If-info.json";
		String textFilePath3 = ".\\assets\\Demo-If-info.json";
		String[] textFilePaths = {textFilePath, textFilePath2, textFilePath3};
		textFilePathInZip = "FlowDiagram/Demo-If.json";
		textFilePathInZip2 = "FlowDiagram/Demo-If-info.json";
		String textFilePathInZip3 = "Functions/Function1/Demo-If-info.json";
		String[] textFilePathsInZip = {textFilePathInZip, textFilePathInZip2, textFilePathInZip3};
		zipFilePath = ".\\assets\\TestZip.foo";
		
		copyTextFilesIntoZippedFile(textFilePaths,textFilePathsInZip ,zipFilePath);
		
	}
}
