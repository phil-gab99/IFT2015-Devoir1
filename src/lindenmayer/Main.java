package lindenmayer;

import java.awt.geom.Rectangle2D;

import java.io.IOException;

// Minor current issues to look into
    //The LSystem.tell(Turtle, Seq, int) questionnable recursion implementation
    //The EPSTurtle.pop() which doesn't follow requested guidelines but works
        //as opposed to failing when following guidelines
    //The EPSTurtle.turnL and EPSTurtle.turnR which are coded in reverse manner
        //in the teacher provided outputs
    //Adding the bonus implementations (III.5a and III.5b)
    //Consider adding an EPSOptimizedTurtle class for shorter EPS files

public class Main {
    
    public static void main(String[] args) throws IOException {
        
        Turtle turtle = new EPSTurtle();
        LSystem system = new LSystem();
        JSONUtilsLSystem.readJSONFile(args[0], system, turtle);
        
        ((EPSTurtle)turtle).setBoundBox(system.tell(turtle, system.getAxiom(), 5));
        turtle.end();
    }
}