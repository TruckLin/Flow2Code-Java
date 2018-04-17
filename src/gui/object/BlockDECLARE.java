package gui.object;

import java.awt.*;
import java.util.ArrayList;

import javax.swing.*;

import org.json.JSONArray;
import org.json.JSONObject;

import gui.editDialog.BlockEditDialog;
import gui.editDialog.DeclareEditDialog;
import gui.editDialog.IfEditDialog;
import gui.manager.UndoManager;

public class BlockDECLARE extends OrdinaryBlockFD {
	
	private DeclareEditDialog editDialog;
	private Color bgColor = new Color(255,214,153,255);
	public BlockDECLARE(JSONObject model) {
		super(model);
		
		this.updateBlockContent();
		blockLabel.setOpaque(false);
		this.setBackground(null);
		this.add(blockLabel);
		
		//Temporary
		//this.setBorder(BorderFactory.createLineBorder(Color.black));
		
		//this.setBorder(BorderFactory.createStrokeBorder(new BasicStroke(2.0f),borderColor));
		//this.setBorder(BorderFactory.createEtchedBorder(new Color(255,153,0),bgCOlor));

		//Testing
		//System.out.println("Constructor 1 of BlockDECLARE is called and N = " + N);

	}
	@Override
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D)g;
		g2.setColor(bgColor);
		g2.drawRoundRect(0, 0, this.getWidth(),this.getHeight(),20,20);
		g2.fillRoundRect(0, 0, this.getWidth(),this.getHeight(),20,20);
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
