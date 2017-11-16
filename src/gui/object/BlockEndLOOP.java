package gui.object;

import java.awt.Color;
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
	
	Point Inport;
	
	public BlockEndLOOP() {
		super(null);
		
		// setDefault bounds
		this.setBounds(0,80,25,25);
		this.setBorder(BorderFactory.createLineBorder(Color.black));
		this.setOpaque(false);
		
		// set default inport
		this.setInport(new Point(Math.round(this.getWidth()/2),0));
		
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
	
	/** interface functions **/
	@Override
	public Point getInport() {
		return this.Inport ;
	}

	@Override
	public void setInport(Point p) {
		this.Inport = p;
	}
	
	/** override abstract methods **/
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
