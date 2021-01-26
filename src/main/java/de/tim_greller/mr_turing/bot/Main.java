package de.tim_greller.mr_turing.bot;

/**
 * This class runs the application by creating a {@link Bot} instance and logging it in
 * with the provided token.
 */
public class Main {

	/**
	 * Creates and starts the bot.
	 * 
	 * @param args The arguments required to build the bot. The first argument has to be
	 *             the token.
	 */
	public static void main(String[] args) {
		if (args.length < 1) {
			throw new IllegalArgumentException("No token recieved.");
		}
		
		String token = args[0];
		new Bot(token).login();
	}

}
