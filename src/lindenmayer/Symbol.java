package lindenmayer;

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
     * 
     * 
     * @see java.lang.Object
     */
    @Override
    public String toString() {
        
        return Character.toString(value);
    }
    
    public interface Seq extends Iterable<Symbol> {}
}