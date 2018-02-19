package simple.geometry;

import java.awt.geom.Point2D;

public class FullLine {
	Point2D origin;
	double theta; // in radian, represents the direction.
	
	public FullLine(){
		
	}
	public FullLine(Point2D origin, double theta) {
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
	public boolean parallelTo(FullLine line) {
		return parallelTo(line.getTheta());
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
		boolean oppositeDirection = Math.abs(phi + this.theta) < GeometricTools.Angle_Tol;
		return sameDirection || oppositeDirection;
	}
	
	/** Macro function **/
	public Point2D findIntersection(FullLine line) {
		if(this.parallelTo(line)) {
			if(this.contains(line.getOrigin())) {
				// ideally this situation should not happen.
				// we should do the check before finding the intersection.
				System.out.println("The full lines overlap.");
				System.out.println("This full line : \n p = " + this.origin.toString() + "\n theta = " + this.theta);
				System.out.println("Target full line : \n p = " + line.origin.toString() + "\n theta = " + line.theta);
				return null;
			}
			else{return null;}
			
		}else {
			double a = Math.cos(this.theta);
			double b = -Math.cos(line.theta);
			double c = line.origin.getX() - this.origin.getX();
			double d = Math.sin(this.theta);
			double e = -Math.sin(line.theta);
			double f = line.origin.getY() - this.origin.getY();
			
			double t1 = (e*c - b*f)/ (a*e - b*d);
			
			double intersection_x = this.origin.getX() + t1*Math.cos(this.theta);
			double intersection_y = this.origin.getY() + t1*Math.sin(this.theta);
			return new Point2D.Double(intersection_x, intersection_y);
		}
	}
}
