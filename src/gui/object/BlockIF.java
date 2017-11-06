package gui.object;

import java.awt.Color;
import java.awt.Point;
import java.awt.Rectangle;
import java.beans.PropertyChangeListener;

import javax.swing.BorderFactory;

import org.json.JSONObject;

import gui.manager.UndoManager;
import gui.mouselistener.DemoMouseListener;
import gui.mouselistener.MouseEnterListener;

public class BlockIF extends OrdinaryBlockFD{
	private BlockStartIF blockStartIF;
	private BlockEndIF blockEndIF;
	
	private LineFD trueLine;
	private LineFD falseLine; 
	// This is a stupid check that I need when adding blocks inside BlockIF, trueLine is the line on the right,
	// falseLine is the line one the left.
	
	private PropertyChangeListener listener = e -> resetOutInPorts();
	
	public BlockIF(JSONObject model) {
		super(model);

		this.setOpaque(false); // we should always see through this while panel.
		
		//Set various default property
		this.setSize(110,110);
		this.setLayout(null);
		this.setBorder(BorderFactory.createLineBorder(Color.black));
		//UndoManager undoManager = new UndoManager();
		//DemoMouseListener myListener = new DemoMouseListener(undoManager,this);
		//this.addMouseMotionListener(myListener);
		//this.addMouseListener(myListener);
		
		//Finally set the location of ports.
		//this.setPorts();
	}
	
	/** Utility Functions **/
	public void resetOutInPorts() {
		Point oldPoint = this.getOutport();
		resetOutport();
		resetInport();
		
		// Testing
		//System.out.println("resetOutInPorts() is called.");
		
		this.getPropertyChangeSupport().firePropertyChange("Outport", oldPoint, this.getOutport());
		
	}
	public void resetOutport() {
		Point p = this.getBlockEndIF().toContainerCoordinate(this.getBlockEndIF().getOutport());
		this.setOutport(p);
	}
	public void resetInport() {
		Point p = this.getBlockStartIF().toContainerCoordinate(this.getBlockStartIF().getInport());
		this.setInport(p);
	}
	
	
	/** Getters and Setters **/
	public BlockStartIF getBlockStartIF() {
		return this.blockStartIF;
	}
	public void setBlockStartIF(BlockStartIF comp) {
		if (this.blockStartIF != null) {
			// disconnect from previous model
			this.blockStartIF.removePropertyChangeListener(listener);
		}
		this.blockStartIF = comp;
		if (this.blockStartIF != null) {
			// connect to new model
			this.blockStartIF.addPropertyChangeListener(listener);

			// initialize fields in the UI
			resetInport();
		}
	}
	public BlockEndIF getBlockEndIF() {
		return this.blockEndIF; 
	}
	public void setBlockEndIF(BlockEndIF comp) {
		if (this.blockEndIF != null) {
			// disconnect from previous model
			this.blockEndIF.removePropertyChangeListener(listener);
		}
		this.blockEndIF = comp;
		if (this.blockEndIF != null) {
			// connect to new model
			this.blockEndIF.addPropertyChangeListener(listener);

			// initialize fields in the UI
			resetOutport();
		}
	}
	public LineFD getTrueLine() {
		return this.trueLine;
	}
	public void setTrueLine(LineFD line) {
		this.trueLine = line;
	}
	public LineFD getFalseLine() {
		return this.falseLine;
	}
	public void setFalseLine(LineFD line) {
		this.falseLine = line;
	}
	
	
	/** Utilities **/
	public void setAppropriateBounds() {
		// This function set approriate size according to it's children.
		// Size that is just big enough to contain all the children.
		super.setAppropriateBounds();
		resetOutInPorts();
	}
}
