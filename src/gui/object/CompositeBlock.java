package gui.object;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Line2D;
import java.util.ArrayList;

import javax.swing.BorderFactory;

import org.json.JSONObject;

public abstract class CompositeBlock extends BlockFD{

	protected ArrayList<LineFD> lineList = new ArrayList<LineFD>();
	
	public CompositeBlock(JSONObject model) {
		super(model);
		
		this.setLayout(null);
		
		// Temporary
		this.setOpaque(false);
		this.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		
	}
	
	/** Add line and remove line**/
	public void removeLineFD(LineFD line) {
		this.lineList.remove(line);
	}
	public void addLineFD(LineFD line) {
		this.lineList.add(line);
	}
	
	/** Paint Component function**/
	// This function is responsible for painting the lines.
	@Override
	public void paintComponent(Graphics g) {
		Graphics2D g2 = (Graphics2D)g;
		
		for(int i = 0; i < lineList.size(); i++) {
			for(int j = 0; j < lineList.get(i).getLineSegments().size(); i++) {
				Line2D segment = lineList.get(i).getLineSegments().get(j);
				g2.setColor(Color.red);
				g2.draw(segment);
			}
		}
	}
	
	/** Generate Line segments for one LineFD **/
	public void generateLineSegments(LineFD lineFD) {
		ArrayList<Line2D> segments = lineFD.getLineSegments();
		
		// We use straight line for the moment
		segments.add(new Line2D.Double(lineFD.getStartPoint(),lineFD.getEndPoint()));
	}
	public void generateLineSegmentsForAllLines() {
		for(LineFD line :lineList) {
			generateLineSegments(line);
		}
	}
}
