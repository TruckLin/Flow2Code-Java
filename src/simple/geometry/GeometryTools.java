package simple.geometry;

public abstract class GeometryTools {
	public static final double Angle_Tol = 0.00000001;
	
	public static double modulo2PI(double theta) {
		return theta % (2*Math.PI);
	}
	
}
