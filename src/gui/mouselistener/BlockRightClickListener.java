package gui.mouselistener;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.SwingUtilities;

import gui.BlockPopup;
import gui.LinePopup;
import gui.object.BlockFD;
import gui.object.LineFD;

public class BlockRightClickListener implements MouseListener{
	BlockPopup blockPopup;
	public BlockRightClickListener(BlockPopup blockPopup) {
		this.blockPopup = blockPopup;
	}
	
	/** Getter and Setters **/
	public BlockPopup getBlockPopup() {
		return this.blockPopup;
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		if(SwingUtilities.isRightMouseButton(e)) {
			// Do some thing
			blockPopup.setBlockFD((BlockFD)e.getComponent());
			blockPopup.show(e.getComponent(),e.getX(), e.getY());
		}
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
}
