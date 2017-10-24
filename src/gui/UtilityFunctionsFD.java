package gui;

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
	
	
}
