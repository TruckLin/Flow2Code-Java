package graph.object;

import java.awt.Color;
import java.awt.Point;

import javax.swing.BorderFactory;
import javax.swing.JLabel;

import org.json.JSONObject;


public class BlockStartLOOP extends OrdinaryBlockFD{
	
	public BlockStartLOOP(JSONObject model) {
		super(model);
		
		
		// set the specific outport for BlockLOOP
		this.setOutport(new Point( this.getWidth(), (int)this.getHeight()/2 ) );
		
		// set the specific inport for BlockLOOP
		this.setInport(new Point( Math.round(this.getWidth() - this.getHeight()/2), this.getHeight() ));
		
		// Temporary
		JLabel temp = new JLabel("StartLoop");
		this.add(temp);
		temp.setBounds(0,0,100,25);
		this.setBorder(BorderFactory.createLineBorder(Color.black));
	}
	
	
	/** Getters and Setters **/
	
	
	/** Utility Functions **/

}
