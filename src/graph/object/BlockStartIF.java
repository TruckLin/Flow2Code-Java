package graph.object;

import java.awt.Color;
import java.awt.Point;

import javax.swing.BorderFactory;
import javax.swing.JLabel;

import org.json.JSONObject;

public class BlockStartIF extends BlockEND{
	private Point trueOutport;
	private Point falseOutport;
	
	
	public BlockStartIF(JSONObject model){
		super(model);
		this.removeAll();
		this.setLayout(null);
		
		
		// Temporary
		JLabel temp = new JLabel("StartIF");
		this.add(temp);
		temp.setBounds(0,0,100,25);
		this.setBorder(BorderFactory.createLineBorder(Color.black));
		
		// set the Outports
		this.trueOutport = new Point(this.getWidth(), Math.round(this.getHeight()/2));
		this.falseOutport = new Point(0, Math.round(this.getHeight()/2));
	}
	
	/** Getters and Setters **/
	public Point getTrueOutport(){
		return this.trueOutport;
	}
	public void setTrueOutport(Point p){
		this.trueOutport = p;
	}
	public Point getFalseOutport(){
		return this.trueOutport;
	}
	public void setFalseOutport(Point p){
		this.falseOutport = p;
	}
	
	
}
