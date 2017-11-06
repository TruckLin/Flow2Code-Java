package gui.object;

import java.awt.*;
import java.util.ArrayList;

import javax.swing.*;

import org.json.JSONObject;

public class BlockDECLARE extends OrdinaryBlockFD {
	
	public BlockDECLARE(JSONObject model) {
		super(model);
		
		//Temporary
		JLabel temp = new JLabel("Declare");
		this.add(temp);
		temp.setBounds(0,0,100,25);
		this.setBorder(BorderFactory.createLineBorder(Color.black));
		
		//Testing
		//System.out.println("Constructor 1 of BlockDECLARE is called and N = " + N);

	}
	
	/** getters **/
	
	
	/** Setters **/
	

}
