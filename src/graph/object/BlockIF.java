package graph.object;

import java.awt.Color;
import java.awt.Point;
import java.awt.Rectangle;

import javax.swing.BorderFactory;
import javax.swing.JLabel;

public class BlockIF extends BlockFD{
	
	private Point trueOutPort;
	private Point falseOutPort;
	
	/** Constructors and initialisations **/
	public BlockIF(String N) {
		super(N);
		// TODO Auto-generated constructor stub
		
		this.add(new JLabel("if"));
		this.setBorder(BorderFactory.createLineBorder(Color.black));
		
		this.setOutPorts();
		
		//Testing
		//System.out.println("Constructor 1 of BlockDECLARE is called and N = " + N);

	}
	public BlockIF(String N, Rectangle rec) {
		super(N,rec);
		
		this.add(new JLabel("if"));
		this.setBorder(BorderFactory.createLineBorder(Color.black));
		
		this.setOutPorts();
		
		//Testing
		//System.out.println("Constructor 2 of BlockDECLARE is called and N = " + N);
		
	}
	
	
	
	/** Setters **/
	public void setOutPorts() {
		int x = this.getWidth();
		int y = (int)Math.round(this.getHeight()/2);
		this.trueOutPort = new Point(x,y);
		this.falseOutPort = new Point(0,y);
	}
	
	/** Getters **/
	public Point getTrueOutPort() {
		return this.trueOutPort;
	}
	
	public Point getFalseOutPort() {
		return this.falseOutPort;
	}
	
	
	/** Utility functions **/
	/*public CompositeBlocks buildCompositeBlocks() {
		CompositeBlocks output = new CompositeBlocks();
		// Set inport point for composition blocks.
		Point p1 = this.toContainerCoordinate(this.getLocation());
		output.setInPortPoint(p1);
		
		BlockFD temp = new BlockFD();
		for(int i = 0; i < this.lines.size(); i++) {
			LineFD L = (LineFD)this.lines.get(i);
			
			if(L.isSource(this)) {
				
				while(!(temp instanceof BlockENDIF)){
			
				}
			}
		}
		return output;
	}*/
	
}
