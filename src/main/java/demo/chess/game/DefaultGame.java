package demo.chess.game;

import demo.chess.Game;
import demo.chess.game.Board.Move;
import demo.chess.player.Player;
import demo.chess.game.piece.Piece;
import demo.chess.game.piece.Piece.PieceColor;

import java.util.ArrayList;
import java.util.List;

public class DefaultGame implements Game {


    private Board board;
    private Player white, black, currentTurn;

    //History, Logging
    private List<Move> movesPlayed;
    private List<Piece> capturedPieces;

    private DefaultGame(){}

    public static DefaultGame newGame(Player p1, Player p2){
        DefaultGame game = new DefaultGame();
        game.initialize(p1, p2);
        return game;
    }


    @Override
    public Board board() {
        return board;
    }

    @Override
    public boolean move(Player player,
                        Coords start, Coords end) {

        Square startSquare = board.getSquare(start);
        Square endSquare = board.getSquare(end);
        Piece sourcePiece = startSquare.getPiece();

        boolean validMove = turnIsValid(player) &&
                player.owns(sourcePiece) &&
                sourcePiece.isMoveValid(board, start, end);

        if (validMove) {

            Move move = board.makeMove(startSquare, endSquare);
            saveHistory(move);
            switchTurn();
        }

        return validMove;
    }

    private void saveHistory(Move move) {
        if(move.getPieceCaptured()!=null){
            capturedPieces.add(move.getPieceCaptured());
        }

        movesPlayed.add(move);
    }

    public Player whiteSidePlayer() {
        return white;
    }

    public Player blackSidePlayer() {
        return black;
    }

    private void initialize(Player p1, Player p2) {

        if (p1.isWhiteSide() && !p2.isWhiteSide()) {
            this.white = p1;
            this.black = p2;
        } else if (!p1.isWhiteSide() && p2.isWhiteSide()) {
            this.white = p2;
            this.black = p1;
        } else {
            throw new IllegalArgumentException();
        }

        this.board = Board.newBoard();
        this.movesPlayed = new ArrayList<>();
        this.capturedPieces = new ArrayList<>();
        this.setTurn(PieceColor.WHITE);

    }

    private void setTurn(PieceColor turn) {
        currentTurn = (turn == PieceColor.BLACK) ? black : white;
    }

    private void switchTurn(){
        currentTurn = (currentTurn==black)?white:black;
    }



    private boolean turnIsValid(Player player) {
        return player == currentTurn;
    }
}
