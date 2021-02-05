package lindenmayer;

import java.awt.Rectangle;

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

// Indices:
    //Table Map<Character,Symbol> est utile pour les associations char → Symbol

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
    
    /**
     * The constructor method {@link #LSystem} initializes the fields
     * associated with the current LSystem.
     */
    
    public LSystem() {
        
        alphabet = new ArrayList<Symbol>();
        rules = new HashMap<Symbol, List<Symbol.Seq>>();
        axiom = new Sequence();
        actions = new HashMap<Symbol, String>();
    }
    
    public Symbol addSymbol(char sym) {
        
        alphabet.add(new Symbol(sym));
        return alphabet.get(alphabet.size() - 1);
    }
    
    public Symbol getSymbol(char c) {
        
        Symbol sym = new Symbol(c);
        
        for (Symbol s : alphabet) {
            
            if (s.equals(sym)) {
                
                return s;
            }
        }
        
        return null;
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
    
    // Indice:
        //On veut éviter le calcul explicite de la chaîne complète
            //(donc ne pas juste faire applyRules(..,n), mais plutôt exploîter
            //la récursivité avec une tortue «intelligente».
        //Si n=0, on exécute directement la chaîne (avec tell(turtle, sym))
            //symbole-par-symbole, sinon on fait des appels récursifs de
            //tell(..,n-1)). Élaborez l’algorithme sur papier avant de coder
            //(p.e., vérifiez l’exécution avec n=2,3 et des règles simples).
        //Si la tortue passe par les coordonnées (x1, y1), … (xm, ym), alors
            //on a besoin des coordonnées extremes xmin=min{x1, …, xm},
            //xmax, ymin, et ymax (d’où la largeur et hauteur se calculent par
            //w=xmax–xmin et h=ymax–ymin).
        //Il est pratique de travailler avec des rectangles en calculant leur
            //union comme dictée par la géométrie (étudiez la documentation de
            //Rectangle2D).
    
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
        
        createAlphabet(alphabet, system);
        createRules(rules, system);
        system.setAxiom(axiom);
        createActions(actions, system);
        
        // Incomplete method, need to apply start parameters and initiate
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
            
            system.addSymbol((char)(alphabet.get(i)));
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
                
                system.addRule(sym, (String)(rules.get(key)));
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
            
            system.setAction(sym, (String)(actions.get(key)));
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