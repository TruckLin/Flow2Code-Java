package gui.object;

import java.awt.Point;

import org.json.JSONObject;

import gui.interfaces.WithInport;
import gui.interfaces.WithOutport;
import gui.manager.NameCounterManager;
import gui.manager.UndoManager;

public abstract class OrdinaryBlockFD extends BlockFD implements WithInport, WithOutport{
	protected PortFD inport;
	protected PortFD outport; // with respect to Block's coordinate.
	
	
	public OrdinaryBlockFD(JSONObject model) {
		super(model);
		
		// Initialise inport
		this.inport = new PortFD(new Point( Math.round(this.getWidth()/2), 0), "top");
		// Initialise outport
		this.outport = new PortFD(new Point( Math.round(this.getWidth()/2), (int)this.getHeight()), "bottom" );
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
