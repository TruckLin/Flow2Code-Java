package gui.manager;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

import org.json.JSONObject;

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
	     
	    	for (int i = 0; i < fileIn.length()/2; i++) {
	    		temp = temp + in.readChar();
	    	}
	    	
	    	//System.out.println(temp);
	    	return temp;
		} catch (IOException ex) {
	        ex.printStackTrace();
	    }
		return temp;
	}
	
	
	public static void loadFunctionsFromZippedFile(ArrayList<JSONObject> fdFunctions,
														ArrayList<JSONObject> funcInfos ,String zipFilePath) {
		File f = new File(zipFilePath);
		fdFunctions.clear();
		funcInfos.clear();
		
		
		try(ZipInputStream in = new ZipInputStream(new FileInputStream(f)) ) {
			ZipEntry currentEntry;
			
			while( (currentEntry = in.getNextEntry()) != null ) {
				//System.out.println(currentEntry.getName().toString());
				
				int begin = 0;
				int end = 4;
				
				if(currentEntry.getName().substring(begin, end).equals("Flow")) continue;
                
				byte[] buffer = new byte[2048];
				int len;
				String temp = "";
				while ((len = in.read(buffer)) > 0){
					ByteArrayInputStream byteIn = new ByteArrayInputStream(buffer);
					DataInputStream dataIn = new DataInputStream(byteIn);
					
					
					for (int i = 0; i < len; i++) {
						if(dataIn.available() > 1) {
							temp = temp + dataIn.readChar();
						}
					}
					
					//System.out.println("len = " + len);
                    //System.out.println("buffer.length = " + buffer.length);
					//System.out.println((char)buffer[0]);
                   
				}
				
				if(currentEntry.getName().contains("info")) {
					// Clone JSONObject
					funcInfos.add(new JSONObject(temp));
				}else {
					// Clone JSONObject
					fdFunctions.add(new JSONObject(temp));
				}
				//Testing
				//System.out.println(temp);
				//System.out.println(currentEntry.getName().contains("info"));
				//System.out.println(fdModel.toString(10));
                //System.out.println("Length of the string read in : " + temp.length());
			}
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}
	
	public static void loadZippedFile(String zipFilePath) {
		File f = new File(zipFilePath);
		
		
		try(ZipInputStream in = new ZipInputStream(new FileInputStream(f)) ) {
			ZipEntry currentEntry;
			
			while( (currentEntry = in.getNextEntry()) != null ) {
				System.out.println(currentEntry.getName().toString());
				Path path = Paths.get(zipFilePath, currentEntry.getName());
				
				int begin = 0;
				int end = 4;
				//System.out.println("length of the name = " + currentEntry.getName().length());
				//System.out.println("substring(" + begin + "," + end + ") = " + currentEntry.getName().substring(begin,end));
				//System.out.println("currentEntry.getName().substring(begin,end).equals(\"Flow\") = "
				//							+ currentEntry.getName().substring(begin,end).equals("Flow"));
				
				if(currentEntry.getName().substring(begin, end).equals("Flow")) continue;
                
				byte[] buffer = new byte[2048];
				int len;
				String temp = "";
				while ((len = in.read(buffer)) > 0){
					ByteArrayInputStream byteIn = new ByteArrayInputStream(buffer);
					DataInputStream dataIn = new DataInputStream(byteIn);
					
					
					for (int i = 0; i < len; i++) {
						if(dataIn.available() > 1) {
							temp = temp + dataIn.readChar();
						}
					}
					
					//System.out.println("len = " + len);
                    // System.out.println("buffer.length = " + buffer.length);
					//System.out.println((char)buffer[0]);
                   
				}
			//	System.out.println(temp);
            //	System.out.println("Length of the string read in : " + temp.length());
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
		
		// copyTextFilesIntoZippedFile(textFilePaths,textFilePathsInZip ,zipFilePath);
		
		//loadZippedFile(zipFilePath);
		
		JSONObject fdModel = new JSONObject();
		JSONObject fdInfo = new JSONObject();
		SaveAndLoadManagerFD.loadFlowDiagramFromZippedFile(fdModel, fdInfo, zipFilePath);
		
		//Testing
		//System.out.println(fdModel.toString(10));
		//System.out.println(fdInfo.toString(10));
	}
}
