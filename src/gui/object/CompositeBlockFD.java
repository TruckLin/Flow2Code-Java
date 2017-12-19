package gui.object;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.geom.Line2D;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;

import javax.swing.BorderFactory;

import org.json.JSONObject;

import gui.manager.NameCounterManager;
import gui.manager.UndoManager;
import gui.mouselistener.LineRightClickListener;
import simple.geometry.GeometryTools;

public abstract class CompositeBlockFD extends BlockFD{

	protected ArrayList<LineFD> lineList = new ArrayList<LineFD>();
	protected PropertyChangeListener repaintListener = 
			e -> {generateLineSegmentsForAllLines();
				// Testing
				//System.out.println("line changes detected by parent block");
														
				repaint(); // repaint the lines
			};
	
	public CompositeBlockFD(JSONObject model) {
		super(model);
		
		this.setLayout(null);
		
		// Add right clisk listener for line
		LineRightClickListener lineListener = new LineRightClickListener(this);
		this.addMouseListener(lineListener);
		this.addMouseMotionListener(lineListener);
		
		// Temporary
		this.setOpaque(false);
		this.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		
	}
	/** Getters and Setters **/
	public ArrayList<LineFD> getLineList() {
		return this.lineList;
	}
	
	/** Add line and remove line**/
	public void removeLineFD(LineFD line) {
		line.removePropertyChangeListener(repaintListener);
		this.lineList.remove(line);
		this.repaint();
	}
	public void addLineFD(LineFD line) {
		line.addPropertyChangeListener(repaintListener);
		this.lineList.add(line);
		GeometryTools.generateLineSegments(line);
		this.repaint();
	}
	
	/** Paint Component function**/
	// This function is responsible for painting the lines.
	@Override
	public void paintComponent(Graphics g) {
		Graphics2D g2 = (Graphics2D)g;
		
		for(int i = 0; i < lineList.size(); i++) {
			LineFD currentLine = this.lineList.get(i);
			
			for(int j = 0; j < lineList.get(i).getLineSegments().size(); j++) {
				ArrayList<Line2D> segments = lineList.get(i).getLineSegments();
				Line2D segment = segments.get(j);
				
				if(currentLine.getHasBorder()) {
					g2.setColor(currentLine.getLineBorderColor());
					BasicStroke myStroke = new BasicStroke((float)currentLine.getTriggerRagius(),
													BasicStroke.CAP_BUTT,BasicStroke.JOIN_ROUND, 10.0f);
					g2.setStroke(myStroke);
					g2.draw(segment);
				}
				g2.setColor(currentLine.getLineColor());
				g2.setStroke(new BasicStroke());
				g2.draw(segment);
			}
		}
	}
	
	/** Set appropriateBounds **/
	public void setAppropriateBounds() {
		// This function set appropriate size according to it's children.
		// Size that is just big enough to contain all the children.
		
		//Testing
		//System.out.println("setAppropriateBounds() is called :");
		//System.out.println("Initial parameters : ");
		//System.out.println("Type of the Block = " + this.getModel().getString("Type"));
		//System.out.println("Name of the Block = " + this.getModel().getString("Name"));
		
		int x_min = Integer.MAX_VALUE;
		int y_min = Integer.MAX_VALUE;
		int x_max = Integer.MIN_VALUE;
		int y_max = Integer.MIN_VALUE;
		// Find min and max of x-coordinate and y-coordinate of Blocks.
		int len = this.getComponents().length;
		for(int i = 0; i < len; i++) {
			Rectangle tempBounds = this.getComponent(i).getBounds();

			//Testing
			//System.out.println(i + "th component's name : " + this.getComponent(i).getClass());
			//System.out.println(i + "th component's bounds : " + tempBounds.toString());
			
			
			if(tempBounds.getMinX() < x_min) {
				x_min = (int)tempBounds.getMinX();
			}
			if(tempBounds.getMaxX() > x_max) {
				x_max = (int)tempBounds.getMaxX();
			}
			if(tempBounds.getMinY() < y_min) {
				y_min = (int)tempBounds.getMinY();
			}
			if(tempBounds.getMaxY() > y_max) {
				y_max = (int)tempBounds.getMaxY();
			}
		}
		
		// Find min and max of x-coordinate and y-coordinate of lines.
		int numOfLines = this.lineList.size();
		for(int i = 0; i < numOfLines; i++) {
			ArrayList<Line2D> currentLineSegments = lineList.get(i).getLineSegments();
			int numOfSegments = currentLineSegments.size();
			for(int j = 0; j < numOfSegments; j++) {
				Line2D currentSegment = currentLineSegments.get(j);
				double x1 = currentSegment.getX1();
				double y1 = currentSegment.getY1();
				double x2 = currentSegment.getX2();
				double y2 = currentSegment.getY2();
				
				double tempXmin = (int)Math.min(x1, x2);
				double tempXmax = Math.max(x1, x2);
				double tempYmin = Math.min(y1, y2);
				double tempYmax = Math.max(y1, y2);
				
				if(tempXmin < x_min) {
					x_min = (int)tempXmin;
				}
				if(tempXmax > x_max) {
					x_max = (int)tempXmax;
				}
				if(tempYmin < y_min) {
					y_min = (int)tempYmin;
				}
				if(tempYmax > y_max) {
					y_max = (int)tempYmax;
				}
			}
		}
		
		
		//Testing
		//System.out.println("\nEnd parameters : ");
		
		// some Extra width if required. extraWidth represents the minimum horizontal 
		// distance from CompositeBlock's boundary to the components it contains.
		int extraWidth = (int)Math.round( 5 * this.getCurrentZoomRatio());
		int extraHeight = (int)Math.round( 0 * this.getCurrentZoomRatio());	
		
		// Shift children components according to minimums.
		int x;
		int y;
		for(int i = 0; i < len; i++) {
			
			Point tempPoint = this.getComponent(i).getLocation();
			x = (int)tempPoint.getX() - x_min + extraWidth;
			y = (int)tempPoint.getY() - y_min + extraHeight;
		//	x = (int)tempPoint.getX() - x_min;
		//	y = (int)tempPoint.getY() - y_min;
			this.getComponent(i).setLocation(new Point(x,y));
			
			//Testing
			//System.out.println(i + "th component's bounds : " + 
			//					this.getComponent(i).getBounds().toString());
		}
		
		// Set the bounds for CompositeBlockFD.
		
		int width = x_max - x_min + 2*extraWidth;
		int height = y_max - y_min + 2*extraHeight; // just big enough to contain all of them.
	//	int width = x_max - x_min;
	//	int height = y_max - y_min; // just big enough to contain all of them.
		
		Point tempPoint = this.getLocation();
		x = (int)tempPoint.getX() + x_min - extraWidth;
		y = (int)tempPoint.getY() + y_min - extraHeight;
	//	x = (int)tempPoint.getX() + x_min;
	//	y = (int)tempPoint.getY() + y_min;
		this.setBounds(x, y, width, height);
		
		if(this.getParent() instanceof BlockFD) {
			((CompositeBlockFD)this.getParent()).setAppropriateBounds();
		}
		//Testing
		//System.out.println("BlockWHILE.getBounds = " + this.getBounds().toString());
		
		// Repaint the everyThing?
		this.repaint();
		
	}
	/** Generate Line segments for one LineFD **/
	public void generateLineSegmentsForAllLines() {
		for(LineFD line :lineList) {
			GeometryTools.generateLineSegments(line);
			//generateLineSegments(line);
		}
	}
	
	/** override abstract method **/
	@Override 
	public JSONObject getGraphicalInfo() {
		JSONObject loc = new JSONObject();
		loc.put("x", (int)Math.round(this.getLocation().getX()/this.currentZoomRatio));
		loc.put("y", (int)Math.round(this.getLocation().getY()/this.currentZoomRatio));
		JSONObject ans = new JSONObject();
		ans.put(this.getModel().getString("Name"), loc);
		
		for (Component comp : this.getComponents()) {
			JSONObject tempInfo = ((BlockFD)comp).getGraphicalInfo();
			for(String key : tempInfo.keySet()) {
				ans.put(key, tempInfo.get(key));
			}
		}
		return ans;
	}
	@Override
	public void setUndoManager(UndoManager undoManager) {
		this.undoManager = undoManager;
		Component[] componentList = this.getComponents();
		for(Component comp:componentList ) {
			if(comp instanceof BlockFD) {
				((BlockFD)comp).setUndoManager(undoManager);
			}
		}
	}
	@Override
	public void setNameCounterManager(NameCounterManager nameManager) {
		// TODO Auto-generated method stub
		this.nameManager = nameManager;
		Component[] componentList = this.getComponents();
		for(Component comp:componentList ) {
			if(comp instanceof BlockFD) {
				((BlockFD)comp).setNameCounterManager(nameManager);
			}
		}
	}
	
	@Override
	protected boolean shouldAddBlockDrag() {
		return false;
	}
	
	@Override
	protected boolean isCompositeBlockFD() {
		return true;
	}
	
	@Override
	protected boolean shouldAddLoopDrag() {
		return false;
	}
	@Override
	protected boolean shouldAddEndLoopDrag() {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	protected boolean shouldAddBlockRightClick() {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public boolean isEditable() {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public boolean representCompositeBlock() {
		// TODO Auto-generated method stub
		return false;
	}
}
