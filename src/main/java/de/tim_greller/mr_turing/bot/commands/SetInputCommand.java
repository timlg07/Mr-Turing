package de.tim_greller.mr_turing.bot.commands;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.reactivestreams.Publisher;

import de.tim_greller.mr_turing.bot.InvalidCommandSyntaxException;
import de.tim_greller.mr_turing.turing_machine.TuringMachine;
import discord4j.core.object.entity.Message;
import reactor.core.publisher.Mono;

public class SetInputCommand implements BotCommand {

    @Override
    public String getTitle() {
        return "Set Input";
    }

    @Override
    public String getDescription() {
        return "Sets the input of a Turing machine by writing the given string to its tape";
    }

    @Override
    public String getCallName() {
        return "input";
    }

    @Override
    public Publisher<?> execute(Message message, String argument, TuringMachine tm)
            throws InvalidCommandSyntaxException {

        tm.setInput(argument);
        
        return Mono.empty();
    }

}
