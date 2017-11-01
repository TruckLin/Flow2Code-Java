package gui.mouselistener;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import graph.object.BlockFD;
import gui.commands.TranslateLocationCommand;
import gui.manager.UndoManager;

public class LoopDragListener extends MouseAdapter{
	private UndoManager undoManager;
	private BlockFD block;
	private int x;
	private int y;
	
	public LoopDragListener(UndoManager undoManager, BlockFD block) {
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
	public void mouseDragged(MouseEvent e) {
		//Testing
		//System.out.println("mouseDragged in loopDragListener is triggered.");
		
		int x2 = e.getX();
		int y2 = e.getY();
		int dx = x2 - x;
		int dy = y2 - y;
		
		TranslateLocationCommand tLC = new TranslateLocationCommand((BlockFD)block.getParent(), dx, dy);
		undoManager.execute(tLC);
	}
}
