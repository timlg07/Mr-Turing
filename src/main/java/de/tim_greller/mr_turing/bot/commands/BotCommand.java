package de.tim_greller.mr_turing.bot.commands;

import de.tim_greller.mr_turing.turing_machine.TuringMachineManager;
import discord4j.core.object.entity.Message;

/**
 * This interface has to be implemented by each command that will be added to the Discord
 * bot.
 */
public interface BotCommand {
	
	/**
	 * The full and fancy name of the command.
	 * 
	 * @return The command's title. Basic Discord Markdown formating can be used.
	 */
	String getTitle();
	
	/**
	 * This method should provide a description of the command that helps the user to use
	 * it properly.
	 * 
	 * @return The description / help text. Basic Discord Markdown formating can be used.
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
	 * This method should parse the given message to extract needed data. It can then
	 * execute operations from the {@link TuringMachineManager} and/or respond to the
	 * user.
	 * 
	 * @param message   The message that triggered this command. This will be used to give
	 *                  results and feedback to the user.
	 * @param argument  The argument the user provided for this command call.
	 * @param tmManager The {@link TuringMachineManager} of the corresponding channel.
	 */
	void execute(Message message, String argument, TuringMachineManager tmManager);
}
