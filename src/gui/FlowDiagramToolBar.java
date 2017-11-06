package gui;

import java.awt.Color;
import java.beans.PropertyChangeListener;

import javax.swing.JButton;
import javax.swing.JToolBar;

import gui.manager.UndoManager;

public class FlowDiagramToolBar extends JToolBar{
	UndoManager undoManager;
	private JButton undoButton;
	private JButton redoButton;
	
	private PropertyChangeListener undoRedoListener = e -> updateUndoRedoButtons();
	
	public FlowDiagramToolBar(UndoManager undoManager) {
		super();
		this.undoManager = undoManager;
		
		undoButton = new JButton("Undo");
		redoButton = new JButton("Redo");
		undoManager.addPropertyChangeListener(undoRedoListener);
		
		undoButton.addActionListener(e-> undoManager.undo());
		redoButton.addActionListener(e-> undoManager.redo());
		
		updateUndoRedoButtons();
		
		this.add(undoButton);
		this.add(redoButton);
		
	}
	
	public void updateUndoRedoButtons() {
		if(undoManager.isUndoAvailable()) {
			undoButton.setForeground(Color.BLACK);
			undoButton.setEnabled(true);
		}else {
			undoButton.setForeground(null);
			undoButton.setEnabled(false);
		}
		if(undoManager.isRedoAvailable()) {
			redoButton.setForeground(Color.BLACK);
			redoButton.setEnabled(true);
		}else {
			redoButton.setForeground(null);
			redoButton.setEnabled(false);
		}
	}
}
