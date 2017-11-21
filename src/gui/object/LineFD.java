package gui.object;

import java.awt.*;
import java.awt.geom.Line2D;
import java.awt.geom.PathIterator;
import java.awt.geom.Point2D;
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
	
	private PortFD startPort;
	private PortFD endPort;// These two coordinate are with respect to container.
	
	// Various variables that control the appearance of the line. 
	private Color color = Color.RED;
	private Color borderColor = new Color(153,217,234);
	private boolean hasBorder = false;
	private double triggerRadius = 5;
	
	
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
			
			// Testing
			//System.out.println("Property of Blocks changed and detected by line.");
			
			/** tell parent panel to repaint **/
			propertyChangeSupport.firePropertyChange("EndPoints",null, this); // we don't really care about old value.
		}
	}
	
	// A LineFD consists of a few shape, which could be line, curve or other.
	private ArrayList<Line2D> lineSegments = new ArrayList<Line2D>(); // use Line2D just for now.
	
	
	/** Constructors **/
	// Constructor from two BlockFD object and specified points.
	public LineFD(BlockFD b1, BlockFD b2, PortFD startPort, PortFD endPort) {
		
		// note that these startpt, endpt are with respect to container's coordinate.
		
		//Testing
		//System.out.println("outPort = " + t1.toString());
		//System.out.println("b1 = " + b1.toString());
		//System.out.println("b1.getLocation() = " + b1.getLocation().toString());
		
		this.Source = b1;
		this.Terminal = b2;
		
		this.startPort = startPort;
		this.endPort = endPort;
		
		Source.addPropertyChangeListener(blocklistener);
		Terminal.addPropertyChangeListener(blocklistener);
	}
	
	/** Getters **/
	public PortFD getStartPort() {
		return this.startPort;
	}
	
	public PortFD getEndPort() {
		return this.endPort;
	}
	public BlockFD getSource() {
		return this.Source;
	}
	public BlockFD getTerminal() {
		return this.Terminal;
	}
	public Point getCentrePoint() {
		Point startPt = this.getStartPort().getPortLocation();
		startPt = this.Source.toContainerCoordinate(startPt);
		Point endPt = this.getEndPort().getPortLocation();
		endPt = this.Terminal.toContainerCoordinate(endPt);
		double x1 = startPt.getX();
		double y1 = startPt.getY();
		double x2 = endPt.getX();
		double y2 = endPt.getY();
		int x = (int)Math.round((x1 + x2)/2);
		int y = (int)Math.round((y1 + y2)/2);
		
		//Testing
		//System.out.println("startPt = " + startPt);
		//System.out.println("endPt = " + endPt);
		//System.out.println("center - " + new Point(x,y));
		
		return new Point(x,y);
	}
	public BlockChangeListener getBlockChangeListener() {
		return this.blocklistener;
	}
	public ArrayList<Line2D> getLineSegments(){
		return this.lineSegments;
	}
	
	public Color getLineColor() {
		return this.color;
	}
	public void setLineColor(Color c) {
		this.color = c;
	}
	public Color getLineBorderColor() {
		return this.borderColor;
	}
	public void setLineBorderColor(Color c) {
		this.borderColor = c;
	}
	public boolean getHasBorder() {
		return this.hasBorder;
	}
	public void setHasBorder(boolean b) {
		this.hasBorder = b;
	}
	public double getTriggerRagius() {
		return this.triggerRadius;
	}
	public void setTriggerRadius(double r) {
		this.triggerRadius = r;
	}
	
	/** Setters **/
	
	/** Utility functions **/
	
	// "is" functions
	public boolean isSource(BlockFD b) {
		return this.Source.equals(b);
	}
	public boolean isTerminal(BlockFD b) {
		return this.Terminal.equals(b);
	}
	
	// this function is used to determine whether a mouse click point should trigger mouse event
	public boolean isInRange(Point p) {
		Point2D.Double p2 = new Point2D.Double(p.getX(), p.getY());
		
		for(int i = 0; i < lineSegments.size(); i++) {
			if(lineSegments.get(i).ptSegDist(p2) <= this.triggerRadius) {
				return true;
			}
		}
		return false;
	}
}
