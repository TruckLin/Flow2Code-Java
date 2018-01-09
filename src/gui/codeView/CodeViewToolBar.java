package gui.codeView;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JToolBar;
import javax.swing.undo.UndoManager;

import gui.Flow2Code;
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
	private UndoAction undoAction;
	private JButton redoButton;
	private ImageIcon redoIcon;
	private RedoAction redoAction;
	
	
	public CodeViewToolBar(CodeViewContainer codeViewContainer) {
		super();
		
		this.codeViewContainer = codeViewContainer;
		
		this.undoManager = codeViewContainer.getCodeUndoManager();
		
		saveIcon = new ImageIcon("icon/save.png");
		saveIcon = 
			new ImageIcon(saveIcon.getImage().getScaledInstance(iconWidth, iconHeight, java.awt.Image.SCALE_SMOOTH));
		this.saveButton = new JButton(saveIcon);
		this.add(this.saveButton);
		
		/** Undo and Redo **/
		this.undoAction = codeViewContainer.getUndoAction();
		undoIcon = new ImageIcon("icon/Undo_Arrow.png");
		undoIcon = 
			new ImageIcon(undoIcon.getImage().getScaledInstance(iconWidth, iconHeight, java.awt.Image.SCALE_SMOOTH));
		undoButton = new JButton(undoIcon);
		
		this.redoAction = codeViewContainer.getRedoAction();
		redoIcon = new ImageIcon("icon/Redo_Arrow.png");
		redoIcon = 
			new ImageIcon(redoIcon.getImage().getScaledInstance(iconWidth, iconHeight, java.awt.Image.SCALE_SMOOTH));
		redoButton = new JButton(redoIcon);
		//this.undoManager.addPropertyChangeListener(undoRedoListener);
		
		undoButton.setAction(this.undoAction);
		redoButton.setAction(this.redoAction);
		
		//updateUndoRedoButtons();
		
		this.add(undoButton);
		this.add(redoButton);
		
		
	}
	
	/** Functions that needs to be called after construction **/
	public void setSaveAction() {}
	public void setRunAction() {}

}
	
