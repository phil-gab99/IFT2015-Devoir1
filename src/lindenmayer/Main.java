package lindenmayer;

import lindenmayer.standard.*;

/**
 * The class Main handles user-passed arguments to generate the PostScript
 * files via the different implementations to generate it.
 * 
 * @author Philippe Gabriel
 * @version 1.0.5 2021-02-21
 */

public class Main {
    
    public static void main(String[] args) {
        
        try {
            
            generateEPSOutput(new EPSTurtle(), new LSystem(), args[0],
            Integer.parseInt(args[1]));
            
            generateEPSOutput(new EPSTurtleOptimized(), new LSystem(), args[0],
            Integer.parseInt(args[1]));
        } catch(ArrayIndexOutOfBoundsException e) {
            
            System.err.println("Insufficient arguments: " + e.getMessage());
        }
    }
    
    /**
     * The method {@link #generateEPSOutput} builds the {@link LSystem} and
     * configures the {@link Turtle} according the specified given filepath
     * and number of iterations passed.
     *
     * @param t {@link Turtle} which will take care of writing the PostScript
     * file
     * @param l {@link LSystem} holding the json file specifications for the
     * drawing
     * @param f File path to the json file to parse
     * @param n Integer indicating number of iterations for applying rules onto
     * a starting sequence specified in the json file
     */
    
    public static void generateEPSOutput(Turtle t, LSystem l, String f, int n) {
        
        try {
        
            JSONUtilsLSystem.readJSONFile(f, l, t);
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