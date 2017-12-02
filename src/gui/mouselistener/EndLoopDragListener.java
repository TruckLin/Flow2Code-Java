package gui.mouselistener;

import java.awt.Component;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import gui.commands.TranslateLocationCommand;
import gui.manager.UndoManager;
import gui.object.BlockFD;

public class EndLoopDragListener extends MouseAdapter{
	private UndoManager undoManager;
	private BlockFD block;
	private int x;
	private int y;
	
	public EndLoopDragListener(UndoManager undoManager, BlockFD block) {
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
		
		// For this kind of block, if the boundary is not touching parentBlock's bottom boundary,
		// we do not allow it to go further upward.
		Rectangle blockBound = block.getBounds();
		Rectangle parentBound = block.getParent().getBounds();
		double newMaxY = blockBound.getMaxY() + dy; // maximum y coordinate after translation.
		
		Component[] compList = block.getParent().getComponents();
		double parentMaxY = Double.MIN_VALUE;
		for(Component comp : compList) {
			if(comp == block) continue;
			else {
				if(parentMaxY < comp.getBounds().getMaxY())
					parentMaxY = comp.getBounds().getMaxY();
			}
		}
		
		if(newMaxY < parentMaxY){
			TranslateLocationCommand tLC = new TranslateLocationCommand(block, dx, 0);
			undoManager.execute(tLC);
		}else {
			TranslateLocationCommand tLC = new TranslateLocationCommand(block, dx, dy);
			undoManager.execute(tLC);
		}
		
		
		
		
	}
}