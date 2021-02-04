package lindenmayer;

import java.awt.geom.Point2D;
import java.awt.Point;
import java.util.Stack;

/**
 * The class TurtlePointer manages the turtle actions and keeps track of its
 * states.
 * 
 * @author Philippe Gabriel
 * @version 1.0 2021-mm-dd
 */

public class TurtlePointer implements Turtle {
    
    private Point2D coords;   //Point2D indicating turtle's position
    private double orient;    //Double indicating turtle's current orientation
    
    private Point2D unitStep; //Point2D holding the set unit step components
    private double unitAngle; //Double indicating set unit angle
    
    private Stack<State> savedStates; //Stack keeping track of turtle states
    
    /**
     * The constructor method TurtlePointer initializes a Turtle with an empty
     * Stack and default position and movement values
     */
    
    public TurtlePointer() {
        
        init(new Point(0, 0), 0);
    }
    
    public void draw() {
        
        //TODO: Draws
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
        
        savedStates.push(new State(coords, orient));
    }
    
    public void pop() {
        
        State previous = savedStates.pop();
        
        coords = previous.position;
        orient = previous.angle;
    }
    
    public void stay() {
        
        //TODO: Stays
    }
    
    public void init(Point2D pos, double angle_deg) {
        
        coords = pos;
        orient = angle_deg;
        
        unitStep = new Point(0, 0);
        unitAngle = 0;
        
        savedStates = new Stack<State>();
    }
    
    public Point2D getPosition() {
        
        return coords;
    }
    
    public double getAngle() {
        
        return orient;
    }
    
    public void setUnits(double step, double delta) {
        
        unitStep = makeCoordinates(step, delta);
        unitAngle = delta;
    }
    
    /**
     * The method makeCoordinates converts a given length with help of its
     * orientation to its corresponding horizontal and vertical components.
     * 
     * @param length Double representing length module
     * @param delta Double representing length orientation
     * @return Point object holding the components of the given length
     */
    
    private Point2D makeCoordinates(double length, double angle) {
        
        return new Point(length * Math.cos(angle), length * Math.sin(angle));
    }
    
    /**
     * The method updateLocation updates the turtle's location upon the calling
     * of a method which affected its position
     */
    
    private void updateLocation() {
    
        coords.setLocation(coords.getX() + unitStep.getX(), coords.getY() + unitStep.getY());
    }
    
    /**
     * The class State defines the fields associated with a turtle's state
     */
    
    private static class State {
        
        private Point2D position; //Point2D object indicating turtle's position
        private double angle;     //Double indicating turtle's orientation
        
        /**
         * The constructor method State assigns the position and angle values
         * corresponding to the turtle's current state
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