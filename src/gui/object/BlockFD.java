package gui.object;

import java.awt.*;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;

import javax.swing.*;

import org.json.JSONObject;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public abstract class BlockFD extends JPanel{
	
	private JSONObject model;
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
	
	/** Constructors **/
	public BlockFD(JSONObject model) {
		super();
		this.model = model;
		this.setLayout(null);
		
		// Temporary
		this.setSize(100,25);
		this.setOpaque(false);
		
	}
	
	
	/** Getters and Setters **/
	public JSONObject getModel() {
		return this.model;
	}
	public void setLocation(int x, int y) {
		Point oldValue = this.getLocation();
		super.setLocation(x, y);
		propertyChangeSupport.firePropertyChange("Location", oldValue, this.getLocation());
	}
	public void setLocation(Point p) {
		Point oldValue = this.getLocation();
		super.setLocation(p);
		propertyChangeSupport.firePropertyChange("Location", oldValue, p);
	}
	
	public void setBounds(int x, int y, int width, int height) {
		Rectangle oldValue = this.getBounds();
		super.setBounds(x, y, width, height);
		propertyChangeSupport.firePropertyChange("Bounds", oldValue, this.getBounds());
	}
	public void setBounds(Rectangle rec) {
		Rectangle oldValue = this.getBounds();
		super.setBounds(rec);
		propertyChangeSupport.firePropertyChange("Bounds", oldValue, rec);
	}
	
	public PropertyChangeSupport getPropertyChangeSupport() {
		return this.propertyChangeSupport;
	}


	
	/** Event handling functions **/
	
	/** Utility functions **/
	public Point toContainerCoordinate(Point coordWRTblock) {
		int x = (int)(this.getLocation().getX() + coordWRTblock.getX());
		int y = (int)(this.getLocation().getY() + coordWRTblock.getY());
		return new Point(x,y);
	}
	
	public void setAppropriateBounds() {
		// This function set appropriate size according to it's children.
		// Size that is just big enough to contain all the children.
		
		//Testing
		//System.out.println("setAppropriateBounds() is called :");
		//System.out.println("Initial parameters : ");
		//System.out.println("BlockWHILE.getBounds = " + this.getBounds().toString());
		
		int x_min = Integer.MAX_VALUE;
		int y_min = Integer.MAX_VALUE;
		int x_max = Integer.MIN_VALUE;
		int y_max = Integer.MIN_VALUE;
		int len = this.getComponents().length;
		for(int i = 0; i < len; i++) {
			Rectangle tempBounds = this.getComponent(i).getBounds();

			//Testing
			//System.out.println(i + "th component's bounds : " + tempBounds.toString());
			
			if(tempBounds.getMinX() < x_min) {
				x_min = (int)tempBounds.getMinX();
			}
			if(tempBounds.getMaxX() > x_max) {
				x_max = (int)tempBounds.getMaxX();
			}
			if(tempBounds.getMinY() < y_min) {
				y_min = (int)tempBounds.getMinY();
			}
			if(tempBounds.getMaxY() > y_max) {
				y_max = (int)tempBounds.getMaxY();
			}
		}
		
		//Testing
		//System.out.println("\nEnd parameters : ");
		
		// Shift children components according to minimums.
		int x;
		int y;
		for(int i = 0; i < len; i++) {
			if(! (this.getComponent(i) instanceof LineFD)) {
			Point tempPoint = this.getComponent(i).getLocation();
			x = (int)tempPoint.getX() - x_min + 5;
			y = (int)tempPoint.getY() - y_min + 5;
			this.getComponent(i).setLocation(new Point(x,y));
			
			//Testing
			//System.out.println(i + "th component's bounds : " + 
			//					this.getComponent(i).getBounds().toString());
			}
		}
		
		// Now set the bounds for While panel
		int width = x_max - x_min + 10;
		int height = y_max - y_min + 10; // just big enough to contain all of them.
		Point tempPoint = this.getLocation();
		x = (int)tempPoint.getX() + x_min - 5;
		y = (int)tempPoint.getY() + y_min - 5;
		this.setBounds(x,y,width,height);
		
		
		if(this.getParent() instanceof BlockFD) {
			((BlockFD)this.getParent()).setAppropriateBounds();
		}
		
		
		//Testing
		//System.out.println("BlockWHILE.getBounds = " + this.getBounds().toString());
		
	}

	public void translateLocation(int dx, int dy) {
		int x = (int)this.getLocation().getX();
		int y = (int)this.getLocation().getY();
		
		x = x + dx;
		y = y + dy;
		
		this.setLocation(x, y);
	}
}
