package gui.object;

import java.awt.Color;
import java.awt.Component;
import java.awt.Point;
import java.awt.Rectangle;
import java.beans.PropertyChangeListener;

import javax.swing.BorderFactory;

import org.json.JSONObject;

import gui.BlockEditDialog;
import gui.interfaces.WithInport;
import gui.interfaces.WithOutport;
import gui.manager.UndoManager;

public class BlockFOR extends BlockLOOPFD{
	
	public BlockFOR(JSONObject model){
		super(model);
	}
	
	/** Getters and Setters **/	
	public String getInitialisation() {
		return this.getModel().getString("Initialisation");
	}
	public String getCondition() {
		return this.getModel().getString("Condition");
	}
	public String getStep() {
		return this.getModel().getString("Step");
	}
	
	/** EventHandling functions **/
	@Override
	public void updateBlockContent() {
		String displayString = "For( ";
		displayString = displayString + this.getInitialisation() + ", " + this.getCondition() + ", " + this.getStep() + " )";
		this.blockStartLOOP.getBlockLabel().setText(displayString);
		
		this.blockStartLOOP.adjustLabelSize();
		this.blockStartLOOP.adjustBlockSizeByLabel();
		this.blockStartLOOP.adjustLabelLocation();
		
		//Testing
		//System.out.println("blockStartLOOP's label's preferrable size = : " + 
		//						this.blockStartLOOP.getDisplayLabel().getPreferredSize());
		
		// blockStartLOOP may need to change size and location
		// this.blockStartLOOP.setAppropriateBounds();
	}
	
	
	/** Utility Functions **/
	
}