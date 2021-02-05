package lindenmayer;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * The class Symbol defines a symbol an L-system's alphabet
 * 
 * @author Philippe Gabriel
 * @version 1.0 2021-mm-dd
 */

public class Symbol {
    
    private final char value;
    
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

    @Override
    public int hashCode() {
        
        return Objects.hash(value);
    }

    public char getValue() {
        
        return this.value;
    }
    
    public interface Seq extends Iterable<Symbol> {
        
        void add(Symbol sym);
        void append(Seq sequence);
    }
}