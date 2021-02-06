package lindenmayer;

// import java.io.FileNotFoundException;
// import java.io.IOException;
// 
// import org.json.JSONException;

// Minor current issues to look into
    //The LSystem.tell(Turtle, Seq, int) questionnable recursion implementation
    //The EPSTurtle.pop() which doesn't follow requested guidelines but works
        //as opposed to failing when following guidelines
        //Don't forget to add first state initial position to avoid empty stack
    //The EPSTurtle.turnL and EPSTurtle.turnR which are coded in reverse manner
        //in the teacher provided outputs
    //Adding the bonus implementations (III.5a and III.5b)
    //Consider adding an EPSOptimizedTurtle class for shorter EPS files

public class Main {
    
    public static void main(String[] args) {
        
        Turtle turtle = new EPSTurtle();
        LSystem system = new LSystem();
        
        try {
            
            JSONUtilsLSystem.readJSONFile(args[0], system, turtle);
        } catch(Exception e) {
            
            System.err.println(e.getMessage());
            return;
        }
        
        ((EPSTurtle)turtle).setBoundBox(system.tell(turtle, system.getAxiom(), 5));
        turtle.end();
    }
}