package de.tim_greller.mr_turing.bot.commands;

import org.reactivestreams.Publisher;

import de.tim_greller.mr_turing.turing_machine.TuringMachineManager;
import discord4j.core.object.entity.Message;
import reactor.core.publisher.Mono;

/**
 * This command creates a new transition to the current turing machine hold by the 
 * {@link TuringMachineManager}.
 */
public class TransitionCreationCommand implements BotCommand {

	@Override
	public String getTitle() {
		return "Add Transition";
	}

	@Override
	public String getDescription() {
		return "Creates a new transition and adds it to the Turing machine";
	}

	@Override
	public String getCallName() {
		return "add";
	}

	@Override
	public Publisher<?> execute(Message msg, String arg, TuringMachineManager tmManager) {
		return Mono.fromRunnable(() -> System.out.println(msg.getContent()));
	}

}
