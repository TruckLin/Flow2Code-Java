package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.beans.PropertyChangeListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.JToolBar;

import editor.system.testing.TextBranch;
import gui.action.SaveAndLoadActions;
import gui.manager.UndoManager;
import strategy.codegenerator.JavaCodeGenerator;

public class FlowDiagramToolBar extends JToolBar{
	private Flow2Code mainFrame;
	
	private UndoManager undoManager;
	
	private int iconWidth = 25;
	private int iconHeight = 25;
	private JButton undoButton;
	private ImageIcon undoIcon;
	private JButton redoButton;
	private ImageIcon redoIcon;
	
	private ScrollablePanelForFD scrollablePanel;
	private JButton zoomInButton;
	private ImageIcon zoomInIcon;
	private JButton zoomOutButton;
	private ImageIcon zoomOutIcon;
	private JLabel zoomRatioLabel;
	
	private JButton openButton;
	private ImageIcon openIcon;
	private JButton saveButton;
	private ImageIcon saveIcon;
	
	private JButton codeGenButton;
	private ImageIcon codeGenIcon;
	
	private PropertyChangeListener undoRedoListener = e -> updateUndoRedoButtons();
	
	public FlowDiagramToolBar(Flow2Code mainFrame) {
		super();
		this.mainFrame = mainFrame;
		
		this.undoManager = mainFrame.getUndoManager();
		
		SaveAndLoadActions actions = new SaveAndLoadActions(mainFrame);
		openIcon = new ImageIcon("icon/load.png");
		openIcon = 
			new ImageIcon(openIcon.getImage().getScaledInstance(iconWidth, iconHeight, java.awt.Image.SCALE_SMOOTH));
		this.openButton = new JButton(openIcon);
		saveIcon = new ImageIcon("icon/save.png");
		saveIcon = 
			new ImageIcon(saveIcon.getImage().getScaledInstance(iconWidth, iconHeight, java.awt.Image.SCALE_SMOOTH));
		this.saveButton = new JButton(saveIcon);
		this.openButton.addActionListener(actions.getLoadActionListener());
		this.saveButton.addActionListener(actions.getSaveActionListener());
		this.add(this.openButton);
		this.add(this.saveButton);
		
		undoIcon = new ImageIcon("icon/Undo_Arrow.png");
		undoIcon = 
			new ImageIcon(undoIcon.getImage().getScaledInstance(iconWidth, iconHeight, java.awt.Image.SCALE_SMOOTH));
		undoButton = new JButton(undoIcon);
		
		redoIcon = new ImageIcon("icon/Redo_Arrow.png");
		redoIcon = 
			new ImageIcon(redoIcon.getImage().getScaledInstance(iconWidth, iconHeight, java.awt.Image.SCALE_SMOOTH));
		redoButton = new JButton(redoIcon);
		this.undoManager.addPropertyChangeListener(undoRedoListener);
		
		undoButton.addActionListener(e-> undoManager.undo());
		redoButton.addActionListener(e-> undoManager.redo());
		
		updateUndoRedoButtons();
		
		this.add(undoButton);
		this.add(redoButton);
		
		// Zoom in and Zoom out button
		this.scrollablePanel = mainFrame.getScrollablePanelForFD();
		zoomOutIcon = new ImageIcon("icon/zoom-out.png");
		zoomOutIcon = 
			new ImageIcon(zoomOutIcon.getImage().getScaledInstance(iconWidth, iconHeight, java.awt.Image.SCALE_SMOOTH));
		zoomOutButton = new JButton(zoomOutIcon);
		zoomInIcon = new ImageIcon("icon/zoom-in.png");
		zoomInIcon = 
			new ImageIcon(zoomInIcon.getImage().getScaledInstance(iconWidth, iconHeight, java.awt.Image.SCALE_SMOOTH));
		zoomInButton = new JButton(zoomInIcon);
		
		double zoomGap = 0.25;
		zoomInButton.addActionListener(e -> {if(scrollablePanel.getCurrentZoomRatio() >= 5) {
											 	zoomRatioLabel.setText(scrollablePanel.getCurrentZoomRatio()*100 + "%");
											 }else {
												 scrollablePanel.zoom(scrollablePanel.getCurrentZoomRatio() + zoomGap);
												 zoomRatioLabel.setText(scrollablePanel.getCurrentZoomRatio()*100 + "%");
											 }
											});
		zoomOutButton.addActionListener(e -> {if(scrollablePanel.getCurrentZoomRatio() <= 0.5) {
												 zoomRatioLabel.setText(scrollablePanel.getCurrentZoomRatio()*100 + "%");
		 									  }else {
		 										 scrollablePanel.zoom(scrollablePanel.getCurrentZoomRatio() - zoomGap);
												 zoomRatioLabel.setText(scrollablePanel.getCurrentZoomRatio()*100 + "%");
		 									  }
											  });
		zoomRatioLabel = new JLabel(scrollablePanel.getCurrentZoomRatio()*100 + "%");
		this.add(zoomOutButton);
		this.add(zoomRatioLabel);
		this.add(zoomInButton);
		
		this.addSeparator();
		
		//Testing what will happen if we group the buttons
		JPanel codeGenPanel = new JPanel();
		
		
		codeGenButton = new JButton("CodeGen");
		codeGenButton.addActionListener(e -> {
			JavaCodeGenerator codeGenerator = new JavaCodeGenerator(this.mainFrame.getBlockFlowDiagram());
			TextBranch code = new TextBranch();
			code = (TextBranch) codeGenerator.generate(this.mainFrame.getFlowDiagramModel(),code, "");
			this.mainFrame.getCodeEditPanel().setTextModel(code);
		});
		
		codeGenPanel.add(codeGenButton);
		
		//Testing
		JCheckBoxMenuItem forBox = new JCheckBoxMenuItem("for");
		forBox.setSelected(true);
		forBox.addActionListener(e->{
				JCheckBoxMenuItem box = (JCheckBoxMenuItem) e.getSource();
				boolean shouldCodeGen = box.getState();
				this.mainFrame.getBlockFlowDiagram().setCodeGenForAll("For", shouldCodeGen);
			});
	    JCheckBoxMenuItem whileBox = new JCheckBoxMenuItem("while");
	    whileBox.setSelected(true);
	    whileBox.addActionListener(e->{
			JCheckBoxMenuItem box = (JCheckBoxMenuItem) e.getSource();
			boolean shouldCodeGen = box.getState();
			this.mainFrame.getBlockFlowDiagram().setCodeGenForAll("While", shouldCodeGen);
		});
	    JCheckBoxMenuItem ifBox = new JCheckBoxMenuItem("if");
	    ifBox.setSelected(true);
	    ifBox.addActionListener(e->{
			JCheckBoxMenuItem box = (JCheckBoxMenuItem) e.getSource();
			boolean shouldCodeGen = box.getState();
			this.mainFrame.getBlockFlowDiagram().setCodeGenForAll("If", shouldCodeGen);
		});
	    JCheckBoxMenuItem declareBox = new JCheckBoxMenuItem("declare");
	    declareBox.setSelected(true);
	    declareBox.addActionListener(e->{
			JCheckBoxMenuItem box = (JCheckBoxMenuItem) e.getSource();
			boolean shouldCodeGen = box.getState();
			this.mainFrame.getBlockFlowDiagram().setCodeGenForAll("Declare", shouldCodeGen);
		});
	    JCheckBoxMenuItem assignBox = new JCheckBoxMenuItem("assign");
	    assignBox.setSelected(true);
	    assignBox.addActionListener(e->{
			JCheckBoxMenuItem box = (JCheckBoxMenuItem) e.getSource();
			boolean shouldCodeGen = box.getState();
			this.mainFrame.getBlockFlowDiagram().setCodeGenForAll("Assign", shouldCodeGen);
		});
	    
	    JMenuBar menuBar = new JMenuBar();
	    JMenu myMenu = new JMenu("Options");
	    myMenu.add(forBox);
	    myMenu.add(whileBox);
	    myMenu.add(ifBox);
	    myMenu.add(declareBox);
	    myMenu.add(assignBox);
	    menuBar.add(myMenu);
		
		codeGenPanel.add(menuBar);
		codeGenPanel.setOpaque(false);
		
		this.add(codeGenPanel);
		
		//this.add(codeGenButton);
		
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
