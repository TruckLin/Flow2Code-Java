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

public class BlockIF extends OrdinaryCompositeBlockFD{
	private BlockStartIF blockStartIF;
	private BlockEndIF blockEndIF;
	
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
	
	/** Utilities **/
	@Override
	public void resetInOutPorts() {
		resetInport();
		resetOutport();
	}
	
	@Override
	public void resetInport() {
		Point oldPoint = this.getInport();
		Point p = this.getBlockStartIF().toContainerCoordinate(this.getBlockStartIF().getInport());
		this.setInport(p);
	//	this.getPropertyChangeSupport().firePropertyChange("Inport", oldPoint, this.getInport());
	}

	@Override
	public void resetOutport() {
		Point oldPoint = this.getOutport();
		Point p = this.getBlockEndIF().toContainerCoordinate(this.getBlockEndIF().getOutport());
		this.setOutport(p);
	//	this.getPropertyChangeSupport().firePropertyChange("Outport", oldPoint, this.getOutport());
	}
}
