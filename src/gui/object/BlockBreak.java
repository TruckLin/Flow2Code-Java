package gui.object;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.BorderFactory;

import org.json.JSONObject;

public class BlockBreak extends OrdinaryBlockFD{
	
	public BlockBreak(JSONObject model) {
		super(model);

		this.updateBlockContent();
		this.adjustLabelSize();
		this.adjustBlockSizeByLabel();
		this.adjustLabelLocation();
		this.add(blockLabel);
		this.setBounds(0, 0, 25, 25);
		this.blockLabel.setForeground(new Color(255,255,255));
		//Temporary
		//this.setBorder(BorderFactory.createLineBorder(Color.black));
		
		//Testing
		//System.out.println("Constructor 1 of BlockDECLARE is called and N = " + N);
	}
	@Override
	public void paintComponent(Graphics g){
	    Graphics2D g2 = (Graphics2D)g;
			
	    g2.setColor(new Color(0,0,0));
	    g2.drawOval(0, 0, this.getWidth(), this.getHeight());
	    g2.fillOval(0, 0, this.getWidth(), this.getHeight());
	    
	}
	
	@Override
	public void updateBlockContent() {
		// TODO Auto-generated method stub
		this.blockLabel.setText("break");
		
	}

	@Override
	public boolean representCompositeBlock() {
		// TODO Auto-generated method stub
		return false;
	}
	
	
}
