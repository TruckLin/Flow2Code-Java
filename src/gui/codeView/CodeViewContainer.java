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

//Two different UndoManager
//import javax.swing.undo.UndoManager;


import gui.Flow2Code;
import gui.commands.UndoableEditCommand;
import gui.manager.UndoManager;
import strategy.codegenerator.JavaCodeGenerator;

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
        
        JavaCodeGenerator codeGenerator = new JavaCodeGenerator(this.mainFrame.getBlockFlowDiagram());
        String code = "";
		code = codeGenerator.generate(this.mainFrame.getFlowDiagramModel(), code, "");
		this.codeViewTextPane.setText(code);
        
        
        JScrollPane scrollPane = new JScrollPane(codeViewTextPane);
        scrollPane.setPreferredSize(new Dimension(400, 400));
        
        /** created undo and redo actions **/
        this.undoAction = new UndoAction();
        this.redoAction = new RedoAction();
        this.undo = new UndoManager();
        
        //Testing
        //this.undo = new MyTestUndoManager();
        
        
        this.codeViewToolBar = new CodeViewToolBar(this);
        
        //Add the components
        this.add(codeViewToolBar, BorderLayout.NORTH);
	    this.add(codeViewTextPane, BorderLayout.CENTER);
	    
	  //Start watching for undoable edits and caret changes.
      doc.addUndoableEditListener(new MyUndoableEditListener());
      
      
      
      // Fill the hash table
      this.actions = this.createActionTable(this.codeViewTextPane);
		
	}
	
	//This one listens for edits that can be undone.
    protected class MyUndoableEditListener
                    implements UndoableEditListener {
    	
    	private long lastSignificantEditTime;
    	private long lastEditTime;
    	
    	public MyUndoableEditListener() {
    		this.lastSignificantEditTime = System.currentTimeMillis();
    		this.lastEditTime = System.currentTimeMillis();
    		//System.out.println(this.lastSignificantEditTime);
    	}
    	
        public void undoableEditHappened(UndoableEditEvent e) {
            //Remember the edit
        	
        	/*** Swing version ***/
        	/*
        	// Get current time
        	long currentTime = System.currentTimeMillis();
        	// make sure there are edits in undo stack
            if(undo.canUndo()) {
            	//Testing
            //	e.getEdit()
            	String previousUndoType = undo.getUndoPresentationName();
            	int i = previousUndoType.indexOf(' ');
            	previousUndoType = previousUndoType.substring(i + 1, previousUndoType.length());
            //	System.out.println("undo.getUndoPresentationName() = " + undo.getUndoPresentationName());
            //	System.out.println("e.getEdit().getPresentationName() = " + e.getEdit().getPresentationName() );
            //	System.out.println("e.getEdit().getClass() = " + e.getEdit().);
            //	System.out.println("e.getEdit().isSignificant() = " + e.getEdit().isSignificant());
            
            	//	undo.
            //	System.out.println("previousUndoType = " + previousUndoType);
            //	System.out.println("currentUndoType = " + e.getEdit().getPresentationName());
            //	System.out.println("previousUndoType.equals(currentUndoType) = " + 
            //								previousUndoType.equals( e.getEdit().getPresentationName() ));
            	
            	if( (!previousUndoType.equals(e.getEdit().getPresentationName())) || 
            								( (currentTime - this.lastEditTime) > 2000) ) {
            		// if previous type and current type are different or
            		//				It has been x seconds since last significantEdit
            		// Then current edit should be significant.
            		undo.addEdit(e.getEdit());
                	undoAction.updateUndoState();
                    redoAction.updateRedoState();
                    this.lastSignificantEditTime = currentTime;
                    this.lastEditTime = currentTime;
            	}else {
            		// Otherwise we add insignificant edit.
            		 undo.addEdit(new EditorUndoableEdit(e.getEdit()) );
            		 undoAction.updateUndoState();
                     redoAction.updateRedoState(); 
                     this.lastEditTime = currentTime;
            	}
            	
            }else {
            	undo.addEdit(e.getEdit());
            	undoAction.updateUndoState();
                redoAction.updateRedoState();
                this.lastSignificantEditTime = currentTime;
                this.lastEditTime = currentTime;
            }
            */
            /** My version **/
        	UndoableEditCommand undoableEditCommand = new UndoableEditCommand(e.getEdit());
            undo.addUndoableCommand(undoableEditCommand);
            
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
            //System.out.println(i + "th action : ");
            //System.out.println("    Action name = " + a.getValue(Action.NAME));
            //System.out.println("    Short description = " + a.getValue(Action.SHORT_DESCRIPTION));
            
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
        	/** Swing Version **/
        	/*
            if (undo.canUndo()) {
                setEnabled(true);
                putValue(Action.NAME, undo.getUndoPresentationName());
            } else {
                setEnabled(false);
                putValue(Action.NAME, "Undo");
            }
            */
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
        	/** Swing Version **/
        	/*
            if (undo.canRedo()) {
                setEnabled(true);
                putValue(Action.NAME, undo.getRedoPresentationName());
            } else {
                setEnabled(false);
                putValue(Action.NAME, "Redo");
            }
            */
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
