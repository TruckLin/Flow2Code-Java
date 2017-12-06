package gui.object;

import java.awt.*;
import java.util.ArrayList;

import javax.swing.*;

import org.json.JSONArray;
import org.json.JSONObject;

import gui.BlockEditDialog;
import gui.DeclareEditDialog;
import gui.IfEditDialog;
import gui.manager.UndoManager;

public class BlockDECLARE extends OrdinaryBlockFD {
	private DeclareEditDialog editDialog;
	
	public BlockDECLARE(JSONObject model) {
		super(model);
		
		this.blockLabel.setText("Declare");
		this.adjustLabelSize();
		this.adjustBlockSizeByLabel();
		this.adjustLabelLocation();
		this.add(blockLabel);
		
		//Temporary
		this.setBorder(BorderFactory.createLineBorder(Color.black));
		
		//Testing
		//System.out.println("Constructor 1 of BlockDECLARE is called and N = " + N);

	}
	
	/** Getters and Setters **/
	public JSONArray getVariables() {
		return this.getModel().getJSONArray("Variables");
	}
	/** Override the abstract methods **/
	@Override
	public boolean representCompositeBlock() {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public void updateBlockContent() {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public BlockEditDialog getBlockEditDialog(UndoManager undoManager) {
		this.editDialog = new DeclareEditDialog(undoManager, this);
		return this.editDialog;
	}
	
	/** getters **/
	
	
	/** Setters **/
	

}
