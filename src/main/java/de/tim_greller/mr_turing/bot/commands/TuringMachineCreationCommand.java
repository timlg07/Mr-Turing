package de.tim_greller.mr_turing.bot.commands;

import org.reactivestreams.Publisher;

import de.tim_greller.mr_turing.turing_machine.TuringMachine;
import discord4j.core.object.entity.Message;
import reactor.core.publisher.Mono;

/**
 * This command tells the {@link TuringMachineManager} of the channel it was triggered in
 * to create a completely new Turing machine.
 */
public class TuringMachineCreationCommand implements BotCommand {

    @Override
    public String getTitle() {
        return "New Turing machine";
    }

    @Override
    public String getDescription() {
        return "Creates a new modifiable Turing Machine. If a Turing machine was created "
                + "on this channel before, the old one will get deleted.";
    }

    @Override
    public String getCallName() {
        return "new";
    }

    @Override
    public Publisher<?> execute(Message msg, String arg, TuringMachine tm) {
        tm.clear();
        return Mono.empty();
    }

}
