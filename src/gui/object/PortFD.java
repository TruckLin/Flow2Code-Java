package gui.object;

import java.awt.Point;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class PortFD {
	private Point portLocation;
	private String side;
	/* side:
	/*				  top
	 * 			-----------------
	 * 			|				|
	 * 	left 	|				| right 
	 * 			|				|
	 * 			-----------------
	 * 				 bottom
	*/
	
	public PortFD(Point p, String side) {
		this.portLocation = p;
		this.side = side;
	}
	
	/** Getters and Setters **/
	public Point getPortLocation() {
		return this.portLocation;
	}
	public void setPortLocation(Point p) {
		this.portLocation = p;
	}
	public String getSide() {
		return this.side;
	}
	public void setSide(String side) {
		this.side = side;
	}
}
