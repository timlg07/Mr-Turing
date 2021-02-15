package de.tim_greller.mr_turing.bot.commands;

import java.util.stream.Stream;

import org.reactivestreams.Publisher;

import de.tim_greller.mr_turing.bot.InvalidCommandSyntaxException;
import de.tim_greller.mr_turing.turing_machine.State;
import de.tim_greller.mr_turing.turing_machine.TuringMachine;
import discord4j.core.object.entity.Message;
import reactor.core.publisher.Mono;

/**
 * This command enables the definition of custom accepting states.
 */
public class SetAcceptingStatesCommand implements BotCommand {
    
    @Override
    public String getTitle() {
        return "Define the set of final, accepting states";
    }

    @Override
    public String getDescription() {
        return "If the Turing machine is in one of the given states, it accepts. "
                + "The states must be given as a list, separated by whitespaces and/or "
                + "commas.";
    }

    @Override
    public String getCallName() {
        return "accept";
    }

    @Override
    public Publisher<?> execute(Message message, String argument, TuringMachine tm)
            throws InvalidCommandSyntaxException {
        
        if (argument.isBlank()) {
            throw new InvalidCommandSyntaxException("No states given.");
        }
        
        String[] stateStrings = argument.split("(\\s+|,)+");
        
        tm.setAcceptingStates(
                Stream.of(stateStrings)
                .map(State::new)
                .toArray(State[]::new)
        );
        
        return Mono.empty();
    }

}
