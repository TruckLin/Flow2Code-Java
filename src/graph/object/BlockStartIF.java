package graph.object;

import java.awt.Color;
import java.awt.Point;

import javax.swing.BorderFactory;
import javax.swing.JLabel;

import org.json.JSONObject;

import gui.mouselistener.MouseEnterListener;
import gui.interfaces.WithInport;

public class BlockStartIF extends BlockFD implements WithInport{
	private Point inport;
	
	private Point trueOutport;
	private Point falseOutport;
	
	
	public BlockStartIF(JSONObject model){
		super(model);
		this.removeAll();
		this.setLayout(null);
		
		// Initialise inport
		this.inport = new Point( Math.round(this.getWidth()/2), 0);
		// set the Outports
		this.trueOutport = new Point(this.getWidth(), Math.round(this.getHeight()/2));
		this.falseOutport = new Point(0, Math.round(this.getHeight()/2));
		
		// Temporary
		JLabel temp = new JLabel("StartIF");
		this.add(temp);
		temp.setBounds(0,0,100,25);
		this.setBorder(BorderFactory.createLineBorder(Color.black));
		
	}
	
	/** Getters and Setters **/
	public Point getTrueOutport(){
		return this.trueOutport;
	}
	public void setTrueOutport(Point p){
		this.trueOutport = p;
	}
	public Point getFalseOutport(){
		return this.falseOutport;
	}
	public void setFalseOutport(Point p){
		this.falseOutport = p;
	}
	
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
