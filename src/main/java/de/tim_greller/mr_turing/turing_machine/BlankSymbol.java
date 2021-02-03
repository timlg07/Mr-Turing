package de.tim_greller.mr_turing.turing_machine;

/**
 * A special {@link Symbol} that represents a blank cell on the tape.
 */
public class BlankSymbol extends Symbol {

	/**
	 * Creates a new blank symbol with the specified representation.
	 * 
	 * @param content The {@link String} that should represent a blank cell.
	 */
	public BlankSymbol(String content) {
		super(content);
	}

}
