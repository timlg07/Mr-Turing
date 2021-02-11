package de.tim_greller.mr_turing.bot.commands;

import org.reactivestreams.Publisher;

import de.tim_greller.mr_turing.bot.InvalidCommandSyntaxException;
import de.tim_greller.mr_turing.turing_machine.TuringMachine;
import discord4j.core.object.entity.Message;

/**
 * This interface has to be implemented by each command that will be added to the Discord
 * bot.
 */
public interface BotCommand {
    
    /**
     * The full and fancy name of the command.
     * 
     * @return The command's title.
     */
    String getTitle();
    
    /**
     * This method should provide a description of the command that helps the user to use
     * it properly.
     * 
     * @return The description / help text. Basic Discord Markdown formating may be used.
     */
    String getDescription();
    
    /**
     * The name with which the user has to call the command. This name has to be unique:
     * If two commands have the same call-name, they cannot be both added to the same bot.
     * The call name is not case sensitive and is usually provided in lower case.
     * 
     * @return The name which which should trigger this command.
     */
    String getCallName();
    
    /**
     * This method should parse the given arguments to extract needed data. It can then
     * execute operations on the {@link TuringMachine} and respond to the user.
     * 
     * @param message The message that triggered this command. This will be used to give
     *                 results and feedback to the user.
     * @param argument The argument the user provided for this command call.
     * @param tm The {@link TuringMachine} of the corresponding channel.
     * 
     * @return A publisher that completes when this command has finished execution.
     * 
     * @throws InvalidCommandSyntaxException Thrown when the given argument is not of a
     *                                          valid syntax for this command.
     */
    Publisher<?> execute(Message message, String argument, TuringMachine tm) 
            throws InvalidCommandSyntaxException;
}
