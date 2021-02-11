package de.tim_greller.mr_turing.bot;

import de.tim_greller.mr_turing.bot.commands.*;

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
        createBot(token).login();
    }
    
    /**
     * Builds the Turing machine Discord bot and returns it.
     * 
     * @param token The bots login token.
     * @return The fully configured bot, ready to be logged in.
     */
    private static Bot createBot(String token) {
        Bot bot = new Bot(token);
        
        bot.addCommands(
            new TuringMachineCreationCommand(),
            new TransitionCreationCommand(),
            new SetInputCommand(),
            new BuildTuringMachineCommand(),
            new PrintConfigurationCommand(),
            new DoStepCommand(),
            new RunTuringMachineCommand()
        );
        
        return bot;
    }
}
