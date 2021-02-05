package lindenmayer;

import java.awt.geom.Rectangle2D;

import java.io.FileReader;
import java.io.IOException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

// Implémentez les opérations principales du TA:
    //addSymbol                    DONE (Might need to prevent duplicates)
    //addRule                      DONE
    //setAction                    DONE
    //rewrite                      DONE
    //tell                         DONE
    //Rectangle2D tell             HEEEELP!!!!
    //setAxiom                     DONE
    //getAxiom                     DONE
    //classe Symbol                DONE (Could add more methods if needed)
    //implémentation de Symbol.Seq DONE (Could add more methods if needed)
    //classe LSystem.              WORKING ON IT
// Indices:
    //Utilisez toujours la même instance de Symbol         IMPORTANT KEEP TRACK
    //Table Map<Character,Symbol> est utile pour
        //les associations char → Symbol
    //Stocker les règles dans Map<Symbol,List<Symbol.Seq>> DONE
        //Permet de récupérer toutes les règles d’un
        //symbole dans une liste et en choisir un hasard
    //Possible d’implémenter tell en accédant aux méthodes DONE
        //de tortue directement (via getClass().getDeclaredMethod +
        //Method.invoke si une telle solution vous plaît) mais une série de
        //if-else (if ("draw".equals(str) ...) est plus simple à coder et nous
        //suffit parfaitement.

/**
 * The class LSystem
 * 
 * @author Philippe Gabriel
 * @version 1.0 2021-mm-dd
 */

public class LSystem extends AbstractLSystem {
    
    private ArrayList<Symbol> alphabet;
    private Map<Symbol, List<Symbol.Seq>> rules;
    private Map<Symbol, String> actions;
    private Symbol.Seq axiom;
    
    public LSystem() {
        
        alphabet = new ArrayList<Symbol>();
        rules = new HashMap<Symbol, List<Symbol.Seq>>();
        actions = new HashMap<Symbol, String>();
        axiom = new Sequence();
    }
    
    public Symbol addSymbol(char sym) {
        
        alphabet.add(new Symbol(sym));
        return alphabet.get(alphabet.size() - 1);
    }
    
    public void addRule(Symbol sym, String expansion) {

        //Retrieving expansions list for given symbol and appropriate sequence
        List<Symbol.Seq> expansionList = rules.get(sym);
        Symbol.Seq sequence = Sequence.strToSeq(expansion);
    
        if (rules.containsKey(sym)) { //Symbol already present in rules

            expansionList.add(sequence);
        } else {
            
            expansionList = new ArrayList<Symbol.Seq>();
            expansionList.add(sequence);
            rules.put(sym, expansionList);
        }
    }
    
    public void setAction(Symbol sym, String action) {

        actions.put(sym, action);
    }
    
    public void setAxiom(String str) {
    
        axiom = Sequence.strToSeq(str);
    }
    
    public Symbol.Seq getAxiom() {
        
        return axiom;
    }
    
    public Symbol.Seq rewrite(Symbol sym) {
        
        List<Symbol.Seq> expansions = rules.get(sym);
        
        if(expansions == null || expansions.size() == 0) {
            
            return null;
        }
        
        return expansions.get(new Random().nextInt(expansions.size()));
    }
    
    public void tell(Turtle turtle, Symbol sym) {
        
        try {
            
            turtle.getClass().getDeclaredMethod(actions.get(sym)).invoke(null, new Object[0]);
        } catch(Exception e) {
            
            e.printStackTrace();
        }
    }
    
    public Symbol.Seq applyRules(Symbol.Seq seq, int n) {
        
        Symbol.Seq interm;
        
        for (int i = 0; i < n; i++) {
            
            interm = new Sequence();
            
            for (Symbol s : seq) {
                
                Symbol.Seq exp = rewrite(s);
                
                if (exp == null) {
                    
                    interm.add(s);
                } else {
                    
                    interm.append(exp);
                }
            }
            
            seq = interm;
        }
        
        return seq;
    }
    
    public Rectangle2D tell(Turtle turtle, Symbol seq, int n) {
        
        return null;
    }
    
    public static void readJSONFile(String file, LSystem system, Turtle turtle)
    throws IOException {
        
        JSONObject input = new JSONObject(new JSONTokener(new FileReader(file)));
        
        JSONArray alphabet = input.getJSONArray("alphabet");
        JSONObject rules = input.getJSONObject("rules");
        String axiom = input.getString("axiom");
        JSONObject actions = input.getJSONObject("actions");
        JSONObject parameters = input.getJSONObject("parameters");
        
        createAlphabet(alphabet, system);
        createRules(rules, system);
        
        S.setAxiom(axiom);
    }
    
    private static void createAlphabet(JSONArray alphabet, LSystem system) {
        
        for (int i = 0; i < alphabet.length(); i++) {
            
            system.addSymbol(alphabet.get(i));
        }
    }
    
    private static void createRules(JSONObject rules, LSystem system) {
        
        for (String key : rules.keySet()) {
            
            
        }
    }
    
    /**
     * The class Sequence
     * 
     * 
     */
    
    private static class Sequence implements Symbol.Seq {
        
        private List<Symbol> elements = new ArrayList<Symbol>();;
        
        public void add(Symbol sym) {
            
            elements.add(sym);
        }
        
        public void append(Symbol.Seq sequence) {
            
            for (Symbol s : sequence) {
                
                add(s);
            }
        }
        
        public Iterator<Symbol> iterator() {
            
            return elements.iterator();
        }
        
        private static Symbol.Seq strToSeq(String characters) {
            
            Symbol.Seq sequence = new Sequence();
            char[] symbols = characters.toCharArray();
            
            for (char c : symbols) {
                
                sequence.add(new Symbol(c));
            }
            
            return sequence;
        }
    }
}