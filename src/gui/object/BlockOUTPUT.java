package gui.object;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;

import javax.swing.BorderFactory;
import javax.swing.JLabel;

import org.json.JSONObject;

import gui.editDialog.BlockEditDialog;
import gui.editDialog.InputEditDialog;
import gui.editDialog.OutputEditDialog;
import gui.manager.UndoManager;

public class BlockOUTPUT extends OrdinaryBlockFD{
	
	private OutputEditDialog editDialog;
	private Color bgColor = new Color(255,230,255,255);
	public BlockOUTPUT(JSONObject model) {
		super(model);

		this.updateBlockContent();
		blockLabel.setOpaque(false);
		this.setBackground(null);
		this.add(blockLabel);
		
		//Temporary
		//this.setBorder(BorderFactory.createLineBorder(Color.black));
		
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
	public String getExpression() {
		return this.getModel().getString("Expression");
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
		this.blockLabel.setText("Output : " + this.getExpression());
		this.adjustLabelSize();
		this.adjustBlockSizeByLabel();
		this.adjustLabelLocation();
		if(this.getParent() instanceof CompositeBlockFD) {
			((CompositeBlockFD)this.getParent()).setAppropriateBounds();
		}
	}
	
	@Override
	public BlockEditDialog getBlockEditDialog(UndoManager undoManager) {
		this.editDialog = new OutputEditDialog(undoManager, this);
		return this.editDialog;
	}
}
