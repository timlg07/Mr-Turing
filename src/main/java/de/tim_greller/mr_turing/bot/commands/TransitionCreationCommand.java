package de.tim_greller.mr_turing.bot.commands;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.reactivestreams.Publisher;

import de.tim_greller.mr_turing.bot.InvalidCommandSyntaxException;
import de.tim_greller.mr_turing.turing_machine.TuringMachine;
import discord4j.core.object.entity.Message;
import reactor.core.publisher.Mono;

/**
 * This command creates a new transition to the current turing machine hold by the 
 * {@link TuringMachineManager}.
 */
public class TransitionCreationCommand implements BotCommand {

	@Override
	public String getTitle() {
		return "Add Transition";
	}

	@Override
	public String getDescription() {
		return "Creates a new transition and adds it to the Turing machine";
	}

	@Override
	public String getCallName() {
		return "add";
	}

	@Override
	public Publisher<?> execute(Message msg, String arg, TuringMachine tm) 
			throws InvalidCommandSyntaxException {
		
		String currentTupelRegExp = "\\((?<currentState>\\w+),\\s*"
				+ "(?<scannedSymbol>\\w+)\\)";
		String transitionArrowRegExp = "\\s*-\\>\\s*";
		String resultingTupelRegExp = "\\((?<nextState>\\w+),\\s*"
				+ "(?<printSymbol>\\w+),\\s*(?<tapeMotion>[lnrLNR])\\)";
		
		Matcher m = Pattern.compile(
				currentTupelRegExp + 
				transitionArrowRegExp + 
				resultingTupelRegExp
		).matcher(arg);
		
		if (!m.find()) {
			throw new InvalidCommandSyntaxException("This is not a valid transition.");
		}
		
		String currentState = m.group("currentState");
		String scannedSymbol = m.group("scannedSymbol");
		String nextState = m.group("nextState");
		String printSymbol = m.group("printSymbol");
		String tapeMotion = m.group("tapeMotion");
		
		return Mono.fromRunnable(() -> {
			// TODO
		});
	}

}
