package gui.commands;

import java.awt.event.MouseListener;

import org.json.JSONArray;
import org.json.JSONObject;

import gui.BlockPopup;
import gui.LinePopup;
import gui.interfaces.Command;
import gui.interfaces.WithOutport;
import gui.manager.NameCounterManager;
import gui.manager.UndoManager;
import gui.object.BlockFD;
import gui.object.BlockIF;
import gui.object.LineFD;
import gui.object.OrdinaryBlockFD;
import saveload.SaveAndLoadManagerFD;
import gui.mouselistener.BlockDragListener;
import gui.mouselistener.LineRightClickListener;

public class AddBlockCommand implements Command {
	private UndoManager undoManager;
	private NameCounterManager nameManager;
	
	private LineFD line;
	private String type;
	
	private OrdinaryBlockFD emptyBlock; // the new block.
	private BlockFD sourceBlock;
	private BlockFD terminalBlock;
	private LineFD line1; // line from sourceBlock to emptyBlock.
	private LineFD line2; // line from emptyBlock to terminalBlock.
	
	
	private BlockFD parentBlock;
	private JSONObject parentModel;
	
	public AddBlockCommand(UndoManager undoManager,NameCounterManager nameManager,LineFD line, String type) {
		this.undoManager = undoManager;
		this.nameManager = nameManager;
		this.line = line;
		this.type = type;
		this.parentBlock = (BlockFD)line.getParent();
		this.parentModel = parentBlock.getModel();

	}
	
	@Override
	public void execute() {
		// generate block with it's model
		this.emptyBlock = (OrdinaryBlockFD)CommandUtilityFunctions.generateEmptyBlock(this.nameManager, this.type);
		JSONObject emptyModel = emptyBlock.getModel();
		
		this.sourceBlock = line.getSource();
		this.terminalBlock = line.getTerminal();
		JSONObject sourceModel = line.getSource().getModel();
		JSONObject terminalModel = line.getTerminal().getModel();
		
		/** ================================= put emptyModel at the right place. =====================================**/
		CommandUtilityFunctions.addJSONObjectToParentModel(parentModel, emptyBlock.getModel(),
												sourceBlock.getModel(), terminalBlock.getModel(), parentBlock, this.line);
//Testing
/*		if(parentModel.has("Members")) {
			parentModel.append("Members", emptyModel);
			sourceModel.put("Child", emptyModel.getString("Name"));
			emptyModel.put("Child",terminalModel.getString("Name"));
		}else if(parentModel.getString("Type").equals("If")) {
			// the parent block is BlockIF.
			
			// A test to check if wither source or terminal is in trueMembers. 
			boolean inTrueMembers = false;
			JSONArray trueModelList = parentModel.getJSONArray("TrueMembers");
			for(int i = 0; i < trueModelList.length(); i++) {
				String tempName = trueModelList.getJSONObject(i).getString("Name");
				if(tempName.equals(sourceModel.getString("Name")) || tempName.equals(terminalModel.getString("Name"))) {
					inTrueMembers = true;
					break;
				}
			}
			boolean inFalseMembers = false;
			JSONArray falseModelList = parentModel.getJSONArray("FalseMembers");
			for(int i = 0; i < falseModelList.length(); i++) {
				String tempName = falseModelList.getJSONObject(i).getString("Name");
				if(tempName.equals(sourceModel.getString("Name")) || tempName.equals(terminalModel.getString("Name"))) {
					inFalseMembers = true;
					break;
				}
			}
			
			if(inTrueMembers) {
				parentModel.append("TrueMembers", emptyModel);
				sourceModel.put("Child", emptyModel.getString("Name"));
				emptyModel.put("Child",terminalModel.getString("Name"));
			}else if(inFalseMembers){
				parentModel.append("TrueMembers", emptyModel);
				sourceModel.put("Child", emptyModel.getString("Name"));
				emptyModel.put("Child",terminalModel.getString("Name"));
			}else {
				if( ((BlockIF)this.parentBlock).getTrueLine().equals(this.line) ) {
					parentModel.append("TrueMembers", emptyModel);
					sourceModel.put("Child", emptyModel.getString("Name"));
					emptyModel.put("Child",terminalModel.getString("Name"));
				}else if( ((BlockIF)this.parentBlock).getFalseLine().equals(this.line) ) {
					parentModel.append("FalseMembers", emptyModel);
					sourceModel.put("Child", emptyModel.getString("Name"));
					emptyModel.put("Child",terminalModel.getString("Name"));
				}else {
					System.err.println("Something unexpected happened in AddBlockCommand :\n add block within BlockIF, insert model section");
				}
			}
			
		}else {
			System.err.println("Parent Model has no key called Members and it's not If.\n Detail:" + parentModel.toString(10));
		}
*/
		/** ========================================================================================================= **/
		
		
		// Add the actual block.
		parentBlock.add(emptyBlock);
		emptyBlock.setLocation(line.getCentreOnPanel());
		sourceBlock.removePropertyChangeListener(line.getBlockChangeListener());
		terminalBlock.removePropertyChangeListener(line.getBlockChangeListener());
		
		this.line1 = new LineFD(sourceBlock, emptyBlock, line.getStartPoint(), emptyBlock.toContainerCoordinate(emptyBlock.getInport()));
		this.line2 = new LineFD(emptyBlock, terminalBlock,  emptyBlock.toContainerCoordinate(emptyBlock.getOutport()), line.getEndPoint());
		
		parentBlock.add(line1);
		parentBlock.add(line2);
		
		// attach listeners to lines and emptyBlock
		// input line now has only right Click Listeners
		MouseListener[] listenerList = line.getMouseListeners();
		MouseListener temp = listenerList[0];
		LinePopup linePopup = null;
		if(temp instanceof LineRightClickListener) {
			linePopup = ((LineRightClickListener)temp).getLinePopup();
		}else {
			System.err.println("AddBlockCommand : LineRightClickListener not found when adding listeners.");
		}
		SaveAndLoadManagerFD.attachMouseListenersToLine(line1,  linePopup);
		SaveAndLoadManagerFD.attachMouseListenersToLine(line2,  linePopup);
		SaveAndLoadManagerFD.attachMouseListenersToAllLines(emptyBlock, linePopup); // Make sure lines generate in emptyBlock also have listeners attached.
		
		// add listeners to the blocks.
		// just use a new blockPopup should not cause any problem.
		BlockPopup blockPopup = new BlockPopup(undoManager);
		SaveAndLoadManagerFD.attachMouseListenersToBlock(undoManager, emptyBlock, blockPopup);
		
		//Testing
		//System.out.println("AddBlockCommand is called.");
		
		// remove the current line
		parentBlock.remove(line);
		
		// allow parent block to update.
		parentBlock.setAppropriateBounds();
	}

	@Override
	public void undo() {
		
		/** Remove the model we inserted into parent model **/
		// Change the child name of sourceModel to terminalModel's name.
		CommandUtilityFunctions.removeJSONObjectFromParentModel(parentBlock.getModel(),emptyBlock.getModel(), 
																sourceBlock.getModel(), terminalBlock.getModel());
		
		// Remove the block and lines.
		parentBlock.remove(emptyBlock);
		parentBlock.remove(line1);
		parentBlock.remove(line2);
		
		// remove listeners emptyBlock registered by sourceBlock and terminalBlock.
		sourceBlock.removePropertyChangeListener(line1.getBlockChangeListener());
		terminalBlock.removePropertyChangeListener(line2.getBlockChangeListener());
		
		// Add back the original line.
		parentBlock.add(line);
		
		// Register listener of line back into sourceBlock and terminalBlock
		sourceBlock.addPropertyChangeListener(line.getBlockChangeListener());
		terminalBlock.addPropertyChangeListener(line.getBlockChangeListener());
				
		// allow parent block to update.
		parentBlock.setAppropriateBounds();
	}

	@Override
	public void redo() {
		/** Add back the model. **/
		CommandUtilityFunctions.addJSONObjectToParentModel(parentModel, emptyBlock.getModel(),
				sourceBlock.getModel(), terminalBlock.getModel(), parentBlock, this.line);
		
		
		// Add back the emptyBlock.
		parentBlock.add(emptyBlock);
		//emptyBlock.setLocation(line.getCentreOnPanel()); // location should be the same.
		
		// de-register the listeners of the sourceBlock and terminalBlock from line.
		sourceBlock.removePropertyChangeListener(line.getBlockChangeListener());
		terminalBlock.removePropertyChangeListener(line.getBlockChangeListener());
		
		// Add back line1 and line2
		parentBlock.add(line1);
		parentBlock.add(line2);
		
		// register listeners of sourceBlock and terminalBlock
		sourceBlock.addPropertyChangeListener(line1.getBlockChangeListener());
		terminalBlock.addPropertyChangeListener(line2.getBlockChangeListener());
				
		// remove original line
		parentBlock.remove(line);

		// allow parent block to update.
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
