package lindenmayer;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * The class {@link Symbol} defines a symbol within an L-system's alphabet.
 * 
 * @author Philippe Gabriel
 * @version 1.0 2021-mm-dd
 */

public class Symbol {
    
    private final char value;
    
    /**
     * The constructor method {@link Symbol} assigns a given <code>char</code>
     * value to the current instance's field.
     *
     * @param value <code>char</code> of the symbol
     */
    
    public Symbol(char value) {
        
        this.value = value;
    }
    
    /**
     * @see java.lang.Object
     */
    @Override
    public String toString() {
        
        return Character.toString(value);
    }

    /**
     * @see java.lang.Object
     */
    @Override
    public boolean equals(Object o) {
        
        if (o == this) { //Same reference
            
            return true;
        }
        
        if (o == null || !(o instanceof Symbol)) {
            
            return false;
        }
        
        return value == ((Symbol)o).value;
    }

    /**
     * @see java.lang.Object
     */
    @Override
    public int hashCode() {
        
        return Objects.hash(value);
    }
    
    /**
     * The interface {@link Seq} defines a sequence of symbols and other useful
     * methods for manipulating sequences.
     */
    
    public interface Seq extends Iterable<Symbol> {
        
        /**
         * The method {@link #add} adds an additional symbol onto the sequence.
         *
         * @param sym {@link Symbol} to be added
         */
         
        void add(Symbol sym);
        
        /**
         * The method {@link append} appends a sequence of symbols onto the
         * current sequence.
         *
         * @param sequence {@link Seq} to be appended
         */
         
        void append(Seq sequence);
    }
}