package gui.object;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Line2D;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;

import javax.swing.BorderFactory;

import org.json.JSONObject;

import gui.manager.UndoManager;

public abstract class CompositeBlockFD extends BlockFD{

	protected ArrayList<LineFD> lineList = new ArrayList<LineFD>();
	protected PropertyChangeListener repaintLineListener = 
			e -> {generateLineSegmentsForAllLines();
				// Testing
				//System.out.println("line changes detected by parent bock");
																
				repaint(); // repaint the lines
			};
	
	
	public CompositeBlockFD(JSONObject model) {
		super(model);
		
		this.setLayout(null);
				
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
		line.removePropertyChangeListener(repaintLineListener);
		this.lineList.remove(line);
	}
	public void addLineFD(LineFD line) {
		line.addPropertyChangeListener(repaintLineListener);
		this.lineList.add(line);
		
	}
	
	/** Paint Component function**/
	// This function is responsible for painting the lines.
	@Override
	public void paintComponent(Graphics g) {
		Graphics2D g2 = (Graphics2D)g;
		
		for(int i = 0; i < lineList.size(); i++) {
			for(int j = 0; j < lineList.get(i).getLineSegments().size(); j++) {
				ArrayList<Line2D> segments = lineList.get(i).getLineSegments();
				Line2D segment = segments.get(j);
				g2.setColor(Color.red);
				g2.draw(segment);
			}
		}
	}
	
	/** Generate Line segments for one LineFD **/
	public void generateLineSegments(LineFD lineFD) {
		ArrayList<Line2D> segments = lineFD.getLineSegments();
		segments.clear();
		
		// Testing
		//System.out.println("In generateLineSegments(LineFD lineFD) : ");
		//System.out.println("lineFD : \n Source = " + lineFD.getSource().toString());
		//System.out.println(" startPoint = " + lineFD.getStartPoint().toString());
		//System.out.println(" Terminal = " + lineFD.getTerminal().toString());
		//System.out.println(" endPoint = " + lineFD.getEndPoint().toString());
		
		// We use straight line for the moment
		segments.add(new Line2D.Double(lineFD.getStartPoint(),lineFD.getEndPoint()));
	}
	public void generateLineSegmentsForAllLines() {
		for(LineFD line :lineList) {
			generateLineSegments(line);
		}
	}
	
	/** override abstract method **/
	@Override
	protected void setCustomBounds(int x, int y, int width, int height) {
		this.setBounds(x,y,width,height);
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
}
