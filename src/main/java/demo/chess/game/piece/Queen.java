package demo.chess.game.piece;

import demo.chess.game.Coords;
import demo.chess.game.Board;

public class Queen extends Piece{

    Queen(PieceColor color) {
        super(color);
    }

    @Override
    public boolean isMoveValid(Board board, Coords start, Coords end) {
        return true;
    }

    @Override
    public PieceType type() {
        return PieceType.QUEEN;
    }
}
