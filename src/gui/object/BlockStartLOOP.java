package gui.object;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Point;

import javax.swing.BorderFactory;
import javax.swing.JLabel;

import org.json.JSONObject;

import gui.mouselistener.MouseEnterListener;


public class BlockStartLOOP extends OrdinaryBlockFD{
	JLabel displayLabel;
	
	PortFD loopOutport;
	
	public BlockStartLOOP(JSONObject model) {
		super(model);
		this.setLayout(new FlowLayout());
		
		// set the specific outport for BlockStartLOOP
		this.outport = new PortFD(new Point( this.getWidth(), (int)this.getHeight()/2 ) , "right");
		
		// set the specific inport for BlockStartLOOP
		this.inport = new PortFD(new Point( Math.round(this.getWidth() - this.getHeight()/2), this.getHeight() ), "bottom");
		
		// set default loopOutport
		this.loopOutport = new PortFD(new Point( Math.round(this.getWidth()/4), this.getHeight() ), "bottom");
		
		// Set the location of BlockStartLOOP
		//this.setLocation(5, 5);
		
		// Add a listener that change the border of it's parent when mouse enter.
		MouseEnterListener mouseEnter = new MouseEnterListener(this);
		mouseEnter.setSouldChangeParentBlock(true);
		this.addMouseListener(mouseEnter);
		
		// Temporary
		this.displayLabel = new JLabel("StartLoop");
		this.add(this.displayLabel);
		this.displayLabel.setBounds(0,0,100,25);
		this.setBorder(BorderFactory.createLineBorder(Color.black));
	}
	
	
	/** Getters and Setters **/
	public JLabel getDisplayLabel() {
		return this.displayLabel;
	}
	public void setDiaplyLabel(JLabel temp) {
		if(temp != null) {
			this.remove(this.displayLabel);
			this.displayLabel = temp;
			this.add(temp);
		}
	}
	public PortFD getLoopOutport() {
		return this.loopOutport;
	}
	public void setLoopOutport(PortFD p) {
		this.loopOutport = p;
	}

	/** override abstract method **/
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
