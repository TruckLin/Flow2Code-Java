package gui.object;

import java.awt.FlowLayout;
import java.awt.Point;

import javax.swing.JLabel;

import org.json.JSONObject;

import gui.interfaces.WithInport;
import gui.interfaces.WithOutport;
import gui.manager.NameCounterManager;
import gui.manager.UndoManager;

public abstract class OrdinaryBlockFD extends BlockFD implements WithInport, WithOutport{
	protected PortFD inport = new PortFD(new Point( Math.round(this.getWidth()/2), 0), "top");
	protected PortFD outport = new PortFD(new Point( Math.round(this.getWidth()/2), (int)this.getHeight()), "bottom" );
	// with respect to Block's coordinate.
	
	public OrdinaryBlockFD(JSONObject model) {
		super(model);
		
		// Set defaultBounds
		this.setBounds(0,0,100,25);
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
	
	/** Override the abstract methods **/
	@Override
	protected void updatePorts() {
		// update inport
		this.inport.setPortLocation(new Point(Math.round(this.getWidth()/2), 0));
		// Initialise outport
		this.outport.setPortLocation(new Point( Math.round(this.getWidth()/2), (int)this.getHeight()-1));
	}
	@Override
	public void setUndoManager(UndoManager undoManager) {
		this.undoManager = undoManager;
	}
	@Override
	public void setNameCounterManager(NameCounterManager nameManager) {
		// TODO Auto-generated method stub
		this.nameManager = nameManager;
	}
	@Override
	protected boolean isCompositeBlockFD() {
		return false;
	}
	
	@Override
	protected boolean shouldAddBlockDrag() {
		// TODO Auto-generated method stub
		return true;
	}
	@Override
	protected boolean shouldAddLoopDrag() {
		return false;
	}
	@Override
	protected boolean shouldAddEndLoopDrag() {
		// TODO Auto-generated method stub
		return false;
	}
	
	@Override
	protected boolean shouldAddBlockRightClick() {
		// TODO Auto-generated method stub
		return true;
	}
	@Override
	public boolean isEditable() {
		// TODO Auto-generated method stub
		return true;
	}

}
