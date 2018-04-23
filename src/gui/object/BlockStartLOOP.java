package gui.object;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;

import javax.swing.BorderFactory;
import javax.swing.JLabel;

import org.json.JSONObject;

import gui.mouselistener.MouseEnterListener;


public class BlockStartLOOP extends OrdinaryBlockFD{
	
	private PortFD loopOutport = new PortFD(new Point( Math.round(this.getWidth()/4), this.getHeight() ), "bottom");
	public BlockStartLOOP(JSONObject model) {
		super(model);
		// set the specific outport for BlockStartLOOP
		this.outport.setPortLocation(new Point( this.getWidth() - 1, (int)this.getHeight()/2 ));
		this.outport.setSide("right");
		
		// set the specific inport for BlockStartLOOP
		this.inport.setPortLocation(new Point( Math.round(this.getWidth() - this.getHeight()/2), this.getHeight() - 1 ));
		this.inport.setSide("bottom");
		
		// Set the location of BlockStartLOOP
		//this.setLocation(5, 5);
		
		// Add a listener that change the border of it's parent when mouse enter.
		MouseEnterListener mouseEnter = new MouseEnterListener(this);
		mouseEnter.setSouldChangeParentBlock(true);
		this.addMouseListener(mouseEnter);

		this.blockLabel.setText("StartLoop");
		this.blockLabel.setMaximumSize(new Dimension(150,50));
		this.adjustLabelSize();
		this.adjustBlockSizeByLabel();
		this.adjustLabelLocation();
		//blockLabel.setOpaque(true);
		this.add(blockLabel);
		
		// Temporary
		//this.setBorder(BorderFactory.createLineBorder(Color.black));
	}
	

	/** Getters and Setters **/

	public PortFD getLoopOutport() {
		return this.loopOutport;
	}
	public void setLoopOutport(PortFD p) {
		this.loopOutport = p;
	}

	/** override abstract method **/
	@Override
	protected void updatePorts() {
		// update inport
		if(this.outport != null) {
			this.outport.setPortLocation(new Point( this.getWidth() - 1, (int)this.getHeight()/2 ));
			this.outport.setSide("right");
		}
		// update outport
		if(this.inport != null) {
			this.inport.setPortLocation(new Point( Math.round(this.getWidth() - this.getHeight()/2), this.getHeight() - 1 ));
			this.inport.setSide("bottom");
		}
		// update loopPort
		if(this.loopOutport != null) {
			this.loopOutport.setPortLocation(new Point( Math.round(this.getWidth()/4), this.getHeight() ));
		}
	}
	
	@Override
	protected boolean shouldAddBlockDrag() {
		return false;
	}
	@Override
	protected boolean shouldAddLoopDrag() {
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
		// do nothing
	}
	
}
