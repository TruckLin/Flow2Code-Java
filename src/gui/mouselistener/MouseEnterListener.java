package gui.mouselistener;

import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

import gui.manager.UndoManager;
import gui.object.BlockFD;


// This listener is should be registered by BlockStartLOOP, BlockStartIF, maybe BlockSTART.
public class MouseEnterListener implements MouseListener{
	private BlockFD block;
	
	public MouseEnterListener(BlockFD block) {
		this.block = block;
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		((JPanel)block.getParent()).setBorder(BorderFactory.createDashedBorder(Color.BLUE));
	}


	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		((JPanel)block.getParent()).setBorder(null);
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
