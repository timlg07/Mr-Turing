package de.tim_greller.mr_turing.bot.commands;

import org.reactivestreams.Publisher;

import de.tim_greller.mr_turing.bot.InvalidCommandSyntaxException;
import de.tim_greller.mr_turing.bot.TMFormatterUtils;
import de.tim_greller.mr_turing.turing_machine.TuringMachine;
import discord4j.core.object.entity.Message;
import discord4j.rest.util.Color;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * This command runs a Turing machine until it terminates.
 */
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
        
        Publisher<?> infoMessage = Mono.empty();
        
        if (tm.isUnbuilt()) {
            tm.build();
            infoMessage = message.getChannel().flatMap(c -> c.createEmbed(s -> 
                    s.setTitle("Info")
                     .setDescription("Executed Turing machine build automatically.")
                     .setColor(Color.YELLOW)));
        }
        
        do {
            tm.performStep();
        } while (tm.isRunning());

        final String terminateMessage = TMFormatterUtils.getTerminationMessageContent(tm);
        return Flux.concat(infoMessage, message.getChannel().flatMap(
                c -> c.createEmbed(s -> 
                    s.setTitle("The Turing machine terminated.")
                     .setDescription(terminateMessage)
                     .setColor(Color.DISCORD_WHITE)
                )));
    }

}
