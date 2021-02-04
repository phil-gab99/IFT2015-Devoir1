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
    
    public Point2D distance;
    public double angle;
    
    public Stack<State> savedStates;
    
    public TurtlePointer() {
        
        savedStates = new Stack<State>();
    }
    
    public void draw() {
        
        //Draws
    }
    
    
    /**
     * The method setUnits sets the unit step and the unit angle to apply with
     * the graphical methods.
     *
     * @param step Double indicating unit step
     * @param delta Double indicating unit angle
     */
    
    public void setUnits(double step, double delta) {
        
        distance = makeCoordinates(step, delta);
        angle = delta;
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
    
    private static class State {
        
        private Point2D position;
        private double angle;
        
        private State(Point2D position, double angle) {
            
            this.position = position;
            this.angle = angle;
        }
        
        Point2D getPosition() {
            
            return position;
        }
        
        double getAngle() {
            
            return angle;
        }
    }
}