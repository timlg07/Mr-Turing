package de.tim_greller.mr_turing.bot.commands;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.reactivestreams.Publisher;

import de.tim_greller.mr_turing.bot.InvalidCommandSyntaxException;
import de.tim_greller.mr_turing.turing_machine.BlankSymbol;
import de.tim_greller.mr_turing.turing_machine.TuringMachine;
import discord4j.core.object.entity.Message;
import reactor.core.publisher.Mono;

/**
 * This command can be used to set a custom blank symbol.
 */
public class SetBlankSymbolCommand implements BotCommand {

    @Override
    public String getTitle() {
        return "Set the blank symbol";
    }

    @Override
    public String getDescription() {
        return "Sets the blank symbol with which the initial empty tape will be filled.";
    }

    @Override
    public String getCallName() {
        return "blank";
    }

    @Override
    public Publisher<?> execute(Message message, String argument, TuringMachine tm)
            throws InvalidCommandSyntaxException {
        
        if (argument.isBlank()) {
            throw new InvalidCommandSyntaxException(
                    "The blank symbol cannot be blank. lol.\nIf you want to set a"
                    + "whitespace character as blank symbol, surround it with quotes.");
        }
        
        final Matcher quoteMatcher = Pattern.compile("\"(.+)\"").matcher(argument);
        final String blank = (quoteMatcher.find() ? quoteMatcher.group(1) : argument);
        
        tm.setBlankSymbol(new BlankSymbol(blank));
        
        return Mono.empty();
    }

}
