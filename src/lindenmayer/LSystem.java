package lindenmayer;

import java.awt.geom.Rectangle2D;
import java.awt.geom.Rectangle2D.Double;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * The class {@link #LSystem} builds its data structures by calling
 * {@link #addSymbol}, {@link #addRule}, {@link #addRule}, {@link #setAxiom}
 * and {@link #setAction}. The implementation provides access to symbols as
 * instances of {@link Symbol}, and to sequences as {@link Symbol.Seq}.
 * 
 * @author Philippe Gabriel
 * @version 1.10.4 2021-02-21
 */

public class LSystem extends AbstractLSystem {
    
    private List<Symbol> alphabet; // Alphabet associated with LSystem
    private Map<Symbol, List<Symbol.Seq>> rules; // Symbol-Sequence pair rules
    private Symbol.Seq axiom; // Starting sequence of current LSystem
    private Map<Symbol, String> actions; // Symbol-Action pairing
    private Map<Character, Symbol> charSymPairs; //Char-Symbol Pairing
    
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
    
    
    @Override
    public Symbol addSymbol(char sym) {
        
        alphabet.add(new Symbol(sym));
        charSymPairs.put(sym, alphabet.get(alphabet.size() - 1));
        
        return alphabet.get(alphabet.size() - 1);
    }
    
    @Override
    public Symbol addSymbol(String str) {
        
        if (str.length() > 1) {
            
            return null;
        }
        
        return addSymbol(str.charAt(0));
    }
    
    @Override
    public Symbol getSymbol(char c) {
        
        return charSymPairs.get(c);
    }
    
    @Override
    public Symbol getSymbol(String str) {
        
        if (str.length() > 1) {
            
            return null;
        }
        
        return getSymbol(str.charAt(0));
    }
    
    @Override
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
    
    @Override
    public void setAxiom(String str) {
    
        axiom = Sequence.strToSeq(str);
    }
    
    @Override
    public Symbol.Seq getAxiom() {
        
        return axiom;
    }
    
    @Override
    public void setAction(Symbol sym, String action) {

        actions.put(sym, action);
    }
    
    @Override
    public Symbol.Seq rewrite(Symbol sym) {
        
        List<Symbol.Seq> expansions = rules.get(sym);
        
        if(expansions == null || expansions.size() == 0) {
            
            return null;
        }
        
        return expansions.get(new Random().nextInt(expansions.size()));
    }
    
    @Override
    public void tell(Turtle turtle, Symbol sym) {
        
        try { //Invoking method paired with given symbol
            
            turtle.getClass().getDeclaredMethod(actions.get(sym))
            .invoke(turtle, new Object[0]);
        } catch(Exception e) {
            
            e.printStackTrace();
        }
    }
    
    @Override
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
    
    @Override
    public Rectangle2D tell(Turtle turtle, Symbol.Seq seq, int n) {
        
        if (n == 0) {
            
            Rectangle2D rec1 = new Rectangle2D.Double
            (turtle.getPosition().getX(), turtle.getPosition().getY(), 0, 0);
            
            for (Symbol s : seq) {
                
                tell(turtle, s);
                
                double xMin, xMax;
                double yMin, yMax;
                
                if (rec1.getMinX() < turtle.getPosition().getX()) {
                    
                    xMin = rec1.getMinX();
                    xMax = turtle.getPosition().getX();
                } else {
                    
                    xMin = turtle.getPosition().getX();
                    xMax = rec1.getMinX();
                }
                
                if (rec1.getMinY() < turtle.getPosition().getY()) {
                    
                    yMin = rec1.getMinY();
                    yMax = turtle.getPosition().getY();
                } else {
                    
                    yMin = turtle.getPosition().getY();
                    yMax = rec1.getMinY();
                }
                
                Rectangle2D rec2 = new Rectangle2D.Double(xMin, yMin, xMax - xMin, yMax - yMin);
                
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
        
        @Override
        public void add(Symbol sym) {
            
            elements.add(sym);
        }
        
        @Override
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
         * @return The {@link Symbol.Seq} variant
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