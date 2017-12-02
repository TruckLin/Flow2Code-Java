package gui.object;

import java.awt.*;
import java.util.ArrayList;

import javax.swing.*;

import org.json.JSONObject;

public class BlockDECLARE extends OrdinaryBlockFD {
	
	public BlockDECLARE(JSONObject model) {
		super(model);
		
		this.blockLabel.setText("Declare");
		this.adjustLabelBounds();
		this.add(blockLabel);
		
		//Temporary
		this.setBorder(BorderFactory.createLineBorder(Color.black));
		
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
	public void updateBlockContent() {
		// TODO Auto-generated method stub
		
	}
	/** getters **/
	
	
	/** Setters **/
	

}
