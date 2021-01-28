package de.tim_greller.mr_turing.turing_machine;

/**
 * This enumeration represents the different move directions a Turing machine's head can
 * perform on a tape.
 */
public enum TapeMove {

	/**	Moves the head one cell to the left. */
	LEFT, 
	
	/** Does not move the head. */
	NONE, 
	
	/** Moves the head one cell to the right. */
	RIGHT;
	
}
