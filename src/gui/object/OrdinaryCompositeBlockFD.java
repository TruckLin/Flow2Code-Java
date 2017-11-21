package gui.object;

import java.awt.Point;
import java.beans.PropertyChangeListener;

import org.json.JSONObject;

import gui.interfaces.WithInport;
import gui.interfaces.WithOutport;

public abstract class OrdinaryCompositeBlockFD extends CompositeBlockFD implements WithInport, WithOutport{
	
	protected PortFD inport;
	protected PortFD outport; // with respect to Block's coordinate.
	
	protected PropertyChangeListener listener = e -> {resetInOutPorts();
													  this.firePropertyChange("Ports", null, null);
													 };
	
	public OrdinaryCompositeBlockFD(JSONObject model) {
		super(model);
		
		// Initialise inport
		this.inport = new PortFD(new Point( Math.round(this.getWidth()/2), 0), "top");
		// Initialise outport
		this.outport = new PortFD(new Point( Math.round(this.getWidth()/2), (int)this.getHeight()), "bottom" );
	}
	
	/** All composite should write a function resetInOutPorts()
	 * @return **/
	public abstract void resetInOutPorts() ;
	public abstract void resetInport();
	public abstract void resetOutport();
	
	
	public void setAppropriateBounds() {
		// This function set approriate size according to it's children.
		// Size that is just big enough to contain all the children.
		super.setAppropriateBounds();
		resetInOutPorts();
		
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
