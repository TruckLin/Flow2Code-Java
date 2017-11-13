package gui.object;

import java.awt.*;
import java.util.ArrayList;

import javax.swing.*;

import org.json.JSONObject;

import gui.interfaces.WithOutport;
import gui.manager.UndoManager;

public class BlockSTART extends BlockFD implements WithOutport{
	private Point outport;
	
	/** Constructors **/
	public BlockSTART(JSONObject model){
		super(model);
		
		// Initialise outport
		this.outport = new Point( Math.round(this.getWidth()/2), (int)this.getHeight());
				
		// Temporary
		JLabel temp = new JLabel("Start");
		this.add(temp);
		temp.setBounds(0,0,100,25);
		this.setBorder(BorderFactory.createLineBorder(Color.black));
	}
	
	/** Getters and Setters **/

	@Override
	public Point getOutport() {
		// TODO Auto-generated method stub
		return this.outport;
	}
	@Override
	public void setOutport(Point p) {
		// TODO Auto-generated method stub
		this.outport = p;
	}
	
	/** override abstract methods**/
	@Override
	protected void setCustomBounds(int x, int y, int width, int height) {
		this.setBounds(x, y, width, height);
		
	}
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
	
}
