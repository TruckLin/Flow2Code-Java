package gui.object;

import java.awt.Point;

import org.json.JSONObject;

import gui.interfaces.WithOutport;
import gui.manager.NameCounterManager;
import gui.manager.UndoManager;

public class BlockStartFalseIF extends BlockFD implements WithOutport{

	private BlockStartIF blockStartIF;

	private Point Outport;

	public BlockStartFalseIF(JSONObject model) {
		super(model);

		this.setSize(1,1);

		this.setOutport(new Point(0,0));

	}

	/** Override some getters and setters **/
	public BlockStartIF getBlockStartIF() {
		return this.blockStartIF;
	}
	public void setBlockStartIF(BlockStartIF block) {
		this.blockStartIF = block;
	}

	@Override
	public JSONObject getModel() {
		return blockStartIF.getModel();
	}


	@Override
	public Point getOutport() {
		// TODO Auto-generated method stub
		return this.Outport;
	}

	@Override
	public void setOutport(Point p) {
		// TODO Auto-generated method stub
		this.Outport = p;
	}


	/** override abstract method **/
	@Override
	public void setUndoManager(UndoManager undoManager) {
		this.undoManager = undoManager;

	}

	@Override
	protected boolean isCompositeBlockFD() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	protected boolean shouldAddBlockDrag() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	protected boolean shouldAddLoopDrag() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	protected boolean shouldAddEndLoopDrag() {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public void setNameCounterManager(NameCounterManager nameManager) {
		// TODO Auto-generated method stub
		this.nameManager = nameManager;
	}
}