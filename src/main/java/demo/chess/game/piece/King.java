package demo.chess.game.piece;

import demo.chess.game.Coords;
import demo.chess.game.Board;

public class King extends Piece {

    King(PieceColor color) {
        super(color);
    }

    @Override
    public boolean doValidate(Board board, Coords start, Coords end) {
        return true;
    }

    @Override
    public PieceType type() {
        return PieceType.KING;
    }
}
