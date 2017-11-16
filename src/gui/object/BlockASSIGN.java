package gui.object;

import java.awt.Color;
import java.awt.Rectangle;
import java.beans.PropertyChangeListener;

import javax.swing.BorderFactory;
import javax.swing.JLabel;

import org.json.JSONObject;

public class BlockASSIGN extends OrdinaryBlockFD{
	
	public BlockASSIGN(JSONObject model) {
		super(model);
		
		//Temporary
		JLabel temp = new JLabel("Assign");
		this.add(temp);
		temp.setBounds(0,0,100,25);
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
