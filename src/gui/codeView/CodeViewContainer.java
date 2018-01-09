package gui.codeView;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.util.HashMap;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.event.UndoableEditEvent;
import javax.swing.event.UndoableEditListener;
import javax.swing.text.AbstractDocument;
import javax.swing.text.JTextComponent;
import javax.swing.text.StyledDocument;
import javax.swing.undo.CannotRedoException;
import javax.swing.undo.CannotUndoException;
import javax.swing.undo.UndoManager;

import gui.Flow2Code;

public class CodeViewContainer extends JPanel{
	
	private Flow2Code mainFrame;
	
	private CodeViewToolBar codeViewToolBar;
	private CodeViewTextPane codeViewTextPane;
	private AbstractDocument doc;
	
    private HashMap<Object, Action> actions;
    
    //undo helpers
    protected UndoAction undoAction;
    protected RedoAction redoAction;
    protected UndoManager undo;
    
	public CodeViewContainer(Flow2Code mainFrame) {
		super(new BorderLayout());
		this.mainFrame = mainFrame;
		
		this.codeViewTextPane = new CodeViewTextPane();
		
		StyledDocument styledDoc = codeViewTextPane.getStyledDocument();
        if (styledDoc instanceof AbstractDocument) {
            doc = (AbstractDocument)styledDoc;
        } else {
            System.err.println("Text pane's document isn't an AbstractDocument!");
            System.exit(-1);
        }
        JScrollPane scrollPane = new JScrollPane(codeViewTextPane);
        scrollPane.setPreferredSize(new Dimension(400, 400));
        
        /** created undo and redo actions **/
        this.undoAction = new UndoAction();
        this.redoAction = new RedoAction();
        this.undo = new UndoManager();
        
        
        this.codeViewToolBar = new CodeViewToolBar(this);
        
        //Add the components
        this.add(codeViewToolBar, BorderLayout.NORTH);
	    this.add(codeViewTextPane, BorderLayout.CENTER);
		
	}
	
	//This one listens for edits that can be undone.
    protected class MyUndoableEditListener
                    implements UndoableEditListener {
        public void undoableEditHappened(UndoableEditEvent e) {
            //Remember the edit and update the menus.
            undo.addEdit(e.getEdit());
            undoAction.updateUndoState();
            redoAction.updateRedoState();
        }
    }
    
  //The following two methods allow us to find an
    //action provided by the editor kit by its name.
    private HashMap<Object, Action> createActionTable(JTextComponent textComponent) {
        HashMap<Object, Action> actions = new HashMap<Object, Action>();
        Action[] actionsArray = textComponent.getActions();
        for (int i = 0; i < actionsArray.length; i++) {
            Action a = actionsArray[i];
            actions.put(a.getValue(Action.NAME), a);
            
            //Testing
            //System.out.println("Action name = " + a.getValue(Action.NAME));
        }
	return actions;
    }

    private Action getActionByName(String name) {
        return actions.get(name);
    }

    class UndoAction extends AbstractAction {
        public UndoAction() {
            super("Undo");
            setEnabled(false);
        }

        public void actionPerformed(ActionEvent e) {
            try {
                undo.undo();
            } catch (CannotUndoException ex) {
                System.out.println("Unable to undo: " + ex);
                ex.printStackTrace();
            }
            updateUndoState();
            redoAction.updateRedoState();
        }

        protected void updateUndoState() {
            if (undo.canUndo()) {
                setEnabled(true);
                putValue(Action.NAME, undo.getUndoPresentationName());
            } else {
                setEnabled(false);
                putValue(Action.NAME, "Undo");
            }
        }
    }

    class RedoAction extends AbstractAction {
        public RedoAction() {
            super("Redo");
            setEnabled(false);
        }

        public void actionPerformed(ActionEvent e) {
            try {
                undo.redo();
            } catch (CannotRedoException ex) {
                System.out.println("Unable to redo: " + ex);
                ex.printStackTrace();
            }
            updateRedoState();
            undoAction.updateUndoState();
        }

        protected void updateRedoState() {
            if (undo.canRedo()) {
                setEnabled(true);
                putValue(Action.NAME, undo.getRedoPresentationName());
            } else {
                setEnabled(false);
                putValue(Action.NAME, "Redo");
            }
        }
    }
	
	/** Getters and Setters **/
	public CodeViewToolBar getCodeViewToolBar() {
		return this.codeViewToolBar;
	}
	public CodeViewTextPane getCodeViewTextPane() {
		return this.codeViewTextPane;
	}
	public UndoAction getUndoAction() {
		return this.undoAction;
	}
	public RedoAction getRedoAction() {
		return this.redoAction;
	}
	public UndoManager getCodeUndoManager() {
		return this.undo;
	}
}
