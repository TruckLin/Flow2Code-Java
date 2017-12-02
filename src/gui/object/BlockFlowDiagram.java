package gui.object;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.awt.Graphics2D;
import java.awt.geom.*;

import javax.swing.BorderFactory;

import org.json.JSONObject;

public class BlockFlowDiagram extends CompositeBlockFD{
	
	public BlockFlowDiagram(JSONObject model) {
		super(model);
	}

	@Override
	public void updateBlockContent() {
		// TODO Auto-generated method stub
		// do nothing
	}
	
	/** Getters and Setters **/
	
	/** Utility Functions **/
	
	/** Abstract functions needed override **/
	@Override
	protected void updatePorts() {
		// TODO Auto-generated method stub
		
	}

}
