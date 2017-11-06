package gui.object;

import java.awt.*;
import java.util.ArrayList;

import javax.swing.*;

import org.json.JSONObject;

import gui.interfaces.WithOutport;

public class BlockSTART extends BlockFD implements WithOutport{
	private Point outport;
	
	/** Constructors **/
	public BlockSTART(JSONObject model){
		super(model);
		
		// Initialise outport
		this.outport = new Point( Math.round(this.getWidth()/2), (int)this.getHeight());
				
		// Temporary
		JLabel temp = new JLabel("Start");
		this.add(temp);
		temp.setBounds(0,0,100,25);
		this.setBorder(BorderFactory.createLineBorder(Color.black));
	}
	
	/** Getters and Setters **/

	@Override
	public Point getOutport() {
		// TODO Auto-generated method stub
		return this.outport;
	}
	@Override
	public void setOutport(Point p) {
		// TODO Auto-generated method stub
		this.outport = p;
	}
	
}
