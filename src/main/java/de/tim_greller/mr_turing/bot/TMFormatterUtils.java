package de.tim_greller.mr_turing.bot;

import java.util.Deque;

import de.tim_greller.mr_turing.turing_machine.Symbol;
import de.tim_greller.mr_turing.turing_machine.TuringMachine;

/**
 * This class contains utility methods to create String representations of Turing machine
 * components.
 */
public final class TMFormatterUtils {
    
    /**
     * Private constructor to prevent instantiation.
     */
    private TMFormatterUtils() {
        throw new AssertionError("This class should not be instantiated.");
    }
    
    /**
     * If the given Turing machine is in a terminated state, a String representing its
     * outcome is returned. If the TM is still running (or wasn't even started),
     * {@code null} will be returned.
     * 
     * @param tm The Turing machine that should be checked and printed.
     * @return The String representing the TMs outcome or {@code null}.
     */
    public static String getTerminationMessageContent(TuringMachine tm) {
        final String originalInput = formatWord(tm.getInput(), "");
        final String resultingTape = formatWord(tm.getTapeContent(), "");
        
        if (tm.isAccepting()) {
            return "The Turing machine accepted the input word \"" + originalInput 
                    + "\".\n Its output was: \"" + resultingTape + "\".";
        }
        
        if (tm.isDenying()) {
            return "The Turing machine terminated and did not accept the word \"" 
                    + originalInput + "\".";
        }
        
        return null;
    }
    
    /**
     * Formats a word using the given separator. Each {@link Symbol} will be
     * shown ordered from left to right and separated by the given separator.
     * The separator will also appear as the pre- and suffix.
     * 
     * @param word The word that should be formatted as a stack of {@link Symbol}s.
     * @param separator The separator between each Symbol.
     * @return The formatted string representation of the given word.
     */
    public static String formatWord(Deque<Symbol> word, String separator) {
        StringBuilder output = new StringBuilder();
        
        word.iterator().forEachRemaining(
                s -> output.append(separator).append(s.toString())
        );
        
        return output.append(separator).toString();
    }
}
