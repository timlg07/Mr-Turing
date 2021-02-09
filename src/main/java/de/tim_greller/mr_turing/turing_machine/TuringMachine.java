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
     * Clears the tape and starts the TM over.
     */
    public void restart();

    /**
     * Changes the TMs status from modifiable to running. Uses default values if the
     * initial and accepting states or the blank symbol properties were not set.
     */
    public void build();
    
    /**
     * Adds the given {@link Transition} to this Turing machine and returns {@code true}.
     * <p>
     * The implementation of the Turing machine might reject the given transition, because
     * an identical one was already added or because a previously added transition has the
     * same initial configuration and the TM implementation is deterministic. In this case
     * {@code false} is returned.
     * <p>
     * The TM can only be modified when it is in its modifiable state.
     * 
     * @param transition The transition that should be added to this TM.
     * @return Whether the addition was successful or not.
     */
    public boolean addTransition(Transition transition);
    
    /**
     * Adds the given state to the TM as an accepting state. Once the TM reaches an
     * accepting state, it terminates and accepts the input.
     * <p>
     * Each state can only be added once. If you try to add it twice, this method will
     * return false and not change the list of accepting states.
     * <p>
     * The TM can only be modified when it is in its modifiable state.
     * 
     * @param state The accepting state that should be added.
     * @return Whether the state was successfully added or already existed.
     */
    public boolean addAcceptingState(State state);
    
    /**
     * Adds the given state to the TM as an initial state. This means the TM can start its
     * processing from the given state.
     * <p>
     * The implementation of this behavior varies between different types of TMs: While
     * non deterministic TMs will accept adding multiple initial states, deterministic
     * ones won't allow that.
     * 
     * @param state The state that should be added to the initial states of the TM.
     * @return Whether the state was added ({@code true}) or rejected ({@code false}).
     */
    public boolean addInitialState(State state);
    
    /**
     * Sets the input for the Turing machine. Once the TM starts its execution, this
     * String has to be written on the TMs tape. Each character in the String will be one
     * symbol.
     * 
     * @param input The new input for the TM.
     */
    public void setInput(String input);
    
    /**
     * Sets the blank symbol of this TM.
     * <p>
     * The TM can only be modified when it is in its modifiable state.
     * 
     * @param blank The new blank symbol of the tape.
     */
    public void setBlankSymbol(BlankSymbol blank);
    
    /**
     * This blank symbol is used if no custom one was set.
     * 
     * @return The default blank symbol.
     */
    public BlankSymbol getDefaultBlankSymbol();
    
    /**
     * This state will be used as accepting state if no custom ones were added.
     * 
     * @return The default accepting state.
     */
    public State getDefaultAcceptingState();
    
    /**
     * The TM will start from this state if no custom initial states were added.
     * 
     * @return The default initial state.
     */
    public State getDefaultInitialState();
    

    /**
     * If the Turing machine is running, this method performs one calculation step on it.
     */
    public void performStep();
    
    /**
     * Returns a string representation of the current tape content.
     * 
     * @return The content of the TMs tape.
     */
    public String getTapeContent();
    
    /**
     * The position of the head on the tape.
     * 
     * @return The index of the cell the head is pointing at.
     */
    public int getHeadIndex();
    
    /**
     * The current state of the TM.
     * 
     * @return The state of the TM.
     */
    public State getCurrentState();

}
