package simple.geometry;

import java.awt.Point;
import java.awt.geom.*;

public class HalfLine {
	Point2D origin;
	double theta; // in radian, represents the direction.
	
	public HalfLine(Point2D origin, double theta) {
		this.origin = origin;
		this.theta = theta % (2 * Math.PI);
	}
	
	
	/** Getters and Setters **/
	public Point2D getOrigin() {
		return this.origin;
	}
	public void setOrigin(Point2D p) {
		this.origin = p;
	}
	public double getTheta() {
		return this.theta;
	}
	public void setTheta(double theta) {
		this.theta = theta % (2 * Math.PI);
	}
	/** Micro functions **/
	public boolean parallelTo(HalfLine halfLine) {
		return parallelTo(halfLine.getTheta());
	}
	public boolean parallelTo(double phi) {
		phi = phi % (2 * Math.PI);
		boolean sameDirection = Math.abs(phi - this.theta) < GeometricTools.Angle_Tol;
		boolean oppositeDirection = Math.abs(phi + this.theta) < GeometricTools.Angle_Tol;
		return sameDirection || oppositeDirection;
	}
	public boolean contains(Point2D p) {
		// if the points are very close to each other.
		if(this.origin.distance(p) < GeometricTools.Angle_Tol ) return true;
		
		// otherwise
		double dx = p.getX() - this.origin.getX();
		double dy = p.getY() - this.origin.getY();
		double phi = Math.atan2(dy, dx) % (2 * Math.PI);
		
		boolean sameDirection = Math.abs(phi - this.theta) < GeometricTools.Angle_Tol;
		return sameDirection;
		
	}
	
	/** Macro function **/
	public boolean intersects(HalfLine halfLine) {
		
		
		if(this.parallelTo(halfLine)) {
			if(this.contains(halfLine.getOrigin()) || halfLine.contains(this.getOrigin())) {
				// Testing
				System.out.println("One half line contain the other.");
				return false;
			}else return false;
			
		}else {
			// if not parallel.
			FullLine Line1 = new FullLine();
			Line1.setOrigin(this.origin);
			Line1.setTheta(this.theta);
			FullLine Line2 = new FullLine();
			Line2.setOrigin(halfLine.origin);
			Line2.setTheta(halfLine.getTheta());
			
			Point2D intersection = Line1.findIntersection(Line2);
			
			return this.contains(intersection) && halfLine.contains(intersection);
		}
	}
	
	public Point2D findIntersection(HalfLine halfLine) {
		FullLine Line1 = new FullLine();
		Line1.setOrigin(this.origin);
		Line1.setTheta(this.theta);
		FullLine Line2 = new FullLine();
		Line2.setOrigin(halfLine.origin);
		Line2.setTheta(halfLine.getTheta());
		
		Point2D intersection = Line1.findIntersection(Line2);
		
		
		return intersection;
	}
	
	
	
	/** Main function **/
	public static void main(String[] arg) {
		FullLine line1 = new FullLine(new Point(0,0), Math.PI/2);
		FullLine line2 = new FullLine(new Point(-3,-2), Math.PI/4);
		System.out.println(line1.findIntersection(line2));
		
		HalfLine line3 = new HalfLine(new Point(0,0),Math.PI);
		System.out.println(line3.contains(new Point2D.Double(-2,1)));
		
	}
}
