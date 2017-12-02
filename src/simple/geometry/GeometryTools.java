package simple.geometry;

import java.awt.Point;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.util.ArrayList;

import gui.object.BlockFD;
import gui.object.LineFD;
import gui.object.PortFD;

public abstract class GeometryTools {
	public static final double Angle_Tol = 0.00000001;
	
	public static double modulo2PI(double theta) {
		return theta % (2*Math.PI);
	}
	
	public static void generateLineSegments(LineFD line) {
		ArrayList<Line2D> segments = line.getLineSegments();
		segments.clear();
		BlockFD source = line.getSource();
		BlockFD terminal = line.getTerminal();
		PortFD startPort = line.getStartPort();
		PortFD endPort = line.getEndPort();
		Point startPoint = startPort.getPortLocation();
		startPoint = source.toContainerCoordinate(startPoint);
		Point endPoint = endPort.getPortLocation();
		endPoint = terminal.toContainerCoordinate(endPoint);
		
		// Testing
		//System.out.println("In generateLineSegments(LineFD lineFD) : ");
		//System.out.println("lineFD : \n Source = " + lineFD.getSource().toString());
		//System.out.println(" startPoint = " + lineFD.getStartPoint().toString());
		//System.out.println(" Terminal = " + lineFD.getTerminal().toString());
		//System.out.println(" endPoint = " + lineFD.getEndPoint().toString());
	
		
		/** Normal line **/
		if(startPort.getSide().equals("bottom") && endPort.getSide().equals("top")) {
			//  points slightly away from start point and end point.
			Point2D p1 = new Point2D.Double(startPoint.getX(), startPoint.getY() + line.getTriggerRagius());
			Point2D p2 = new Point2D.Double(endPoint.getX(), endPoint.getY() - line.getTriggerRagius());
			segments.add(new Line2D.Double(startPoint,p1));
			segments.add(new Line2D.Double(p2,endPoint));
			
			if(p1.getY() >= p2.getY() && p1.getX() >= p2.getX()) {
				double SourceMinX = line.getSource().getBounds().getMinX();
				double TerminalMaxX = line.getTerminal().getBounds().getMaxX();
				
				Point2D p3 = new Point2D.Double((SourceMinX + TerminalMaxX)/2, p1.getY());
				Point2D p4 = new Point2D.Double((SourceMinX + TerminalMaxX)/2, p2.getY());
				segments.add(new Line2D.Double(p1,p3));
				segments.add(new Line2D.Double(p3,p4));
				segments.add(new Line2D.Double(p4,p2));
				
			}else if(p1.getY() >= p2.getY() && p1.getX() < p2.getX()) {
				double SourceMaxX = line.getSource().getBounds().getMaxX();
				double TerminalMinX = line.getTerminal().getBounds().getMinX();
				
				Point2D p3 = new Point2D.Double((SourceMaxX + TerminalMinX)/2, p1.getY());
				Point2D p4 = new Point2D.Double((SourceMaxX + TerminalMinX)/2, p2.getY());
				segments.add(new Line2D.Double(p1,p3));
				segments.add(new Line2D.Double(p3,p4));
				segments.add(new Line2D.Double(p4,p2));
			}
			else {
				Point2D center = new Point2D.Double((p1.getX() + p2.getX())/2, (p1.getY() + p2.getY())/2);
				Point2D p3 = new Point2D.Double(p1.getX(), center.getY());
				Point2D p4 = new Point2D.Double(p2.getX(), center.getY());
				segments.add(new Line2D.Double(p1,p3));
				segments.add(new Line2D.Double(p3,p4));
				segments.add(new Line2D.Double(p4,p2));
				
			}
			
		
		}
		/** Loop lines **/
		else if(startPort.getSide().equals("right") && endPort.getSide().equals("bottom")){
			//  points slightly away from start point and end point.
			Point2D p1 = new Point2D.Double(startPoint.getX() + line.getTriggerRagius(), startPoint.getY());
			Point2D p2 = new Point2D.Double(endPoint.getX(), endPoint.getY() + line.getTriggerRagius());
			segments.add(new Line2D.Double(startPoint,p1));
			segments.add(new Line2D.Double(p2,endPoint));
			
			Point2D p3 = new Point2D.Double(p1.getX(), p2.getY());
			segments.add(new Line2D.Double(p1,p3));
			segments.add(new Line2D.Double(p3,p2));
		}else if(startPort.getSide().equals("right") && endPort.getSide().equals("top")){
			//  points slightly away from start point and end point.
			Point2D p1 = new Point2D.Double(startPoint.getX() + line.getTriggerRagius(), startPoint.getY());
			Point2D p2 = new Point2D.Double(endPoint.getX(), endPoint.getY() - line.getTriggerRagius());
			segments.add(new Line2D.Double(startPoint,p1));
			segments.add(new Line2D.Double(p2,endPoint));
			
			if(p2.getX() >= p1.getX() && p2.getY() >= p1.getY()) {
				Point2D p3 = new Point2D.Double(p2.getX(), p1.getY());
				segments.add(new Line2D.Double(p1,p3));
				segments.add(new Line2D.Double(p3,p2));
			}else if(p2.getX() >= p1.getX() && p2.getY() < p1.getY()) {
				double x1 = line.getSource().getBounds().getMaxX();
				double x2 = line.getTerminal().getBounds().getMinX();
				
				Point2D p3 = new Point2D.Double((x2-x1)/2 + x1, p1.getY());
				Point2D p4 = new Point2D.Double((x2-x1)/2 + x1, p2.getY());
				
				segments.add(new Line2D.Double(p1,p3));
				segments.add(new Line2D.Double(p3,p4));
				segments.add(new Line2D.Double(p4,p2));
			}else if(p2.getX() < p1.getX()) {
				double y1 = line.getSource().getBounds().getMaxY();
				double y2 = line.getTerminal().getBounds().getMinY();
				
				Point2D p3 = new Point2D.Double(p1.getX(),(y2-y1)/2 + y1);
				Point2D p4 = new Point2D.Double(p2.getX(),(y2-y1)/2 + y1);
				
				segments.add(new Line2D.Double(p1,p3));
				segments.add(new Line2D.Double(p3,p4));
				segments.add(new Line2D.Double(p4,p2));
			}
			
		}else if(startPort.getSide().equals("bottom") && endPort.getSide().equals("bottom")){
			//  points slightly away from start point and end point.
			Point2D p1 = new Point2D.Double(startPoint.getX(), startPoint.getY() + line.getTriggerRagius());
			Point2D p2 = new Point2D.Double(endPoint.getX(), endPoint.getY() + line.getTriggerRagius());
			segments.add(new Line2D.Double(startPoint,p1));
			segments.add(new Line2D.Double(p2,endPoint));
			
			if(p2.getX() > line.getSource().getBounds().getMinX()) {
				double SourceMinX = line.getSource().getBounds().getMinX();
				double SourceMinY = line.getSource().getBounds().getMinY();
				Point2D p3 = new Point2D.Double(SourceMinX - line.getTriggerRagius(), p1.getY());
				Point2D p4 = new Point2D.Double(p3.getX(), SourceMinY - line.getTriggerRagius());
				Point2D p5 = new Point2D.Double(p2.getX(), p4.getY());
				segments.add(new Line2D.Double(p1,p3));
				segments.add(new Line2D.Double(p3,p4));
				segments.add(new Line2D.Double(p4,p5));
				segments.add(new Line2D.Double(p5,p2));
				
			}else {
				double maxY = Math.max(p1.getY(), p2.getY());
				Point2D p3 = new Point2D.Double(p1.getX(), maxY);
				Point2D p4 = new Point2D.Double(p2.getX(), maxY);
				
				segments.add(new Line2D.Double(p1,p3));
				segments.add(new Line2D.Double(p3,p4));
				segments.add(new Line2D.Double(p4,p2));
			}
		}
		/** If Lines **/
		// right-top has been dealt with.
		else if(startPort.getSide().equals("left") && endPort.getSide().equals("top")) {
		//  points slightly away from start point and end point.
			Point2D p1 = new Point2D.Double(startPoint.getX() - line.getTriggerRagius(), startPoint.getY());
			Point2D p2 = new Point2D.Double(endPoint.getX(), endPoint.getY() - line.getTriggerRagius());
			segments.add(new Line2D.Double(startPoint,p1));
			segments.add(new Line2D.Double(p2,endPoint));
			
			if(p2.getX() <= p1.getX() && p2.getY() >= p1.getY()) {
				Point2D p3 = new Point2D.Double(p2.getX(), p1.getY());
				segments.add(new Line2D.Double(p1,p3));
				segments.add(new Line2D.Double(p3,p2));
			}else if(p2.getX() <= p1.getX() && p2.getY() < p1.getY()) {
				double x1 = line.getSource().getBounds().getMaxX();
				double x2 = line.getTerminal().getBounds().getMinX();
				
				Point2D p3 = new Point2D.Double((x2-x1)/2 + x1, p1.getY());
				Point2D p4 = new Point2D.Double((x2-x1)/2 + x1, p2.getY());
				
				segments.add(new Line2D.Double(p1,p3));
				segments.add(new Line2D.Double(p3,p4));
				segments.add(new Line2D.Double(p4,p2));
			}else if(p2.getX() > p1.getX()) {
				double y1 = line.getSource().getBounds().getMaxY();
				double y2 = line.getTerminal().getBounds().getMinY();
				
				Point2D p3 = new Point2D.Double(p1.getX(),(y2-y1)/2 + y1);
				Point2D p4 = new Point2D.Double(p2.getX(),(y2-y1)/2 + y1);
				
				segments.add(new Line2D.Double(p1,p3));
				segments.add(new Line2D.Double(p3,p4));
				segments.add(new Line2D.Double(p4,p2));
			}
		}else if(startPort.getSide().equals("bottom") && endPort.getSide().equals("right")) {
			//  points slightly away from start point and end point.
				Point2D p1 = new Point2D.Double(startPoint.getX(), startPoint.getY() + line.getTriggerRagius());
				Point2D p2 = new Point2D.Double(endPoint.getX() + line.getTriggerRagius(), endPoint.getY());
				segments.add(new Line2D.Double(startPoint,p1));
				segments.add(new Line2D.Double(p2,endPoint));
				
				if(p1.getY() < p2.getY() && p1.getX() >= p2.getX()) {
					Point2D p3 = new Point2D.Double(p1.getX(), p2.getY());
					segments.add(new Line2D.Double(p1,p3));
					segments.add(new Line2D.Double(p3,p2));
				}else if(p1.getY() < p2.getY() && p1.getX() < p2.getX()) {
					double SourceMaxY = line.getSource().getBounds().getMaxY();
					double TerminalMinY = line.getTerminal().getBounds().getMinY();
					Point2D p3 = new Point2D.Double(p1.getX(),(SourceMaxY + TerminalMinY)/2);
					Point2D p4 = new Point2D.Double(p2.getX(),(SourceMaxY + TerminalMinY)/2);
					
					segments.add(new Line2D.Double(p1,p3));
					segments.add(new Line2D.Double(p3,p4));
					segments.add(new Line2D.Double(p4,p2));
				}else if(p1.getY() >= p2.getY() && line.getSource().getBounds().getMinX() >= p2.getX()) {
					double SourceMinX = line.getSource().getBounds().getMinX();
					double TerminalMaxX = line.getTerminal().getBounds().getMaxX();
					Point2D p3 = new Point2D.Double((SourceMinX + TerminalMaxX)/2, p1.getY());
					Point2D p4 = new Point2D.Double((SourceMinX + TerminalMaxX)/2, p2.getY());
					
					segments.add(new Line2D.Double(p1,p3));
					segments.add(new Line2D.Double(p3,p4));
					segments.add(new Line2D.Double(p4,p2));
				}
				else {// (p1.getY() >= p2.getY() && line.getSoutce().getBounds().getMinX() < p2.getX()
					double SourceMinY = line.getSource().getBounds().getMinY();
					double TerminalMaxY = line.getTerminal().getBounds().getMaxY();
					Point2D p3 = new Point2D.Double(line.getSource().getBounds().getMinX() - line.getTriggerRagius(), p1.getY());
					Point2D p4 = new Point2D.Double(p3.getX(), (SourceMinY + TerminalMaxY)/2);
					Point2D p5 = new Point2D.Double(p2.getX(),p4.getY());
					
					segments.add(new Line2D.Double(p1,p3));
					segments.add(new Line2D.Double(p3,p4));
					segments.add(new Line2D.Double(p4,p5));
					segments.add(new Line2D.Double(p5,p2));
				}
			}else if(startPort.getSide().equals("bottom") && endPort.getSide().equals("left")) {
			//  points slightly away from start point and end point.
				Point2D p1 = new Point2D.Double(startPoint.getX(), startPoint.getY() + line.getTriggerRagius());
				Point2D p2 = new Point2D.Double(endPoint.getX() - line.getTriggerRagius(), endPoint.getY());
				segments.add(new Line2D.Double(startPoint,p1));
				segments.add(new Line2D.Double(p2,endPoint));
				
				if(p1.getY() < p2.getY() && p1.getX() <= p2.getX()) {
					Point2D p3 = new Point2D.Double(p1.getX(), p2.getY());
					segments.add(new Line2D.Double(p1,p3));
					segments.add(new Line2D.Double(p3,p2));
				}else if(p1.getY() < p2.getY() && p1.getX() > p2.getX()) {
					double SourceMaxY = line.getSource().getBounds().getMaxY();
					double TerminalMinY = line.getTerminal().getBounds().getMinY();
					Point2D p3 = new Point2D.Double(p1.getX(),(SourceMaxY + TerminalMinY)/2);
					Point2D p4 = new Point2D.Double(p2.getX(),(SourceMaxY + TerminalMinY)/2);
					
					segments.add(new Line2D.Double(p1,p3));
					segments.add(new Line2D.Double(p3,p4));
					segments.add(new Line2D.Double(p4,p2));
				}else if(p1.getY() >= p2.getY() && line.getSource().getBounds().getMaxX() <= p2.getX()) {
					double SourceMaxX = line.getSource().getBounds().getMaxX();
					double TerminalMinX = line.getTerminal().getBounds().getMinX();
					Point2D p3 = new Point2D.Double((SourceMaxX + TerminalMinX)/2, p1.getY());
					Point2D p4 = new Point2D.Double((SourceMaxX + TerminalMinX)/2, p2.getY());
					
					segments.add(new Line2D.Double(p1,p3));
					segments.add(new Line2D.Double(p3,p4));
					segments.add(new Line2D.Double(p4,p2));
				}
				else {// (p1.getY() >= p2.getY() && line.getSoutce().getBounds().getMaxX() > p2.getX()
					double SourceMinY = line.getSource().getBounds().getMinY();
					double TerminalMaxY = line.getTerminal().getBounds().getMaxY();
					Point2D p3 = new Point2D.Double(line.getSource().getBounds().getMaxX() + line.getTriggerRagius(), p1.getY());
					Point2D p4 = new Point2D.Double(p3.getX(), (SourceMinY + TerminalMaxY)/2);
					Point2D p5 = new Point2D.Double(p2.getX(),p4.getY());
					
					segments.add(new Line2D.Double(p1,p3));
					segments.add(new Line2D.Double(p3,p4));
					segments.add(new Line2D.Double(p4,p5));
					segments.add(new Line2D.Double(p5,p2));
				}
			}else if(startPort.getSide().equals("right") && endPort.getSide().equals("right")) {
			//  points slightly away from start point and end point.
			Point2D p1 = new Point2D.Double(startPoint.getX() + line.getTriggerRagius(), startPoint.getY());
			Point2D p2 = new Point2D.Double(endPoint.getX() + line.getTriggerRagius(), endPoint.getY());
			segments.add(new Line2D.Double(startPoint,p1));
			segments.add(new Line2D.Double(p2,endPoint));
			
			Point2D p3 = new Point2D.Double(Math.max(p1.getX(),p2.getX()), p1.getY());
			Point2D p4 = new Point2D.Double(Math.max(p1.getX(),p2.getX()), p2.getY());
			
			segments.add(new Line2D.Double(p1,p3));
			segments.add(new Line2D.Double(p3,p4));
			segments.add(new Line2D.Double(p4,p2));
			}else if(startPort.getSide().equals("left") && endPort.getSide().equals("left")) {
			//  points slightly away from start point and end point.
			Point2D p1 = new Point2D.Double(startPoint.getX() - line.getTriggerRagius(), startPoint.getY());
			Point2D p2 = new Point2D.Double(endPoint.getX() - line.getTriggerRagius(), endPoint.getY());
			segments.add(new Line2D.Double(startPoint,p1));
			segments.add(new Line2D.Double(p2,endPoint));
			
			Point2D p3 = new Point2D.Double(Math.min(p1.getX(),p2.getX()), p1.getY());
			Point2D p4 = new Point2D.Double(Math.min(p1.getX(),p2.getX()), p2.getY());
			
			segments.add(new Line2D.Double(p1,p3));
			segments.add(new Line2D.Double(p3,p4));
			segments.add(new Line2D.Double(p4,p2));
			}
			
			else {
			segments.add( new Line2D.Double(startPoint,endPoint) );
		}
	}
	
}
