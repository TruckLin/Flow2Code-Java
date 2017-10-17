package graph.object;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;

import javax.swing.BorderFactory;
import javax.swing.JLabel;

public class BlockENDIF extends BlockFD{
	private Point trueInPort;
	private Point falseInPort;
	
	/** Constructors and initialisations **/
	public BlockENDIF(String N) {
		super(N);
		// TODO Auto-generated constructor stub
		
		this.add(new JLabel(""));
		this.setBorder(BorderFactory.createLineBorder(Color.black));
		
		this.setInPorts();
		
		this.setOpaque(false);
		
		//Testing
		//System.out.println("Constructor 1 of BlockDECLARE is called and N = " + N);

	}
	public BlockENDIF(String N, Rectangle rec) {
		super(N,rec);
		
		this.add(new JLabel(""));
		this.setBorder(BorderFactory.createLineBorder(Color.black));
		
		this.setInPorts();
		
		this.setOpaque(false);
		
		//Testing
		//System.out.println("Constructor 2 of BlockDECLARE is called and N = " + N);
		
	}
	
	/** Graphics setting **/
	protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        g.setColor(Color.yellow);
        g.fillOval(0, 0, this.getWidth(), this.getHeight());
        
    }
	
	/** Setters **/
	public void setInPorts() {
		int x = this.getWidth();
		int y = (int)Math.round(this.getHeight()/2);
		this.trueInPort = new Point(x,y);
		this.falseInPort = new Point(0,y);
	}
	
	/** Getters **/
	public Point getTrueInPort() {
		return this.trueInPort;
	}
	
	public Point getFalseInPort() {
		return this.falseInPort;
	}
}
