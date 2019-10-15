package demo.chess.game.piece;

import demo.chess.game.Coords;
import demo.chess.game.Board;


public abstract class Piece {
    private PieceColor color;
    private PieceType type;

    Piece(PieceColor color) {
        this.color = color;
    }


    boolean notCapturingOwnPiece(Board board, Coords coords) {
        Piece capturablePiece = board.getSquare(coords).getPiece();
        return capturablePiece==null || this.isWhite() != capturablePiece.isWhite();
    }


    public abstract PieceType type();

    public boolean isWhite() {
        return color == PieceColor.WHITE;
    }

    public static Piece newPiece(PieceType type, PieceColor color) {

        switch (type) {
            case KING:
                return new King(color);
            case QUEEN:
                return new Queen(color);
            case ROOK:
                return new Rook(color);
            case KNIGHT:
                return new Knight(color);
            case BISHOP:
                return new Bishop(color);
            case PAWN:
                return new Pawn(color);
            default:
                throw new IllegalArgumentException();
        }

    }


    public enum PieceColor {
        BLACK, WHITE;
    }

    public static enum PieceType {
        PAWN, ROOK, KNIGHT, BISHOP, QUEEN, KING;
    }


    public boolean isMoveValid(Board board, Coords start, Coords end){
        return  notCapturingOwnPiece(board,end) &&
                doValidate(board, start, end);
    }

    public abstract boolean doValidate(Board board, Coords start, Coords end);


}
