package de.tim_greller.mr_turing.turing_machine;

/**
 * An interface that defines how to interact with Turing machines. Implementations of this
 * interface can be deterministic or nondeterministic.
 */
public interface TuringMachine {
	
	/**
	 * Completely resets the Turing machine. This action is equivalent to replacing the
	 * Turing machine by a newly constructed one of the same type.
	 */
	public void clear();
	
	/**
	 * Adds the given {@link Transition} to this Turing machine and returns {@code true}.
	 * <p>
	 * The implementation of the Turing machine might reject the given transition, because
	 * an identical one was already added or because a previously added transition has the
	 * same initial configuration and the TM implementation is deterministic. In this case
	 * {@code false} is returned.
	 * 
	 * @param transition The transition that should be added to this TM.
	 * @return Whether the addition was successful or not.
	 */
	public boolean addTransition(Transition transition);

}
