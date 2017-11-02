package gui.object;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;

import javax.swing.BorderFactory;
import javax.swing.JLabel;

import org.json.JSONObject;

import gui.interfaces.WithOutport;

public class BlockEndIF extends BlockFD implements WithOutport{
	private Point outport;
	
	private Point trueInport;
	private Point falseInport;
	
	public BlockEndIF(JSONObject model){
		super(model);
		this.removeAll();
		this.setLayout(null);
		
		// setDefault bounds
		this.setBounds(43,80,25,25);
		this.setBorder(BorderFactory.createLineBorder(Color.black));
		
		// Initialise outport
		this.setOutport(new Point( Math.round(this.getWidth()/2), (int)this.getHeight()));
		
		// Initialise inports
		this.trueInport = new Point(this.getWidth(), Math.round(this.getHeight()/2));
		this.falseInport = new Point(0, Math.round(this.getHeight()/2));
	}
	
	/** Graphics setting **/
	protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        int x = (int)this.getLocation().getX();
        int y = (int)this.getLocation().getY();
        int width = this.getWidth();
        int height = this.getHeight();
        
        g.setColor(Color.red);
        g.fillOval(x, y, width, height);
    }
	
	/** Getters and Setters **/
	public Point getTrueInport(){
		return this.trueInport;
	}
	public void setTrueInport(Point p){
		this.trueInport = p;
	}
	public Point getFalseInport(){
		return this.falseInport;
	}
	public void setFalseInport(Point p){
		this.falseInport = p;
	}
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
