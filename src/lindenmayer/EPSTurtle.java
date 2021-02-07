package lindenmayer;

import java.awt.geom.Point2D;
import java.awt.geom.Point2D.Double;
import java.awt.geom.Rectangle2D;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import java.util.Stack;

/**
 * The class {@link EPSTurtle} is responsible for creating the PostScript file
 * resulting from applying each {@link Symbol}'s paired action. The PostScript
 * operators used to accomplish this task are mainly <code>lineto</code> and
 * <code>moveto</code>. This class handles its angles in radians to facilitate
 * the calculation of each position's components.
 * 
 * @author Philippe Gabriel
 * @version 1.0 2021-mm-dd
 */

public class EPSTurtle implements Turtle {
    
    private BufferedWriter writer; //BufferedWriter for writing PostScript file
    private StringBuilder content; //Contents of Postscript file to be written
    
    private Rectangle2D boundBox; //Bounding Box necessary for page dimensions
    
    private Point2D coord; //Point2D indicating turtle's position
    private double orient; //Double indicating turtle's current orientation
    
    private double unitStep;  //Double holding the set unit step length
    private double unitAngle; //Double indicating set unit angle length
    
    private Stack<State> savedStates; //Stack keeping track of turtle states
    
    public void draw() {
        
        updateLocation();
        content.append(coord.getX() + " " + coord.getY() + " L\n");
    }
    
    public void move() {
        
        updateLocation();
        content.append(coord.getX() + " " + coord.getY() + " M\n");
    }
    
    public void turnR() {
        
        orient = (orient - unitAngle) % (2 * Math.PI);
        
        if (orient < 0) {
        
            orient += (2 * Math.PI);
        }
    }
    
    public void turnL() {
        
        orient = (orient + unitAngle) % (2 * Math.PI);
    }
    
    public void push() {
        
        content.append("currentpoint stroke newpath M\n");
        savedStates.push(new State(coord, orient));
    }
    
    public void pop() {
        
        State previous = savedStates.pop();
        
        coord = previous.position;
        orient = previous.angle;
        
        content.append("stroke\n");
        content.append(coord.getX() + " " + coord.getY() + " newpath M\n");
    }
    
    public void stay() {
        
        // Does nothing
    }
    
    public void init(Point2D pos, double angle_deg) {
        
        content.append("%!PS-Adobe-3.0 EPSF-3.0\n");
        content.append("%%Title: L-system\n");
        content.append("%%Creator: lindenmayer.EPSTurtle\n");
        content.append("%%BoundingBox: (atend)\n");
        content.append("%%EndComments\n");
        content.append("/M {moveto} bind def\n");
        content.append("/L {lineto} bind def\n");
        content.append("0.5 setlinewidth\n");
        
        coord = pos;
        orient = Math.toRadians(angle_deg);
        
        content.append("newpath " + coord.getX() + " " + coord.getY() + " M\n");
        
        savedStates = new Stack<State>();
    }
    
    public void end() {
        
        content.append("stroke\n");
        content.append("%%Trailer\n");
        content.append("%%BoundingBox: ");
        content.append((int)(boundBox.getMinX()) + " ");
        content.append((int)(boundBox.getMinY()) + " ");
        content.append((int)(boundBox.getMaxX()) + " ");
        content.append((int)(boundBox.getMaxY()) + "\n");
        content.append("%%EOF");
        
        try {
            
            writer.write(content.toString());
            writer.close();
        } catch(IOException e) {
            
            System.err.println("Error accessing file: " + e.getMessage());
            e.printStackTrace();
            System.exit(0);
        }
    }
    
    public Point2D getPosition() {
        
        return coord;
    }
    
    public double getAngle() {
        
        return orient;
    }
    
    public void setUnits(double step, double delta) {
        
        unitStep = step;
        unitAngle = Math.toRadians(delta);
    }
    
    /**
     * The setter method {@link #setBoundBox} sets the bounding box rectangle
     * necessary for the dimensions of the final drawing
     *
     * @param box Bounding box with proper position and dimensions
     */
    
    public void setBoundBox(Rectangle2D box) {
        
        boundBox = box;
    }
    
    public void createOutput(String path) throws IOException {
        
        content = new StringBuilder();
        
        try {
            
            int i = 1;
            File output = new File(path + ".eps");
            
            while (!output.createNewFile()) {
                
                output = new File(path + i++ + ".eps");
            }
            
            writer = new BufferedWriter(new FileWriter(output));
        } catch(IOException e) {
            
            throw new IOException("Unable to access file " + e.getMessage());
        }
    }
    
    /**
     * The method {@link #updateLocation} updates the turtle's location upon
     * the calling of a method which affected its position.
     */
    
    private void updateLocation() {
        
        Point2D distance = new Point2D.Double((unitStep * Math.cos(orient)),
        (unitStep * Math.sin(orient)));
    
        coord = new Point2D.Double(
        Math.round(10 * (coord.getX() + distance.getX())) / 10.0,
        Math.round(10 * (coord.getY() + distance.getY())) / 10.0);
    }
    
    /**
     * The class {@link State} defines the fields associated with a turtle's
     * state.
     */
    
    private static class State {
        
        private Point2D position; //Point2D object indicating turtle's position
        private double angle;     //Double indicating turtle's orientation
        
        /**
         * The constructor method {@link State} assigns the position and angle
         * values corresponding to the turtle's current state.
         *
         * @param position Point2D object indicating turtle's position
         * @param angle Double indicating turtle's orientation
         */
         
        private State(Point2D position, double angle) {
            
            this.position = position;
            this.angle = angle;
        }
    }
}