package gui.codeView;

import javax.swing.undo.CannotRedoException;
import javax.swing.undo.CannotUndoException;
import javax.swing.undo.UndoableEdit;

public class EditorUndoableEdit implements UndoableEdit{

	private UndoableEdit undoableEdit;
	
	public EditorUndoableEdit(UndoableEdit edit) {
		this.undoableEdit = edit;
	}
	
	@Override
	public boolean addEdit(UndoableEdit anEdit) {
		// TODO Auto-generated method stub
		return this.undoableEdit.addEdit(anEdit);
	}

	@Override
	public boolean canRedo() {
		// TODO Auto-generated method stub
		return this.undoableEdit.canRedo();
	}

	@Override
	public boolean canUndo() {
		// TODO Auto-generated method stub
		return this.undoableEdit.canUndo();
	}

	@Override
	public void die() {
		// TODO Auto-generated method stub
		this.undoableEdit.die();
	}

	@Override
	public String getPresentationName() {
		// TODO Auto-generated method stub
		return this.undoableEdit.getPresentationName();
	}

	@Override
	public String getRedoPresentationName() {
		// TODO Auto-generated method stub
		return this.undoableEdit.getRedoPresentationName();
	}

	@Override
	public String getUndoPresentationName() {
		// TODO Auto-generated method stub
		return this.undoableEdit.getUndoPresentationName();
	}

	/** important override **/
	@Override
	public boolean isSignificant() {
		// TODO Auto-generated method stub
		
		return false;
	}

	@Override
	public void redo() throws CannotRedoException {
		// TODO Auto-generated method stub
		this.undoableEdit.redo();
	}

	@Override
	public boolean replaceEdit(UndoableEdit anEdit) {
		// TODO Auto-generated method stub
		return this.undoableEdit.replaceEdit(anEdit);
	}

	@Override
	public void undo() throws CannotUndoException {
		// TODO Auto-generated method stub
		this.undoableEdit.undo();
	}

}
