package graph.object;

import java.awt.Color;
import java.awt.Point;

import javax.swing.BorderFactory;

import org.json.JSONObject;

public class BlockFOR extends OrdinaryBlockFD {
	
	public BlockFOR(JSONObject model){
		super(model);
		
		this.setOpaque(false); // we should always see through this while panel.
		
		// Set the specific outport for FOR.
		this.setOutport( new Point( Math.round( this.getWidth()/4), this.getHeight() ) );
		
		//Temporary
		this.setBorder(BorderFactory.createLineBorder(Color.black));
		
	}
	
	/** Getters and Setters **/

	
}