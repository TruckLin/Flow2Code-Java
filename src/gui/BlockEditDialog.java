package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;

import gui.manager.UndoManager;

public abstract class BlockEditDialog extends JDialog{
	protected UndoManager undoManager;
	
	// North panel, help dialog and icon label.
	protected JPanel northPanel = new JPanel(new FlowLayout());
	protected JLabel helpDialog = new JLabel("Help Dialog");
	protected JLabel icon = new JLabel("Icon");
	
	// Centre panel, content edition.
	protected JPanel editingPanel = new JPanel();
	
	// South panel, buttons and advancedDialog.
	protected JPanel southPanel = new JPanel(new BorderLayout());
	protected JButton btnPopDown = new JButton("\\/");
	protected JLabel advancedDialog = new JLabel("Miscellaneous Technique.");
	
	protected JPanel buttonPanel = new JPanel(new BorderLayout());
	protected JButton btnConfirm = new JButton("Ok");
	protected JButton btnCancel = new JButton("Cancel");

	public BlockEditDialog (UndoManager undoManager) {
		super();
		this.undoManager = undoManager;
	}
	
	protected void buildEditDialog() {
		this.setModal(true);
		this.setLocationByPlatform(true);
		this.setLayout(new BorderLayout());
	//	this.setResizable(false);
		
		// North Panel
		helpDialog.setBorder(BorderFactory.createDashedBorder(Color.BLACK));
		icon.setBorder(BorderFactory.createDashedBorder(Color.BLACK));
		this.drawIcon();
		northPanel.add(icon);
		this.writeHelpDialog();
		northPanel.add(helpDialog);
		
		// Center panel
		this.constructEditingPanel();
		
		// Button panel
		btnConfirm.addActionListener(e -> this.EditAction());
		btnCancel.addActionListener(e -> this.dispose());
		buttonPanel.add(btnConfirm, BorderLayout.WEST);
		buttonPanel.add(btnCancel, BorderLayout.EAST);
		
		// South panel
		southPanel.add(advancedDialog, BorderLayout.WEST);
		southPanel.add(btnPopDown,BorderLayout.CENTER);
		southPanel.add(buttonPanel, BorderLayout.EAST);
		
		
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
