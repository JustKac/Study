package application;

import boardgame.Board;
import boardgame.Position;
import chess.ChessMatch;

public class Program {

	public static void main(String[] args) {

		Position pos = new Position(3, 5);
		System.out.println(pos.toString());
		
		Board board = new Board(8, 8);
		
		ChessMatch chessMatch = new ChessMatch();
		UI.printBorard(chessMatch.getPieces());

	}

}
