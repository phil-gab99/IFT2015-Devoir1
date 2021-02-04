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
     * Stack and default movement values
     */
    
    public TurtlePointer() {
        
        savedStates = new Stack<State>();
    }
    
    public void draw() {
        
        //Draws
    }
    
    public void move() {
        
        updateLocation();
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
    
    // /**
    //  * The method updateLocation updates the turtle's location upon the calling
    //  * of a method which affected its position
    //  */
    // 
    // private void updateLocation() {
    // 
    //     coords.setLocation(coords.getX() + unitStep.getX(), coords.getY() + unitStep.getY());
    // }
    // 
    // /**
    //  * The method updateOrientation updates the turtle's orientation upon the
    //  * calling of a method which affected its orientation
    //  * 
    //  */
    // 
    // private void updateOrientation() {
    // 
    //     angle += unitAngle;
    // }
    
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
        
        /**
         * The getter method getPosition retrieves the turtle's current state's
         * position
         *
         * @return Point2D object indicating turtle's position
         */
        
        Point2D getPosition() {
            
            return position;
        }
        
        /**
         * The getter method getAngle retrieves the turtle's current state's
         * angle
         *
         * @return Double indicating turtle's orientation
         */
        
        double getAngle() {
            
            return angle;
        }
    }
}