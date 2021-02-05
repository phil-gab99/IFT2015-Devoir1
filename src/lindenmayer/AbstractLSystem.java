package lindenmayer;

import java.awt.geom.Rectangle2D;

/**
 * The abstract class {@link @AbstractLSystem} illustrates the interface to an
 * L-System
 *
 * @author Philippe Gabriel
 * @version 1.0 2021-mm-dd
 */

public abstract class AbstractLSystem {
    
    /**
     * The method {@link #addSymbol} registers a new character in the alphabet.
     * Called while parsing the input specifying the alphabet for the L-system.
     * 
     * @param sym <code>char</code> used in the input to denote this symbol
     * @return Corresponding {@link Symbol} in the alphabet
     */
    public abstract Symbol addSymbol(char sym);
    
    /**
     * The method {@link #addRule} Adds a new rule to the grammar. Called while
     * parsing the input. Symbols on the right-hand side are encoded by
     * <code>char</code>s in the same way as in {@link #addSymbol(char)}. It is
     * allowed to add the same rule more than once - each one is stored as an
     * alternative.
     * 
     * @param sym {@link Symbol} on left-hand side to be rewritten by this rule
     * @param expansion Sequence on right-hand side
     */
    public abstract void addRule(Symbol sym, String expansion);
    
    /**
     * The method {@link #setAction} associates a turtle action with a symbol.
     * Called while parsing the input. The action must correspond to one of the
     * methods in {@link Turtle}: {@link Turtle#draw()}, {@link Turtle#move()},
     * {@link Turtle#turnL()}, {@link Turtle#turnR()}, {@link Turtle#stay()},
     * {@link Turtle#pop()}, {@link Turtle#push()}.
     * 
     * @param sym {@link Symbol} corresponding to a turtle action
     * @param action A turtle action
     * @see {@link Turtle}
     */
    public abstract void setAction(Symbol sym, String action);
    
    /**
     * The setter method {@link #setAxiom} defines the starting sequence for
     * the L-System. Called when parsing the input. Symbols are encoded by
     * <code>char</code>s as in {@link #addSymbol(char)}.
     * 
     * @param str Starting sequence
     */
    public abstract void setAxiom(String str);
 
    /**
     * The getter method {@link #getAxiom} retrieves the defined starting
     * sequence for the L-System.
     *
     * @return {@link Symbol#Seq} axiom defined as starting sequence
     */
    public abstract Symbol.Seq getAxiom();
    
    /**
     * The method {@link #rewrite} applies a symbol's rewriting rule.
     * <ul><li>If no rule was previously stored via {@link #addRule}, then it
     * returns null.</li><li>If a single rule was given, it uses the rule's
     * right-hand side</li><li>If multiple rules were given ({@link #addRule}
     * called with the same {@link Symbol} argument more than once),
     * then one of them is chosen randomly</li></ul>.
     *
     * @param sym {@link Symbol} to be rewritten
     * @return One of the applicable rules chosen randomly or null if no rule
     * applies
     */
    public abstract Symbol.Seq rewrite(Symbol sym);
    
    /**
     * The method {@link #tell} executes the action corresponding to a symbol
     * (specified by {@link #setAction}) on a given turtle.  
     * 
     * @param turtle {@link Turtle} object used for executing the action
     * @param sym {@link Symbol} that needs to be executed
     */
    public abstract void tell(Turtle turtle, Symbol sym);

    /**
     * The method {@link #applyRules} calculates the result of multiple rounds
     * of rewriting. Symbols with no rewriting rules are simply copied at each
     * round.
     *
     * @param seq {@link Symbol#Seq} object representing starting sequence
     * @param n Integer indicating number of rounds
     * @return {@link Symbol#Seq} obtained after rewriting the entire sequence
     * <var>n</var> times
     */
    public abstract Symbol.Seq applyRules(Symbol.Seq seq, int n);
    
    /**
     * The method {@link tell} draws the result after multiple rounds of
     * rewriting, starting from a given sequence. Symbols with no rewriting
     * rules are simply copied at each round.
     * 
     * @param turtle {@link Turtle} used for drawing
     * @param seq {@link Symbol.Seq} starting sequence
     * @param n Integer indicating number of rounds
     * @return bounding box (minimal rectangle covering all visited turtle positions)
     */
    public abstract Rectangle2D tell(Turtle turtle, Symbol.Seq seq, int n);
}