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
	private NameCounterManager nameManager;
	private LineFD line;
	private JMenuItem declareItem = new JMenuItem("Declare");
	private JMenuItem assignItem = new JMenuItem("Assign");
	private JMenuItem inputItem = new JMenuItem("Input");
	private JMenuItem outputItem = new JMenuItem("Output");
	private JMenuItem forItem = new JMenuItem("For");
	private JMenuItem whileItem = new JMenuItem("While");
	private JMenuItem ifItem = new JMenuItem("If");
	
	
	public LinePopup(UndoManager undoManager,NameCounterManager nameManager) {
		super();
		this.setLayout(new GridLayout(4,2));
		this.undoManager = undoManager;
		this.nameManager = nameManager;
		
		// Add all the menu items
		this.add(this.declareItem);
		this.add(this.assignItem);
		this.add(this.inputItem);
		this.add(this.outputItem);
		this.add(this.forItem);
		this.add(this.whileItem);
		this.add(this.ifItem);
	}
	
	/** Getters and Setters **/
	public LineFD getLine() {
		return this.line;
	}
	public void setLine(LineFD newLine) {
		this.line = newLine;
		
		// remove previous action listeners
		ActionListener[] listenerList = this.declareItem.getActionListeners();
		for(ActionListener listener : listenerList) {
			this.declareItem.removeActionListener(listener);
		}
		listenerList = this.assignItem.getActionListeners();
		for(ActionListener listener : listenerList) {
			this.assignItem.removeActionListener(listener);
		}
		listenerList = this.inputItem.getActionListeners();
		for(ActionListener listener : listenerList) {
			this.inputItem.removeActionListener(listener);
		}
		listenerList = this.outputItem.getActionListeners();
		for(ActionListener listener : listenerList) {
			this.outputItem.removeActionListener(listener);
		}
		listenerList = this.forItem.getActionListeners();
		for(ActionListener listener : listenerList) {
			this.forItem.removeActionListener(listener);
		}
		listenerList = this.whileItem.getActionListeners();
		for(ActionListener listener : listenerList) {
			this.whileItem.removeActionListener(listener);
		}
		listenerList = this.ifItem.getActionListeners();
		for(ActionListener listener : listenerList) {
			this.ifItem.removeActionListener(listener);
		}
		
		// Add new action listener to menu items
		this.declareItem.addActionListener(e -> addBlockAction(this.undoManager,this.nameManager, newLine, "Declare"));
		this.assignItem.addActionListener(e -> addBlockAction(this.undoManager,this.nameManager, newLine, "Assign"));
		this.inputItem.addActionListener(e -> addBlockAction(this.undoManager,this.nameManager, newLine, "Input"));
		this.outputItem.addActionListener(e -> addBlockAction(this.undoManager,this.nameManager, newLine, "Output"));
		this.forItem.addActionListener(e -> addBlockAction(this.undoManager,this.nameManager, newLine, "For"));
		this.whileItem.addActionListener(e -> addBlockAction(this.undoManager,this.nameManager, newLine, "While"));
		this.ifItem.addActionListener(e -> addBlockAction(this.undoManager,this.nameManager, newLine, "If"));
		
	}
	
	
	/** The action that tells undoManager to execute various command **/
	public void addBlockAction(UndoManager undoManager,NameCounterManager nameManager ,LineFD line, String type) {
		AddBlockCommand command = new AddBlockCommand(undoManager,nameManager,line, type);
		
		undoManager.execute(command);
	}
}
