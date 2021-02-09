package de.tim_greller.mr_turing.turing_machine;

/**
 * This class models symbols that can be written to a tape of a Turing machine.
 */
public class Symbol {

    /** The representation of the symbol. */
    private final String content;
    
    /**
     * Creates a new Symbol with the specified content.
     * 
     * @param content The {@link String} this symbol should represent.
     */
    public Symbol(String content) {
        this.content = content;
    }
    
    /**
     * Creates a new Symbol with the specified content.
     * 
     * @param content The single character this symbol should represent.
     */
    public Symbol(char content) {
        this(Character.toString(content));
    }

    @Override
    public String toString() {
        return content;
    }
    
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Symbol) {
            return ((Symbol) obj).toString().equals(toString());
        } else {
            return false;
        }
    }
}
