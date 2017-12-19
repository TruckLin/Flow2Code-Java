package gui.action;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JFileChooser;

import org.json.JSONObject;

import filesystem.FileChooserDemo;
import gui.Flow2Code;
import gui.ScrollablePanelForFD;
import gui.manager.SaveAndLoadManagerFD;
import gui.manager.UndoManager;
import gui.object.CompositeBlockFD;
import strategy.generator.BlockGenerator;

public class SaveAndLoadActions {

	private JFileChooser fc;
	
	private Flow2Code mainFrame;
	
	private JSONObject flowDiagramModel;
	private JSONObject flowDiagramInfo;
	private CompositeBlockFD blockFlowDiagram;
	
	private ScrollablePanelForFD scrollablePanel;
	
	ActionListener loadAction = e -> {
		int returnVal = fc.showOpenDialog(null);
    	if (returnVal == JFileChooser.APPROVE_OPTION) {
    		File file = fc.getSelectedFile();
    		//This is where a real application would open the file.
    		//log.append("Opening: " + file.getName() + "." + newline);
    		String path = file.toString();
    		SaveAndLoadManagerFD.loadFlowDiagramFromZippedFile(this.flowDiagramModel, this.flowDiagramInfo, path);
    		this.mainFrame.setFlowDiagramModel(this.flowDiagramModel);
    		this.mainFrame.setFlowDiagramInfo(this.flowDiagramInfo);
    		this.mainFrame.updateAllReferences();
    		
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
             String path = file.toString();
             JSONObject model = this.blockFlowDiagram.getModel();
             JSONObject graphicalInfo = this.blockFlowDiagram.getGraphicalInfo();
             SaveAndLoadManagerFD.saveFlowDiagramIntoZippedFile(model, graphicalInfo, path);
             
             //Testing
             System.out.println(graphicalInfo.toString(10));
             
         } else {
             //log.append("Save command cancelled by user." + newline);
         }
         //log.setCaretPosition(log.getDocument().getLength());
    };
	
	
	
	public SaveAndLoadActions(Flow2Code mainFrame) {
		this.mainFrame = mainFrame;
		this.flowDiagramModel = mainFrame.getFlowDiagramModel();
		this.flowDiagramInfo =  mainFrame.getFlowDiagramInfo();
		this.blockFlowDiagram = mainFrame.getBlockFlowDiagram();
		
		this.scrollablePanel = mainFrame.getScrollablePanelForFD();
		
		this.fc = new JFileChooser();
		fc.setCurrentDirectory(new File("."));
	}
	
	
	public ActionListener getSaveActionListener() {
		return saveAction;
	}
	public ActionListener getLoadActionListener() {
		return loadAction;
	}
}
