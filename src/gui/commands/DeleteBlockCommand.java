package gui.commands;

import java.awt.event.MouseListener;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;

import org.json.JSONObject;

import gui.LinePopup;
import gui.interfaces.Command;
import gui.mouselistener.LineRightClickListener;
import gui.object.BlockFD;
import gui.object.LineFD;
import gui.object.LineFD.BlockChangeListener;
import saveload.SaveAndLoadManagerFD;

public class DeleteBlockCommand implements Command{
	BlockFD currentBlock;
	
	BlockFD sourceBlock;
	BlockFD terminalBlock;
	LineFD line1; // Block1 ---- line1 ---- currentBlock ---- line2 ---- Bock3
	LineFD line2;
	
	LineFD newLine; // Block1 ---- newLine ---- Block2
	
	BlockFD parentBlock;
	
	public DeleteBlockCommand(BlockFD block) {
		
		boolean isComposite = block.getModel().getString("Type").equals("StartIf") || block.getModel().getString("Type").equals("StartLoop");
		
		// Testing
		//System.out.println("isComposite = " + isComposite);
		
		if(isComposite) {
			this.currentBlock = (BlockFD)block.getParent();
			
			// Testing
			//System.out.println("currentBlock = " + currentBlock.toString());
		}else {
			this.currentBlock = block;
			
			// Testing
			//System.out.println("currentBlock = " + currentBlock.toString());
		}
		
		PropertyChangeListener[] listOfPropertyListeners = this.currentBlock.getPropertyChangeSupport().getPropertyChangeListeners();
		//Testing
		//System.out.println("length of listeners = " + listOfPropertyListeners.length);
		
		for(int i = 0; i < listOfPropertyListeners.length; i++) {
			
			//System.out.println(i + "th listener is BlockChangeListener:" + (listOfPropertyListeners[i] instanceof BlockChangeListener));
			
			if(listOfPropertyListeners[i] instanceof BlockChangeListener) {
				LineFD tempLine = ((BlockChangeListener)listOfPropertyListeners[i]).getOwnerLine();
				if( tempLine.getTerminal().getModel().getString("Name").equals(currentBlock.getModel().getString("Name"))) {
					this.line1 = ((BlockChangeListener)listOfPropertyListeners[i]).getOwnerLine();
				}
				if(tempLine.getSource().getModel().getString("Name").equals(currentBlock.getModel().getString("Name"))) {
					this.line2 = ((BlockChangeListener)listOfPropertyListeners[i]).getOwnerLine();
				}
				//Testing
				//System.out.println("TerminalName of line" + i + " : " + tempLine.getTerminal().getModel().getString("Name"));
				//System.out.println("SourceName of line" + i + " : " + tempLine.getSource().getModel().getString("Name"));
			}
		}
		
		
		
		this.sourceBlock = line1.getSource();
		this.terminalBlock = line2.getTerminal();
		
		this.parentBlock = (BlockFD)this.currentBlock.getParent();
	}
	
	@Override
	public void execute() {
		
		// remove currentBlock's model and modify the child of sourceModel.
		CommandUtilityFunctions.removeJSONObjectFromParentModel(parentBlock.getModel(), currentBlock.getModel(),
																	sourceBlock.getModel(), terminalBlock.getModel());
		// create a newLine
		this.newLine = new LineFD(sourceBlock, terminalBlock, line1.getStartPoint(), line2.getEndPoint());
		// attach listener to the line.
		MouseListener[] listenerList = line1.getMouseListeners();
		MouseListener temp = listenerList[0];
		LinePopup linePopup = null;
		if(temp instanceof LineRightClickListener) {
			linePopup = ((LineRightClickListener)temp).getLinePopup();
		}else {
			System.err.println("AddBlockCommand : LineRightClickListener not found when adding listeners.");
		}
		SaveAndLoadManagerFD.attachMouseListenersToLine(newLine,  linePopup);
		
		// remove currentBlock, line1 and line2.
		parentBlock.remove(currentBlock);
		parentBlock.remove(line1);
		parentBlock.remove(line2);
		
		// de-register line1 and line2 from sourceBlock and terminalBlock respectively
		sourceBlock.removePropertyChangeListener(line1.getBlockChangeListener());
		terminalBlock.removePropertyChangeListener(line2.getBlockChangeListener());
		
		// add the new line
		parentBlock.add(newLine);
		
		
		// Register listener of newLine for sourceBlock and terminalBlock
		sourceBlock.addPropertyChangeListener(newLine.getBlockChangeListener());
		terminalBlock.addPropertyChangeListener(newLine.getBlockChangeListener());
		
		parentBlock.setAppropriateBounds();
		
	}

	@Override
	public void undo() {
		// add back the deleted model
		CommandUtilityFunctions.addJSONObjectToParentModel(parentBlock.getModel(), currentBlock.getModel(),
												sourceBlock.getModel(), terminalBlock.getModel(),
												parentBlock, newLine); //***** this is going to cause bugs.*****//
		// add back the currentBlock, line1 and line2.
		parentBlock.add(currentBlock);
		parentBlock.add(line1);
		parentBlock.add(line2);
		
		// remove newLine
		parentBlock.remove(newLine);
		
		// de-register line from sourceBlock and terminalBlock.
		sourceBlock.removePropertyChangeListener(newLine.getBlockChangeListener());
		terminalBlock.removePropertyChangeListener(newLine.getBlockChangeListener());
		
		// register line1 and line2 for sourceBlock and terminalBlock respectively.
		sourceBlock.addPropertyChangeListener(line1.getBlockChangeListener());
		terminalBlock.addPropertyChangeListener(line2.getBlockChangeListener());
		
		parentBlock.setAppropriateBounds();
		
	}

	@Override
	public void redo() {
		// remove currentBlock's model and modify the child of sourceModel.
		CommandUtilityFunctions.removeJSONObjectFromParentModel(parentBlock.getModel(), currentBlock.getModel(),
																			sourceBlock.getModel(), terminalBlock.getModel());
		// remove currentBlock, line1 and line2.
		parentBlock.remove(currentBlock);
		parentBlock.remove(line1);
		parentBlock.remove(line2);
				
		// de-register line1 and line2 from sourceBlock and terminalBlock respectively
		sourceBlock.removePropertyChangeListener(line1.getBlockChangeListener());
		terminalBlock.removePropertyChangeListener(line2.getBlockChangeListener());
				
		// add the new line
		parentBlock.add(newLine);
				
		// Register listener of newLine for sourceBlock and terminalBlock
		sourceBlock.addPropertyChangeListener(newLine.getBlockChangeListener());
		terminalBlock.addPropertyChangeListener(newLine.getBlockChangeListener());
				
		parentBlock.setAppropriateBounds();
		
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
