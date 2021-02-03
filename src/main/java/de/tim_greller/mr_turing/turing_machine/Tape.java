package de.tim_greller.mr_turing.turing_machine;

/**
 * This class models the Tape of a Turing machine which consists of {@link TapeCell}s.
 */
public class Tape {
	
	/** The blank symbol that is used for empty cells on this tape. */
	BlankSymbol blank;
	
	/** The cell on which the header is currently pointing at. */
	TapeCell currentCell;
	
	public Tape(BlankSymbol blank) {
		this.blank = blank;
		currentCell = new TapeCell(blank);
	}
}
