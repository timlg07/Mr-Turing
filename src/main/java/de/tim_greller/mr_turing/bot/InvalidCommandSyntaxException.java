package de.tim_greller.mr_turing.bot;

/**
 * A exception that should be thrown when a user tried to execute a command with invalid
 * syntax.
 */
public class InvalidCommandSyntaxException extends Exception {

	/** Auto-generated serial version UID */
	private static final long serialVersionUID = -1759803108586275629L;
	
	/**
	 * Creates a new exception with the given reason.
	 * 
	 * @param reason The reason why the syntax is invalid. Will be shown to the user who
	 *               tried to execute the command.
	 */
	public InvalidCommandSyntaxException(String reason) {
		super(reason);
	}

}
