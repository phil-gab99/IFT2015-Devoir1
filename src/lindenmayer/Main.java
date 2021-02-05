package lindenmayer;

import java.io.IOException;

public class Main {
    
    public static void main(String[] args) throws IOException {
        
        LSystem l = new LSystem();
        LSystem.readJSONFile("./res/test.JSON", l);
    }
}