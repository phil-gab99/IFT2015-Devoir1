package lindenmayer;

import java.awt.geom.Point2D;
import java.util.Stack;

/**
 * The class TurtlePointer manages the turtle actions and keeps track of its
 * states.
 * 
 * @author Philippe Gabriel
 * @version 1.0 2021-mm-dd
 */

public class TurtlePointer implements Turtle {
    
    public Stack<State> savedStates;
    
    public TurtlePointer() {
        
        savedStates = new Stack<State>();
    }
    
    public void draw() {
        
        //Draws
    }
    
    public void setUnits(double step, double delta) {
        
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