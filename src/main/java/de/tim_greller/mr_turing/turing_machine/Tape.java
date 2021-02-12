package de.tim_greller.mr_turing.turing_machine;

import java.util.Deque;
import java.util.LinkedList;

/**
 * This class models the Tape of a Turing machine which consists of {@link TapeCell}s.
 */
public class Tape {
    
    /** The blank symbol that is used for empty cells on this tape. */
    final BlankSymbol blank;
    
    /** The cell on which the header is currently pointing at. */
    private TapeCell currentCell;
    
    public Tape(BlankSymbol blank) {
        this.blank = blank;
        currentCell = new TapeCell(blank);
    }
    
    /**
     * Moves the head one cell in the given direction (or does not move it at all).
     * 
     * @param direction The direction in which the head should be moved on the tape.
     */
    public void moveHead(TapeMove direction) {
        switch (direction) {
        case LEFT:
            currentCell = currentCell.getLeftCell();
            break;
        
        case RIGHT:
            currentCell = currentCell.getRightCell();
            break;
            
        default:
            break;
        }
    }

    /**
     * Reads the {@link Symbol} that is stored in the tape at the head position.
     * 
     * @return The content of the current cell.
     */
    public Symbol readSymbol() {
        return currentCell.read();
    }
    
    /**
     * Writes a {@link Symbol} to the tape at the current head position.
     * 
     * @param symbol The symbol that should be written to the tape.
     */
    public void writeSymbol(Symbol symbol) {
        currentCell.write(symbol);
    }
    
    /**
     * Deletes the symbol at the current head position. The cell will then be blank again.
     */
    public void clearSymbol() {
        writeSymbol(blank);
    }
    
    /**
     * Write a word (= sequence of symbols) to the tape, starting from the current head
     * position and continuing to the right.
     * 
     * @param word The word as a stack of symbols. The original stack won't get modified.
     */
    public void writeWord(Deque<Symbol> word) {
        currentCell.writeWord(new LinkedList<>(word));
    }
    
    /**
     * Returns all symbols stored on the tape, ordered from left to right.
     * 
     * @return The tapes content as a stack of symbols.
     */
    public Deque<Symbol> getContent() {
        return currentCell.collectAll();
    }

    /**
     * The position of the head on the tape.
     * 
     * @return The index of the cell the head is pointing at.
     */
    public int getHeadIndex() {
        return currentCell.index;
    }
}
