package chess;

import boardgame.Position;

public class ChessPosition {

	private int row;
	private char column;

	public ChessPosition(int row, char column) {
		if (column < 'a' || column > 'h' || row < 1 || row > 8) {
			throw new ChessException("Error instatianting ChessPosition. Valid vallues are from a1 to h8");
		}
		this.row = row;
		this.column = column;
	}

	public int getRow() {
		return row;
	}

	public int getColumn() {
		return column;
	}

	protected Position toPosition() {
		return new Position(8 - row, column - 'a');
	}

	protected static ChessPosition fromPosition(Position position) {
		return new ChessPosition(8 - position.getRow(), (char) ('a' + position.getColumn()));
	}

	@Override
	public String toString() {
		return "" + column + row;
	}

}
