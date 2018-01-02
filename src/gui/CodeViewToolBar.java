package gui;

import java.awt.Color;
import java.beans.PropertyChangeListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JToolBar;

import gui.action.SaveAndLoadActions;
import gui.manager.UndoManager;
import strategy.codegenerator.JavaCodeGenerator;

public class CodeViewToolBar extends JToolBar{
	private Flow2Code mainFrame;
	
	private UndoManager undoManager;
	
	private int iconWidth = 25;
	private int iconHeight = 25;

	private JButton saveButton;
	private ImageIcon saveIcon;
	
	private JButton codeGenButton;
	private ImageIcon codeGenIcon;
	
	public CodeViewToolBar(Flow2Code mainFrame) {
		super();
		this.mainFrame = mainFrame;
		
		this.undoManager = mainFrame.getUndoManager();
		
		saveIcon = new ImageIcon("icon/save.png");
		saveIcon = 
			new ImageIcon(saveIcon.getImage().getScaledInstance(iconWidth, iconHeight, java.awt.Image.SCALE_SMOOTH));
		this.saveButton = new JButton(saveIcon);
		this.add(this.saveButton);
		
	}

}
	
