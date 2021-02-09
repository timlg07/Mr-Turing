package de.tim_greller.mr_turing.bot.commands;

import org.reactivestreams.Publisher;

import de.tim_greller.mr_turing.bot.InvalidCommandSyntaxException;
import de.tim_greller.mr_turing.turing_machine.TuringMachine;
import discord4j.core.object.entity.Message;
import reactor.core.publisher.Mono;

public class BuildTuringMachineCommand implements BotCommand {

    @Override
    public String getTitle() {
        return "Build Turing machine";
    }

    @Override
    public String getDescription() {
        return "Builds the Turing machine using the defined data. Once a TM was builded, "
                + "it can no longer be modified. If data like initial/accepting states "
                + "or the blank symbol was not specified, the default will be used.";
    }

    @Override
    public String getCallName() {
        return "build";
    }

    @Override
    public Publisher<?> execute(Message message, String argument, TuringMachine tm)
            throws InvalidCommandSyntaxException {
        
        tm.build();
        
        return Mono.empty();
    }

}
