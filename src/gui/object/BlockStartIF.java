package gui.object;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.beans.PropertyChangeListener;

import javax.swing.BorderFactory;
import javax.swing.JLabel;

import org.json.JSONObject;

import gui.mouselistener.MouseEnterListener;
import gui.interfaces.WithInport;
import gui.manager.NameCounterManager;
import gui.manager.UndoManager;

public class BlockStartIF extends BlockFD implements WithInport{
	
	private PortFD inport = new PortFD(new Point( Math.round(this.getWidth()/2), 0), "top");
	
	private PortFD trueOutport = new PortFD(new Point(this.getWidth() - 1, Math.round(this.getHeight()/2)), "right");
	private PortFD falseOutport = new PortFD(new Point( 0 , Math.round(this.getHeight()/2)), "left");
	public BlockStartIF(JSONObject model){
		super(model);
		
		//Testing
		//System.out.println("\nStart of the constructor of BlockStartIF : ");
		
		this.removeAll();
		this.setLayout(null);
		
		// Set Default bounds
		this.setBounds(0,0,100,50);
		
		// Add a listener that change the border of it's parent when mouse enter.
		MouseEnterListener mouseEnter = new MouseEnterListener(this);
		mouseEnter.setSouldChangeParentBlock(true);
		this.addMouseListener(mouseEnter);
		//Testing
/*		System.out.println("startIF.getBounds = " + this.getBounds().toString());
		System.out.println("blockStartTrueIF.getTrueOutport = " + this.getTrueOutport().toString());
		System.out.println("blockStartFalseIF.getFalseOutport = " + this.getFalseOutport().toString());*/ 

		this.blockLabel.setText("StartIF");
		this.adjustLabelSize();
		this.adjustBlockSizeByLabel();
		this.adjustLabelLocation();
		this.add(blockLabel);
		// Temporary
//		this.setBorder(BorderFactory.createLineBorder(Color.black));
		this.setBorder(BorderFactory.createEmptyBorder());
	}
	
	@Override
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D)g;
		g2.setColor(new Color(0,0,0));
		
		int[] xPoints = { 0, this.getWidth()/2, this.getWidth(), this.getWidth()/2};
		int[] yPoints = {this.getHeight()/2, 0, this.getHeight()/2, this.getHeight()};
		
		g2.drawPolygon(xPoints, yPoints, 4);
	}
	
	@Override
	public void adjustBlockSizeByLabel() {
		//super.adjustBlockSizeByLabel();
		int minWidth = (int)Math.round(100*this.currentZoomRatio);
		int minHeight = (int)Math.round(50*this.currentZoomRatio);
		
		boolean sizeShouldChange = false;
		
		int newWidth = minWidth;
		int newHeight = minHeight;
		
		
		Dimension labelDimension = this.blockLabel.getPreferredSize();
		
		// We need to also deal with the case when text label got shorter. BlockSize needs to shrink.
		int x = (int)this.getLocation().getX();
		int y = (int)this.getLocation().getY();
		
		this.setBounds(x,y, minWidth , minHeight);
		
		if(labelDimension.getWidth() > minWidth) {
			newWidth = (int)labelDimension.getWidth();
			sizeShouldChange = true;
		}
		if(labelDimension.getHeight() > minHeight){
			newHeight = (int)labelDimension.getHeight();
			sizeShouldChange = true;
		}
		
		/** Mark it always true as supervisor requested.**/
		sizeShouldChange = true;
		int offset = (int)(minHeight - labelDimension.getHeight());
		newHeight = (int) (labelDimension.getHeight() + offset);
		newWidth = (int) (((labelDimension.getWidth()*labelDimension.getHeight())/offset) + labelDimension.getWidth());
		
		if(sizeShouldChange) {
			this.setBounds(x,y,
					newWidth + (int)Math.round(10*this.currentZoomRatio),
					newHeight + (int)Math.round(0*this.currentZoomRatio));
		}
	}

	/** Getters and Setters for ports**/
	public PortFD getTrueOutport(){
		return this.trueOutport;
	}
	public void setTrueOutport(PortFD p){
		this.trueOutport = p;
	}
	public PortFD getFalseOutport(){
		return this.falseOutport;
	}
	public void setFalseOutport(PortFD p){
		this.falseOutport = p;
	}
	
	@Override
	public PortFD getInport() {
		// TODO Auto-generated method stub
		return this.inport;
	}
	@Override
	public void setInport(PortFD p) {
		// TODO Auto-generated method stub
		this.inport = p;
	}
	
	/**    Getters and Setter for label    **/

	
	/** override abstract methods**/
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
	protected void updatePorts() {
		// reset inport
		this.inport.setPortLocation(new Point( Math.round(this.getWidth()/2), 0) );
		
		// reset Outport panels
		this.trueOutport.setPortLocation( new Point(this.getWidth() - 1, Math.round(this.getHeight()/2) ) );
		this.falseOutport.setPortLocation(new Point( 0 , Math.round(this.getHeight()/2) ) );
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

	@Override
	public boolean representCompositeBlock() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public void updateBlockContent() {
		// TODO Auto-generated method stub
		// do nothing.
	}

	
	
	
	
	
	
}
