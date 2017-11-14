package gui.object;

import java.awt.Point;

import org.json.JSONObject;

import gui.interfaces.WithInport;
import gui.interfaces.WithOutport;
import gui.manager.NameCounterManager;
import gui.manager.UndoManager;

public abstract class OrdinaryBlockFD extends BlockFD implements WithInport, WithOutport{
	private Point inport;
	private Point outport; // with respect to Block's coordinate.
	
	
	public OrdinaryBlockFD(JSONObject model) {
		super(model);
		
		// Initialise inport
		this.inport = new Point( Math.round(this.getWidth()/2), 0);
		// Initialise outport
		this.outport = new Point( Math.round(this.getWidth()/2), (int)this.getHeight());
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

}
