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
        return "Sets the input of a Turing machine. The given string will be written to "
                + "the TMs tape when the TM is build.";
    }

    @Override
    public String getCallName() {
        return "input";
    }

    @Override
    public Publisher<?> execute(Message message, String argument, TuringMachine tm)
            throws InvalidCommandSyntaxException {

        final Matcher quoteMatcher = Pattern.compile("\"(.+)\"").matcher(argument);
        final String input = (quoteMatcher.find() ? quoteMatcher.group(1) : argument);
        
        tm.setInput(input);
        
        return Mono.empty();
    }

}
