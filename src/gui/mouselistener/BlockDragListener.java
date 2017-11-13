package gui.mouselistener;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import gui.commands.TranslateLocationCommand;
import gui.manager.UndoManager;
import gui.object.BlockFD;

public class BlockDragListener extends MouseAdapter{
	private UndoManager undoManager;
	private BlockFD block;
	private int x;
	private int y;
	
	public BlockDragListener(UndoManager undoManager, BlockFD block) {
		this.undoManager = undoManager;
		this.block = block;
	}
	
	/** Getters and Setters **/
	public UndoManager getUndoManager() {
		return this.undoManager;
	}
	
	@Override
	public void mousePressed(MouseEvent e) {
		//Testing
		System.out.println("mousePressed triggered.");
		
		this.x = e.getX();
		this.y = e.getY();
	}
	@Override
	public void mouseDragged(MouseEvent e) {
		//Testing
		System.out.println("mouseDragged triggered.");
		
		int x2 = e.getX();
		int y2 = e.getY();
		int dx = x2 - x;
		int dy = y2 - y;
		
		TranslateLocationCommand tLC = new TranslateLocationCommand(block, dx, dy);
		undoManager.execute(tLC);
	}
}
