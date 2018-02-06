package gui.codeView;

import java.awt.event.ActionListener;
import java.beans.PropertyChangeListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JToolBar;
//import javax.swing.undo.UndoManager;
import gui.manager.UndoManager;

import gui.Flow2Code;
import gui.action.RunJavaCodeActionWithCMD;
import gui.codeView.CodeViewContainer.RedoAction;
import gui.codeView.CodeViewContainer.UndoAction;

public class CodeViewToolBar extends JToolBar{
	private CodeViewContainer codeViewContainer;
	
	private UndoManager undoManager;
	
	private int iconWidth = 25;
	private int iconHeight = 25;

	private JButton saveButton;
	private ImageIcon saveIcon;
	
	private JButton undoButton;
	private ImageIcon undoIcon;
	//private UndoAction undoAction; // Swing version
	private JButton redoButton;
	private ImageIcon redoIcon;
	//private RedoAction redoAction; // Swing version
	
	private JButton runButton;
	private ImageIcon runIcon;
	private ActionListener runAction;
	
	//PropertyChangeListener that listens to changes in undomanager
	private PropertyChangeListener undoListener = e->{
		this.undoButton.setEnabled(this.undoManager.isUndoAvailable());
		this.redoButton.setEnabled(this.undoManager.isRedoAvailable());
	};
	
	public CodeViewToolBar(CodeViewContainer codeViewContainer) {
		super();
		
		this.codeViewContainer = codeViewContainer;
		
		this.undoManager = codeViewContainer.getCodeUndoManager();
		this.undoManager.addPropertyChangeListener(this.undoListener);// Add proertyChangeListener
		
		saveIcon = new ImageIcon("icon/save.png");
		saveIcon = 
			new ImageIcon(saveIcon.getImage().getScaledInstance(iconWidth, iconHeight, java.awt.Image.SCALE_SMOOTH));
		this.saveButton = new JButton(saveIcon);
		this.add(this.saveButton);
		
		/** Undo and Redo **/
		//Swing Version
		//this.undoAction = codeViewContainer.getUndoAction();
		undoIcon = new ImageIcon("icon/Undo_Arrow.png");
		undoIcon = 
			new ImageIcon(undoIcon.getImage().getScaledInstance(iconWidth, iconHeight, java.awt.Image.SCALE_SMOOTH));
		undoButton = new JButton(undoIcon);
		
		//Swign version
		//this.redoAction = codeViewContainer.getRedoAction();
		redoIcon = new ImageIcon("icon/Redo_Arrow.png");
		redoIcon = 
			new ImageIcon(redoIcon.getImage().getScaledInstance(iconWidth, iconHeight, java.awt.Image.SCALE_SMOOTH));
		redoButton = new JButton(redoIcon);
		//this.undoManager.addPropertyChangeListener(undoRedoListener);
		
		//Swing version
		//undoButton.addActionListener(new UndoRedoActionWrapper(undoAction, undoButton));
		//redoButton.addActionListener(new UndoRedoActionWrapper(redoAction,redoButton));
		//undoButton.setEnabled(undoAction.isEnabled());
		//redoButton.setEnabled(redoAction.isEnabled());
		//updateUndoRedoButtons();
		
		/** My undoManager version **/
		this.undoButton.addActionListener(e->this.undoManager.undo());
		this.redoButton.addActionListener(e->this.undoManager.redo());
		this.undoButton.setEnabled(this.undoManager.isUndoAvailable());
		this.redoButton.setEnabled(this.undoManager.isRedoAvailable());
		
		this.add(undoButton);
		this.add(redoButton);
		
		// Run button
		this.runButton = new JButton("Run");
		this.runAction = new RunJavaCodeActionWithCMD(this.codeViewContainer);
		this.runButton.addActionListener(runAction);
		this.add(runButton);
		
		
	}
	
	/** Functions that needs to be called after construction **/
	public void setSaveAction() {}
	public void setRunAction() {}

}
	
