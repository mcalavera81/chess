package demo.chess.game.piece;

import demo.chess.game.Coords;
import demo.chess.game.Board;

public class Pawn extends Piece {

    Pawn(PieceColor color) {
        super(color);
    }

    @Override
    public boolean isMoveValid(Board board, Coords start, Coords end) {

        return isValidForwardMove(board, start, end) ||
        isValidCaptureMove(board, start, end);

    }

    private boolean isValidCaptureMove(Board board, Coords start, Coords end) {
        Piece capturablePiece = board.getSquare(end).getPiece();


        return capturablePiece != null &&
                capturablePiece.isWhite() != isWhite() &&
                end.X - start.X == direction() &&
                Math.abs(end.Y -start.Y) == 1;

    }

    private boolean isValidForwardMove(Board board, Coords start, Coords end) {

        return (end.X - start.X == direction() || (end.X - start.X == 2 * direction() && isInitPosition(start.X, start.Y))) &&
                         start.Y == end.Y &&
                         !forwardPathBlocked(board, start, end);

    }

    private boolean forwardPathBlocked(Board board, Coords start, Coords end) {

        for(int xPos=start.X; xPos!=end.X; xPos += direction()){
            if(!board.getSquare(xPos+direction(), start.Y).isEmpty()){
                return true;
            }
        }

        return false;
    }


    private  int direction(){
        return isWhite() ? -1 : 1;
    }
    private boolean isInitPosition(int x, int y) {
        return (isWhite() && x == 6) || (!isWhite() && x == 1);

    }

    @Override
    public PieceType type() {
        return PieceType.PAWN;
    }


}
