package gui.object;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;

import javax.swing.BorderFactory;
import javax.swing.JLabel;

import org.json.JSONObject;

import gui.interfaces.WithOutport;
import gui.manager.NameCounterManager;
import gui.manager.UndoManager;

public class BlockEndIF extends BlockFD implements WithOutport{
	private PortFD outport;
	
	private PortFD trueInport;
	private PortFD falseInport;
	
	public BlockEndIF(JSONObject model){
		super(model);
		this.setLayout(null);
		
		// setDefault bounds
		this.setBounds(43,80,25,25);
		this.setBorder(BorderFactory.createLineBorder(Color.black));
		this.setOpaque(false);
		
		// Initialise outport
		this.outport = new PortFD(new Point( Math.round(this.getWidth()/2), (int)this.getHeight()), "bottom");
		
		// Initialise inports
		this.trueInport = new PortFD(new Point(this.getWidth(), Math.round(this.getHeight()/2)), "right");
		this.falseInport = new PortFD(new Point(0, Math.round(this.getHeight()/2) ), "left") ;
	}
	
	/** Graphics setting **/
	@Override
	protected void paintComponent(Graphics g) {
		Graphics2D g2 = (Graphics2D)g;
		g2.setColor(Color.yellow);
		
        int x = (int)this.getLocation().getX();
        int y = (int)this.getLocation().getY();
        int width = this.getWidth();
        int height = this.getHeight();
        
		g2.fillOval(0, 0, width, height);
    }
	
	/** Getters and Setters **/
	public PortFD getTrueInport(){
		return this.trueInport;
	}
	public void setTrueInport(PortFD p){
		this.trueInport = p;
	}
	public PortFD getFalseInport(){
		return this.falseInport;
	}
	public void setFalseInport(PortFD p){
		this.falseInport = p;
	}
	@Override
	public PortFD getOutport() {
		// TODO Auto-generated method stub
		return this.outport;
	}
	@Override
	public void setOutport(PortFD p) {
		// TODO Auto-generated method stub
		this.outport = p;
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
		return false;
	}
	@Override
	protected boolean shouldAddLoopDrag() {
		return false;
	}
	@Override
	protected boolean shouldAddEndLoopDrag() {
		// TODO Auto-generated method stub
		return true;
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
		
	}

}
