package de.tim_greller.mr_turing.turing_machine;

import java.util.Deque;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Stream;

/**
 * This is a deterministic implementation of a Turing machine. It has one tape, one
 * initial state and can get to one of multiple accepting states using the given
 * transitions. If no transition can be applied, it automatically denies the input.
 */
public class DeterministicTuringMachine implements TuringMachine {

    /** 
     * The status of the Turing machine. Signalizes whether the TM is running or not and
     * if not, if it terminated in an accepting or denying state or if it is currently
     * modifiable.
     */
    private TMState status = TMState.MODIFIABLE;
    
    /** 
     * The current state of the automata/machine. 
     */
    private State currentState;
    
    /** 
     * The initial state from which the TM starts its execution. Because this is a
     * deterministic implementation, only one initial state can exist. The state is stored
     * in case the TM should be restarted later.
     */
    private State initialState;
    
    /** 
     * All transitions between the different states of this TM.
     */
    private final List<Transition> transitions = new LinkedList<>();
    
    /** 
     * If the TM is in one of this states, it instantly terminates and accepts the input.
     */
    private final List<State> acceptingStates = new LinkedList<>();
    
    /**
     * The input word that will be written on the tape once the TM is getting build.
     */
    private final Deque<Symbol> input = new LinkedList<>();
    
    /**
     * The tape of the Turing machine.
     */
    private Tape tape;
    
    @Override
    public void clear() {
        status = TMState.MODIFIABLE;
        input.clear();
        transitions.clear();
        acceptingStates.clear();
        currentState = null;
        initialState = null;
    }

    @Override
    public void restart() {
        builtOrThrow();
        
        status = TMState.MODIFIABLE;
        tape = new Tape(tape.blank);
        
        build();
    }

    @Override
    public void build() {
        modifiableOrThrow();
        
        if (tape == null) {
            tape = new Tape(getDefaultBlankSymbol());
        }
        tape.writeWord(input);
        
        if (initialState == null) {
            initialState = getDefaultInitialState();
        }
        currentState = initialState;
        
        if (acceptingStates.isEmpty()) {
            acceptingStates.add(getDefaultAcceptingState());
        }
        
        status = TMState.RUNNING;
    }

    @Override
    public boolean addTransition(Transition transition) {
        modifiableOrThrow();
        
        if (getTransition(transition.currentState, transition.scannedSymbol) != null) {
            return false;
        } else {
            return transitions.add(transition);
        }
    }

    @Override
    public boolean addAcceptingState(State state) {
        modifiableOrThrow();
        
        if (isAcceptingState(state)) {
            return false;
        }
        
        return acceptingStates.add(state);
    }
    
    @Override
    public boolean setAcceptingStates(State... states) {
        modifiableOrThrow();
        
        acceptingStates.clear();
        return Stream.of(states).map(this::addAcceptingState).allMatch(b -> b);
    }
    
    /**
     * {@inheritDoc}
     * <p>
     * This implementation does only accept one initial state. If the initial state was
     * already set, a call of this method will have no effect and {@code false} will be
     * returned.
     */
    @Override
    public boolean addInitialState(State state) {
        if (initialState == null) {
            initialState = state;
            return true;
        } else {
            return false;
        }
    }

    /**
     * {@inheritDoc}
     * <p>
     * This method creates a new tape using the given blank symbol.
     */
    @Override
    public void setBlankSymbol(BlankSymbol blank) {
        modifiableOrThrow();
        
        tape = new Tape(blank);
    }

    @Override
    public void setInput(String input) {
        modifiableOrThrow();
        this.input.clear();
        
        for (char c : input.toCharArray()) {
            this.input.addLast(new Symbol(c));
        }
    }
    
    @Override
    public BlankSymbol getDefaultBlankSymbol() {
        return new BlankSymbol("_");
    }

    @Override
    public State getDefaultAcceptingState() {
        return new State("F");
    }

    @Override
    public State getDefaultInitialState() {
        return new State("S");
    }
    
    @Override
    public void performStep() {
        if (!isRunning()) {
            throw new IllegalStateException("The TM is not running.");
        }
        
        Transition transition = getTransition(currentState, tape.readSymbol());
        
        if (transition == null) {
            
            /*
             * The transition for this configuration is not explicitly defined.
             * It's assumed that every unspecified configuration leads to the TM
             * denying.
             */
            status = TMState.DENYING;
            
        } else {
            performTransition(transition);
        }
        
        checkIfAccepting();
    }
    
    @Override
    public Deque<Symbol> getTapeContent() {
        builtOrThrow();
        
        if (tape == null) {
            return new LinkedList<>();
        }
        
        return tape.getContent();
    }
    
    @Override
    public Deque<Symbol> getInput() {
        return new LinkedList<Symbol>(input);
    }
    
    @Override
    public int getHeadIndex() {
        return tape.getHeadIndex();
    }
    
    @Override
    public State getCurrentState() {
        return currentState;
    }
    
    @Override
    public boolean isAccepting() {
        return status == TMState.ACCEPTING;
    }
    
    @Override
    public boolean isDenying() {
        return status == TMState.DENYING;
    }
    
    @Override
    public boolean isUnbuilt() {
        return status == TMState.MODIFIABLE;
    }
    
    @Override
    public boolean isRunning() {
        return status == TMState.RUNNING;
    }
    
    /**
     * Performs the given transition on this Turing machine.
     * 
     * @param transition The transition, must not be {@code null}.
     */
    private void performTransition(Transition transition) {
        tape.writeSymbol(transition.printSymbol);
        currentState = transition.nextState;
        tape.moveHead(transition.tapeMotion);
    }
    
    /**
     * Returns the {@link Transition} for a given precondition or {@code null} if no 
     * Transition for the precondition exists.
     * 
     * @param state The required current state for the precondition.
     * @param readSymbol The symbol that should be read currently to meet the
     *                      precondition.
     * @return The matching transition or {@code null}.
     */
    private Transition getTransition(State state, Symbol scannedSymbol) {
        // Return the first match (or null), as there  should only be 1 or 0 matches.
        return transitions.stream().filter(t -> t.matches(state, scannedSymbol))
                .findFirst().orElse(null);
    }
    
    /**
     * If the TM is currently in an accepting state, the TMs status gets set to 
     * {@link TMState#ACCEPTING}.
     */
    private void checkIfAccepting() {
        if (isAcceptingState(currentState)) {
            status = TMState.ACCEPTING;
        }
    }
    
    /**
     * Returns {@code true} if the given state is an accepting state of this TM.
     * 
     * @param state The state that should be checked.
     * @return Whether this state is an accepting state or not.
     */
    private boolean isAcceptingState(State state) {
        return acceptingStates.contains(state);
    }
    
    /**
     * Throws an {@link IllegalStateException} if the TM is not modifiable currently.
     */
    private void modifiableOrThrow() {
        if (!isUnbuilt()) {
            throw new IllegalStateException(
                    "The TM has to be in its modifiable state to do that.");
        }
    }
    
    /**
     * Throws an {@link IllegalStateException} if the TM is not built.
     */
    private void builtOrThrow() {
        if (isUnbuilt()) {
            throw new IllegalStateException(
                    "The TM has to be built in order to do that.");
        }
    }
}
