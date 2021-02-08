package de.tim_greller.mr_turing.turing_machine;

/**
 * Instances of this class represent a transition of a valid Turing machine configuration
 * to another valid TM configuration. Applying a transition to a TM means to perform a
 * calculation step. The transition can change the TM's state, its head position and the
 * symbol at the current head position in the tape.
 */
public class Transition {
    
    public final State currentState;
    public final Symbol scannedSymbol;
    public final Symbol printSymbol;
    public final TapeMove tapeMotion;
    public final State nextState;

    public Transition(State currentState, Symbol scannedSymbol, Symbol printSymbol, 
            TapeMove tapeMotion, State nextState) {
        this.currentState = currentState;
        this.scannedSymbol = scannedSymbol;
        this.printSymbol = printSymbol;
        this.tapeMotion = tapeMotion;
        this.nextState = nextState;
    }
    
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
