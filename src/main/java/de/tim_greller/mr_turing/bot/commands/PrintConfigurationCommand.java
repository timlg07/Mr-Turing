package de.tim_greller.mr_turing.bot.commands;

import org.reactivestreams.Publisher;

import de.tim_greller.mr_turing.bot.InvalidCommandSyntaxException;
import de.tim_greller.mr_turing.bot.TMFormatterUtils;
import de.tim_greller.mr_turing.turing_machine.TuringMachine;
import discord4j.core.object.entity.Message;

/**
 * This command is used to print the current configuration of the Turing machine.
 */
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
        
        return message.getChannel().flatMap(c -> 
                c.createEmbed(s ->
                    s.setTitle("The current configuration of the Turing machine:")
                     .addField("Tape content:", "```" + tapeContent.trim() + "```", true)
                     .addField("Head index:", "`" + headIndex + "`", true)
                     .addField("Current state:", "`" + currentState + "`", true)
                )
        );
    }

}
