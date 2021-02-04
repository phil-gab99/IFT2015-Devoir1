package lindenmayer;

import java.awt.geom.Point2D;

/**
 * The interface Turtle
 * 
 * @author Philippe Gabriel
 * @version 1.0 2021-mm-dd
 */

public interface Turtle {
    
    /**
     * The method draw draws a line of unit length
     */
     
    void draw();
    
    /**
     * The method move moves the turtle by unit length without drawing
     */
    
    void move();
    
    /**
     * The method turnR turns the turtle clockwise by unit angle
     */
    
    void turnR();
    
    /**
     * The method turnL turns the turtle counter-clockwise by unit angle
     */
     
    void turnL();
    
    /**
     * The method push saves the current turtle state
     */
     
    void push();
    
    /**
     * The method pop recovers the most recently saved turtle state
     */
     
    void pop();
    
    /**
     * The method stay does nothing, the turtle's state is unchanged
     */
     
    void stay();
    
    void init();
}