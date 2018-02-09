package gui.codeView;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.util.HashMap;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.event.UndoableEditEvent;
import javax.swing.event.UndoableEditListener;
import javax.swing.text.AbstractDocument;
import javax.swing.text.JTextComponent;
import javax.swing.text.StyledDocument;
import javax.swing.undo.CannotRedoException;
import javax.swing.undo.CannotUndoException;

import gui.commands.UndoableEditCommand;
import gui.mainFrame.Flow2Code;
import gui.manager.UndoManager;
import strategy.codegenerator.JavaCodeGenerator;

public class CodeViewContainer extends JPanel{
	
	private Flow2Code mainFrame;
	
	private CodeViewToolBar codeViewToolBar;
	private CodeEditPanel codeEditPanel;
    
	public CodeViewContainer(Flow2Code mainFrame) {
		super(new BorderLayout());
		this.mainFrame = mainFrame;
		
		this.codeEditPanel = new CodeEditPanel();
		
        JavaCodeGenerator codeGenerator = new JavaCodeGenerator(this.mainFrame.getBlockFlowDiagram());
        TextBranch code = new TextBranch();
		code = (TextBranch) codeGenerator.generate(this.mainFrame.getFlowDiagramModel(), code, "");
		this.codeEditPanel.setTextModel(code);
        
        
        JScrollPane scrollPane = new JScrollPane(codeEditPanel);
        //codeEditPanel.setPreferredSize(scrollPane.getMinimumSize());
        //scrollPane.setPreferredSize(new Dimension(400, 400));
        
        
        //Testing
        //this.undo = new MyTestUndoManager();
        
        
        this.codeViewToolBar = new CodeViewToolBar(this);
        
        //Add the components
        this.add(codeViewToolBar, BorderLayout.NORTH);
        this.add(codeEditPanel, BorderLayout.CENTER);
	    //this.add(scrollPane, BorderLayout.CENTER);
		
	}
	

    


	/** Getters and Setters **/
	public CodeViewToolBar getCodeViewToolBar() {
		return this.codeViewToolBar;
	}
	public CodeEditPanel getCodeEditPanel() {
		return this.codeEditPanel;
	}
}
