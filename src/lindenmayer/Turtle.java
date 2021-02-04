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
    
    /**
     * The method init intializes the turtle state and clears the stack holding
     * the saved states
     *
     * @param pos Point2D object describing the turtle's coordinates
     * @param angle_deg Double representing the turtle's orientation in degrees
     */
     
    void init(Point2D pos, double angle_deg);
    
    /**
     * The getter method getPosition retrieves the turtle's coordinates on the
     * plane
     *
     * @return The turtle's coordinates
     */
     
    Point2D getPosition();
    
    /**
     * The method getAngle retrieves the turtle's orientation on the plane
     * 
     * @return The turtle's orientation
     */
     
    double getAngle();
    
    /**
     * The method setUnits sets the unit step and unit angle to consider when
     * applying the other movement related methods
     *
     * @param step Double indicating length of an advance
     * @param delta Double indicating unit angle change in degrees
     */
     
    void setUnits(double step, double delta);
}