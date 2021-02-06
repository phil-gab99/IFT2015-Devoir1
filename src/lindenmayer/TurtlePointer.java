package lindenmayer;

import java.awt.Point;

import java.awt.geom.Point2D;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import java.util.Stack;

/**
 * The class {@link TurtlePointer} manages the turtle actions and keeps track
 * of its states.
 * 
 * @author Philippe Gabriel
 * @version 1.0 2021-mm-dd
 */

public class TurtlePointer implements Turtle {
    
    private BufferedWriter writer;
    private File output;
    private StringBuffer content;
    
    private Point2D coord;    //Point2D indicating turtle's position
    private double orient;    //Double indicating turtle's current orientation
    
    private Point2D unitStep; //Point2D holding the set unit step components
    private double unitAngle; //Double indicating set unit angle
    
    private Stack<State> savedStates; //Stack keeping track of turtle states
    
    public void draw() {
        
        //TODO: Draws
        System.out.println("draw");
        updateLocation();
    }
    
    public void move() {
        
        //TODO: Moves
        updateLocation();
    }
    
    public void turnR() {
        
        if ((orient -= unitAngle) < 0) {
            
            orient += 360;
        } else {
            
            orient = orient % 360;
        }
    }
    
    public void turnL() {
        
        orient = (orient + unitAngle) % 360;
    }
    
    public void push() {
        
        savedStates.push(new State(coord, orient));
    }
    
    public void pop() {
        
        State previous = savedStates.pop();
        
        coord = previous.position;
        orient = previous.angle;
    }
    
    public void stay() {
        
        // content.append();
        //TODO: Stays
    }
    
    public void init(Point2D pos, double angle_deg) {
        
        content.append("%!PS-Adobe-3.0 EPSF-3.0\n");
        content.append("%%Title: L-system\n");
        content.append("%%Creator: lindenmayer.EPSTurtle\n");
        content.append("%%BoundingBox: (atend)\n");
        content.append("%%EndComments\n");
        
        coord = pos;
        orient = angle_deg;
        
        savedStates = new Stack<State>();
    }
    
    public Point2D getPosition() {
        
        return coord;
    }
    
    public double getAngle() {
        
        return orient;
    }
    
    public void setUnits(double step, double delta) {
        
        unitStep = makeCoordinates(step, delta);
        unitAngle = delta;
    }
    
    /**
     * The method {@link createOutput} creates the PostScript file used to
     * write the various the turtle actions to be undertaken.
     *
     * @param path String indicating path of file
     */
    
    public void createOutput(String path) {
        
        try {
            
            output = new File(path + ".eps");
            output.createNewFile();
            
            writer = new BufferedWriter(new FileWriter(output));
        } catch(IOException e) {
            
            e.printStackTrace();
        }
    }
    
    /**
     * The method {@link #makeCoordinates} converts a given length with help of
     * its orientation to its corresponding horizontal and vertical components.
     * 
     * @param length Double representing length module
     * @param delta Double representing length orientation
     * @return Point object holding the components of the given length
     */
    
    private Point2D makeCoordinates(double length, double angle) {
        
        Point2D point = new Point((int)(length * Math.cos(angle)),
        (int)(length * Math.sin(angle)));
        
        return point;
    }
    
    /**
     * The method {@link #updateLocation} updates the turtle's location upon
     * the calling of a method which affected its position.
     */
    
    private void updateLocation() {
    
        coord.setLocation(coord.getX() + unitStep.getX(),
        coord.getY() + unitStep.getY());
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