package gui.object;

import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;

import javax.swing.*;

import gui.interfaces.WithInport;
import gui.interfaces.WithOutport;

public class LineFD{
	private BlockFD Source;
	private BlockFD Terminal;
	
	private Point startPt;
	private Point endPt;// These two coordinate are with respect to container.
	
	private double startPositionRatioX;
	private double startPositionRatioY;
	private double endPositionRatioX;
	private double endPositionRatioY; // Keep these variable so we don't have to call getOut/Inport() methods from Source
									// and terminal.
	
	// any object interested in the line will register with propertyChangeSupport.
	private PropertyChangeSupport propertyChangeSupport = new PropertyChangeSupport(this);
	
	public void addPropertyChangeListener(PropertyChangeListener listener) {
		propertyChangeSupport.addPropertyChangeListener(listener);
	}
	public void removePropertyChangeListener(PropertyChangeListener listener) {
		propertyChangeSupport.removePropertyChangeListener(listener);
	}
	public void addPropertyChangeListener(String propertyName,PropertyChangeListener listener) {
		propertyChangeSupport.addPropertyChangeListener(propertyName, listener);
	}
	public void removePropertyChangeListener(String propertyName, PropertyChangeListener listener) {
		propertyChangeSupport.removePropertyChangeListener(propertyName,listener);
	}
	
	
	private BlockChangeListener blocklistener = new BlockChangeListener(); // keep this.
	// Named inner class which serve as a PropertyChangeListener¡C
	public class BlockChangeListener implements PropertyChangeListener{	
		public LineFD getOwnerLine() {
			return LineFD.this;
		}
		
		@Override
		public void propertyChange(PropertyChangeEvent evt) {
			
			updateLine();
			/** tell parent panel to repaint **/
			propertyChangeSupport.firePropertyChange("EndPoints",null, this); // we don't really care about old value.
		}
	}
	
	private ArrayList<Line2D> lineSegments;
	
	
	/** Constructors **/
	// Constructor from two BlockFD object and specified points.
	public LineFD(BlockFD b1, BlockFD b2, Point startpt, Point endpt) {
		
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
		
		this.startPt = startpt;
		this.endPt = endPt;
		
		Source.addPropertyChangeListener(blocklistener);
		Terminal.addPropertyChangeListener(blocklistener);
		
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
	public Point getCentrePoint() {
		double x1 = this.startPt.getX();
		double y1 = this.startPt.getY();
		double x2 = this.endPt.getX();
		double y2 = this.endPt.getY();
		int x = (int)Math.round((x1 + x2)/2);
		int y = (int)Math.round((y1 + y2)/2);
		return new Point(x,y);
	}
	public BlockChangeListener getBlockChangeListener() {
		return this.blocklistener;
	}
	
	/** Setters **/
	
	/** Utility functions **/
	// update startPt and endPt.
	public void updateLine(){
		// Testing
		//System.out.println("Line between " + Source.getModel().getString("Name") + " and " + Terminal.getModel().getString("Name"));
		//System.out.println("reDrawLine() is called.");
		
		boolean sourceIsLoop = this.Source instanceof BlockIF || this.Source instanceof BlockWHILE || this.Source instanceof BlockFOR;
		boolean terminalIsLoop	= this.Terminal instanceof BlockIF || this.Terminal instanceof BlockWHILE || this.Terminal instanceof BlockFOR;
		
		int x1 = (int)Math.round(this.Source.getWidth() * this.startPositionRatioX);
		int y1 = (int)Math.round(this.Source.getHeight() * this.startPositionRatioY);
		int x2 = (int)Math.round(this.Terminal.getWidth() * this.endPositionRatioX);
		int y2 = (int)Math.round(this.Terminal.getHeight() * this.endPositionRatioY);
		Point p1 = new Point(x1, y1);
		Point p2 = new Point(x2, y2);
		if(sourceIsLoop) {
			p1 = ((WithOutport)this.Source).getOutport();
		}
		if(terminalIsLoop) {
			p2 = ((WithInport)this.Terminal).getInport();
		}
		
		p1 = Source.toContainerCoordinate(p1);
		p2 = Terminal.toContainerCoordinate(p2);
		
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
		
		Point a = new Point(1,1);
		Point b = new Point(2,2);
		Point temp;
		temp = a;
		a = b;
		b = temp;
		System.out.println("After swap : ");
		System.out.println(a);
		System.out.println(b);
		System.out.println(temp);
		
	}
	

}
