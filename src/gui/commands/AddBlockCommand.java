package gui.commands;

import java.awt.Point;

import org.json.JSONObject;

import gui.interfaces.Command;
import gui.interfaces.WithOutport;
import gui.manager.NameCounterManager;
import gui.manager.UndoManager;
import gui.object.BlockFD;
import gui.object.CompositeBlockFD;
import gui.object.LineFD;
import strategy.generator.BlockGenerator;
import strategy.generator.ModelGenerator;
import gui.interfaces.*;

public class AddBlockCommand implements Command {
	private UndoManager undoManager;
	private NameCounterManager nameManager;

	private LineFD line;
	private String type;
	
	private BlockFD emptyBlock; // the new block.
	private JSONObject emptyModel;
	private BlockFD sourceBlock;
	private BlockFD terminalBlock;
	private LineFD line1; // line from sourceBlock to emptyBlock.
	private LineFD line2; // line from emptyBlock to terminalBlock.
	
	private ModelGenerator modelGenerator;
	private BlockGenerator blockGenerator;
	
	private BlockFD parentBlock;
	private JSONObject parentModel;
	
	public AddBlockCommand(CompositeBlockFD parentBlock,LineFD line, String type) {
		this.undoManager = parentBlock.getUndoManager();
		this.nameManager = parentBlock.getNameCounterManager();
		this.parentBlock = parentBlock;
		this.parentModel = parentBlock.getModel();
		this.line = line;
		this.type = type;
		
		this.modelGenerator = new ModelGenerator(this.nameManager);
		this.blockGenerator = new BlockGenerator();
	}
	
	@Override
	public void execute() {
		// generate block with it's model
		this.emptyModel = modelGenerator.generate(this.type);
		this.emptyBlock = blockGenerator.generate(emptyModel, null);
		
		this.sourceBlock = line.getSource();
		this.terminalBlock = line.getTerminal();
		
		this.emptyBlock.zoom(this.parentBlock.getCurrentZoomRatio());
	
		/*this.emptyBlock.setCurrentZoomRatio(this.parentBlock.getCurrentZoomRatio());
		int width = (int)Math.round(this.emptyBlock.getWidth()*this.emptyBlock.getCurrentZoomRatio());
		int height = (int)Math.round(this.emptyBlock.getHeight()*this.emptyBlock.getCurrentZoomRatio());
		this.emptyBlock.setBounds(0,0,width,height);*/
		
		/** ================================= put emptyModel at the right place. =====================================**/
		CommandUtilityFunctions.addJSONObjectToParentModel(parentModel, emptyBlock.getModel(),
												sourceBlock.getModel(), terminalBlock.getModel(), parentBlock, this.line);

		/** ========================================================================================================= **/
		
		
		// Add the actual block.
		parentBlock.add(emptyBlock);
		Point p = line.getCentrePoint();
		p.setLocation(p.getX() - (emptyBlock.getWidth()/2), p.getY() - (emptyBlock.getHeight()/2));
		emptyBlock.setLocation(p);
		sourceBlock.removePropertyChangeListener(line.getBlockChangeListener());
		terminalBlock.removePropertyChangeListener(line.getBlockChangeListener());
		
		this.line1 = new LineFD(sourceBlock, emptyBlock, line.getStartPort(), ((WithInport)emptyBlock).getInport());
		this.line2 = new LineFD(emptyBlock, terminalBlock,  ((WithOutport)emptyBlock).getOutport(), line.getEndPort());
		
		((CompositeBlockFD)parentBlock).addLineFD(line1);
		((CompositeBlockFD)parentBlock).addLineFD(line2);
		
		// attach undoManager and nameCounterManager
		emptyBlock.setUndoManager(undoManager);
		emptyBlock.setNameCounterManager(nameManager);
		
		// Testing
		//System.out.println(nameManager.getCurrentCount() + "");
		
		// attach listeners to emptyBlock
		emptyBlock.addVariousMouseListeners();
		
		// set the languaeBundle
		emptyBlock.setLanguageBundle(parentBlock.getLanguageBundle());
		
		//Testing
		//System.out.println("AddBlockCommand is called.");
		
		// remove the current line
		((CompositeBlockFD)parentBlock).removeLineFD(line);
		
		// tell parentBlock to generateLineSegements for lineList.
		((CompositeBlockFD)parentBlock).generateLineSegmentsForAllLines();
		
		// allow parent block to update.
		((CompositeBlockFD)parentBlock).setAppropriateBounds();
		parentBlock.repaint();
	}

	@Override
	public void undo() {
		
		/** Remove the model we inserted into parent model **/
		// Change the child name of sourceModel to terminalModel's name.
		CommandUtilityFunctions.removeJSONObjectFromParentModel(parentBlock.getModel(),emptyBlock.getModel(), 
																sourceBlock.getModel(), terminalBlock.getModel());
		
		// Remove the block and lines.
		parentBlock.remove(emptyBlock);
		((CompositeBlockFD)parentBlock).removeLineFD(line1);
		((CompositeBlockFD)parentBlock).removeLineFD(line2);
		
		// remove listeners emptyBlock registered by sourceBlock and terminalBlock.
		sourceBlock.removePropertyChangeListener(line1.getBlockChangeListener());
		terminalBlock.removePropertyChangeListener(line2.getBlockChangeListener());
		
		// Add back the original line.
		((CompositeBlockFD)parentBlock).addLineFD(line);
		
		// Register listener of line back into sourceBlock and terminalBlock
		sourceBlock.addPropertyChangeListener(line.getBlockChangeListener());
		terminalBlock.addPropertyChangeListener(line.getBlockChangeListener());
				
		// allow parent block to update.
		((CompositeBlockFD)parentBlock).setAppropriateBounds();
		parentBlock.repaint();
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
		((CompositeBlockFD)parentBlock).addLineFD(line1);
		((CompositeBlockFD)parentBlock).addLineFD(line2);
		
		// register listeners of sourceBlock and terminalBlock
		sourceBlock.addPropertyChangeListener(line1.getBlockChangeListener());
		terminalBlock.addPropertyChangeListener(line2.getBlockChangeListener());
				
		// remove original line
		((CompositeBlockFD)parentBlock).removeLineFD(line);

		// allow parent block to update.
		((CompositeBlockFD)parentBlock).setAppropriateBounds();
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
