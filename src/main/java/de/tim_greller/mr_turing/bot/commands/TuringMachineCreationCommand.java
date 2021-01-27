package de.tim_greller.mr_turing.bot.commands;

import de.tim_greller.mr_turing.turing_machine.TuringMachineManager;
import discord4j.core.object.entity.Message;

/**
 * This command tells the {@link TuringMachineManager} of the channel it was triggered in
 * to create a completely new Turing machine.
 */
public class TuringMachineCreationCommand implements BotCommand {

	@Override
	public String getName() {
		return "New Turing machine";
	}

	@Override
	public String getDescription() {
		return "Creates a new Turing Machine. You need to use this command before you can"
			   + " configure the Turing machine. If a Turing machine was created on this "
			   + "channel before, the old one will get deleted.";
	}

	@Override
	public String getCallName() {
		return "new";
	}

	@Override
	public void execute(Message msg, String arg, TuringMachineManager tmManager) {
		// TODO: This is only a demo implementation for testing purposes.
		System.out.println("command 'new' triggered by: " + msg.getContent());
	}

}
