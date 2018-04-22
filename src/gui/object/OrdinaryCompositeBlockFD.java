package gui.object;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.beans.PropertyChangeListener;

import javax.swing.BorderFactory;

import org.json.JSONObject;

import gui.interfaces.WithInport;
import gui.interfaces.WithOutport;

public abstract class OrdinaryCompositeBlockFD extends CompositeBlockFD implements WithInport, WithOutport{
	
	protected PortFD inport = new PortFD(new Point( Math.round(this.getWidth()/2), 0), "top");
	protected PortFD outport= new PortFD(new Point( Math.round(this.getWidth()/2), (int)this.getHeight()), "bottom" );
	// with respect to Block's coordinate.
	
	protected PropertyChangeListener listener = e -> {updatePorts();
													  this.firePropertyChange("Ports", null, null);
													 };
	
	public OrdinaryCompositeBlockFD(JSONObject model) {
		super(model);
	}
	/** Abstract functions needed override **/
	@Override
	protected void updatePorts() {
		updateInport();
		updateOutport();
	}
	public abstract void updateInport();
	public abstract void updateOutport();
	
	
	public void setAppropriateBounds() {
		// This function set appropsriate size according to it's children.
		// Size that is just big enough to contain all the children.
		super.setAppropriateBounds();
		updatePorts();
		
		// Testing
		//System.out.println("setAppropriateBounds() in OrdinaryCompositeBlockFD is called.");
	}
	
	@Override
	public PortFD getOutport() {
		return this.outport;
	}


	@Override
	public void setOutport(PortFD p) {
		this.outport = p;
	}


	@Override
	public PortFD getInport() {
		return this.inport;
	}


	@Override
	public void setInport(PortFD p) {
		this.inport = p;	
	}
	
	
}
