import java.awt.Dimension;
import java.awt.Toolkit;
import java.util.Arrays;

public class RotatingTorus {
	public static Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	
	public final static double THETA_SPACING = 0.07; //  How fast theta increments
	public final static double PHI_SPACING = 0.02; // How fast phi increments
	public final static int R1 = 1; // Radius of the circle
	public final static int R2 = 2; //Radius of the torus
	public final static int K2 = 5; // Distance of the torus from the viewer
	//Distance from the viewer to the screen we project to. The maximum x-distance
	//occurs at the edge of the torus (x=R1+R2, z=0). We want that displayed 3/8th of the width of the screen
	//which is 3/4th the way from the center of the torus to the screen
	public final static double K1 = screenSize.getWidth()*K2*3/(8*(R1+R2)); 
	
	
    public static void main(String[] args) {
        
    }
    
    
    public static void render(double xAxisRotation, double zAxisRotation) {
    	//Note that in the explanation, xAxisRotation is A, and zAxisRotation is B.
    	
    	//precompute sines and cosines for xAxisRotation and zAxisRotation
    	double cosX = Math.cos(xAxisRotation), sinX = Math.sin(xAxisRotation);
    	double cosZ = Math.cos(zAxisRotation), sinZ = Math.sin(zAxisRotation);
    	
    	char output[][] = new char[(int) screenSize.getHeight()][(int) screenSize.getWidth()];
    	for(int i = 0; i<output.length;i++)Arrays.fill(output[i],' ');
    	double zBuffer[][] = new double[(int) screenSize.getHeight()][(int) screenSize.getWidth()];
    	
    	// We draw the circle centered at R2. We sweep an angle theta from 0 to 2Pi to get the circle.
    	for(double theta = 0; theta < 2*Math.PI; theta += THETA_SPACING) { 
    		double cosTheta = Math.cos(theta), sinTheta = Math.sin(theta); //precompute values
    		
    		//We rotate the circle around the center of revolution (y-axis) to create a torus.
    		for(double phi = 0; phi < 2*Math.PI;phi += PHI_SPACING) {
    			double cosPhi = Math.cos(phi), sinPhi = Math.sin(phi); //precompute values
    			
    			
    			double circleX = R2 + R1 * cosTheta; //X-coordinate on the circle before the rotation
    			double circleY = R1 * sinTheta; //Y-coordinate on the circle before the rotation
    			
    			//We take the dot product of the each point on the circle (R2+R1*cosTheta, R1*sinTheta, 0) 
    			// by the rotation matrices around the y-axis (by theta), x-axis (by xAxisRotation) and, 
    			// z-axis (zAxisRotation). We also add K2 to z for the distance to the viewer,
    			double x = circleX*(cosZ*cosPhi + sinX*sinZ*sinPhi) - circleY*cosX*sinZ;
    			double y = circleX*(cosPhi*cosZ - cosZ*sinX*sinPhi) + circleY*cosX*cosZ;
    			double z = K2+cosX*circleX*sinPhi+circleY*sinX;
    		}
    		
    		
    		
    	}
    	
    }
}