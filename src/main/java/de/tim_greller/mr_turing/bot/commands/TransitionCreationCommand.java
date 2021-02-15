package de.tim_greller.mr_turing.bot.commands;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.reactivestreams.Publisher;

import de.tim_greller.mr_turing.bot.InvalidCommandSyntaxException;
import de.tim_greller.mr_turing.turing_machine.State;
import de.tim_greller.mr_turing.turing_machine.Symbol;
import de.tim_greller.mr_turing.turing_machine.TapeMove;
import de.tim_greller.mr_turing.turing_machine.Transition;
import de.tim_greller.mr_turing.turing_machine.TuringMachine;
import discord4j.core.object.entity.Message;
import reactor.core.publisher.Mono;

/**
 * This command creates a new transition and adds it to the Turing machine.
 */
public class TransitionCreationCommand implements BotCommand {

    @Override
    public String getTitle() {
        return "Add Transition";
    }

    @Override
    public String getDescription() {
        return "Creates a new transition and adds it to the Turing machine.\n"
                + "A transition has to be in this form: `(currentState, scannedSymbol) ->"
                + " (nextState, printSymbol, tapeMotion)` with TapeMotion being either "
                + "left, right or none.";
    }

    @Override
    public String getCallName() {
        return "add";
    }

    @Override
    public Publisher<?> execute(Message msg, String arg, TuringMachine tm) 
            throws InvalidCommandSyntaxException {
        
        final String currentTupelRegExp = "\\((?<currentState>\\w+),\\s*"
                + "(?<scannedSymbol>\\w+)\\)";
        final String transitionArrowRegExp = "\\s*-\\>\\s*";
        final String resultingTupelRegExp = "\\((?<nextState>\\w+),\\s*"
                + "(?<printSymbol>\\w+),\\s*(?<tapeMotion>\\w+)\\)";
        
        final Matcher matcher = Pattern.compile(
                currentTupelRegExp + 
                transitionArrowRegExp + 
                resultingTupelRegExp
        ).matcher(arg);
        
        if (!matcher.find() || matcher.groupCount() < 5 /* amount of required groups */) {
            throw new InvalidCommandSyntaxException("This is not a valid transition.");
        }
        
        final Transition transition = new Transition(
                new State(matcher.group("currentState")),
                new Symbol(matcher.group("scannedSymbol")),
                new Symbol(matcher.group("printSymbol")),
                TapeMove.from(matcher.group("tapeMotion")),
                new State(matcher.group("nextState"))
        );

        final boolean success = tm.addTransition(transition);
        if (!success) {
            throw new InvalidCommandSyntaxException("This transition was already added.");
        }
        
        return Mono.empty();
    }

}
