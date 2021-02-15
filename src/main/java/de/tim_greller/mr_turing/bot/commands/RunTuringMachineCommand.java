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
    
    /**
     * Defines how many computation steps can maximally be done per run.
     * {@code 0} signalizes no limitation.
     */
    private int maxStepsPerRun = 0;
    
    /**
     * Constructs a new Run-command that will execute the given maximum amount of steps
     * per run. Using this constructor, the bot can prohibit infinite loops.
     * 
     * @param maxStepsPerRun The maximum amount of steps per run. {@code 0} for no limit.
     */
    public RunTuringMachineCommand(int maxStepsPerRun) {
        this.maxStepsPerRun = maxStepsPerRun;
    }

    @Override
    public String getTitle() {
        return "Run the Turing machine";
    }

    @Override
    public String getDescription() {
        String limitationInfo = "";
        
        if (maxStepsPerRun != 0) {
            limitationInfo = " or reaches the maximum of " + maxStepsPerRun + " steps per run.";
        }
        
        return "Executes the Turing machine until it terminates" + limitationInfo + ".";
    }

    @Override
    public String getCallName() {
        return "run";
    }

    @Override
    public Publisher<?> execute(Message message, String argument, TuringMachine tm)
            throws InvalidCommandSyntaxException {
        
        Publisher<?> infoMessage = Mono.empty();
        int stepsDone = 0;
        
        if (tm.isUnbuilt()) {
            tm.build();
            infoMessage = message.getChannel().flatMap(c -> c.createEmbed(s -> 
                    s.setTitle("Info")
                     .setDescription("Executed Turing machine build automatically.")
                     .setColor(Color.YELLOW)));
        }
        
        do {
            tm.performStep();
            
            if (++stepsDone == maxStepsPerRun) {
                return Flux.concat(infoMessage, message.getChannel().flatMap(
                        c -> c.createEmbed(s -> 
                            s.setTitle("Limitation of " + maxStepsPerRun + " steps per "
                                    + "run reached.")
                             .setDescription("Maybe you created an infinite loop?")
                             .setColor(Color.RED)
                    )));
            }
        } while (tm.isRunning());

        final String title = "The Turing machine terminated after " + stepsDone + " steps.";
        final String terminateMessage = TMFormatterUtils.getTerminationMessageContent(tm);
        return Flux.concat(infoMessage, message.getChannel().flatMap(
                c -> c.createEmbed(s -> 
                    s.setTitle(title)
                     .setDescription(terminateMessage)
                     .setColor(Color.DISCORD_WHITE)
                )));
    }

}
