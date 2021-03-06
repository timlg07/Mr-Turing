package de.tim_greller.mr_turing.turing_machine;

import java.util.Deque;
import java.util.LinkedList;

/**
 * This class models one cell of a Turing machine's tape.
 */
public class TapeCell {

    /** The index of this cell in the tape. Starts with 0, can be positive or negative. */
    public final int index;
    
    /** The blank symbol the tape uses. */
    private final BlankSymbol blank;
    
    /** The currently stored symbol. */
    private Symbol symbol;
    
    /** The cell left to this one. */
    private TapeCell leftCell;
    
    /** The cell right to this one. */
    private TapeCell rightCell;
    
    
    /**
     * Constructs the first tape cell in a yet completely empty tape.
     * 
     * @param blank The blank symbol of the tape this cell is a part of.
     */
    public TapeCell(BlankSymbol blank) {
        this(0, blank, null, null);
    }
    
    /**
     * Constructs a tape cell with the given index and symbol.
     * 
     * @param index The index of the cell.
     * @param blank The blank symbol of the tape.
     * @param left The left-next cell to this new cell, may be {@code null}.
     * @param right The right-next cell to this new cell, may be {@code null}.
     */
    private TapeCell(int index, BlankSymbol blank, TapeCell left, TapeCell right) {
        this.index = index;
        this.blank = blank;
        this.symbol = blank;
        this.leftCell = left;
        this.rightCell = right;
    }
    
    /**
     * Returns if the left cell was ever defined either by the Turing machine's head or
     * through the writing of the input.
     * <p>
     * This is used to determine when you can stop fetching the output of the TM.
     * 
     * @return Whether the next left cell was defined yet or not.
     */
    public boolean wasLeftCellVisited() {
        return (leftCell != null);
    }
    
    /**
     * Returns if the right cell was ever defined either by the Turing machine's head or
     * through the writing of the input.
     * <p>
     * This is used to determine when you can stop fetching the output of the TM.
     * 
     * @return Whether the right left cell was defined yet or not.
     */
    public boolean wasRightCellVisited() {
        return (rightCell != null);
    }
    
    /**
     * Returns the cell left to this one. If this cell was never visited before, it gets
     * created.
     * 
     * @return The cell left to this one.
     */
    public TapeCell getLeftCell() {
        if (!wasLeftCellVisited()) {
            leftCell = new TapeCell(index - 1, blank, null, this);
        }
        return leftCell;
    }
    
    /**
     * Returns the cell right to this one. If this cell was never visited before, it gets
     * created.
     * 
     * @return The cell right to this one.
     */
    public TapeCell getRightCell() {
        if (!wasRightCellVisited()) {
            rightCell = new TapeCell(index + 1, blank, this, null);
        }
        return rightCell;
    }
    
    /**
     * Write a word (= sequence of symbols) to the tape.
     * 
     * @param word The word as a stack of symbols.
     */
    public void writeWord(Deque<Symbol> word) {
        if (word.size() < 1) {
            return;
        }
        
        this.symbol = word.pop();
        
        // Avoid creating an additional cell if its not necessary.
        if (word.size() > 0) {
            getRightCell().writeWord(word);
        }
    }
    
    /**
     * Get the content of the tape right to this cell (including the cell itself) as a
     * stack of {@link Symbol}s.
     * 
     * @return The word that is stored by the tape left to this cell as stack.
     */
    public Deque<Symbol> collect() {
        Deque<Symbol> wordStack;
        
        if (wasRightCellVisited()) {
            wordStack = getRightCell().collect();
        } else {
            // Initialize the stack on the most right cell:
            wordStack = new LinkedList<>();
        }
        
        wordStack.push(read());
        return wordStack;
    }
    
    /**
     * Get the content of the whole tape from the most left visited to the most right
     * visited cell as a stack of {@link Symbol}s.
     * 
     * @return The word that is stored on the tape as a stack.
     */
    public Deque<Symbol> collectAll() {
        if (wasLeftCellVisited()) {
            return getLeftCell().collectAll();
        } else {
            return collect();
        }
    }
    
    /**
     * Reads the content of this cell.
     * 
     * @return The symbol stored in this cell.
     */
    public Symbol read() {
        return symbol;
    }
    
    /**
     * Writes a {@link Symbol} to this cell.
     * 
     * @param symbol The symbol that should be stored in this cell.
     */
    public void write(Symbol symbol) {
        this.symbol = symbol;
    }
}
