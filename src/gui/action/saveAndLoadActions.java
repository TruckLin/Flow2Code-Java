package gui.action;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JFileChooser;

import org.json.JSONObject;

import filesystem.FileChooserDemo;

public class saveAndLoadActions {

	JFileChooser fc;
	
	JSONObject flowDiagram;
	JSONObject[] functions;
	
	ActionListener loadAction = e -> {
		int returnVal = fc.showOpenDialog(null);
    	if (returnVal == JFileChooser.APPROVE_OPTION) {
    		File file = fc.getSelectedFile();
    		//This is where a real application would open the file.
    		//log.append("Opening: " + file.getName() + "." + newline);
    	} else {
    		//log.append("Open command cancelled by user." + newline);
    	}
    	//log.setCaretPosition(log.getDocument().getLength());
    };
	ActionListener saveAction = e -> { 
		 int returnVal = fc.showSaveDialog(null);
         if (returnVal == JFileChooser.APPROVE_OPTION) {
             File file = fc.getSelectedFile();
             //This is where a real application would save the file.
             //log.append("Saving: " + file.getName() + "." + newline);
         } else {
             //log.append("Save command cancelled by user." + newline);
         }
         //log.setCaretPosition(log.getDocument().getLength());
    };
	
	
	
	public saveAndLoadActions(JSONObject flowDiagram, JSONObject[] functions) {
		this.flowDiagram = flowDiagram;
		this.functions =  functions;
		
		this.fc = new JFileChooser();
	}
	
	
	public ActionListener getSaveActionListener() {
		return saveAction;
	}
	public ActionListener getLoadActionListener() {
		return loadAction;
	}
}
