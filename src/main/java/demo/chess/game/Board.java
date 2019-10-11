package demo.chess.game;

import demo.chess.game.piece.Piece;

import java.util.Objects;

import static demo.chess.game.Square.newEmptySquare;
import static demo.chess.game.Square.newSquareWith;

public class Board {

    private final static int ROWS=8, COLUMNS=8;
    private Square[][] squares;

    private Board(){
        squares = new Square[ROWS][COLUMNS];
    }

    static Board newBoard() {
        Board board = new Board();
        board.createAndPlacePieces(Piece.PieceColor.BLACK);
        board.createAndPlacePieces(Piece.PieceColor.WHITE);
        board.initEmptySquares();
        return board;

    }

    Move makeMove(Square srcSquare, Square dstSquare){

        Piece pieceCaptued = dstSquare.getPiece();

        dstSquare.setPiece(srcSquare.getPiece());
        srcSquare.setPiece(null);


        return new Move(srcSquare, dstSquare, dstSquare.getPiece(), pieceCaptued);
    }


    private void createAndPlacePieces(Piece.PieceColor color) {
        int firstRow, secondRow;
        switch (color) {
            case BLACK:
                firstRow = 0;
                secondRow = 1;
                break;
            case WHITE:
                firstRow = 7;
                secondRow = 6;
                break;
            default:
                throw new IllegalArgumentException();
        }

        int column = 0;
        squares[firstRow][column++] = newSquareWith(Piece.newPiece(Piece.PieceType.ROOK, color));
        squares[firstRow][column++] = newSquareWith(Piece.newPiece(Piece.PieceType.KNIGHT, color));
        squares[firstRow][column++] = newSquareWith(Piece.newPiece(Piece.PieceType.BISHOP, color));
        squares[firstRow][column++] = newSquareWith(Piece.newPiece(Piece.PieceType.QUEEN, color));
        squares[firstRow][column++] = newSquareWith(Piece.newPiece(Piece.PieceType.KING, color));
        squares[firstRow][column++] = newSquareWith(Piece.newPiece(Piece.PieceType.BISHOP, color));
        squares[firstRow][column++] = newSquareWith(Piece.newPiece(Piece.PieceType.KNIGHT, color));
        squares[firstRow][column++] = newSquareWith(Piece.newPiece(Piece.PieceType.ROOK, color));

        column=0;
        for(int i=0 ;i<COLUMNS;i++){
            squares[secondRow][column++] = newSquareWith(Piece.newPiece(Piece.PieceType.PAWN, color));
        }

    }


    public Square getSquare(Coords coords) {
        return getSquare(coords.X, coords.Y);
    }

    public Square getSquare(int x, int y) {
        if (x < 0 || x > 7 || y < 0 || y > 7) {
            throw new RuntimeException("Index out of bound");
        }
        return squares[x][y];
    }

    private void initEmptySquares() {

        for (int i = 2; i < 6; i++) {
            for (int j = 0; j < 8; j++) {
                squares[i][j] = newEmptySquare();
            }
        }
    }

    static class Move {
        private final Square startSquare;
        private final Square endSquare;
        private final Piece pieceMoved;
        private final Piece pieceCaptured;

        private Move(Square startSquare, Square endSquare,
                     Piece pieceMoved, Piece pieceCaptured) {

            this.startSquare = Objects.requireNonNull(startSquare);
            this.endSquare = Objects.requireNonNull(endSquare);
            this.pieceMoved = Objects.requireNonNull(pieceMoved);
            this.pieceCaptured = pieceCaptured;

        }

        public Piece getPieceCaptured() { return pieceCaptured;}

    }

}

