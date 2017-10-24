package model.object;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

import model.interfaces.WithDirectChild;
import model.interfaces.WithDirectParent;

public abstract class ComponentFD {
	private String name;
	
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
	
	/** Getters and Setters **/
	public String getName() {
		return name;
	}
	public void setName(String str) {
		this.name = str;
	}
	
	public ComponentFD(String name) {
		this.name = name;
	}
	
	/** UtilityFunctions **/
	public void addComponentFD(OrdinaryComponentFD comp, WithDirectChild parent, WithDirectParent child) {
		
	}
	public void removeComponentFD(OrdinaryComponentFD comp) {
		
	}
	
	public String toReadableString(String indent) {
		String temp = indent + this.name + " : " + this.getClass() + "\n";
		return temp;
	}
	
}
