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
    	//precompute sines and cosines for xAxisRotation and zAxisRotation
    	double cosX = Math.cos(xAxisRotation), sinX = Math.sin(xAxisRotation);
    	double cosZ = Math.cos(zAxisRotation), sinZ = Math.sin(zAxisRotation);
    	
    	char output[][] = new char[(int) screenSize.getHeight()][(int) screenSize.getWidth()];
    	for(int i = 0; i<output.length;i++)Arrays.fill(output[i],' ');
    	double zBuffer[][] = new double[(int) screenSize.getHeight()][(int) screenSize.getWidth()];
    	
    }
}