package graph.object;

import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.*;

import gui.interfaces.WithInport;
import gui.interfaces.WithOutport;

public class LineFD extends JPanel implements PropertyChangeListener{
	private BlockFD Source;
	private BlockFD Terminal;	
	
	private Point startPt;
	private Point endPt;// These two coordinate are with respect to container.
	
	private int width;
	private int height;
	

	
	/** Constructors **/
	// Constructor from two BlockFD object.
	public LineFD(WithOutport b1, WithInport b2) {
		Point t1 = b1.getOutport();
		Point t2 = b2.getInport(); 
		// note that these are with respect to BlockFD's coordinate. 
		// Therefore we will map them to container coordinate.
		
		
		//Testing 
		//System.out.println("outPort = " + t1.toString());
		//System.out.println("b1 = " + b1.toString());
		//System.out.println("b1.getLocation() = " + ((BlockFD)b1).getLocation().toString());
		
		int x1 = (int)(((BlockFD)b1).getLocation().getX() + t1.getX());
		int y1 = (int)(((BlockFD)b1).getLocation().getY() + t1.getY());
		int x2 = (int)(((BlockFD)b2).getLocation().getX() + t2.getX());
		int y2 = (int)(((BlockFD)b2).getLocation().getY() + t2.getY());
		Point p1 = new Point(x1, y1);
		Point p2 = new Point(x2, y2);
		initialise(p1, p2);
		
		this.Source = (BlockFD)b1;
		this.Terminal = (BlockFD)b2;
	}
	// Constructor from two BlockFD object and specified points.
	public LineFD(WithOutport b1, WithInport b2, Point startpt, Point endpt) {
		// note that these startpt, endpt are with respect to container's coordinate.
		
		//Testing 
		//System.out.println("outPort = " + t1.toString());
		//System.out.println("b1 = " + b1.toString());
		//System.out.println("b1.getLocation() = " + b1.getLocation().toString());
		
		
		initialise(startpt, endpt);
			
		this.Source = (BlockFD)b1;
		this.Terminal = (BlockFD)b2;
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
		this.width = width + tolerance;
		this.height = height + tolerance;
		// +2 makes sure that horizontal or vertical line will not have 0 height or width respectively.
		
		//this.add(new JLabel("Line"));
		
		this.setBounds(new Rectangle(xmin, ymin, this.width, this.height));
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
		int x = (int)(topleft.getX() + Math.round(this.width/2));
		int y = (int)(topleft.getY() + Math.round(this.height/2));
		return new Point(x,y);
	}
	
	/** Setters **/
	
	/** Utility functions **/
	// This function re-initialise the line, essentially redraw.
	public void reDrawLine(WithOutport b1, WithInport b2){
		Point t1 = b1.getOutport();
		Point t2 = b2.getInport(); 
		// note that these are with respect to BlockFD's coordinate. 
		// Therefore we will map them to container coordinate.
		int x1 = (int)(((BlockFD)b1).getLocation().getX() + t1.getX());
		int y1 = (int)(((BlockFD)b1).getLocation().getY() + t1.getY());
		int x2 = (int)(((BlockFD)b2).getLocation().getX() + t2.getX());
		int y2 = (int)(((BlockFD)b2).getLocation().getY() + t2.getY());
		Point p1 = new Point(x1, y1);
		Point p2 = new Point(x2, y2);
		initialise(p1, p2);
		
		//Testing
		//System.out.println("LineFD.reDrawLine() has been called.");
	}
	
	// this assume the coordinate is with respect to container.
	public void reDrawLine(Point startpt, Point endpt){

		initialise(startpt, endpt);
		
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
	
	/** PropertyChangeEvent handle **/
	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		// TODO Auto-generated method stub
		
		BlockFD SourceOfEvent = (BlockFD)evt.getSource();
		//Testing
		//System.out.println("In LineFD.propertyChange() : ");
		//System.out.println("Object Name" + evt.getSource().toString() +"\n" + 
		//						"Changed property: " + evt.getPropertyName() + "\n" + 
		//						"[old -> " + evt.getOldValue() + "] \n" + 
		//						"[new -> " + evt.getNewValue() +"] ");
		//System.out.println(this.Source.getLocation().toString());
		//System.out.println("\n");
		//if(SourceOfEvent.getName().equals(Source.getName())){  this works as well.
		
		Point oldTopLeft = (Point)evt.getOldValue();
		Point newTopLeft = (Point)evt.getNewValue();
		int dx = (int)(newTopLeft.getX() - oldTopLeft.getX());
		int dy = (int)(newTopLeft.getY() - oldTopLeft.getY());
		
		
		if(SourceOfEvent.equals(this.Source)){
			Point newStartPoint = new Point((int)(this.startPt.getX() + dx), (int)(this.startPt.getY() + dy));
			reDrawLine(newStartPoint, this.endPt);
		}else if(SourceOfEvent.equals(this.Terminal)) {
			Point newEndPoint = new Point((int)(this.endPt.getX() + dx), (int)(this.endPt.getY() + dy));
			reDrawLine(this.startPt, newEndPoint);
		}
		/** Important!!!!! is it really necessary to split into 2 cases? If everything is
		 * passed by reference? **/
	}

}
