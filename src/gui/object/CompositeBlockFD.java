package gui.object;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;

import javax.swing.BorderFactory;

import org.json.JSONObject;

import gui.LinePopup;
import gui.manager.NameCounterManager;
import gui.manager.UndoManager;
import gui.mouselistener.LineRightClickListener;
import simple.geometry.FullLine;
import simple.geometry.HalfLine;

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
		this.generateLineSegments(line);
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
													BasicStroke.CAP_SQUARE,BasicStroke.JOIN_ROUND, 10.0f);
					g2.setStroke(myStroke);
					g2.draw(segment);
				}
				g2.setColor(currentLine.getLineColor());
				g2.setStroke(new BasicStroke());
				g2.draw(segment);
				
				
			}
		}
	}
	
	/** Generate Line segments for one LineFD **/
	public static String findWhichSide(BlockFD block, Point p) {
		/*				  top
		 * 			-----------------
		 * 			|				|
		 * 	left 	|				| right 
		 * 			|				|
		 * 			-----------------
		 * 				 bottom
		*/ 
		Rectangle rec = block.getBounds();
		double left = rec.getLocation().getX();
		double right = rec.getLocation().getX() + rec.getWidth() - 1;
		double top = rec.getLocation().getY();
		double bottom = rec.getLocation().getY() + rec.getHeight() - 1;

		double x = p.getX();
		double y = p.getY();
		
		if( Math.abs(left - x) <= 1 )  return "left";
		else if( Math.abs(right - x) <= 1 ) return "right";
		else if( Math.abs(bottom - y) <= 1 ) return "bottom";
		else if( Math.abs(top - y) <= 1 ) return "top";
		else {
			// Testing 
			System.err.println("Cannot find which side : ");
			System.out.println("block type = " + block.getModel().getString("Type"));
			System.out.println("block bounds = " + block.getBounds().toString());
			System.out.println("Point = " + p.toString());
			System.out.println("left = " + left);
			System.out.println("right = " + right);
			System.out.println("top = " + top);
			System.out.println("bottom = " + bottom);
			
			
			return null;
		}
	}
	
	public static HalfLine findHalfLine(BlockFD block, Point p) {
		String side = findWhichSide(block,p);
		
		if(side.equals("bottom")) {
			return new HalfLine(p, Math.PI * 1.5);
		}else if(side.equals("top")) {
			return new HalfLine(p, Math.PI * 0.5);
		}else if(side.equals("left")) {
			return new HalfLine(p, Math.PI);
		}else {
			return new HalfLine(p, 0);
		}
	}
	 
	public void generateLineSegments(LineFD line) {
		final int min_length = 5;
		ArrayList<Line2D> segments = line.getLineSegments();
		segments.clear();
		BlockFD source = line.getSource();
		BlockFD terminal = line.getTerminal();
		Point startPoint = line.getStartPoint();
		Point endPoint = line.getEndPoint();
		
		// find the point that is just a little bit away from the block.
		String sourceSide = this.findWhichSide(source, startPoint);
		String terminalSide = this.findWhichSide(terminal, endPoint);
		
		// Testing
		if(sourceSide == null) {
			System.err.println("Source didn't find correct side.");
			System.out.println();
		}else if(terminalSide == null) {
			System.err.println("Terminal didn't find correct side.");
		}
		
		
		double dx = endPoint.getX() - startPoint.getX();
		double dy = endPoint.getY() - startPoint.getY();
		// Testing
		//System.out.println("In generateLineSegments(LineFD lineFD) : ");
		//System.out.println("lineFD : \n Source = " + line.getSource().toString());
		//System.out.println(" startPoint = " + line.getStartPoint().toString());
		//System.out.println(" Terminal = " + line.getTerminal().toString());
		//System.out.println(" endPoint = " + line.getEndPoint().toString());
		
		// We use straight line for the moment
		//segments.add(new Line2D.Double(line.getStartPoint(),line.getEndPoint()));
		
		FullLine horizontalLine = new FullLine(startPoint, 0);
		FullLine verticalLine = new FullLine(startPoint, Math.PI*0.5);
		if(horizontalLine.contains(endPoint) || verticalLine.contains(endPoint)) {
			segments.add(new Line2D.Double(startPoint, endPoint));
		}else {
			Point center = line.getCentrePoint();
			Point p1 = new Point((int)startPoint.getX(), (int)center.getY());
			Point p2 = new Point((int)endPoint.getX(), (int)center.getY());
			segments.add(new Line2D.Double(startPoint, p1 ));
			segments.add(new Line2D.Double(p1,p2));
			segments.add(new Line2D.Double(p2,endPoint));
			
		}
	}
	public void generateLineSegmentsForAllLines() {
		for(LineFD line :lineList) {
			generateLineSegments(line);
		}
	}
	
	/** override abstract method **/
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
