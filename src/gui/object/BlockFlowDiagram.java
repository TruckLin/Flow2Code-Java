package gui.object;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.awt.Graphics2D;
import java.awt.geom.*;

import javax.swing.BorderFactory;

import org.json.JSONObject;

public class BlockFlowDiagram extends CompositeBlock{
	
	public BlockFlowDiagram(JSONObject model) {
		super(model);
	}
	
	/** Getters and Setters **/
	
	/** Utility Functions **/
	public void setAppropriateBounds() {
		super.setAppropriateBounds();
		// We need a minimum size for FlowDiagram Panel.
	}
}
