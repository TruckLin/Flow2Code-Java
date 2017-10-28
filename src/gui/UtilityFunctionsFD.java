package gui;

import java.awt.Point;

import javax.swing.JPanel;

import model.FD.*;
import model.object.*;

public class UtilityFunctionsFD {
	public static boolean isCompositeComponentFD(ComponentFD comp) {
		if(comp instanceof FlowDiagram) {
			return true;
		}else if(comp instanceof CompositeComponentFD) {
			return true;
		}else {
			return false;
		}
	}
	
	public static void downCastCompositeComponentFD(ComponentFD comp) {
		if(comp instanceof FlowDiagram) {
			comp = (FlowDiagram)comp;
		}else if(comp instanceof CompositeComponentFD) {
			comp = (CompositeComponentFD)comp;
		}else {
			// Do nothing
		}
	}
	
	public static Point toContainerCoordinate(JPanel contianer, JPanel block, Point coordWRTblock) {
		int x = (int)(block.getLocation().getX() + coordWRTblock.getX());
		int y = (int)(block.getLocation().getY() + coordWRTblock.getY());
		return new Point(x,y);
	}
	
}
