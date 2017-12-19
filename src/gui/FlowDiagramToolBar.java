package gui;

import java.awt.Color;
import java.beans.PropertyChangeListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JToolBar;

import gui.action.SaveAndLoadActions;
import gui.manager.UndoManager;

public class FlowDiagramToolBar extends JToolBar{
	private Flow2Code mainFrame;
	
	private UndoManager undoManager;
	private JButton undoButton;
	private JButton redoButton;
	
	private ScrollablePanelForFD scrollablePanel;
	private JButton zoomInButton;
	private JButton zoomOutButton;
	private JLabel zoomRatioLabel;
	
	private JButton openButton;
	private JButton saveButton;
	
	private PropertyChangeListener undoRedoListener = e -> updateUndoRedoButtons();
	
	public FlowDiagramToolBar(Flow2Code mainFrame) {
		super();
		this.mainFrame = mainFrame;
		
		this.undoManager = mainFrame.getUndoManager();
		
		undoButton = new JButton("Undo");
		redoButton = new JButton("Redo");
		this.undoManager.addPropertyChangeListener(undoRedoListener);
		
		undoButton.addActionListener(e-> undoManager.undo());
		redoButton.addActionListener(e-> undoManager.redo());
		
		updateUndoRedoButtons();
		
		this.add(undoButton);
		this.add(redoButton);
		
		// Zoom in and Zoom out button
		this.scrollablePanel = mainFrame.getScrollablePanelForFD();
		zoomInButton = new JButton("ZoomIn");
		zoomOutButton = new JButton("ZoomOut");
		
		double zoomGap = 0.25;
		zoomInButton.addActionListener(e -> {if(scrollablePanel.getCurrentZoomRatio() >= 5) {
											 	zoomRatioLabel.setText(scrollablePanel.getCurrentZoomRatio() + "");
											 }else {
												 scrollablePanel.zoom(scrollablePanel.getCurrentZoomRatio() + zoomGap);
												 zoomRatioLabel.setText(scrollablePanel.getCurrentZoomRatio() + "");
											 }
											});
		zoomOutButton.addActionListener(e -> {if(scrollablePanel.getCurrentZoomRatio() <= 0.5) {
												 zoomRatioLabel.setText(scrollablePanel.getCurrentZoomRatio() + "");
		 									  }else {
		 										 scrollablePanel.zoom(scrollablePanel.getCurrentZoomRatio() - zoomGap);
												 zoomRatioLabel.setText(scrollablePanel.getCurrentZoomRatio() + "");
		 									  }
											  });
		zoomRatioLabel = new JLabel(scrollablePanel.getCurrentZoomRatio() + "");
		this.add(zoomInButton);
		this.add(zoomRatioLabel);
		this.add(zoomOutButton);
		
		SaveAndLoadActions actions = new SaveAndLoadActions(mainFrame);
		this.openButton = new JButton("Open");
		this.saveButton = new JButton("Save");
		this.openButton.addActionListener(actions.getLoadActionListener());
		this.saveButton.addActionListener(actions.getSaveActionListener());
		this.add(this.openButton);
		this.add(this.saveButton);
		
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
	
	/** Getters and Setters **/
	public JLabel getZoomRatioLabel() {
		return this.zoomRatioLabel;
	}
	public void setUndoManager(UndoManager undo) { 
		this.undoManager = undo;
	/*	this.undoButton.removeActionListener(this.undoButton.getActionListeners()[0]);
		this.redoButton.removeActionListener(this.redoButton.getActionListeners()[0]);
		undoButton.addActionListener(e-> undoManager.undo());
		redoButton.addActionListener(e-> undoManager.redo()); */
		this.undoManager.addPropertyChangeListener(undoRedoListener);
		this.updateUndoRedoButtons();
	}
}
