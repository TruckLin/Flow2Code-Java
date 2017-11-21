package gui.interfaces;

import java.awt.Point;
import java.beans.PropertyChangeListener;

import gui.object.PortFD;

public interface WithInport {
	
	public PortFD getInport();
	public void setInport(PortFD p);

	//public PropertyChangeListener InportUpdateListener = e -> resetInport();
	
}
