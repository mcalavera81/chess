package demo.chess.game.piece;

import demo.chess.game.Coords;
import demo.chess.game.Board;


public class Bishop extends Piece {

    Bishop(PieceColor color) {
        super(color);
    }

    @Override
    public PieceType type() {
        return PieceType.BISHOP;
    }

    @Override
    public boolean isMoveValid(Board board, Coords start, Coords end) {

        int diffX = Math.abs(end.X - start.X);
        int diffY = Math.abs(end.Y - start.Y);

        return diffX == diffY && Math.abs(diffX) > 0 &&
                notCapturingOwnPiece(board, end) &&
                !pathBlocked(board, start,end);

    }

    private boolean pathBlocked(Board board, Coords start, Coords end) {

        int dirX = end.X- start.X>0?1:-1;
        int dirY = end.Y - start.Y>0?1:-1;

        for(int xPos=start.X+dirX,yPos=start.Y+dirY; xPos!=end.X || yPos!=end.Y; xPos += dirX, yPos += dirY){
            if(!board.getSquare(xPos, yPos).isEmpty()){
                    return true;
            }
        }

        return false;

    }
    private boolean notCapturingOwnPiece(Board board, Coords coords) {
        Piece capturablePiece = board.getSquare(coords).getPiece();
        return capturablePiece==null || this.isWhite() != capturablePiece.isWhite();
    }

}
