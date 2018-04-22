package gui.object;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
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
	private Color borderColor = Color.BLACK;
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
		
//		g2.drawRoundRect(0, 0, this.getWidth(),this.getHeight(),20,20);
//		g2.fillRoundRect(0, 0, this.getWidth(),this.getHeight(),20,20);
		int[] xPoints = {1,16,this.getWidth()-1,this.getWidth()-16};
		int[] yPoints = {this.getHeight()-1,1,1,this.getHeight()-1};
		g2.setColor(bgColor);
		g2.fillPolygon(xPoints, yPoints, 4);
		int[] xPoints_2 = {1,16,this.getWidth()-1,this.getWidth()-16};
		int[] yPoints_2 = {this.getHeight()-1,1,1,this.getHeight()-1};
		g2.setStroke(new BasicStroke(1.0f));
		g2.setColor(borderColor);
		g2.drawPolygon(xPoints_2, yPoints_2, 4);
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
		String content = this.getExpression();
		String display = "";
		if(content == "" || content.isEmpty()) {
			display = " Output : " + content;
		}else {
			display = " Output...";
		}
		this.blockLabel.setText(display);
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
	@Override
	public void adjustBlockSizeByLabel() {
		super.adjustBlockSizeByLabel();
		int minWidth = (int)Math.round(100*this.currentZoomRatio);
		int minHeight = (int)Math.round(25*this.currentZoomRatio);
		
		boolean sizeShouldChange = false;
		
		int newWidth = minWidth;
		int newHeight = minHeight;
		

		//this.blockLabel.setMaximumSize(new Dimension(150,50));
		Dimension labelDimension = this.blockLabel.getMaximumSize();
		
		// We need to also deal with the case when text label got shorter. BlockSize needs to shrink.
		int x = (int)this.getLocation().getX();
		int y = (int)this.getLocation().getY();
		this.setBounds(x,y, minWidth , minHeight);
		
		if(labelDimension.getWidth() > minWidth) {
			newWidth = (int)labelDimension.getWidth();
			sizeShouldChange = true;
		}
		if(labelDimension.getHeight() > minHeight){
			newHeight = (int)labelDimension.getHeight();
			sizeShouldChange = true;
		}
		
		/** Mark it always true as supervisor requested.**/
		sizeShouldChange = true;
		newWidth = (int) labelDimension.getWidth();
		newHeight = (int) labelDimension.getHeight();
		
		if(sizeShouldChange) {
			this.setBounds(x,y,
					newWidth + (int)Math.round(10*this.currentZoomRatio),
					newHeight + (int)Math.round(0*this.currentZoomRatio));
		}
	}
}
