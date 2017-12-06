package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;

import gui.manager.UndoManager;

public abstract class BlockEditDialog extends JDialog{
	protected UndoManager undoManager;
	
	// North panel, help dialog and icon label.
	protected JPanel northPanel = new JPanel();
	protected JLabel helpDialog = new JLabel("Help Dialog");
	protected JLabel icon = new JLabel("Icon");
	
	// Centre panel, content edition.
	protected JPanel editingPanel = new JPanel();
	
	// South panel.
	protected JPanel southPanel = new JPanel(new FlowLayout());
	
	// Advanced Dialog
	protected JButton btnPopDown = new JButton("\\/");
	protected String advancedDialogTitle = "Miscellaneous Technique.";
	protected String advancedDialogText = "";
	protected boolean popped = false;
	protected JLabel advancedDialog = new JLabel();
	
	// buttons and button panel.
	protected JPanel buttonPanel = new JPanel(new GridLayout(0,2));
	protected JButton btnConfirm = new JButton("Ok");
	protected JButton btnCancel = new JButton("Cancel");

	public BlockEditDialog (UndoManager undoManager) {
		super();
		this.undoManager = undoManager;
	}
	
	protected void buildEditDialog() {
		this.setModal(true);
		//this.setLocationByPlatform(true);
		this.setLocation(500, 250);
		this.setLayout(new BorderLayout());
		this.setResizable(false);
		
		// North Panel
		northPanel.setLayout(null);
		
		int iconWidth = 100;
		int iconHeight = 60;
		int multiple = 4;
		icon.setBounds(0,0,iconWidth,iconHeight);
		icon.setBorder(BorderFactory.createDashedBorder(Color.BLACK));
		this.drawIcon();
		northPanel.add(icon);
		helpDialog.setBounds(iconWidth,0,iconWidth * (multiple-1),iconHeight);
		helpDialog.setBorder(BorderFactory.createDashedBorder(Color.BLACK));
		this.writeHelpDialog();
		northPanel.add(helpDialog);
		northPanel.setPreferredSize(new Dimension(iconWidth * multiple, iconHeight));

		
		// Center panel
		this.constructEditingPanel();
		
		// Button panel
		btnConfirm.addActionListener(e -> this.EditAction());
		btnCancel.addActionListener(e -> this.dispose());
		buttonPanel.add(btnConfirm);
		buttonPanel.add(btnCancel);
		((GridLayout)buttonPanel.getLayout()).setHgap(5);
		
		
		// Advanced Dialog
		advancedDialog.setText(advancedDialogTitle);
		
		this.writeAdvanceDialog();
		btnPopDown.addActionListener(
			e -> {
				if(popped) {
					//advancedDialog.setText(advancedDialogTitle);
					btnPopDown.setText("\\/");
					this.pack();
					popped = false;
				}else {
					//advancedDialog.setText(advancedDialogTitle + advancedDialogText);
					btnPopDown.setText("/\\");
					this.pack();
					popped = true;
				}
			});
		
		// South panel
		southPanel.add(advancedDialog);
		southPanel.add(btnPopDown);
		southPanel.add(buttonPanel);
		
		//Testing
		//System.out.println(southPanel.getComponentCount());
		
		
		// Add all three panels.
		JPanel cp = new JPanel(new BorderLayout());
		cp.add(northPanel, BorderLayout.NORTH);
			// edittingPanel required the block itself which is not available because
			// super class' constructor is called first.
		cp.add(editingPanel, BorderLayout.CENTER);
		cp.add(southPanel, BorderLayout.SOUTH);
		
		this.setDialogTitle();
		this.setContentPane(cp);
		this.pack();
	}
	
	protected abstract void setDialogTitle();
	protected abstract void drawIcon();
	protected abstract void writeHelpDialog();
	protected abstract void constructEditingPanel();
	protected abstract void writeAdvanceDialog();
	protected abstract void EditAction();
	
}
