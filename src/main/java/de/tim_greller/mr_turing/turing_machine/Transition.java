package de.tim_greller.mr_turing.turing_machine;

/**
 * Instances of this class represent a transition of a valid Turing machine configuration
 * to another valid TM configuration. Applying a transition to a TM means to perform a
 * calculation step. The transition can change the TM's state, its head position and the
 * symbol at the current head position in the tape.
 */
public class Transition {
    
    /**
     * The state you have to be in for the transition to be applied. 
     */
    public final State currentState;
    
    /** 
     * The symbol that has to be on the current head index in order to apply this transition.
     */
    public final Symbol scannedSymbol;
    
    /**
     * The symbol that should be printed to the cell on the tape at the current head
     * position.
     */
    public final Symbol printSymbol;
    
    /**
     * The direction in which the head should be moved.
     */
    public final TapeMove tapeMotion;
    
    /**
     * The state in which the Turing machine will be after this transition was applied.
     */
    public final State nextState;

    /**
     * Constructs a new Transition with the given precondition and resulting actions.
     * 
     * @param currentState The initial state of the transition.
     * @param scannedSymbol The symbol that has to be scanned.
     * @param printSymbol The symbol that should get written to the tape.
     * @param tapeMotion The move direction of the head.
     * @param nextState The Turing machines next state.
     */
    public Transition(State currentState, Symbol scannedSymbol, Symbol printSymbol, 
            TapeMove tapeMotion, State nextState) {
        this.currentState = currentState;
        this.scannedSymbol = scannedSymbol;
        this.printSymbol = printSymbol;
        this.tapeMotion = tapeMotion;
        this.nextState = nextState;
    }
    
    /**
     * Determines if the given preconditions are equal to the ones of this transition.
     * 
     * @param currentState The initial state of the transition.
     * @param scannedSymbol The symbol that has to be scanned.
     * @return {@code true} if the values match and {@code false} if not.
     */
    public boolean matches(State currentState, Symbol scannedSymbol) {
        return (currentState.equals(this.currentState) 
                && scannedSymbol.equals(this.scannedSymbol));
    }
    
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Transition) {
            Transition t = (Transition) obj;
            return (t.matches(currentState, scannedSymbol) 
                    && t.printSymbol.equals(printSymbol)
                    && t.tapeMotion.equals(tapeMotion)
                    && t.nextState.equals(nextState));
        } else {
            return false;
        }
    }
}
