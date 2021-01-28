package de.tim_greller.mr_turing.turing_machine;

/**
 * Instances of this class represent a transition of a valid Turing machine configuration
 * to another valid TM configuration. Applying a transition to a TM means to perform a
 * calculation step. The transition can change the TM's state, its head position and the
 * symbol at the current head position in the tape.
 */
public class Transition {

	public Transition(State currentState, Symbol scannedSymbol, Symbol printSymbol, 
			TapeMove tapeMotion, State nextState) {
		
	}
}
