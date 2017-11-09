package gui.object;

import java.awt.Color;
import java.awt.Point;
import java.awt.Rectangle;
import java.beans.PropertyChangeListener;

import javax.swing.BorderFactory;
import javax.swing.JLabel;

import org.json.JSONObject;

import gui.BlockEditDialog;
import gui.WhileEditDialog;
import gui.manager.UndoManager;


public class BlockWHILE extends OrdinaryBlockFD {
	private BlockStartLOOP blockStartLOOP;
	
	private PropertyChangeListener listener = e -> resetOutInPorts();
	
	// Block defining variables
	private WhileEditDialog editDialog;
	
	public BlockWHILE(JSONObject model){
		super(model);
		if(!model.getString("Type").equals("While")) {
			System.out.println("Incompatible model with BlockWHILE.");
		}else {
			this.setOpaque(false); // we should always see through this while panel.
		
			//Temporary
			this.setBorder(BorderFactory.createLineBorder(Color.black));
		}
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
			resetOutInPorts();
		}
	}
	public String getExpression() {
		return this.getModel().getString("Expression");
	}
	
	/** EventHandling functions **/
	@Override
	public void updateBlock() {
		String displayString = ("While( " + this.getExpression() + " )");
		this.blockStartLOOP.getDisplayLabel().setText(displayString);
		
		//Testing
		System.out.println("blockStartLOOP's label's preferrable size = : " + 
								this.blockStartLOOP.getDisplayLabel().getPreferredSize());
		
		// blockStartLOOP may need to change size and location
		//this.blockStartLOOP.setAppropriateBounds();
		
		// update inports and outports in the UI
		resetOutInPorts();
	}
	@Override
	public BlockEditDialog getBlockEditDialog(UndoManager undoManager) {
		this.editDialog = new WhileEditDialog(undoManager, this);
		return this.editDialog;
	}
	
	/** Utility Functions **/
	public void resetOutInPorts() {
		Rectangle rec = this.getBlockStartLOOP().getBounds();
		Point outport = new Point( (int)Math.round(rec.getWidth())/4,(int)rec.getHeight());
		outport = new Point(blockStartLOOP.toContainerCoordinate(outport));
		this.setOutport(outport);

		Point inport = new Point( (int)Math.round(rec.getWidth())/2,0);
		inport = new Point(blockStartLOOP.toContainerCoordinate(inport));
		this.setInport(inport);
	}
	
}
