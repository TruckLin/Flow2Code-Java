package gui.manager;

import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Scanner;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;
import java.nio.charset.Charset;
import java.nio.file.DirectoryNotEmptyException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.json.*;

import com.tcg.json.JSONUtils;


public abstract class SaveAndLoadManagerFD {
	
	public static void saveTextFileFromString(String text, String pathAndFileName) {
		// Write primitives to an output file
		//Testing
		//System.out.println("Before we save the input text is : ");
		//System.out.println("    " + text);
		
		try (DataOutputStream out = new DataOutputStream( new BufferedOutputStream (new FileOutputStream(pathAndFileName)))){
			out.writeChars(text);
			out.flush();
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}
	
	public static JSONObject loadFlowDiagramFromJSON(String path) {
		JSONObject myObj = JSONUtils.getJSONObjectFromFile(path);
		return myObj;
	}
	public static JSONObject loadGraphicalInfoFromJSON(String path) {
		JSONObject myObj = JSONUtils.getJSONObjectFromFile(path);
		return myObj;
	}
	
	// fdModel and fdInfo will hold the data read in from the zipFilePath.
	public static void loadFlowDiagramFromZippedFile(JSONObject fdModel,JSONObject fdInfo,String zipFilePath) {
		File f = new File(zipFilePath);
		
		// ZipInpuStream with specify character set?
		try(ZipInputStream in = new ZipInputStream(new FileInputStream(f)) ) {
			ZipEntry currentEntry;
			
			while( (currentEntry = in.getNextEntry()) != null ) {
				//System.out.println(currentEntry.getName().toString());
				
				//Testing
				//File testFile = new File(zipFilePath + "" + currentEntry.getName().toString());
				//System.out.println(zipFilePath + "" + currentEntry.getName().toString());
				//System.out.print("    ");
				//System.out.println("Entry length = " + testFile.length());
				
				int begin = 0;
				int end = 4;
				
				// if the currentEntry is a function.
				//Testing
				//System.out.println(currentEntry.getName().substring(begin, end));
				//System.out.println(currentEntry.getName().substring(begin, end).equals("Func"));
				
				if(currentEntry.getName().substring(begin, end).equals("Func")) continue;
                
				/** Read in the content from ZipInputStream **/
				
				String temp = ""; // this holds all the characters in the current entry.
				
				// Version 1
				/*
				byte[] buffer = new byte[5096];
				//byte[] buffer = new byte[1024];
				int len;
				

				//Testing
				//System.out.println(temp);
				
				
				while ((len = in.read(buffer,0,buffer.length)) != -1){
					ByteArrayInputStream byteIn = new ByteArrayInputStream(buffer);
					DataInputStream dataIn = new DataInputStream(byteIn);
					
					
					for (int i = 0; i < len/2; i++) {
						char myChar = dataIn.readChar();
						temp = temp + myChar;
						//System.out.print(myChar);
						
					}
					//Testing
					//System.out.println();
					//System.out.println("len = " + len);
                    //System.out.println("buffer.length = " + buffer.length);
					
					dataIn.close();
                   
				}
				*/
				//Version 2
			/*	byte in1,in2;
				while ( (in1 = in.read()) != -1 && (in2 = in.read()) != -1 ) ){
					ByteArrayInputStream byteIn = new ByteArrayInputStream(buffer);
					DataInputStream dataIn = new DataInputStream(byteIn);
					
					
					for (int i = 0; i < len/2; i++) {
						char myChar = dataIn.readChar();
						temp = temp + myChar;
						System.out.print(myChar);
						
					}
					//Testing
					System.out.println();
					System.out.println("len = " + len);
                    System.out.println("buffer.length = " + buffer.length);
					
					dataIn.close();
                   
				}
				*/
				
				//Version 3
				Scanner sc = new Scanner(in, "UTF-16");
				 while (sc.hasNextLine()) {
				     temp = temp + sc.nextLine();
				 }
				// Testing
				 System.out.println("Entry = " + currentEntry.getName());
				 System.out.println("File content = " + temp);
				
				if(currentEntry.getName().contains("info")) {
					// Clone JSONObject
					JSONObject tempModel = new JSONObject(temp);
					for(String tempKey : tempModel.keySet()) {
						fdInfo.put( tempKey, tempModel.get(tempKey) );
					}
					
				}else {
					// Clone JSONObject
					
					//Testing
					//System.out.print("\n    " + temp);
					
					JSONObject tempModel = new JSONObject(temp);
					for(String tempKey : tempModel.keySet()) {
						fdModel.put( tempKey, tempModel.get(tempKey) );
					}
				}
				//Testing
				//System.out.println(temp);
				//System.out.println(currentEntry.getName().contains("info"));
				//System.out.println(fdModel.toString(10));
                //System.out.println("Length of the string read in : " + temp.length());
				//System.out.println("");
				
				in.closeEntry();
			}
			
		} catch (IOException ex) {
			ex.printStackTrace();
		}
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
				
				//Testing
				//System.out.println(textFileInBytes.length);
				
				e.setSize(textFileInBytes.length);
				out.putNextEntry(e);
			
				out.write(textFileInBytes, 0, textFileInBytes.length);
				out.closeEntry();
			}
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		
		
	}
	
	public static void saveFlowDiagramIntoZippedFile(JSONObject fdModel, JSONObject fdInfo, String zipFilePath) {
		String[] textFilePath = {"temp/FlowDiagram.json", "temp/FlowDiagram-info.json"};
		String[] textFilePathInZip = {"FlowDiagram/" + textFilePath[0], "FlowDiagram/" + textFilePath[1]};
		SaveAndLoadManagerFD.saveTextFileFromString(fdModel.toString(), textFilePath[0]);
		SaveAndLoadManagerFD.saveTextFileFromString(fdInfo.toString(), textFilePath[1]);
		
		//Testing
	//	Scanner sc = new Scanner(System.in);
	//	System.out.println("Waiting for input...");
	//	int i = sc.nextInt();
	//	System.out.println("i = :" + i);
		
		SaveAndLoadManagerFD.copyTextFilesIntoZippedFile(textFilePath, textFilePathInZip, zipFilePath);
	
		Path path = null;
		try {
			path = Paths.get(textFilePath[0]);
		    Files.deleteIfExists(path);
		    path = Paths.get(textFilePath[1]);
		    Files.deleteIfExists(path);
		} catch (NoSuchFileException x) {
		    System.err.format("%s: no such" + " file or directory%n", path);
		} catch (DirectoryNotEmptyException x) {
		    System.err.format("%s not empty%n", path);
		} catch (IOException x) {
		    // File permission problems are caught here.
		    System.err.println(x);
		}
		
	}
	
}