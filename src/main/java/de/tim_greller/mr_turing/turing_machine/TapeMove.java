package de.tim_greller.mr_turing.turing_machine;

/**
 * This enumeration represents the different move directions a Turing machine's head can
 * perform on a tape.
 */
public enum TapeMove {

    /**    Moves the head one cell to the left. */
    LEFT, 
    
    /** Does not move the head. */
    NONE, 
    
    /** Moves the head one cell to the right. */
    RIGHT;

    /**
     * Returns the tape move represented by the given String. If the String is no valid
     * tape move, an {@link IllegalArgumentException} is thrown.
     * 
     * @param inp The tape moves string representation.
     * @return The corresponding tape move.
     */
    public static TapeMove from(String inp) {
        switch(inp.toUpperCase()) {
        case "L":
        case "LEFT":
            return LEFT;
        
        case "N":
        case "0":
        case "NONE":
            return NONE;
            
        case "R":
        case "RIGHT":
            return RIGHT;
            
        default:
            throw new IllegalArgumentException(
                    "The given String is not a valid tape move. A tape move can be: "
                    + "LEFT, RIGHT or NONE. The short forms are L, R and N.");
        }
    }
    
}
