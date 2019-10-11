package demo.chess.game.piece;

import demo.chess.game.Coords;
import demo.chess.game.Board;

import java.util.List;

public class Knight extends Piece {

    Knight(PieceColor color) {
        super(color);
    }


    @Override
    public boolean isMoveValid(Board board, Coords start, Coords end) {

        return allowedMoves(Coords.of(start.X, start.Y)) .contains(Coords.of(end.X, end.Y)) &&
                notCapturingOwnPiece(board, Coords.of(end.X, end.Y));
    }

    private boolean notCapturingOwnPiece(Board board, Coords coords) {
        Piece capturablePiece = board.getSquare(coords).getPiece();
        return capturablePiece==null || this.isWhite() != capturablePiece.isWhite();
    }


    private List<Coords> allowedMoves(Coords coords){
        int startX = coords.X;
        int startY = coords.Y;

        return  List.of(
                Coords.of(startX + 1, startY + 2),
                Coords.of(startX - 1, startY + 2),
                Coords.of(startX + 1, startY - 2),
                Coords.of(startX - 1, startY - 2),

                Coords.of(startX + 2, startY - 1),
                Coords.of(startX + 2, startY + 1),
                Coords.of(startX - 2, startY - 1),
                Coords.of(startX - 2, startY + 1)
        );

    }
    @Override
    public PieceType type() {
        return PieceType.KNIGHT;
    }

}
