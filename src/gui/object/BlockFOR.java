package gui.object;

import java.awt.Color;
import java.awt.Point;
import java.awt.Rectangle;
import java.beans.PropertyChangeListener;

import javax.swing.BorderFactory;

import org.json.JSONObject;

import gui.interfaces.WithInport;
import gui.interfaces.WithOutport;

public class BlockFOR extends OrdinaryCompositeBlockFD {
	private BlockStartLOOP blockStartLOOP;
	private BlockEndLOOP blockEndLOOP;
	
	// This listener listen to the change in bounds and move BlockEndLOOP to the correct place.
	private PropertyChangeListener MoveBlockEndLoopListener = 
			e -> {int h = BlockFOR.this.getHeight() - blockEndLOOP.getHeight();
				blockEndLOOP.setLocation((int)blockEndLOOP.getLocation().getX(),h);
				
				//Testing
				System.out.println("MoveBlockEndLoopListener triggered.");
			};
														
	public BlockFOR(JSONObject model){
		super(model);
		
		this.addPropertyChangeListener(MoveBlockEndLoopListener);
		
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
			resetInport();
		}
	}
	public BlockEndLOOP getBlockEndLOOP() {
		return this.blockEndLOOP;
	}
	public void setBlockEndLOOP(BlockEndLOOP comp) {
		if (this.blockEndLOOP != null) {
			// disconnect from previous model
			this.blockEndLOOP.removePropertyChangeListener(listener);
		}
		this.blockEndLOOP = comp;
		if (this.blockEndLOOP != null) {
			// connect to new model
			this.blockEndLOOP.addPropertyChangeListener(listener);

			// initialize fields in the UI
			resetOutport();
		}
	}
	
	
	/** Utility Functions **/
	@Override
	public void resetInOutPorts() {
		resetInport();
		resetOutport();
	}

	@Override
	public void resetInport() {
		Rectangle rec = blockStartLOOP.getBounds();
		Point inport = new Point( (int)Math.round(rec.getWidth())/2,0);
		inport = new Point(blockStartLOOP.toContainerCoordinate(inport));
		this.setInport(inport);
		
	}

	@Override
	public void resetOutport() {
		Rectangle rec = blockEndLOOP.getBounds();
		Point outport = new Point( (int)Math.round(rec.getWidth())/2,(int)rec.getHeight());
		outport = new Point(blockEndLOOP.toContainerCoordinate(outport));
		this.setOutport(outport);
	}
	
}