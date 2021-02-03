package de.tim_greller.mr_turing.turing_machine;

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
	
}
