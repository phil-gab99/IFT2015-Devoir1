package lindenmayer;

import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;
import java.util.List;

// Implémentez les opérations principales du TA:
    //addSymbol                    DONE (Might need to prevent duplicates)
    //addRule                      DONE
    //setAction
    //rewrite
    //tell
    //setAxiom
    //getAxiom
    //classe Symbol                DONE (Could add more methods if needed)
    //implémentation de Symbol.Seq DONE (Could add more methods if needed)
    //classe LSystem.              WORKING ON IT
// Indices:
    //Utilisez toujours la même instance de Symbol         IMPORTANT KEEP TRACK
    //Table Map<Character,Symbol> est utile pour
        //les associations char → Symbol
    //Stocker les règles dans Map<Symbol,List<Symbol.Seq>> DONE
        //Permet de récupérer toutes les règles d’un
        //symbole dans une liste et en choisir un au hasard
    //Possible d’implémenter tell en accédant aux méthodes de tortue
        //directement (via getClass().getDeclaredMethod + Method.invoke si une
        //telle solution vous plaît) mais une série de if-else
        //(if ("draw".equals(str) ...) est plus simple à coder et nous suffit
        //parfaitement.

// IDEA: For rules of type Map<Symbol, List<Symbol.Seq>>, check instantiating
    //inner list -> https://stackoverflow.com/questions/16907594/list-as-value-of-hashmap-in-java

/**
 * The class LSystem
 * 
 * @author Philippe Gabriel
 * @version 1.0 2021-mm-dd
 */

public class LSystem {
    
    private ArrayList<Symbol> alphabet;
    private Map<Symbol, List<Symbol.Seq>> rules;
    // private HashMap<Character, ArrayList<Symbol.Seq>> rules = new HashMap<Character, ArrayList<Symbol.Seq>>(); // probably should be ArrayList<Seq>
    private HashMap<Character, Symbol.Seq> actions = new HashMap<Character, Symbol.Seq>();
    private Symbol.Seq axiom;
    
    public LSystem() {
        
        alphabet = new ArrayList<Symbol>();
        rules = new HashMap<Symbol, List<Symbol.Seq>>();
        axiom = new Sequence();
    }
    
    public Symbol addSymbol(char sym) {
        // (maybe warning) this could bind the element in the arraylist to the returned value
        
        alphabet.add(new Symbol(sym));
        return alphabet.get(alphabet.size() - 1);
    }
    
    public void addRule(Symbol sym, String expansion) {

        //Retreving expansions list for a given symbol and appropriate sequence
        List<Symbol.Seq> expansionList = rules.get(sym);
        Symbol.Seq sequence = Sequence.strToSeq(expansion);
    
        if (rules.containsKey(sym)) { //Symbol already present in rules

            //Avoid duplicate expansions
            if (!(expansionList.contains(sequence))) {
                
                expansionList.add(sequence);
            }
        } else {
            
            expansionList = new ArrayList<Symbol.Seq>();
            expansionList.add(sequence);
            rules.put(sym, expansionList);
        }
    }
    
    public void setAction(Symbol sym, String expansion) {
        //TODO: Action
        //this.actions.put(sym.getValue(), expansion);
    }
    
    public void setAxiom(String str) {
    
        // axiom = 
    }
    
    public Symbol.Seq getAxiom() {
        return this.axiom;
    }
    
    public Symbol.Seq rewrite(Symbol sym) {
        Character key = sym.getValue();
        if(!this.rules.containsKey(key) || this.rules.get(key).size() == 0){
            return null;
        }
        ArrayList<Symbol.Seq> temp = this.rules.get(key);
        return temp.get(new Random().nextInt(temp.size()));
    }
    
    public void tell(Turtle turtle, Symbol sym) {
    }
    
    public Symbol.Seq applyRules(Symbol.Seq seq, int n) {
        return null;
    }
    
    public Rectangle2D tell(Turtle turtle, Symbol.Seq seq, int n) {
        return null;
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