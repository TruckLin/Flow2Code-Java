package gui.object;

import java.awt.*;

import javax.swing.*;

import org.json.JSONObject;

import gui.interfaces.WithInport;
import gui.manager.NameCounterManager;
import gui.manager.UndoManager;

public class BlockEND extends BlockFD implements WithInport{
	private PortFD Inport;
	
	/** Constructors **/
	public BlockEND(JSONObject model) {
		super(model);
		
		// Initialise inport
		this.Inport =new PortFD( new Point( Math.round(this.getWidth()/2), 0), "top");
		
		// Temporary
		JLabel temp = new JLabel("End");
		this.add(temp);
		temp.setBounds(0,0,100,25);
		this.setBorder(BorderFactory.createLineBorder(Color.black));
	}
	
	/** Getters and Setters **/

	@Override
	public PortFD getInport() {
		// TODO Auto-generated method stub
		return this.Inport;
	}
	@Override
	public void setInport(PortFD p) {
		// TODO Auto-generated method stub
		this.Inport = p;
	}
	
	/** override abstract methods**/
	@Override
	public void setUndoManager(UndoManager undoManager) {
		this.undoManager = undoManager;
		
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
	public void setNameCounterManager(NameCounterManager nameManager) {
		// TODO Auto-generated method stub
		this.nameManager = nameManager;
	}

	@Override
	protected boolean shouldAddBlockRightClick() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isEditable() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean representCompositeBlock() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void updateBlockContent() {
		// TODO Auto-generated method stub
		// Do nothing.
	}

	
	
}
