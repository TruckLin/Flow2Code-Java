package gui.commands;

import graph.object.BlockFD;
import gui.interfaces.Command;

public class TranslateLocationCommand implements Command {
	private BlockFD block;
	private int dx;
	private int dy;

	public TranslateLocationCommand(BlockFD block, int dx, int dy) {
		this.block = block;
		this.dx = dx;
		this.dy = dy;
	}
	
	@Override
	public void execute() {
		// TODO Auto-generated method stub
		block.translateLocation(dx, dy);
		((BlockFD)block.getParent()).setAppropriateBounds();
		
	}

	@Override
	public void undo() {
		// TODO Auto-generated method stub
		block.translateLocation(-dx, -dy);
		((BlockFD)block.getParent()).setAppropriateBounds();
	}

	@Override
	public void redo() {
		// TODO Auto-generated method stub
		execute();
	}

	@Override
	public boolean isCollapsible(Command command) {
		// TODO Auto-generated method stub
		
		return false;
	}

	@Override
	public void collapse(Command comand) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return null;
	}

}
