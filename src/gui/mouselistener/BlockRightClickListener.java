package gui.mouselistener;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.SwingUtilities;

import gui.manager.UndoManager;
import gui.object.BlockFD;
import gui.object.LineFD;
import gui.popupMenu.BlockPopup;
import gui.popupMenu.LinePopup;

public class BlockRightClickListener implements MouseListener{
	private UndoManager undoManager;
	private BlockFD block;
	private BlockPopup blockPopup;
	
	public BlockRightClickListener(UndoManager undoManager,BlockFD block) {
		this.undoManager = undoManager;
		this.block = block;
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
			blockPopup = new BlockPopup(undoManager, block);
			blockPopup.show(e.getComponent(),e.getX(), e.getY());
			
			//Testing
			//System.out.println(block.getModel().getString("Name"));
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
