package gui.object;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

import org.json.JSONObject;

import gui.editDialog.BlockEditDialog;
import gui.editDialog.WhileEditDialog;
import gui.interfaces.WithInport;
import gui.interfaces.WithOutport;
import gui.manager.UndoManager;


public class BlockWHILE extends BlockLOOPFD{
	
	private WhileEditDialog editDialog;
	private Color bgColor = new Color(153,230,255,80);
	private Color labelColor = new Color(153,230,255,255);
	private int labelWidth;
	private int labelHeight;
	public BlockWHILE(JSONObject model){
		super(model);
	}
	
	/** Getters and Setters **/	
	public String getExpression() {
		return this.getModel().getString("Expression");
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
		String expression = this.getExpression();
		if(expression == "" || expression.isEmpty()) {
			expression = "While(  )";
		}else {
			expression = "While(...)";
		}
		String displayString = (expression);//"While( " + this.getExpression() + " )");
		this.blockStartLOOP.getBlockLabel().setText(displayString);
		this.blockStartLOOP.getBlockLabel().setOpaque(true);
		this.blockStartLOOP.getBlockLabel().setBackground(labelColor);
		this.blockStartLOOP.adjustLabelSize();
		this.blockStartLOOP.adjustBlockSizeByLabel();
		this.blockStartLOOP.adjustLabelLocation();
		
		//Testing
		//System.out.println("blockStartLOOP's label's preferrable size = : " + 
		//						this.blockStartLOOP.getDisplayLabel().getPreferredSize());
		
		// blockStartLOOP may need to change size and location
		// this.blockStartLOOP.setAppropriateBounds();
		
	}
	
	@Override
	public BlockEditDialog getBlockEditDialog(UndoManager undoManager) {
		this.editDialog = new WhileEditDialog(undoManager, this);
		return this.editDialog;
	}
	/** Utility Functions **/
	
}
