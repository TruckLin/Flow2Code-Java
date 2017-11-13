package gui.object;

import java.awt.Color;
import java.awt.Point;

import javax.swing.BorderFactory;
import javax.swing.JLabel;

import org.json.JSONObject;

import gui.mouselistener.MouseEnterListener;
import gui.interfaces.WithInport;
import gui.manager.UndoManager;

public class BlockStartIF extends BlockFD implements WithInport{
	private Point inport;
	
	private Point trueOutport;
	private Point falseOutport;
	
	
	public BlockStartIF(JSONObject model){
		super(model);
		this.removeAll();
		this.setLayout(null);
		
		// Initialise inport
		this.inport = new Point( Math.round(this.getWidth()/2), 0);
		// set the Outports
		this.trueOutport = new Point(this.getWidth(), Math.round(this.getHeight()/2));
		this.falseOutport = new Point(0, Math.round(this.getHeight()/2));
		
		// Set Default bounds
		this.setBounds(5,5,100,25);
		
		// Temporary
		JLabel temp = new JLabel("StartIF");
		this.add(temp);
		temp.setBounds(0,0,100,25);
		this.setBorder(BorderFactory.createLineBorder(Color.black));
		
	}
	
	/** Getters and Setters **/
	public Point getTrueOutport(){
		return this.trueOutport;
	}
	public void setTrueOutport(Point p){
		this.trueOutport = p;
	}
	public Point getFalseOutport(){
		return this.falseOutport;
	}
	public void setFalseOutport(Point p){
		this.falseOutport = p;
	}
	
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
		return false;
	}
	
	@Override
	protected boolean shouldAddLoopDrag() {
		return true;
	}
	
	
	
}
