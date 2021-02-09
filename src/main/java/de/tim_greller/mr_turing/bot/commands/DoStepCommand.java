package de.tim_greller.mr_turing.bot.commands;

import org.reactivestreams.Publisher;

import de.tim_greller.mr_turing.bot.InvalidCommandSyntaxException;
import de.tim_greller.mr_turing.turing_machine.TuringMachine;
import discord4j.core.object.entity.Message;
import reactor.core.publisher.Mono;

public class DoStepCommand implements BotCommand {

    @Override
    public String getTitle() {
        return "Perform a calculation step";
    }

    @Override
    public String getDescription() {
        return "Performs one calculation step of the Turing machine.";
    }

    @Override
    public String getCallName() {
        return "step";
    }

    @Override
    public Publisher<?> execute(Message message, String argument, TuringMachine tm)
            throws InvalidCommandSyntaxException {

        tm.performStep();
        
        return Mono.empty();
    }

}
