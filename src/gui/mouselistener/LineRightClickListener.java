package gui.mouselistener;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;

import javax.swing.SwingUtilities;

import gui.manager.UndoManager;
import gui.object.BlockFD;
import gui.object.CompositeBlockFD;
import gui.object.LineFD;
import gui.popupMenu.LinePopup;

public class LineRightClickListener implements MouseListener, MouseMotionListener{
	private CompositeBlockFD compositeBlock;
	private ArrayList<LineFD> lineList;
	private LinePopup linePopup;
	
	public LineRightClickListener(CompositeBlockFD compositeBlock) {
		this.compositeBlock = compositeBlock;
		this.lineList = compositeBlock.getLineList();
	}
	
	/** Getter and Setters **/
	public LinePopup getLinePopup() {
		return this.linePopup;
	}
	
	
	/** MouseListener Interface **/
	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		if(SwingUtilities.isRightMouseButton(e)) {
			// Do some thing
			for(LineFD line : lineList) {
				if(line.isInRange(e.getPoint())) {
					linePopup = new LinePopup(compositeBlock, line);
					linePopup.show(e.getComponent(),e.getX(), e.getY());
					break;
				}
			}
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
		//Testging
		//System.out.println(this.compositeBlock.getClass().toString() + " MouseExit Triggered.");
		for(LineFD line : lineList) {	
				line.setHasBorder(false);	
		}
		this.compositeBlock.repaint();
	}
	
	/** MouseMotionsListener Interface **/
	@Override
	public void mouseDragged(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub
		// Testing
		//System.out.println("MouseMoved event : ");
		//System.out.println("e.getPoint() = " + e.getPoint().toString());
		boolean focused = false;
		for(LineFD line : lineList) {
			
			if(line.isInRange(e.getPoint()) && !focused) {
				line.setHasBorder(true);
				focused = true;
			}else {
				line.setHasBorder(false);
			}
		}
		this.compositeBlock.repaint();
	}
}
