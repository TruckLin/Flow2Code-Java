package gui.interfaces;

import java.awt.Point;
import java.beans.PropertyChangeListener;

public interface WithInport {
	
	public Point getInport();
	public void setInport(Point p);

	//public PropertyChangeListener InportUpdateListener = e -> resetInport();
	
}
