package de.tim_greller.mr_turing.turing_machine;

/**
 * This enumeration represents the different states in which a Turing machine can be in.
 */
public enum TMState {
	
	/** The TM is in its initial state. */
	initialState,
	
	/** The TM is no longer in its initial state and has not yet terminated. */
	running,
	
	/** The TM has terminated and accepts the given input. */
	accepting,
	
	/** The TM has terminated and does not accept the input. */
	denying;

}
