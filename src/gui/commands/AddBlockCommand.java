package gui.commands;

import gui.interfaces.Command;
import gui.manager.NameCounterManager;
import gui.object.BlockFD;
import gui.object.LineFD;

public class AddBlockCommand implements Command {
	private NameCounterManager nameManager;
	
	private LineFD line;
	private String type;
	
	private BlockFD block;
	
	private BlockFD parentBlock;
	
	public AddBlockCommand(NameCounterManager nameManager,LineFD line, String type) {
		this.nameManager = nameManager;
		this.line = line;
		this.type = type;
	}
	
	@Override
	public void execute() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void undo() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void redo() {
		// TODO Auto-generated method stub
		
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
