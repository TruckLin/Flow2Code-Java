package graph.object;

import java.awt.*;
import java.util.ArrayList;

import javax.swing.*;

import org.json.JSONObject;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public abstract class BlockFD extends JPanel {
	
	private JSONObject model;
	
	/** Constructors **/
	public BlockFD(JSONObject model) {
		super();
		this.model = model;
		this.setLayout(null);
		
		// Temporary
		this.setSize(100,25);
		
	}
	
	
	/** Getters and Setters **/
	public JSONObject getModel() {
		return this.model;
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
			
			Point tempPoint = this.getComponent(i).getLocation();
			x = (int)tempPoint.getX() - x_min + 5;
			y = (int)tempPoint.getY() - y_min + 5;
			this.getComponent(i).setLocation(new Point(x,y));
			
			//Testing
			//System.out.println(i + "th component's bounds : " + 
			//					this.getComponent(i).getBounds().toString());
		
		}
		
		// Now set the bounds for While panel
		int width = x_max - x_min + 10;
		int height = y_max - y_min + 10; // just big enough to contain all of them.
		Point tempPoint = this.getLocation();
		x = (int)tempPoint.getX() + x_min - 5;
		y = (int)tempPoint.getY() + y_min - 5;
		this.setBounds(x,y,width,height);
		
		//Testing
		//System.out.println("BlockWHILE.getBounds = " + this.getBounds().toString());
		
	}
}
