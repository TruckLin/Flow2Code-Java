package gui.manager;

import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;
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

		try(ZipInputStream in = new ZipInputStream(new FileInputStream(f)) ) {
			ZipEntry currentEntry;
			
			while( (currentEntry = in.getNextEntry()) != null ) {
				//System.out.println(currentEntry.getName().toString());
				
				int begin = 0;
				int end = 4;
				
				if(currentEntry.getName().substring(begin, end).equals("Func")) continue;
                
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
					JSONObject tempModel = new JSONObject(temp);
					for(String tempKey : tempModel.keySet()) {
						fdInfo.put( tempKey, tempModel.get(tempKey) );
					}
					
				}else {
					// Clone JSONObject
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
		String[] textFilePath = {"FlowDiagram.json", "FlowDiagram-info.json"};
		String[] textFilePathInZip = {"/FlowDiagram/" + textFilePath[0], "/FlowDiagram/" + textFilePath[1]};
		SaveAndLoadManagerFD.saveTextFileFromString(fdModel.toString(), textFilePath[0]);
		SaveAndLoadManagerFD.saveTextFileFromString(fdInfo.toString(), textFilePath[1]);
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