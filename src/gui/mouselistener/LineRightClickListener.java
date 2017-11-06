package gui.mouselistener;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.SwingUtilities;

import gui.LinePopup;
import gui.manager.UndoManager;
import gui.object.LineFD;

public class LineRightClickListener implements MouseListener{
	LinePopup linePopup;
	public LineRightClickListener(LinePopup linePopup) {
		this.linePopup = linePopup;
	}
	
	/** Getter and Setters **/
	public LinePopup getLinePopup() {
		return this.linePopup;
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		if(SwingUtilities.isRightMouseButton(e)) {
			// Do some thing
			linePopup.setLine((LineFD)e.getComponent());
			linePopup.show(e.getComponent(),e.getX(), e.getY());
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
