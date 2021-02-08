package de.tim_greller.mr_turing.turing_machine;

/**
 * This enumeration represents the different states in which a Turing machine can be in.
 */
public enum TMState {
    
    /** The TM is modifiable and not running yet. */
    MODIFIABLE,
    
    /** The TM is no longer in its initial state and has not yet terminated. */
    RUNNING,
    
    /** The TM has terminated and accepts the given input. */
    ACCEPTING,
    
    /** The TM has terminated and does not accept the input. */
    DENYING;

}
