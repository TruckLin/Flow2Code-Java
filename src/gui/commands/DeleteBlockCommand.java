package gui.commands;

import java.awt.event.MouseListener;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;

import org.json.JSONObject;

import gui.LinePopup;
import gui.interfaces.Command;
import gui.mouselistener.LineRightClickListener;
import gui.object.BlockFD;
import gui.object.CompositeBlockFD;
import gui.object.LineFD;
import gui.object.LineFD.BlockChangeListener;

public class DeleteBlockCommand implements Command{
	private BlockFD currentBlock;
	
	private BlockFD sourceBlock;
	private BlockFD terminalBlock;
	private LineFD line1; // Block1 ---- line1 ---- currentBlock ---- line2 ---- Bock3
	private LineFD line2;
	
	private LineFD newLine; // Block1 ---- newLine ---- Block2
	
	private CompositeBlockFD parentBlock;
	
	public DeleteBlockCommand(BlockFD block) {
		if(block.representCompositeBlock()) {
			this.currentBlock = (BlockFD)block.getParent();
			
			// Testing
			//System.out.println("currentBlock = " + currentBlock.toString());
		}else {
			this.currentBlock = block;
			
			// Testing
			//System.out.println("currentBlock = " + currentBlock.toString());
		}
		
		this.parentBlock = (CompositeBlockFD)this.currentBlock.getParent();
		
		ArrayList<LineFD> lineList = parentBlock.getLineList();
		
		for(LineFD currentLine : lineList) {
			if(currentLine.getSource().equals(currentBlock)) {
				this.line2 = currentLine;
				this.terminalBlock = currentLine.getTerminal(); 
			}
			if(currentLine.getTerminal().equals(currentBlock)) {
				this.line1 = currentLine;
				this.sourceBlock = currentLine.getSource();
			}
		}
	}
	
	@Override
	public void execute() {
		
		// remove currentBlock's model and modify the child of sourceModel.
		CommandUtilityFunctions.removeJSONObjectFromParentModel(parentBlock.getModel(), currentBlock.getModel(),
																	sourceBlock.getModel(), terminalBlock.getModel());
		// create a newLine
		this.newLine = new LineFD(sourceBlock, terminalBlock, line1.getStartPort(), line2.getEndPort());

		// remove currentBlock, line1 and line2.
		parentBlock.remove(currentBlock);
		parentBlock.removeLineFD(line1);
		parentBlock.removeLineFD(line2);
		
		// de-register line1 and line2 from sourceBlock and terminalBlock respectively
		sourceBlock.removePropertyChangeListener(line1.getBlockChangeListener());
		terminalBlock.removePropertyChangeListener(line2.getBlockChangeListener());
		
		// add the new line
		parentBlock.addLineFD(newLine);
		
		
		// Register listener of newLine for sourceBlock and terminalBlock
		sourceBlock.addPropertyChangeListener(newLine.getBlockChangeListener());
		terminalBlock.addPropertyChangeListener(newLine.getBlockChangeListener());
		
		parentBlock.setAppropriateBounds();
		parentBlock.repaint();
	}

	@Override
	public void undo() {
		// add back the deleted model
		CommandUtilityFunctions.addJSONObjectToParentModel(parentBlock.getModel(), currentBlock.getModel(),
												sourceBlock.getModel(), terminalBlock.getModel(),
												parentBlock, newLine); //***** this is going to cause bugs.*****//
		// add back the currentBlock, line1 and line2.
		parentBlock.add(currentBlock);
		parentBlock.addLineFD(line1);
		parentBlock.addLineFD(line2);
		
		// remove newLine
		parentBlock.removeLineFD(newLine);
		
		// de-register line from sourceBlock and terminalBlock.
		sourceBlock.removePropertyChangeListener(newLine.getBlockChangeListener());
		terminalBlock.removePropertyChangeListener(newLine.getBlockChangeListener());
		
		// register line1 and line2 for sourceBlock and terminalBlock respectively.
		sourceBlock.addPropertyChangeListener(line1.getBlockChangeListener());
		terminalBlock.addPropertyChangeListener(line2.getBlockChangeListener());
		
		parentBlock.setAppropriateBounds();
		parentBlock.repaint();
		
	}

	@Override
	public void redo() {
		// remove currentBlock's model and modify the child of sourceModel.
		CommandUtilityFunctions.removeJSONObjectFromParentModel(parentBlock.getModel(), currentBlock.getModel(),
																			sourceBlock.getModel(), terminalBlock.getModel());
		// remove currentBlock, line1 and line2.
		parentBlock.remove(currentBlock);
		parentBlock.removeLineFD(line1);
		parentBlock.removeLineFD(line2);
				
		// de-register line1 and line2 from sourceBlock and terminalBlock respectively
		sourceBlock.removePropertyChangeListener(line1.getBlockChangeListener());
		terminalBlock.removePropertyChangeListener(line2.getBlockChangeListener());
				
		// add the new line
		parentBlock.addLineFD(newLine);
				
		// Register listener of newLine for sourceBlock and terminalBlock
		sourceBlock.addPropertyChangeListener(newLine.getBlockChangeListener());
		terminalBlock.addPropertyChangeListener(newLine.getBlockChangeListener());
				
		parentBlock.setAppropriateBounds();
		parentBlock.repaint();
		
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
