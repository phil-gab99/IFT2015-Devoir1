package lindenmayer;

import java.awt.geom.Rectangle2D;

import java.io.IOException;

public class Main {
    
    public static void main(String[] args) throws IOException {
        
        Turtle turtle = new TurtlePointer();
        LSystem system = new LSystem();
        JSONUtilsLSystem.readJSONFile(args[0], system, turtle);
        
        ((TurtlePointer)turtle).setBoundBox(system.tell(turtle, system.getAxiom(), 5));
        turtle.stay();
    }
}