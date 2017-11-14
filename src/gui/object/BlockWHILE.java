package gui.object;

import java.awt.Color;
import java.awt.Component;
import java.awt.Point;
import java.awt.Rectangle;
import java.beans.PropertyChangeListener;

import javax.swing.BorderFactory;
import javax.swing.JLabel;

import org.json.JSONObject;

import gui.BlockEditDialog;
import gui.WhileEditDialog;
import gui.interfaces.WithInport;
import gui.interfaces.WithOutport;
import gui.manager.UndoManager;


public class BlockWHILE extends OrdinaryCompositeBlockFD{
	private BlockStartLOOP blockStartLOOP;
	private BlockEndLOOP blockEndLOOP;
	
	// This listener listen to the change in bounds and move BlockEndLOOP to the correct place.
	private PropertyChangeListener MoveBlockEndLoopListener = 
			e -> {  // Find out the maximum Y coordinate without BlockEndLOOP.
					Component[] compList = BlockWHILE.this.getComponents();
					double parentMaxY = Double.MIN_VALUE;
					for(Component comp : compList) {
						if(comp == blockEndLOOP) continue;
						else {
							if(parentMaxY < comp.getBounds().getMaxY())
								parentMaxY = comp.getBounds().getMaxY();
						}
					}
					if(blockEndLOOP.getBounds().getMaxY() < parentMaxY) {
						int h = BlockWHILE.this.getHeight() - blockEndLOOP.getHeight();
						blockEndLOOP.setLocation((int)blockEndLOOP.getLocation().getX(),h);
					}
					
					//Testing
					//System.out.println("MoveBlockEndLoopListener triggered.");
					//System.out.println("height = " + h);
					
				};
	private WhileEditDialog editDialog;
	
	public BlockWHILE(JSONObject model){
		super(model);
		
		this.setOpaque(false); // we should always see through this while panel.
		
		//Temporary
		this.setBorder(BorderFactory.createLineBorder(Color.black));
		this.setBounds(0,0,100,80); // initial bounds
		
		// Add listener that change the position of BlockEndLOOP, order is important,
		// it needs to be put after setBounds or any setters of the component.
		this.addPropertyChangeListener(MoveBlockEndLoopListener);
	}
	
	/** Getters and Setters **/
	public BlockStartLOOP getBlockStartLOOP() {
		return this.blockStartLOOP;
	}
	public void setBlockStartLOOP(BlockStartLOOP comp) {
		if (this.blockStartLOOP != null) {
			// disconnect from previous model
			this.blockStartLOOP.removePropertyChangeListener(listener);
		}
		this.blockStartLOOP = comp;
		if (this.blockStartLOOP != null) {
			// connect to new model
			this.blockStartLOOP.addPropertyChangeListener(listener);
			
			String displayString = ("While( " + this.getExpression() + " )");
			this.blockStartLOOP.getDisplayLabel().setText(displayString);
			
			
			
			// initialize inports and outports in the UI
			resetInport();
		}
	}
	public BlockEndLOOP getBlockEndLOOP() {
		return this.blockEndLOOP;
	}
	public void setBlockEndLOOP(BlockEndLOOP comp) {
		if (this.blockEndLOOP != null) {
			// disconnect from previous model
			this.blockEndLOOP.removePropertyChangeListener(listener);
		}
		this.blockEndLOOP = comp;
		if (this.blockEndLOOP != null) {
			// connect to new model
			this.blockEndLOOP.addPropertyChangeListener(listener);

			// initialize fields in the UI
			resetOutport();
		}
	}
	
	public String getExpression() {
		return this.getModel().getString("Expression");
	}
	
	/** EventHandling functions **/
	@Override
	public void updateBlockContent() {
		String displayString = ("While( " + this.getExpression() + " )");
		this.blockStartLOOP.getDisplayLabel().setText(displayString);
		
		//Testing
		System.out.println("blockStartLOOP's label's preferrable size = : " + 
								this.blockStartLOOP.getDisplayLabel().getPreferredSize());
		
		// blockStartLOOP may need to change size and location
		//this.blockStartLOOP.setAppropriateBounds();
		
		// update inports and outports in the UI
		resetInOutPorts();
	}
	
	@Override
	public BlockEditDialog getBlockEditDialog(UndoManager undoManager) {
		this.editDialog = new WhileEditDialog(undoManager, this);
		return this.editDialog;
	}
	
	/** Utility Functions **/
	@Override
	public void resetInOutPorts() {
		resetInport();
		resetOutport();
	}

	@Override
	public void resetInport() {
		Rectangle rec = blockStartLOOP.getBounds();
		Point inport = new Point( (int)Math.round(rec.getWidth())/2,0);
		inport = new Point(blockStartLOOP.toContainerCoordinate(inport));
		this.setInport(inport);
		
	}

	@Override
	public void resetOutport() {
		Rectangle rec = blockEndLOOP.getBounds();
		Point outport = new Point( (int)Math.round(rec.getWidth())/2,(int)rec.getHeight());
		outport = new Point(blockEndLOOP.toContainerCoordinate(outport));
		this.setOutport(outport);
	}

	
}
