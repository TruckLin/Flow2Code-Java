package gui.object;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.beans.PropertyChangeListener;

import javax.swing.BorderFactory;

import org.json.JSONObject;

import gui.editDialog.BlockEditDialog;
import gui.editDialog.ForEditDialog;
import gui.editDialog.WhileEditDialog;
import gui.interfaces.WithInport;
import gui.interfaces.WithOutport;
import gui.manager.UndoManager;

public class BlockFOR extends BlockLOOPFD{
	
	private ForEditDialog editDialog;
	private Color bgColor = new Color(153,194,255,80);
	private Color labelColor = new Color(153,194,255,255);
	public BlockFOR(JSONObject model){
		super(model);
		//this.CompositeBlockBackgroundColor = new Color(0,0,255,150);
	}
	
	/** Getters and Setters **/	
	public String getInitialisation() {
		return this.getModel().getString("Initialisation");
	}
	public String getCondition() {
		return this.getModel().getString("Condition");
	}
	public String getPostProcess() {
		return this.getModel().getString("PostProcess");
	}
	public String getVariable() {
		return this.getModel().getString("Variable");
	}
	public String getStartValue() {
		return this.getModel().getString("StartValue");
	}
	public String getEndValue() {
		return this.getModel().getString("EndValue");
	}
	public String getDirection() {
		return this.getModel().getString("Direction");
	}
	public String getStepBy() {
		return this.getModel().getString("StepBy");
	}
	
	@Override
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D)g;
//		g2.setColor(bgColor);
//		g2.drawRoundRect(0, 0, this.getWidth(),this.getHeight(),20,20);
//		g2.fillRoundRect(0, 0, this.getWidth(),this.getHeight(),20,20);
	}
	/** EventHandling functions **/
	@Override
	public void updateBlockContent() {
		
		String initialisation, condition, postprocess;
		initialisation = this.getVariable() + " = " + this.getStartValue();
		condition = this.getVariable() + " <= " + this.getEndValue();
		postprocess = this.getVariable() + " = " + this.getVariable();
		if( this.getDirection().equals("Increasing") ) {
			postprocess = postprocess + " + ";
		}else {
			postprocess = postprocess + " - ";
		}
		postprocess = postprocess + this.getStepBy();
		
		
		String displayString = "for( " +  initialisation + "; " 
									+ condition + "; "
									+ postprocess + " )";

		this.blockStartLOOP.getBlockLabel().setText(displayString);
		this.blockStartLOOP.getBlockLabel().setOpaque(true);
		this.blockStartLOOP.getBlockLabel().setBackground(labelColor);
		this.blockStartLOOP.adjustLabelSize();
		this.blockStartLOOP.adjustBlockSizeByLabel();
		this.blockStartLOOP.adjustLabelLocation();
		
		//Testing
		//System.out.println("blockStartLOOP's label's preferrable size = : " + 
		//						this.blockStartLOOP.getDisplayLabel().getPreferredSize());
		
		this.setAppropriateBounds();
	}
	
	@Override
	public BlockEditDialog getBlockEditDialog(UndoManager undoManager) {
		this.editDialog = new ForEditDialog(undoManager, this);
		return this.editDialog;
	}
	
	
	/** Utility Functions **/
	
}