package gui.object;

import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.Line2D;
import java.util.ArrayList;

import javax.swing.BorderFactory;

import gui.interfaces.WithInport;
import gui.manager.NameCounterManager;
import gui.manager.UndoManager;

public class BlockEndLOOP extends BlockFD implements WithInport{
	
	PortFD Inport = new PortFD(new Point(Math.round(this.getWidth()/2),0),"top");
	
	public BlockEndLOOP() {
		super(null);
		
		// setDefault bounds
		this.setBounds(0,80,14,14);
		//this.setBorder(BorderFactory.createLineBorder(Color.black));
		this.setOpaque(false);
	}
	
	/** Graphics setting **/
	@Override
	protected void paintComponent(Graphics g) {
		Graphics2D g2 = (Graphics2D)g;
		
        int x = (int)this.getLocation().getX();
        int y = (int)this.getLocation().getY();
        int width = this.getWidth();
        int height = this.getHeight();
        
        GradientPaint gradientGray = new GradientPaint(0,0,new Color(73,73,73),
        													width, height,new Color(200,200,200));
        g2.setPaint(gradientGray);
        
		g2.fillOval(0, 0, width, height);
    }
	
	/** interface functions **/
	@Override
	public PortFD getInport() {
		return this.Inport ;
	}

	@Override
	public void setInport(PortFD p) {
		this.Inport = p;
	}
	
	/** override abstract methods **/
	@Override
	public void setUndoManager(UndoManager undoManager) {
		this.undoManager = undoManager;
	}
	
	@Override
	protected void updatePorts() {
		// reset inport
		this.Inport.setPortLocation( new Point(Math.round(this.getWidth()/2),0) );
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
