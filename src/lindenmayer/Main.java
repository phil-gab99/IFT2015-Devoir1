package lindenmayer;

// Minor current issues to look into
    //The LSystem.tell(Turtle, Seq, int) questionnable recursion implementation
    //The EPSTurtle.turnL and EPSTurtle.turnR which are coded in reverse manner
        //in the teacher provided outputs
    //Adding the bonus implementations (III.5a and III.5b)

public class Main {
    
    public static void main(String[] args) {
        
        generateEPSOutput(new EPSTurtle(), new LSystem(), args[0], 5);
        generateEPSOutput(new EPSTurtle(), new LSystem(), args[1], 7);
        generateEPSOutput(new EPSTurtle(), new LSystem(), args[2], 6);
        generateEPSOutput(new EPSTurtle(), new LSystem(), args[3], 8);
        
        generateEPSOutput(new EPSTurtleOptimized(), new LSystem(), args[0], 5);
        generateEPSOutput(new EPSTurtleOptimized(), new LSystem(), args[1], 7);
        generateEPSOutput(new EPSTurtleOptimized(), new LSystem(), args[2], 6);
        generateEPSOutput(new EPSTurtleOptimized(), new LSystem(), args[3], 8);
    }
    
    public static void generateEPSOutput(Turtle t, LSystem l, String path, int n) {
        
        try {
        
            JSONUtilsLSystem.readJSONFile(path, l, t);
        } catch(Exception e) {
        
            System.err.println(e.getMessage());
            return;
        }
        
        if (t instanceof EPSTurtle) {
            
            ((EPSTurtle)t).setBoundBox(l.tell(t, l.getAxiom(), n));
        } else if (t instanceof EPSTurtleOptimized) {
            
            ((EPSTurtleOptimized)t).setBoundBox(l.tell(t, l.getAxiom(), n));
        }
        
        t.end();
    }
}