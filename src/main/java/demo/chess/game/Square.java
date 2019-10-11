package demo.chess.game;

import demo.chess.game.piece.Piece;

import java.util.Objects;

public class Square {


    private Square(Piece piece) {
        this.piece = piece;
    }

    private Piece piece;

    void setPiece(Piece piece) {
        this.piece = piece;
    }

    public Piece getPiece() {
        return piece;
    }

    public boolean isEmpty(){
        return Objects.isNull(piece);
    }

    public static Square newEmptySquare(){
        return new Square(null);
    }

    public static Square newSquareWith(Piece piece){
        return new Square(piece);
    }

}
