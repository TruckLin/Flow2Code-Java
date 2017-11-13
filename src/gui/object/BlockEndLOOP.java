package gui.object;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;

import javax.swing.BorderFactory;

import gui.interfaces.WithInport;
import gui.manager.UndoManager;

public class BlockEndLOOP extends BlockFD implements WithInport{
	
	Point Inport;
	
	public BlockEndLOOP() {
		super(null);
		
		// setDefault bounds
		this.setBounds(0,80,25,25);
		this.setBorder(BorderFactory.createLineBorder(Color.black));
		
		// set default inport
		this.setInport(new Point(Math.round(this.getWidth()/2),0));
		
	}
	
	/** Graphics setting **/
	protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        int x = (int)this.getLocation().getX();
        int y = (int)this.getLocation().getY();
        int width = this.getWidth();
        int height = this.getHeight();
        
        g.setColor(Color.yellow);
        g.fillOval(x, y, width, height);
    }

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
