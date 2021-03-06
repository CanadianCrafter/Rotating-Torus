import java.awt.Dimension;
import java.awt.Toolkit;
import java.util.Arrays;

public class RotatingTorus {
//	public static Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	
	public final static double X_AXIS_SPACING = 0.04; //  How fast xAxisRotation increments
	public final static double Z_AXIS_SPACING = 0.02; // How fast zAxisRotation increments
	public final static double THETA_SPACING = 0.07; //  How fast theta increments
	public final static double PHI_SPACING = 0.02; // How fast phi increments
	public final static int R1 = 1; // Radius of the circle
	public final static int R2 = 2; //Radius of the torus
	public final static int K2 = 5; // Distance of the torus from the viewer
	//Distance from the viewer to the screen we project to. The maximum x-distance
	//occurs at the edge of the torus (x=R1+R2, z=0). We want that displayed 3/8th of the width of the screen
	//which is 3/4th the way from the center of the torus to the screen
	public final static int screenWidth = 80;
	public final static int screenHeight = 22;
	public final static double K1 = screenWidth*K2*3/(8*(R1+R2)); 
	
	public final static char luminanceChar[] = {'.',',','-','~',':',';','=','!','*','#','$','@'};
	
	
    public static void main(String[] args) {
    	//Note that in the explanation, xAxisRotation is A, and zAxisRotation is B.
    	double xAxisRotation = 0;
    	double zAxisRotation = 0;
    	
    	while(true) {
    		//precompute sines and cosines for xAxisRotation and zAxisRotation
        	double cosX = Math.cos(xAxisRotation), sinX = Math.sin(xAxisRotation);
        	double cosZ = Math.cos(zAxisRotation), sinZ = Math.sin(zAxisRotation);

        	char output[][] = new char[screenHeight][screenWidth];
        	for(int i = 0; i<output.length;i++)Arrays.fill(output[i],' ');
        	double zBuffer[][] = new double[screenHeight][screenWidth];
        	
        	// We draw the circle centered at R2. We sweep an angle theta from 0 to 2Pi to get the circle.
        	for(double theta = 0; theta < 2*Math.PI; theta += THETA_SPACING) { 
        		double cosTheta = Math.cos(theta), sinTheta = Math.sin(theta); //precompute values
        		
        		//We rotate the circle around the center of revolution (y-axis) to create a torus.
        		for(double phi = 0; phi < 2*Math.PI;phi += PHI_SPACING) {
        			double cosPhi = Math.cos(phi), sinPhi = Math.sin(phi); //precompute values
        			
        			double circleX = R2 + R1 * cosTheta; //X-coordinate on the circle before the rotation
        			double circleY = R1 * sinTheta; //Y-coordinate on the circle before the rotation
        			
        			double t = sinPhi*circleX*cosX-sinTheta*sinX;// Factoring of some terms in xProj and yProj
        			
        			//We take the product of the each point on the circle (R2+R1*cosTheta, R1*sinTheta, 0) 
        			// by the rotation matrices around the y-axis (by theta), x-axis (by xAxisRotation) and, 
        			// z-axis (zAxisRotation). We also add K2 to z for the distance to the viewer,
//        			double x = circleX*(cosZ*cosPhi + sinX*sinZ*sinPhi) - circleY*cosX*sinZ;
//        			double y = circleX*(cosPhi*sinZ - cosZ*sinX*sinPhi) + circleY*cosX*cosZ;
//        			double z = K2+cosX*circleX*sinPhi+circleY*sinX;
        			double z = K2+sinX*circleX*sinPhi+circleY*cosX;
        			double ooz = 1/z; // one over z
        			
        			
        			// project from 3D to our 2D screen. y is negated since y goes up in 3D space
        			// but down in 2D displays
//        			int xProj = (int) (screenWidth/2 + K1*x*ooz);
//        			int yProj = (int) (screenHeight/2 - K1*y*ooz);
        			int xProj = (int) (40+30*ooz*(cosPhi*circleX*cosZ-t*sinZ));
        			int yProj = (int) (12+15*ooz*(cosPhi*circleX*sinZ+t*cosZ));
        			
        			//Lighting. Take the dot product of the normal vectors by (0, 1, -1), the light source.
        			// The surface normal is the dot product of a point on a unit circle at the origin (cosTheta, sinTheta, 0)
        			// and the same rotation matrices.
//        			double L = cosPhi*cosTheta*sinZ - cosX*cosTheta*sinPhi-sinX*sinTheta+cosZ*(cosX*sinTheta-cosTheta*sinX*sinPhi);
        			double L = (sinTheta * sinX - sinPhi * cosTheta * cosX) * cosZ - sinPhi * cosTheta * sinX - sinTheta * cosX - cosPhi * cosTheta * sinZ;
        			
        			//L ranges from -sqrt(2) to sqrt(2), if L < 0, then it's surface faces away from the light. 
        			// Since the light is behind and above the viewer at (0,1,-1), this means they can't see it, so it's not drawn
        			if(L > 0) {
        				//test valid
        				if(yProj >= 0 && yProj < zBuffer.length && xProj >= 0 && xProj < zBuffer[0].length) {
        					//test against the z-buffer. Larger 1/z means the pixel is closer to the viewer than what's already plotted (since z is distance to viewer)
            				if(ooz > zBuffer[yProj][xProj]) {
            					zBuffer[yProj][xProj] = ooz;
            					int luminanceIndex = (int) (L*8);
            					output[yProj][xProj] = luminanceChar[luminanceIndex];
            				}
        				}
        			}
        		}
        		
        	}
        	//Print output to the screen
    		System.out.print("\u001b[H"); // Clears terminal
    		for(int row = 0; row < output.length; row++) {
    			for(int col = 0; col <output[row].length; col++) {
    				System.out.print(output[row][col]);
    			}
    			System.out.println();
    		}
    		xAxisRotation+=X_AXIS_SPACING;
    		zAxisRotation+=Z_AXIS_SPACING;

    	}
    }
    
}