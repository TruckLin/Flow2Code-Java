package gui;

import java.awt.Color;
import java.beans.PropertyChangeListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JToolBar;

import gui.manager.UndoManager;

public class FlowDiagramToolBar extends JToolBar{
	UndoManager undoManager;
	private JButton undoButton;
	private JButton redoButton;
	
	private ScrollablePanelForFD scrollablePanel;
	private JButton zoomInButton;
	private JButton zoomOutButton;
	private JLabel zoomRatioLabel;
	
	private PropertyChangeListener undoRedoListener = e -> updateUndoRedoButtons();
	
	public FlowDiagramToolBar(UndoManager undoManager, ScrollablePanelForFD sp) {
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
		
		// Zoom in and Zoom out button
		this.scrollablePanel = sp;
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
	public ScrollablePanelForFD getScrollablePanel() {
		return this.scrollablePanel;
	}
	public void setScrollablePanel(ScrollablePanelForFD sc) {
		this.scrollablePanel = sc;
	}
}
