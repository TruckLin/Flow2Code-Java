package gui.object;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Point;

import javax.swing.BorderFactory;
import javax.swing.JLabel;

import org.json.JSONObject;


public class BlockStartLOOP extends OrdinaryBlockFD{
	JLabel displayLabel;
	
	public BlockStartLOOP(JSONObject model) {
		super(model);
		this.setLayout(new FlowLayout());
		
		// set the specific outport for BlockStartLOOP
		this.setOutport(new Point( this.getWidth(), (int)this.getHeight()/2 ) );
		
		// set the specific inport for BlockStartLOOP
		this.setInport(new Point( Math.round(this.getWidth() - this.getHeight()/2), this.getHeight() ));
		
		// Set the location of BlockStartLOOP
		this.setLocation(5, 5);
		
		// Temporary
		this.displayLabel = new JLabel("StartLoop");
		this.add(this.displayLabel);
		this.displayLabel.setBounds(0,0,100,25);
		this.setBorder(BorderFactory.createLineBorder(Color.black));
	}
	
	
	/** Getters and Setters **/
	public JLabel getDisplayLabel() {
		return this.displayLabel;
	}
	public void setDiaplyLabel(JLabel temp) {
		if(temp != null) {
			this.remove(this.displayLabel);
			this.displayLabel = temp;
			this.add(temp);
		}
	}
	
	/** Utility Functions **/

}
