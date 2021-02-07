package lindenmayer;

// import java.io.FileNotFoundException;
// import java.io.IOException;
// 
// import org.json.JSONException;

// Minor current issues to look into
    //The LSystem.tell(Turtle, Seq, int) questionnable recursion implementation
    //The EPSTurtle.turnL and EPSTurtle.turnR which are coded in reverse manner
        //in the teacher provided outputs
    //Adding the bonus implementations (III.5a and III.5b)

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
        
        Turtle turtle2 = new EPSTurtleOptimized();
        LSystem system2 = new LSystem();
        
        try {
        
            JSONUtilsLSystem.readJSONFile(args[0], system2, turtle2);
        } catch(Exception e) {
        
            System.err.println(e.getMessage());
            return;
        }
        
        ((EPSTurtleOptimized)turtle2).setBoundBox(system2.tell(turtle2, system2.getAxiom(), 5));
        turtle2.end();
    }
}