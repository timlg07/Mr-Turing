package de.tim_greller.mr_turing.turing_machine;

import java.util.LinkedList;
import java.util.List;

public class DeterministicTuringMachine implements TuringMachine {

	private TMState status;
	private State currentState;
	private State initialState;
	private final List<Transition> transitions = new LinkedList<>();
	
	@Override
	public void clear() {
		status = TMState.initialState;
		transitions.clear();
		currentState = null;
		initialState = null;
	}

	@Override
	public boolean addTransition(Transition transition) {
		if (getTransition(transition.currentState, transition.scannedSymbol) != null) {
			return false;
		} else {
			return transitions.add(transition);
		}
	}
	
	/**
	 * Returns the {@link Transition} for a given precondition or {@code null} if no 
	 * Transition for the precondition exists.
	 * 
	 * @param state The required current state for the precondition.
	 * @param readSymbol The symbol that should be read currently to meet the
	 * 					 precondition.
	 * @return The matching transition or {@code null}.
	 */
	private Transition getTransition(State state, Symbol scannedSymbol) {
		// Return the first match (or null), as there  should only be 1 or 0 matches.
		return transitions.stream().filter(t -> t.matches(state, scannedSymbol))
				.findFirst().orElse(null);
	}

}
