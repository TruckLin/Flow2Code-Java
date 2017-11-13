package gui.object;

import java.awt.*;

import javax.swing.*;

import org.json.JSONObject;

import gui.interfaces.WithInport;
import gui.manager.UndoManager;

public class BlockEND extends BlockFD implements WithInport{
	private Point inport;
	
	/** Constructors **/
	public BlockEND(JSONObject model) {
		super(model);
		
		// Initialise inport
		this.inport = new Point( Math.round(this.getWidth()/2), 0);
		
		// Temporary
		JLabel temp = new JLabel("End");
		this.add(temp);
		temp.setBounds(0,0,100,25);
		this.setBorder(BorderFactory.createLineBorder(Color.black));
	}
	
	/** Getters and Setters **/

	@Override
	public Point getInport() {
		// TODO Auto-generated method stub
		return this.inport;
	}
	@Override
	public void setInport(Point p) {
		// TODO Auto-generated method stub
		this.inport = p;
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
	
}
