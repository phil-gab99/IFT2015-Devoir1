package lindenmayer;

import java.awt.Point;

import java.io.FileReader;
import java.io.IOException;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

/**
 * The class {@link JSONToolsLSystem} contains tools for parsing through a JSON
 * file in order to define an L-System.
 * 
 * @author Philippe Gabriel
 * @version 1.0 2021-mm-dd
 */

public class JSONUtilsLSystem {

    /**
     * The method {@link #readJSONFile} parses through a given JSON file and
     * extracts the appropriate items for constructing an LSystem instance.
     *
     * @param file File path of JSON file
     * @param system {@link LSystem} to build
     * @param turtle {@link Turtle} associated with system
     * @throws {@link java.io.IOException}
     */

    public static void readJSONFile(String file, LSystem system, Turtle turtle)
    throws IOException {
        
        JSONObject in = new JSONObject(new JSONTokener(new FileReader(file)));
        
        JSONArray alphabet = in.getJSONArray("alphabet");
        JSONObject rules = in.getJSONObject("rules");
        String axiom = in.getString("axiom");
        JSONObject actions = in.getJSONObject("actions");
        JSONObject parameters = in.getJSONObject("parameters");
        JSONArray start = parameters.getJSONArray("start");
        
        createAlphabet(alphabet, system);
        createRules(rules, system);
        system.setAxiom(axiom);
        createActions(actions, system);
        
        ((TurtlePointer)turtle).createOutput(
        file.substring(0, file.indexOf(".")));
        
        turtle.init(
        new Point(start.getInt(0), start.getInt(1)), start.getDouble(2));
        
        turtle.setUnits(
        parameters.getDouble("step"), parameters.getDouble("angle"));
    }

    /**
     * The method {@link #createAlphabet} generates a given {@link LSystem}'s
     * alphabet from the {@link #JSONArray} alphabet parsed from a JSON file.
     *
     * @param alphabet {@link #JSONArray} holding the symbols
     * @param system {@link LSystem} for which the alphabet is created
     */

    private static void createAlphabet(JSONArray alphabet, LSystem system) {
        
        for (int i = 0; i < alphabet.length(); i++) {
            
            system.addSymbol(alphabet.getString(i));
        }
    }

    /**
     * The method {@link #createRules} generates a given {@link LSystem}'s
     * rules from the {@link #JSONObject} rules parsed from a JSON file.
     *
     * @param rules {@link #JSONObject} holding the Symbol-Sequence pairs
     * @param system {@link LSystem} for which the rules are created
     */

    private static void createRules(JSONObject rules, LSystem system) {
        
        for (String key : rules.keySet()) {
            
            Symbol sym = system.getSymbol(key);
            
            if (sym != null) {
                
                JSONArray expansions = (JSONArray)(rules.get(key));
                
                for (int i = 0; i < expansions.length(); i++) {
                    
                    system.addRule(sym, expansions.getString(i));
                }
            }
        }
    }

    /**
     * The method {@link #createAactions} generates a given {@link LSystem}'s
     * actions from the {@link #JSONObject} actions parsed from a JSON file.
     *
     * @param actions {@link #JSONObject} holding the Symbol-Action pairs
     * @param system {@link LSystem} for which the alphabet is created
     */

    private static void createActions(JSONObject actions, LSystem system) {
        
        for (String key : actions.keySet()) {
            
            Symbol sym = system.getSymbol(key);
            
            system.setAction(sym, actions.getString(key));
        }
    }
}