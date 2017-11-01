package graph.object;

import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.*;

import gui.interfaces.WithInport;
import gui.interfaces.WithOutport;

public class LineFD extends JPanel{
	private BlockFD Source;
	private BlockFD Terminal;	
	
	private Point startPt;
	private Point endPt;// These two coordinate are with respect to container.
	
	private double startPositionRatioX;
	private double startPositionRatioY;
	private double endPositionRatioX;
	private double endPositionRatioY; // Keep these variable so we don't have to call getOut/Inport() methods from Source
									// and terminal.
	
	private PropertyChangeListener listener = e -> {
													// Testing
													System.out.println("Line Source : " + this.Source.toString());
													System.out.println("Line Terminal : " + this.Terminal.toString());
													System.out.println("Receive propertyChangeEvent.");
													System.out.println("Source of Event : " + e.getSource().toString());
													System.out.println("");
													
													reDrawLine();};
	
	/** Constructors **/
	// Constructor from two BlockFD object and specified points.
	public LineFD(BlockFD b1, BlockFD b2, Point startpt, Point endpt) {
		super();
		
		this.setOpaque(false);
		// note that these startpt, endpt are with respect to container's coordinate.
		
		//Testing 
		//System.out.println("outPort = " + t1.toString());
		//System.out.println("b1 = " + b1.toString());
		//System.out.println("b1.getLocation() = " + b1.getLocation().toString());
		
		double x1 = (startpt.getX() - b1.getLocation().getX())/b1.getWidth();
		double y1 = (startpt.getY() - b1.getLocation().getY())/b1.getHeight();
		double x2 = (endpt.getX() - b2.getLocation().getX())/b2.getWidth();
		double y2 = (endpt.getY() - b2.getLocation().getY())/b2.getHeight();
		this.startPositionRatioX = x1;
		this.startPositionRatioY = y1;
		this.endPositionRatioX = x2;
		this.endPositionRatioY = y2;
		
		this.Source = b1;
		this.Terminal = b2;
		
		initialise(startpt, endpt);
		
		Source.addPropertyChangeListener(listener);
		Terminal.addPropertyChangeListener(listener);
		
	}
	
	// Initialisatise, should make the code easier to read, p1, p2 are with respect to container's coordinate.
	public void initialise(Point p1, Point p2) {
		
		// Testing
		//System.out.println("Initialise() is called.");
		//System.out.println("Point 1 = " + p1.toString());
		//System.out.println("Point 2 =  " + p2.toString());
		
		this.startPt = p1;
		this.endPt = p2;
		
		// Calculate width and height
		int width = (int)Math.abs(p2.getX() - p1.getX());
		int height = (int)Math.abs(p2.getY() - p1.getY());
		
		// Determine where to place topLeftCorner
		int xmin = Math.min((int)p1.getX(), (int)p2.getX());
		int ymin = Math.min((int)p1.getY(), (int)p2.getY());
		
		// testing
		//System.out.println("startPt = " + this.startPt.toString());
        //System.out.println("endPt = " + this.endPt.toString());
        //System.out.println("xmin = " + xmin);
        //System.out.println("ymin = " + ymin);
        //System.out.println("getLocation() = " + this.getLocation().toString());
        //System.out.println("width = " + width);
		
		int tolerance = 2;
		int width2 = width + tolerance;
		int height2 = height + tolerance;
		// +2 makes sure that horizontal or vertical line will not have 0 height or width respectively.
		
		//this.add(new JLabel("Line"));
		
		this.setBounds(new Rectangle(xmin, ymin, width2, height2));
		this.setOpaque(false);
		
		//this.setBorder(BorderFactory.createLineBorder(Color.black));
		
		//this.addMouseListener(new LineMouseClickListener());
	}
	
	/** Graphics setting **/
	protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        Point topLeft = LineFD.this.getLocation(); // there is a problem here...
        int x1 = (int)(this.startPt.getX()-topLeft.getX());
        int y1 = (int)(this.startPt.getY()-topLeft.getY());
        int x2 = (int)(this.endPt.getX()-topLeft.getX());
        int y2 = (int)(this.endPt.getY()-topLeft.getY());
        
        // testing
        //System.out.println("topLeft = " + topLeft.toString());
        //System.out.println("startPt = " + this.startPt.toString());
        //System.out.println("endPt = " + this.endPt.toString());
        //System.out.println("x1 = " + x1);
        //System.out.println("y1 = " + y1);
        //System.out.println("x2 = " + x2);
        //System.out.println("y2 = " + y2);
        //System.out.println("paint component is called.");
       
        g.setColor(Color.red);
        g.drawLine(x1, y1, x2, y2);
        
    }
	
	/** Getters **/
	public Point getStartPoint() {
		return this.startPt;
	}
	
	public Point getEndPoint() {
		return this.endPt;
	}
	public BlockFD getSource() {
		return this.Source;
	}
	public BlockFD getTerminal() {
		return this.Terminal;
	}
	public Point getCentreOnPanel() {
		Point topleft = this.getLocation();
		int x = (int)(topleft.getX() + Math.round(this.getWidth()/2));
		int y = (int)(topleft.getY() + Math.round(this.getHeight()/2));
		return new Point(x,y);
	}
	
	/** Setters **/
	
	/** Utility functions **/
	// This function re-initialise the line, essentially redraw.
	public void reDrawLine(){
		// Testing
		//System.out.println("Line between " + Source.getModel().getString("Name") + " and " + Terminal.getModel().getString("Name"));
		//System.out.println("reDrawLine() is called.");
		
		boolean isLoop = this.Source instanceof BlockIF || this.Source instanceof BlockWHILE || this.Source instanceof BlockFOR ||
						this.Terminal instanceof BlockIF || this.Terminal instanceof BlockWHILE || this.Terminal instanceof BlockFOR;
		
		int x1 = (int)Math.round(this.Source.getWidth() * this.startPositionRatioX);
		int y1 = (int)Math.round(this.Source.getHeight() * this.startPositionRatioY);
		int x2 = (int)Math.round(this.Terminal.getWidth() * this.endPositionRatioX);
		int y2 = (int)Math.round(this.Terminal.getHeight() * this.endPositionRatioY);
		Point p1 = new Point(x1, y1);
		Point p2 = new Point(x2, y2);
		if(isLoop) {
			/*
			if(this.Source instanceof BlockIF || this.Terminal instanceof BlockIF) {
				//Testing
				System.out.println("isLoop is true and triggered.");
				System.out.println("Source : " + Source.toString());
				System.out.println("Source outport = " + ((WithOutport)this.Source).getOutport().toString());
				System.out.println("Terminal : " + Terminal.toString());
				System.out.println("Terminal inport = " + ((WithInport)this.Terminal).getInport().toString() + "\n");
			}*/
			
			p1 = ((WithOutport)this.Source).getOutport();
			p2 = ((WithInport)this.Terminal).getInport();
		}
		
		p1 = Source.toContainerCoordinate(p1);
		p2 = Terminal.toContainerCoordinate(p2);
		initialise(p1, p2);
		
		//Testing
		//System.out.println("LineFD.reDrawLine() has been called.");
	}
	
	// "is" functions
	public boolean isSource(BlockFD b) {
		return this.Source.equals(b);
	}
	public boolean isTerminal(BlockFD b) {
		return this.Terminal.equals(b);
	}
	
	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
	

}
