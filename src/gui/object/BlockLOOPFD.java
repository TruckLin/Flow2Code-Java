package gui.object;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.geom.Line2D;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;

import javax.swing.BorderFactory;

import org.json.JSONObject;

public abstract class BlockLOOPFD extends OrdinaryCompositeBlockFD{
	protected BlockStartLOOP blockStartLOOP;
	protected BlockEndLOOP blockEndLOOP;
	protected LineFD exitLine;
	
	// This listener listen to the change in bounds and move BlockEndLOOP to the correct place.
	protected PropertyChangeListener MoveBlockEndLoopListener = 
			e -> {  // Find out the maximum Y coordinate without BlockEndLOOP.
					Component[] compList = BlockLOOPFD.this.getComponents();
					double parentMaxY = Double.MIN_VALUE;
					for(Component comp : compList) {
						if(comp == blockEndLOOP) continue;
						else {
							if(parentMaxY < comp.getBounds().getMaxY())
								parentMaxY = comp.getBounds().getMaxY();
						}
					}
					if(blockEndLOOP.getBounds().getMaxY() < parentMaxY) {
						int h = BlockLOOPFD.this.getHeight() - blockEndLOOP.getHeight();
						blockEndLOOP.setLocation((int)blockEndLOOP.getLocation().getX(),h);
					}
					
					//Testing
					//System.out.println("MoveBlockEndLoopListener triggered.");
					//System.out.println("height = " + h);
					
				};
	
	public BlockLOOPFD(JSONObject model) {
		super(model);
		// TODO Auto-generated constructor stub
		this.setOpaque(false); // we should always see through this while panel.
		
		//Temporary
		this.setBorder(BorderFactory.createLineBorder(Color.black));
		this.setBounds(0,0,100,80); // initial bounds
		
		// Add listener that change the position of BlockEndLOOP, order is important,
		// it needs to be put after setBounds or any setters of the component.
		this.addPropertyChangeListener(MoveBlockEndLoopListener);
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
			
			updateBlockContent();
			
			// initialize inports and outports in the UI
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
	public LineFD getExitLine() {
		return this.exitLine;
	}
	public void setExitLine(LineFD line) {
	
		if (this.exitLine != null) {
			// disconnect from previous model
			this.exitLine.removePropertyChangeListener(repaintListener);
		}
		this.exitLine = line;
		if (this.exitLine != null) {
			// connect to new model
			this.exitLine.addPropertyChangeListener(repaintListener);

		}

	}
	
	/** Paint Component function**/
	// This function is responsible for painting the lines.
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D)g;	
		for(int j = 0; j < exitLine.getLineSegments().size(); j++) {
			ArrayList<Line2D> segments = exitLine.getLineSegments();
			Line2D segment = segments.get(j);

			g2.setColor(exitLine.getLineColor());
			g2.setStroke(new BasicStroke());
			g2.draw(segment);
		}
	
	}
	
	/** Generate Line segments, including exitLine **/
	@Override
	public void generateLineSegmentsForAllLines() {
		super.generateLineSegmentsForAllLines();
		
		this.generateLineSegments(exitLine);
	}
	
	/** Override abstract methods from OrdinaryCompositeBlockFD**/
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
		this.inport.setPortLocation(inport);
		
	}
	@Override
	public void resetOutport() {
		Rectangle rec = blockEndLOOP.getBounds();
		Point outport = new Point( (int)Math.round(rec.getWidth())/2, (int)rec.getHeight());
		outport = new Point(blockEndLOOP.toContainerCoordinate(outport));
		this.outport.setPortLocation(outport);
	}

}
