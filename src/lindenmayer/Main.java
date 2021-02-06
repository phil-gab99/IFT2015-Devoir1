package lindenmayer;

import java.io.IOException;

public class Main {
    
    public static void main(String[] args) throws IOException {
        
        Turtle turtle = new TurtlePointer();
        LSystem system = new LSystem();
        JSONUtilsLSystem.readJSONFile(args[0], system, turtle);
    }
}