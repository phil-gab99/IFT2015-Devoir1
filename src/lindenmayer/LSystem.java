package lindenmayer;

import java.awt.geom.Rectangle2D;

/**
 * The class LSystem
 * 
 * @author Philippe Gabriel
 * @version 1.0 2021-mm-dd
 */

public class LSystem {
    
    public Symbol addSymbol(char sym) {
        
        //TODO: Add symbol
    }
    
    public void addRule(Symbol sym, String expansion) {
        
        //TODO: Rule
    }
    
    public void setAction(Symbol sym, String expansion) {
        
        //TODO: Action
    }
    
    public void setAxiom(String str) {
        
    }
 
    public Symbol.Seq getAxiom() {
        
    }
    
    public Symbol.Seq rewrite(Symbol sym) {
        
    }
    
    public void tell(Turtle turtle, Symbol sym) {
        
    }
 
    public Symbol.Seq applyRules(Symbol.Seq seq, int n) {
        
    }
    
    public Rectangle2D tell(Turtle turtle, Symbol.Seq seq, int n) {
        
    }
}