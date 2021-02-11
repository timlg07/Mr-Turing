package de.tim_greller.mr_turing.bot.commands;

import org.reactivestreams.Publisher;

import de.tim_greller.mr_turing.bot.InvalidCommandSyntaxException;
import de.tim_greller.mr_turing.bot.TMFormatterUtils;
import de.tim_greller.mr_turing.turing_machine.TuringMachine;
import discord4j.core.object.entity.Message;

public class PrintConfigurationCommand implements BotCommand {

    @Override
    public String getTitle() {
        return "Print the Turing machines current configuration.";
    }

    @Override
    public String getDescription() {
        return "Prints out the current configuration, including the state, tape content "
                + "and head position of the Turing machine.";
    }

    @Override
    public String getCallName() {
        return "config";
    }

    @Override
    public Publisher<?> execute(Message message, String argument, TuringMachine tm)
            throws InvalidCommandSyntaxException {
        
        final String tapeContent = TMFormatterUtils.formatWord(tm.getTapeContent(), " | ");
        final int headIndex = tm.getHeadIndex();
        final String currentState = tm.getCurrentState().toString();
        
        return message.getChannel().flatMap(c -> c.createMessage(
                "Tape content: `" + tapeContent + "`\n"
                + "Head index: `" + headIndex + "`\n"
                + "Current state: `" + currentState + "`"));
    }

}
