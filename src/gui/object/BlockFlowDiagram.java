package gui.object;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.awt.Graphics2D;
import java.awt.geom.*;

import javax.swing.BorderFactory;

import org.json.JSONObject;

public class BlockFlowDiagram extends BlockFD{
	
	ArrayList<LineFD> lineList = new ArrayList<LineFD>();
	
	public BlockFlowDiagram(JSONObject model) {
		super(model);
		this.setLayout(null);
		
		// Temporary
		this.setOpaque(false);
		this.setBorder(BorderFactory.createLineBorder(Color.BLACK));
	}
	
	/** Getters and Setters **/
	
	
	/** Paint Component function**/
	// This function is responsible for painting the lines.
	@Override
	public void paintComponent(Graphics g) {
		
	}
	
	/** Utility Functions **/
	public void setAppropriateBounds() {
		super.setAppropriateBounds();
		// We need a minimum size for FlowDiagram Panel.
	}
}
