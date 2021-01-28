package de.tim_greller.mr_turing.turing_machine;

/**
 * This class represents a state of a Turing machine.
 */
public class State {
	
	/** The name of the state. */
	private final String name;
	
	/**
	 * Creates a new Turing machine state with the given name.
	 * 
	 * @param name The name of this state.
	 */
	public State(String name) {
		this.name = name;
	}
	
	/**
	 * Returns the name of this state.
	 * 
	 * @return The name of this state.
	 */
	public String getName() {
		return name;
	}
	
	@Override
	public String toString() {
		return name;
	}

}
