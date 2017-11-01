package graph.object;

import java.awt.Color;
import java.awt.Point;
import java.awt.Rectangle;
import java.beans.PropertyChangeListener;

import javax.swing.BorderFactory;

import org.json.JSONObject;

public class BlockFOR extends OrdinaryBlockFD {
	private BlockStartLOOP blockStartLOOP;
	
	private PropertyChangeListener listener = e -> resetOutInPorts();
	
	public BlockFOR(JSONObject model){
		super(model);
		
		this.setOpaque(false); // we should always see through this while panel.
		
		//Temporary
		this.setBorder(BorderFactory.createLineBorder(Color.black));
		
	}
	
	/** Getters and Setters **/
	public BlockStartLOOP getBlockStartLOOP() {
		return this.blockStartLOOP;
	}
	public void setBlockStartLOOP(BlockStartLOOP comp) {
		if (this.blockStartLOOP != null) {
			// disconnect from previous model
			this.blockStartLOOP.removePropertyChangeListener(listener);
		}
		this.blockStartLOOP = comp;
		if (this.blockStartLOOP != null) {
			// connect to new model
			this.blockStartLOOP.addPropertyChangeListener(listener);

			// initialize fields in the UI
			resetOutInPorts();
		}
	}
	
	
	/** Utility Functions **/
	public void resetOutInPorts() {
		Rectangle rec = this.getBlockStartLOOP().getBounds();
		Point outport = new Point( (int)Math.round(rec.getWidth())/4,(int)rec.getHeight());
		outport = new Point(blockStartLOOP.toContainerCoordinate(outport));
		this.setOutport(outport);

		Point inport = new Point( (int)Math.round(rec.getWidth())/2,0);
		inport = new Point(blockStartLOOP.toContainerCoordinate(inport));
		this.setInport(inport);
	}
	
}