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
		
		this.updateBlockContent();
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
		JSONArray variables = this.getModel().getJSONArray("Variables");
	
		String temp = "<html>Declare : ";
		for(int i = 0 ; i < variables.length(); i++) {
			if(i==0) {temp = temp + "<ul>";}
			JSONObject currentVariable = variables.getJSONObject(i);
			temp = temp + "<li>"+currentVariable.getString("DataType") + "  ";
			temp = temp + currentVariable.getString("VariableName") + "  ";
			if(currentVariable.getBoolean("IsArray")) {
				temp = temp + "is an array of size " + currentVariable.get("Size");
			}
			if(i < (variables.length() - 1)) {
				temp = temp + "</li>";
			}
		}
		
	
	/*
		// without html:
		String temp = "Declare : ";
		for(int i = 0 ; i < variables.length(); i++) {
			JSONObject currentVariable = variables.getJSONObject(i);
			temp = temp + currentVariable.getString("DataType") + "  ";
			temp = temp + currentVariable.getString("VariableName") + "  ";
			if(currentVariable.getBoolean("IsArray")) {
				temp = temp + "is an array of size " + currentVariable.get("Size");
			}
			if(i < variables.length() - 1) {
				temp = temp + "\n";
			}
		}
	*/
		this.blockLabel.setText(temp);
		
		this.adjustLabelSize();
		this.adjustBlockSizeByLabel();
		this.adjustLabelLocation();
		if(this.getParent() instanceof CompositeBlockFD) {
			((CompositeBlockFD)this.getParent()).setAppropriateBounds();
		}
	}
	
	@Override
	public BlockEditDialog getBlockEditDialog(UndoManager undoManager) {
		this.editDialog = new DeclareEditDialog(undoManager, this);
		return this.editDialog;
	}
	
	/** getters **/
	
	
	/** Setters **/
	

}
