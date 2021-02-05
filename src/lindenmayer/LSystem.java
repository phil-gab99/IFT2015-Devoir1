package lindenmayer;

import java.awt.Rectangle;

import java.awt.geom.Rectangle2D;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;
import java.util.List;

/**
 * The class {@link #LSystem} builds its data structures by calling
 * {@link #addSymbol}, {@link #addRule}, {@link #addRule}, {@link #setAxiom}
 * and {@link #setAction}. The implementation provides access to symbols as
 * instances of {@link Symbol}, and to sequences as {@link Symbol.Seq}.
 * 
 * @author Philippe Gabriel
 * @version 1.0 2021-mm-dd
 */

public class LSystem extends AbstractLSystem {
    
    private List<Symbol> alphabet; // Alphabet associated with LSystem
    private Map<Symbol, List<Symbol.Seq>> rules; // Symbol-Sequence pair rules
    private Symbol.Seq axiom; // Starting sequence of current LSystem
    private Map<Symbol, String> actions; // Symbol-Action pairing
    private Map<Character, Symbol> charSymPairs;
    
    /**
     * The constructor method {@link #LSystem} initializes the fields
     * associated with the current LSystem.
     */
    
    public LSystem() {
        
        alphabet = new ArrayList<Symbol>();
        rules = new HashMap<Symbol, List<Symbol.Seq>>();
        axiom = new Sequence();
        actions = new HashMap<Symbol, String>();
        charSymPairs = new HashMap<Character, Symbol>();
    }
    
    public Symbol addSymbol(char sym) {
        
        alphabet.add(new Symbol(sym));
        charSymPairs.put(sym, alphabet.get(alphabet.size() - 1));
        
        return alphabet.get(alphabet.size() - 1);
    }
    
    public Symbol getSymbol(char c) {
        
        return charSymPairs.get(c);
    }
    
    public Symbol getSymbol(String str) {
        
        if (str.length() > 1) {
            
            return null;
        }
        
        return getSymbol(str.charAt(0));
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
    
    public void setAxiom(String str) {
    
        axiom = Sequence.strToSeq(str);
    }
    
    public void setAction(Symbol sym, String action) {

        actions.put(sym, action);
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
            
            turtle.getClass().getDeclaredMethod(actions.get(sym))
            .invoke(null, new Object[0]);
        } catch(Exception e) {
            
            e.printStackTrace();
        }
    }
    
    public Symbol.Seq applyRules(Symbol.Seq seq, int n) {
        
        Symbol.Seq interm; // Intermediate sequence after each iteration
        
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
    
    public Rectangle2D tell(Turtle turtle, Symbol.Seq seq, int n) {
        
        if (n == 0) {
            
            Rectangle2D rec1 = new Rectangle((int)(Turtle.DFLT_COORD.getX()),
            (int)(Turtle.DFLT_COORD.getY()), 0, 0);
            
            for (Symbol s : seq) {
                
                tell(turtle, s);
                
                int xmin = (int)Math.min(Turtle.DFLT_COORD.getX(),
                turtle.getPosition().getX());
                int xmax = (int)Math.max(Turtle.DFLT_COORD.getX(),
                turtle.getPosition().getX());
                
                int ymin = (int)Math.min(Turtle.DFLT_COORD.getY(),
                turtle.getPosition().getY());
                int ymax = (int)Math.max(Turtle.DFLT_COORD.getY(),
                turtle.getPosition().getY());
                
                Rectangle2D rec2 = new Rectangle(xmin, ymin, xmax - xmin, ymax - ymin);
                
                Rectangle2D.union(rec1, rec2, rec1);
            }
            
            return rec1;
        } else {
            
            seq = applyRules(seq, 1);
            return tell(turtle, seq, n - 1);
        }
    }
    
    /**
     * The class {@link Sequence} defines a sequence of symbols and provides a
     * set of useful methods for managing sequences.
     *
     * @see Symbol.Seq
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
        
        /**
         * @see java.lang.Iterable
         */
        @Override
        public Iterator<Symbol> iterator() {
            
            return elements.iterator();
        }
        
        /**
         * The method {@link #strToSeq} converts a String into a
         * {@link Sequence} of {@link Symbol}'s.
         *
         * @param characters String to be converted
         */
         
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