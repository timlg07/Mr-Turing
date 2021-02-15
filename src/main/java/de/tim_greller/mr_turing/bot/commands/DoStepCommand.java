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
 * This command executes a single computation step of the Turing machine.
 */
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
        
        Publisher<?> infoMessage = Mono.empty();
        
        if (tm.isUnbuilt()) {
            tm.build();
            infoMessage = message.getChannel().flatMap(c -> c.createEmbed(s -> 
                    s.setTitle("Info")
                     .setDescription("Executed Turing machine build automatically.")
                     .setColor(Color.YELLOW)));
        }

        tm.performStep();
        
        final String terminateMessage = TMFormatterUtils.getTerminationMessageContent(tm);
        if (terminateMessage != null) {
            return Flux.concat(infoMessage, message.getChannel().flatMap(
                    c -> c.createEmbed(s -> 
                        s.setTitle("The Turing machine terminated.")
                         .setDescription(terminateMessage)
                         .setColor(Color.DISCORD_WHITE)
                    )));
        }
        
        return infoMessage;
    }

}
