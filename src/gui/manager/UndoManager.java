package gui.manager;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.Stack;

import gui.interfaces.Command;

public class UndoManager {
	private PropertyChangeSupport pcs = new PropertyChangeSupport(this);
	
	public void addPropertyChangeListener(PropertyChangeListener listener) {
		pcs.addPropertyChangeListener(listener);
	}
	public void addPropertyChangeListener(String propertyName, PropertyChangeListener listener) {
		pcs.addPropertyChangeListener(propertyName, listener);
	}
	public void removePropertyChangeListener(PropertyChangeListener listener) {
		pcs.removePropertyChangeListener(listener);
	}
	public void removePropertyChangeListener(String propertyName, PropertyChangeListener listener) {
		pcs.removePropertyChangeListener(propertyName, listener);
	}
	
	private Stack<Command> undoStack = new Stack<Command>();
	private Stack<Command> redoStack = new Stack<Command>();
	
	private class OldState {
		private String undoName = getUndoName();
		private String redoName = getUndoName();
		private boolean undoAvailable = isUndoAvailable();
		private boolean redoAvailable = isRedoAvailable();
	}
	
	// This function is created for UndoableEditCommand.
	// which allow commad to be push into stack without executing it.
	public void addUndoableCommand(Command command) {
		try {
			OldState oldState = new OldState();
		//	command.execute();
			if( !undoStack.isEmpty() && undoStack.peek().isCollapsible(command)) {
				undoStack.peek().collapse(command);
			}else {
				undoStack.push(command);
			}
			redoStack.clear();
			fireChanges(oldState);
		} catch (IllegalStateException e) {
			// report and log
			System.err.println(e.toString());
		}
	}
	
	public void execute(Command command) {
		try {
			OldState oldState = new OldState();
			command.execute();
			if( !undoStack.isEmpty() && undoStack.peek().isCollapsible(command)) {
				undoStack.peek().collapse(command);
			}else {
				undoStack.push(command);
			}
			redoStack.clear();
			fireChanges(oldState);
		} catch (IllegalStateException e) {
			// report and log
			System.err.println(e.toString());
		}
	}
	
	public void undo() {
		if (!undoStack.isEmpty()) {
			try {
				OldState oldState = new OldState();
				Command command = undoStack.pop();
				command.undo();
				redoStack.push(command);
				fireChanges(oldState);
			} catch (IllegalStateException e) {
				// report and log
				System.err.println(e.toString());
			}
		}
	}
	
	
	public void redo() {
		if (!redoStack.isEmpty()) {
			try {
				OldState oldState = new OldState();
				Command command = redoStack.pop();
				command.redo();
				undoStack.push(command);
				fireChanges(oldState);
			} catch (IllegalStateException e) {
				// report and log
				System.err.println(e.toString());
			}
		}
	}
	
	private void fireChanges(OldState oldState) {
		pcs.firePropertyChange("undoName", oldState.undoName, getUndoName());
		pcs.firePropertyChange("redoName", oldState.redoName, getRedoName());
		pcs.firePropertyChange("canUndo", oldState.undoAvailable, isUndoAvailable());
		pcs.firePropertyChange("canRedo", oldState.redoAvailable, isRedoAvailable());
	}

	public boolean isUndoAvailable() {
		//Testing
		//System.out.println("In isUndoAvailable() : ");
		//System.out.println("undoStack.isEmpty() = " + undoStack.isEmpty());
		
		
		return !undoStack.isEmpty();
	}
	public boolean isRedoAvailable() {
		return !redoStack.isEmpty();
	}
	
	public String getUndoName() {
		if (isUndoAvailable()) {
			return undoStack.peek().getName();
		}
		return "";
	}
	public String getRedoName() {
		if (isRedoAvailable()) {
			return redoStack.peek().getName();
		}
		return "";
	}
	
}
