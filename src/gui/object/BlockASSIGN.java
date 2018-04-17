package gui.object;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.beans.PropertyChangeListener;

import javax.swing.BorderFactory;
import javax.swing.JLabel;

import org.json.JSONArray;
import org.json.JSONObject;

import gui.editDialog.AssignEditDialog;
import gui.editDialog.BlockEditDialog;
import gui.editDialog.DeclareEditDialog;
import gui.manager.UndoManager;

public class BlockASSIGN extends OrdinaryBlockFD{
	
	private AssignEditDialog editDialog;
	private Color bgColor = new Color(170,255,128,255);
	public BlockASSIGN(JSONObject model) {
		super(model);

		this.updateBlockContent();
		this.adjustLabelSize();
		this.adjustBlockSizeByLabel();
		this.adjustLabelLocation();
		blockLabel.setOpaque(false);
		this.setBackground(null);
		this.add(blockLabel);
		
		//Temporary
		//this.setBorder(BorderFactory.createLineBorder(Color.black));
		
		//Testing
		//System.out.println("Constructor 1 of BlockDECLARE is called and N = " + N);

	}

	/** Override the abstract methods **/
	@Override
	public boolean representCompositeBlock() {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D)g;
		g2.setColor(bgColor);
		g2.drawRoundRect(0, 0, this.getWidth(),this.getHeight(),20,20);
		g2.fillRoundRect(0, 0, this.getWidth(),this.getHeight(),20,20);
	}
	@Override
	public void updateBlockContent() {
		// TODO Auto-generated method stub
		JSONArray assignments = this.getModel().getJSONArray("Assignments");
		String temp = "<html>Assign : <br>";
		for(int i = 0 ; i < assignments.length(); i++) {
			JSONObject currentAssignment = assignments.getJSONObject(i);
			temp = temp + currentAssignment.getString("TargetVariable") + " = ";
			temp = temp + currentAssignment.getString("Expression");
			if(i < assignments.length() - 1) {
				temp = temp + "<br>";
			}
		}
		
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
		this.editDialog = new AssignEditDialog(undoManager, this);
		return this.editDialog;
	}
	
	/** getters **/
	
	
	/** Setters **/

}
