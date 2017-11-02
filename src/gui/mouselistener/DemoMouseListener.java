package gui.mouselistener;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import gui.commands.TranslateLocationCommand;
import gui.manager.UndoManager;
import gui.object.BlockFD;

public class DemoMouseListener extends MouseAdapter{
	private UndoManager undoManager;
	private BlockFD block;
	private int x;
	private int y;
	
	
	public DemoMouseListener(UndoManager undoManager, BlockFD block) {
		this.undoManager = undoManager;
		this.block = block;
	}
	
	@Override
	public void mousePressed(MouseEvent e) {
		//Testing
		//System.out.println("mousePressed triggered.");
		
		this.x = e.getX();
		this.y = e.getY();
	}
	@Override
	public void mouseClicked(MouseEvent e) {
		//Testing
		//System.out.println("mouseClicked triggered.");
	}
	@Override
	public void mouseDragged(MouseEvent e) {
		//Testing
		//System.out.println("mouseDragged triggered.");
		
		int x2 = e.getX();
		int y2 = e.getY();
		int dx = x2 - x;
		int dy = y2 - y;
		
		TranslateLocationCommand tLC = new TranslateLocationCommand(block, dx, dy);
		undoManager.execute(tLC);
	}
	@Override
	public void mouseReleased(MouseEvent e) {
		//Testing
		//System.out.println("mouseReleased triggered.");
	}
	
	
}
