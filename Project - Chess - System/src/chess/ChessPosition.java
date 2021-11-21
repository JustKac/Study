package chess;

import boardgame.Position;

public class ChessPosition {

	private int row;
	private int column;

	public ChessPosition(int row, int column) {
		if (column < 'a' || column > 'h' || row < 1 || row > 8) {
			throw new ChessException("Error instatianting CessPosition. Valid vallues are from a1 to h8");
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
		return new Position(8 -row, column - 'a');
	}

	protected static ChessPosition fromPosition(Position position) {
		return new ChessPosition((char)('a' - position.getColumn()), 8 - position.getRow());
	}

	@Override
	public String toString() {
		return "" + column + row;
	}
	
	
}
