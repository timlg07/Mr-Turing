package de.tim_greller.mr_turing.bot.commands;

import org.reactivestreams.Publisher;

import de.tim_greller.mr_turing.bot.InvalidCommandSyntaxException;
import de.tim_greller.mr_turing.bot.TMFormatterUtils;
import de.tim_greller.mr_turing.turing_machine.TuringMachine;
import discord4j.core.object.entity.Message;

public class RunTuringMachineCommand implements BotCommand {

    @Override
    public String getTitle() {
        return "Run the Turing machine";
    }

    @Override
    public String getDescription() {
        return "Executes the Turing machine until it terminates.";
    }

    @Override
    public String getCallName() {
        return "run";
    }

    @Override
    public Publisher<?> execute(Message message, String argument, TuringMachine tm)
            throws InvalidCommandSyntaxException {
        
        if (tm.isUnbuilt()) {
            tm.build();
            message.getChannel().flatMap(
                    c -> c.createMessage("The TM was automatically build.")
            ).block();
        }
        
        do {
            tm.performStep();
        } while (tm.isRunning());

        final String terminateMessage = TMFormatterUtils.getTerminationMessageContent(tm);
        return (message.getChannel().flatMap(c -> c.createMessage(terminateMessage)));
    }

}
