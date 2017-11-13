package gui.object;

import java.awt.Point;
import java.beans.PropertyChangeListener;

import org.json.JSONObject;

import gui.interfaces.WithInport;
import gui.interfaces.WithOutport;

public abstract class OrdinaryCompositeBlockFD extends CompositeBlockFD implements WithInport, WithOutport{
	
	protected Point inport;
	protected Point outport; // with respect to Block's coordinate.
	
	protected PropertyChangeListener listener = e -> resetInOutPorts();
	
	public OrdinaryCompositeBlockFD(JSONObject model) {
		super(model);
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
	}
	
	@Override
	public Point getOutport() {
		return this.outport;
	}


	@Override
	public void setOutport(Point p) {
		this.outport = p;
	}


	@Override
	public Point getInport() {
		return this.inport;
	}


	@Override
	public void setInport(Point p) {
		this.inport = p;	
	}

}
