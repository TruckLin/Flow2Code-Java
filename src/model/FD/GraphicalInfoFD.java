package model.FD;

import java.awt.Rectangle;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.HashMap;

public class GraphicalInfoFD {
	private HashMap<String, Rectangle> info;
	
	private PropertyChangeSupport pcs = new PropertyChangeSupport(this);
	
	public void addPropertyChangeListener(PropertyChangeListener listener) {
		pcs.addPropertyChangeListener(listener);
	}
	public void addPropertyChangeListener(String propertyName, PropertyChangeListener listener) {
		pcs.addPropertyChangeListener(propertyName, listener);
	}
	public void removePropertyChangeListener(PropertyChangeListener listener) {
		pcs.removePropertyChangeListener(listener);
	}
	public void removePropertyChangeListener(String propertyName, PropertyChangeListener listener) {
		pcs.removePropertyChangeListener(propertyName, listener);
	}
	
	public GraphicalInfoFD() {
		this.info = new HashMap<String, Rectangle>();
	}
	
	/** Getters and Setters **/
	public Rectangle getBounds(String key) {
		if( info.containsKey(key)) {
			return this.info.get(key);
		}else {
			return null;
		}
	}
	public void setBounds(String key, Rectangle rec) {
		if( ! info.containsKey(key)) {
			System.err.println("The key \"" + key + "\" doesn't exist.");
		}else {
			this.info.put(key, rec);
		}
	}
	public void addBounds(String key, Rectangle rec) {
		if(info.containsKey(key)) {
			System.err.println("The key \"" + key + "\" already exist.");
		}else {
			this.info.put(key, rec);
		}
	}
	/** Utilities **/
	public boolean isEmpty() {
		if(this.info.size() == 0) {
			return true;
		}else {
			return false;
		}
	}
	
	
	public static void main(String[] args) {
		//GraphicalInfoFD myInfo = new GraphicalInfoFD();
		//myInfo.setBounds("owhoh", new Rectangle(1,1,5,5));
		
	}
	
}
