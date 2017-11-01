package graph.object;

import java.awt.*;

import javax.swing.*;

import org.json.JSONObject;

import gui.interfaces.WithInport;

public class BlockEND extends BlockFD implements WithInport{
	private Point inport;
	
	/** Constructors **/
	public BlockEND(JSONObject model) {
		super(model);
		
		// Initialise inport
		this.inport = new Point( Math.round(this.getWidth()/2), 0);
		
		// Temporary
		JLabel temp = new JLabel("End");
		this.add(temp);
		temp.setBounds(0,0,100,25);
		this.setBorder(BorderFactory.createLineBorder(Color.black));
	}
	
	/** Getters and Setters **/

	@Override
	public Point getInport() {
		// TODO Auto-generated method stub
		return this.inport;
	}
	@Override
	public void setInport(Point p) {
		// TODO Auto-generated method stub
		this.inport = p;
	}
	
}
