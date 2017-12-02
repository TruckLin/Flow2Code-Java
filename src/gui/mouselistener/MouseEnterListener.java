package gui.mouselistener;

import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.border.Border;

import gui.manager.UndoManager;
import gui.object.BlockFD;


// This listener is should be registered by BlockStartLOOP, BlockStartIF, maybe BlockSTART.
public class MouseEnterListener implements MouseListener{
	private BlockFD parentBlock;
	private BlockFD block;
	
	private Border originalBorder = null;
	private Border border = BorderFactory.createDashedBorder(Color.BLUE);
	private Color originalBackgroundColor = null;
	private Color backgroundColor = new Color(100,100,255);
	
	private boolean shouldChangeParentPanel = false;
	
	public MouseEnterListener(BlockFD block) {
		this.block = block;
	}
	
	/** Getters and Setters **/
	public Border getSpecialBorder() {
		return this.border;
	}
	public void setSpecialBorder(Border border) {
		this.border = border;
	}
	public Color getBackgroundColor() {
		 return this.backgroundColor;
	 }
	public void setBackgroundColor(Color color) {
		this.backgroundColor = color;
	}
	public boolean getShouldChangeParentBlock() {
		return this.shouldChangeParentPanel;
	}
	public void setSouldChangeParentBlock(boolean yy) {
		this.shouldChangeParentPanel = yy;
	}
	
	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		if(this.shouldChangeParentPanel) {
			this.originalBorder = ((JPanel)block.getParent()).getBorder();
			this.originalBackgroundColor = ((JPanel)block.getParent()).getBackground();
			((JPanel)block.getParent()).setBorder(border);
			((JPanel)block.getParent()).setBackground(backgroundColor);
		}else{
			this.originalBorder = block.getBorder();
			this.originalBackgroundColor = block.getBackground();
			block.setBorder(border);
			block.setBackground(backgroundColor);
		}
	}


	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		if(this.shouldChangeParentPanel) {
			((JPanel)block.getParent()).setBorder(originalBorder);
			((JPanel)block.getParent()).setBackground(originalBackgroundColor);
			
			this.originalBorder = null;
			this.originalBackgroundColor = null;
			
		}else{
			block.setBorder(originalBorder);
			block.setBackground(originalBackgroundColor);
			
			this.originalBorder = null;
			this.originalBackgroundColor = null;
		}
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

}
