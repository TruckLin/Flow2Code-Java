package gui;

import java.awt.GridLayout;
import java.awt.event.ActionListener;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

import gui.commands.AddBlockCommand;
import gui.manager.NameCounterManager;
import gui.manager.UndoManager;
import gui.object.LineFD;

public class LinePopup extends JPopupMenu{
	private UndoManager undoManager;
	private LineFD line;
	private NameCounterManager nameManager;
	private JMenuItem declareItem = new JMenuItem("Declare");
	private JMenuItem assignItem = new JMenuItem("Assign");
	private JMenuItem inputItem = new JMenuItem("Input");
	private JMenuItem outputItem = new JMenuItem("Output");
	private JMenuItem forItem = new JMenuItem("For");
	private JMenuItem whileItem = new JMenuItem("While");
	private JMenuItem ifItem = new JMenuItem("If");
	
	
	public LinePopup(UndoManager undoManager,NameCounterManager nameManager, LineFD line) {
		super();
		this.setLayout(new GridLayout(4,2));
		this.undoManager = undoManager;
		this.nameManager = nameManager;
		this.line = line;
		
		/** Add action listener to menu items **/
		this.declareItem.addActionListener(e -> addBlockAction(this.undoManager,this.nameManager, line, "Declare"));
		this.assignItem.addActionListener(e -> addBlockAction(this.undoManager,this.nameManager, line, "Assign"));
		this.inputItem.addActionListener(e -> addBlockAction(this.undoManager,this.nameManager, line, "Input"));
		this.outputItem.addActionListener(e -> addBlockAction(this.undoManager,this.nameManager, line, "Output"));
		this.forItem.addActionListener(e -> addBlockAction(this.undoManager,this.nameManager, line, "For"));
		this.whileItem.addActionListener(e -> addBlockAction(this.undoManager,this.nameManager, line, "While"));
		this.ifItem.addActionListener(e -> addBlockAction(this.undoManager,this.nameManager, line, "If"));
	}
	
	public void addBlockAction(UndoManager undoManager,NameCounterManager nameManager ,LineFD line, String type) {
		AddBlockCommand command = new AddBlockCommand(nameManager,line, type);
		
		undoManager.execute(command);
	}
	
	/** Getters and Setters **/
	
}
