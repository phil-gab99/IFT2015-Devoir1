package lindenmayer;

import java.awt.geom.Point2D;
import java.util.Stack;

public class TurtlePointer implements Turtle {
    
    Stack<State> savedStates;
    
    
    
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