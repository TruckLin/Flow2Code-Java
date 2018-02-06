package gui.commands;

import java.util.ArrayList;

import javax.swing.undo.UndoableEdit;

import gui.interfaces.Command;

public class UndoableEditCommand implements Command{

	private ArrayList<UndoableEdit> editList = new ArrayList<UndoableEdit>();
	private UndoableEdit edit;
	// I'm not sure what will happen when we do edit.add(anEdit).
	
	public UndoableEditCommand(UndoableEdit edit) {
		this.edit = edit;
		this.editList.add(edit);
	}
	
	/** Getters and Setters **/
	public UndoableEdit getUndoableEdit() {
		return this.edit;
	}
	
	@Override
	public void execute() {
		// TODO Auto-generated method stub
		edit.redo(); // this will never be called, as the edit has been done.
		
		//Testing
		System.out.println("execute() in UndoableEditCommand is called.");
	}

	@Override
	public void undo() {
		// TODO Auto-generated method stub
		for(int i = this.editList.size()-1; i >= 0; i--) {
			this.editList.get(i).undo();
		}
		
		//Testing 
		//sSystem.out.println("undo() is called");
	}

	@Override
	public void redo() {
		// TODO Auto-generated method stub
		for(int i = 0; i <= this.editList.size()-1 ; i++) {
			this.editList.get(i).redo();
		}
	}

	@Override
	public boolean isCollapsible(Command command) {
		// TODO Auto-generated method stub
		if(! (command instanceof UndoableEditCommand)) { 
			return false;
		}else {
			String previousName = ((UndoableEditCommand)command).getUndoableEdit().getPresentationName();
			String currentName = this.edit.getPresentationName();
			if(previousName.equals(currentName)) {
				return true;
			}else {
				return false;
			}
		}
	}

	@Override
	public void collapse(Command command) {
		// TODO Auto-generated method stub
		this.editList.add(((UndoableEditCommand)command).getUndoableEdit());
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return null;
	}

}
