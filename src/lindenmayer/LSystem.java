package lindenmayer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.awt.geom.Rectangle2D;
import java.util.Random;

/**
 * The class LSystem
 * 
 * @author Philippe Gabriel
 * @version 1.0 2021-mm-dd
 */

public class LSystem {
    
    private ArrayList<Symbol> alphabet;
    private HashMap<Character, ArrayList<Symbol.Seq>> rules = new HashMap<Character, ArrayList<Symbol.Seq>>(); // probably should be ArrayList<Seq>
    private HashMap<Character, Symbol.Seq> actions = new HashMap<Character, Symbol.Seq>();
    private Symbol.Seq axiom;
    
    public LSystem() {
        alphabet = new ArrayList<Symbol>();
    }
    
    public Symbol addSymbol(char sym) {
        // (maybe warning) this could bind the element in the arraylist to the returned value
        alphabet.add(new Symbol(sym));
        return new Symbol(sym);
    }
    
    public void addRule(Symbol sym, String expansion) {
        //TODO: Rule
        Character key = sym.getValue();
        ArrayList<Symbol.Seq> temp;
        if(this.rules.containsKey(key)){
            temp = this.rules.get(key);
        }else{
            temp = new ArrayList<Symbol.Seq>();
        }
        //temp.add(expansion); // add the expansion therefore String -> Seq
        this.rules.put(key, temp);

    }
    
    public void setAction(Symbol sym, String expansion) {
        //TODO: Action
        //this.actions.put(sym.getValue(), expansion);
    }
    
    public void setAxiom(String str) {
        //this.axiom = str;
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
}