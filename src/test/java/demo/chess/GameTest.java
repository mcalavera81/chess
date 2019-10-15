package demo.chess;

import demo.chess.game.Board;
import demo.chess.game.Coords;
import demo.chess.game.DefaultGame;
import demo.chess.game.Square;
import demo.chess.game.piece.Piece;
import demo.chess.game.piece.Piece.PieceColor;
import demo.chess.game.piece.Piece.PieceType;
import demo.chess.game.piece.Queen;
import demo.chess.player.Player;
import lombok.Value;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static demo.chess.game.piece.Piece.PieceColor.BLACK;
import static demo.chess.game.piece.Piece.PieceColor.WHITE;
import static demo.chess.game.piece.Piece.PieceType.*;
import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

public class GameTest {


    @Test(expected = IllegalArgumentException.class)
    public void gameWith2PlayersBlackSide() {
        DefaultGame.newGame(Player.newBlackSide(), Player.newBlackSide());
    }

    @Test(expected = IllegalArgumentException.class)
    public void gameWithPlayersWhiteSide() {
        DefaultGame.newGame(Player.newWhiteSide(), Player.newWhiteSide());
    }

    @Test
    public void gameWithPlayersOppositeSides() {
        Player blackSide = Player.newBlackSide();
        Player whiteSide = Player.newWhiteSide();

        Game gameBlackWhite = DefaultGame.newGame(blackSide, whiteSide);
        Assert.assertEquals(blackSide, gameBlackWhite.blackSidePlayer());
        Assert.assertEquals(whiteSide, gameBlackWhite.whiteSidePlayer());

        Game gameWhiteBlack = DefaultGame.newGame(whiteSide, blackSide);
        Assert.assertEquals(blackSide, gameWhiteBlack.blackSidePlayer());
        Assert.assertEquals(whiteSide, gameWhiteBlack.whiteSidePlayer());
    }

    @Test
    public void gameWithBoardInitializedRooks() {
        Player blackSide = Player.newBlackSide();
        Player whiteSide = Player.newWhiteSide();


        Board board = ((Game) DefaultGame.newGame(blackSide, whiteSide)).board();

        validatePieces(PieceExpectation.of(List.of(BLACK, BLACK, WHITE, WHITE), ROOK),
                List.of(board.getSquare(0, 0), board.getSquare(0, 7),
                        board.getSquare(7, 0), board.getSquare(7, 7)));


    }


    @Test
    public void gameWithBoardInitializedKnights() {
        Player blackSide = Player.newBlackSide();
        Player whiteSide = Player.newWhiteSide();


        Board board = ((Game) DefaultGame.newGame(blackSide, whiteSide)).board();

        validatePieces(PieceExpectation.of(List.of(BLACK, BLACK, WHITE, WHITE), KNIGHT),
                List.of(board.getSquare(0, 1), board.getSquare(0, 6),
                        board.getSquare(7, 1), board.getSquare(7, 6)));

    }

    @Test
    public void gameWithBoardInitializedBishops() {
        Player blackSide = Player.newBlackSide();
        Player whiteSide = Player.newWhiteSide();


        Board board = ((Game) DefaultGame.newGame(blackSide, whiteSide)).board();

        validatePieces(PieceExpectation.of(List.of(BLACK, BLACK, WHITE, WHITE), BISHOP),
                List.of(board.getSquare(0, 2), board.getSquare(0, 5),
                        board.getSquare(7, 2), board.getSquare(7, 5)));

    }

    @Test
    public void gameWithBoardInitializedQueens() {


        Board board = ((Game) DefaultGame.newGame(Player.newBlackSide(), Player.newWhiteSide())).board();

        validatePieces(PieceExpectation.of(List.of(BLACK, WHITE), QUEEN),
                List.of(board.getSquare(0, 3),
                        board.getSquare(7, 3)));

    }


    @Test
    public void gameWithBoardInitializedKings() {

        Board board = ((Game) DefaultGame.newGame(Player.newBlackSide(), Player.newWhiteSide())).board();

        validatePieces(PieceExpectation.of(List.of(BLACK, WHITE), KING),
                List.of(board.getSquare(0, 4), board.getSquare(7, 4))
        );

    }


    @Test
    public void gameWithBoardInitializedPawns() {

        Board board = ((Game) DefaultGame.newGame(Player.newBlackSide(), Player.newWhiteSide())).board();


        List<Square> squares = Stream.of(1, 6)
                .flatMap(row -> IntStream.range(0, 8).mapToObj(column -> board.getSquare(row, column)))
                .collect(Collectors.toList());


        List<PieceColor> expectedColors = Stream.of(BLACK, WHITE)
                .flatMap(color -> IntStream.range(0, 8).mapToObj($ -> color))
                .collect(Collectors.toList());

        validatePieces(PieceExpectation.of(expectedColors, PAWN), squares);

    }

    @Test
    public void blackPlayerCannotMoveFirst() {


        Game game = DefaultGame.newGame(Player.newBlackSide(), Player.newWhiteSide());

        assertThat(game.board().getSquare(1, 3).isEmpty()).isFalse();
        assertThat(game.board().getSquare(2, 3).isEmpty()).isTrue();
        assertThat(game.move(Player.newBlackSide(), Coords.of(1, 3), Coords.of(2, 3))).isFalse();
        assertThat(game.board().getSquare(1, 3).isEmpty()).isFalse();
        assertThat(game.board().getSquare(2, 3).isEmpty()).isTrue();
    }


    @Test
    public void whitePlayerCannotMoveBlackPawn() {
        Player blackSide = Player.newBlackSide();
        Player whiteSide = Player.newWhiteSide();
        Game game = DefaultGame.newGame(blackSide, whiteSide);
        assertThat(game.move(whiteSide, Coords.of(1, 3), Coords.of(2, 3))).isFalse();
    }


    @Test
    public void whitePlayerMovesWhitePawn() {
        Player blackSide = Player.newBlackSide();
        Player whiteSide = Player.newWhiteSide();
        Game game = DefaultGame.newGame(blackSide, whiteSide);
        assertThat(game.move(whiteSide, Coords.of(6, 3),Coords.of(5, 3) )).isTrue();
        assertThat(game.board().getSquare(6, 3).isEmpty()).isTrue();
        assertThat(game.board().getSquare(5, 3).isEmpty()).isFalse();
    }


    @Test
    public void whitePawnMoveForward3SquaresIsIllegal() {
        Player blackSide = Player.newBlackSide();
        Player whiteSide = Player.newWhiteSide();
        Game game = DefaultGame.newGame(blackSide, whiteSide);

        assertThat(game.move(whiteSide, Coords.of(6, 3), Coords.of(3, 3))).isFalse();
    }

    @Test
    public void blackPawnMoveForward3SquaresIsIllegal() {
        Player blackSide = Player.newBlackSide();
        Player whiteSide = Player.newWhiteSide();
        Game game = DefaultGame.newGame(blackSide, whiteSide);
        game.move(whiteSide, Coords.of(6, 3), Coords.of(5, 3));

        assertThat(game.move(blackSide, Coords.of(1, 3), Coords.of(4, 3))).isFalse();
    }

    @Test
    public void whitePawnMoveForward2SquaresIsLegal() {
        Player blackSide = Player.newBlackSide();
        Player whiteSide = Player.newWhiteSide();
        Game game = DefaultGame.newGame(blackSide, whiteSide);

        assertThat(game.move(whiteSide, Coords.of(6, 3), Coords.of(4, 3))).isTrue();
    }

    @Test
    public void blackPawnMoveForward2SquaresIsLegal() {
        Player blackSide = Player.newBlackSide();
        Player whiteSide = Player.newWhiteSide();
        Game game = DefaultGame.newGame(blackSide, whiteSide);
        game.move(whiteSide, Coords.of(6, 3), Coords.of(5, 3));


        assertThat(game.move(blackSide, Coords.of(1, 3), Coords.of(3, 3))).isTrue();
    }

    @Test
    public void blackPawn_Can_not_MoveForward2Square_Blocked() {
        Player blackSide = Player.newBlackSide();
        Player whiteSide = Player.newWhiteSide();
        Game game = DefaultGame.newGame(blackSide, whiteSide);
        game.move(whiteSide, Coords.of(6, 3), Coords.of(5, 3));
        game.move(blackSide, Coords.of(1, 0), Coords.of(2, 0));
        game.move(whiteSide, Coords.of(5, 3), Coords.of(4, 3));
        game.move(blackSide, Coords.of(1, 1), Coords.of(2, 1));
        game.move(whiteSide, Coords.of(4, 3), Coords.of(3, 3));
        game.move(blackSide, Coords.of(1, 2), Coords.of(2, 2));
        game.move(whiteSide, Coords.of(3, 3), Coords.of(2, 3));


        assertThat(game.move(blackSide, Coords.of(1, 3), Coords.of(3, 3))).isFalse();
    }

    @Test
    public void blackPawn_Can_not_MoveForwardOneSquare_Blocked() {
        Player blackSide = Player.newBlackSide();
        Player whiteSide = Player.newWhiteSide();
        Game game = DefaultGame.newGame(blackSide, whiteSide);
        game.move(whiteSide, Coords.of(6, 3), Coords.of(5, 3));
        game.move(blackSide, Coords.of(1, 0), Coords.of(2, 0));

        game.move(whiteSide, Coords.of(5, 3), Coords.of(4, 3));
        game.move(blackSide, Coords.of(1, 1), Coords.of(2, 1));

        game.move(whiteSide, Coords.of(4, 3), Coords.of(3, 3));
        game.move(blackSide, Coords.of(1, 2), Coords.of(2, 2));

        game.move(whiteSide, Coords.of(3, 3), Coords.of(2, 3) );


        assertThat(game.move(blackSide, Coords.of(1, 3), Coords.of(2, 3))).isFalse();
    }

    @Test
    public void whitePawn_Can_MoveForward_1_Square() {
        Player blackSide = Player.newBlackSide();
        Player whiteSide = Player.newWhiteSide();
        Game game = DefaultGame.newGame(blackSide, whiteSide);

        assertThat(game.move(whiteSide, Coords.of(6, 3), Coords.of(5, 3))).isTrue();
    }

    @Test
    public void blackPawn_Can_MoveForward_1_Square() {
        Player blackSide = Player.newBlackSide();
        Player whiteSide = Player.newWhiteSide();
        Game game = DefaultGame.newGame(blackSide, whiteSide);
        game.move(whiteSide, Coords.of(6, 3), Coords.of(5, 3));

        assertThat(game.move(blackSide, Coords.of(1, 3), Coords.of(2, 3))).isTrue();
    }

    @Test
    public void whitePawn_Can_Capture_BlackPawn() {
        Player blackSide = Player.newBlackSide();
        Player whiteSide = Player.newWhiteSide();
        Game game = DefaultGame.newGame(blackSide, whiteSide);
        Board board = game.board();

        int whiteColumn = 3;

        game.move(whiteSide, Coords.of(6, whiteColumn), Coords.of(4, whiteColumn));
        game.move(blackSide, Coords.of(1, whiteColumn + 1), Coords.of(3, whiteColumn + 1));


        assertThat(board.getSquare(3, whiteColumn + 1).getPiece().isWhite()).isFalse();
        assertThat(game.move(whiteSide, Coords.of(4, whiteColumn), Coords.of(3, whiteColumn + 1))).isTrue();
        assertThat(board.getSquare(4, whiteColumn).isEmpty()).isTrue();
        assertThat(board.getSquare(3, whiteColumn + 1).getPiece().isWhite()).isTrue();
    }

    @Test
    public void whitePawn_Can_Not_Capture_BlackPawn_NotContiguous() {

        Player blackSide = Player.newBlackSide();
        Player whiteSide = Player.newWhiteSide();
        Game game = DefaultGame.newGame(blackSide, whiteSide);

        int whiteColumn = 3;
        assertThat(game.move(whiteSide, Coords.of(6, whiteColumn), Coords.of(4, whiteColumn))).isTrue();
        assertThat(game.move(blackSide, Coords.of(1, whiteColumn + 2), Coords.of(3, whiteColumn + 2))).isTrue();

        assertThat(game.move(whiteSide, Coords.of(4, whiteColumn), Coords.of(3, whiteColumn + 2))).isFalse();
    }


    @Test
    public void whiteKnight_Can_Not_Move_2Up2RightMove() {
        Player blackSide = Player.newBlackSide();
        Player whiteSide = Player.newWhiteSide();
        Game game = DefaultGame.newGame(blackSide, whiteSide);

        assertThat(game.move(whiteSide, Coords.of(7, 1), Coords.of(5, 3))).isFalse();


    }

    @Test
    public void whiteKnight_Can_Move_2Up1Right() {
        Player blackSide = Player.newBlackSide();
        Player whiteSide = Player.newWhiteSide();
        Game game = DefaultGame.newGame(blackSide, whiteSide);
        Board board = game.board();
        assertThat(game.move(whiteSide, Coords.of(7, 1), Coords.of(5, 2))).isTrue();

        assertThat(board.getSquare(7, 1).isEmpty()).isTrue();
        assertThat(board.getSquare(5, 2).getPiece())
                .extracting("white", "type").containsExactly(true, KNIGHT);


    }

    @Test
    public void whiteKnight_Can_Move_2UpOneLeftMove() {
        Player blackSide = Player.newBlackSide();
        Player whiteSide = Player.newWhiteSide();
        Game game = DefaultGame.newGame(blackSide, whiteSide);
        Board board = game.board();
        assertThat(game.move(whiteSide, Coords.of(7, 1), Coords.of(5, 0))).isTrue();

        assertThat(board.getSquare(7, 1).isEmpty()).isTrue();
        assertThat(board.getSquare(5, 0).getPiece())
                .extracting("white", "type").containsExactly(true, KNIGHT);

    }

    @Test
    public void whiteKnight_Can_Not_Move_CapturingPieceSameSide() {
        Player blackSide = Player.newBlackSide();
        Player whiteSide = Player.newWhiteSide();
        Game game = DefaultGame.newGame(blackSide, whiteSide);
        Board board = game.board();
        assertThat(game.move(whiteSide, Coords.of(7, 1), Coords.of(6, 3))).isFalse();

        assertThat(board.getSquare(7, 1).isEmpty()).isFalse();
        assertThat(board.getSquare(6, 3).getPiece())
                .extracting("white", "type").containsExactly(true, PAWN);


    }

    @Test
    public void whiteKnight_Can_Capture_BlackPawn() {

        Player blackSide = Player.newBlackSide();
        Player whiteSide = Player.newWhiteSide();
        Game game = DefaultGame.newGame(blackSide, whiteSide);
        Board board = game.board();

        assertThat(game.move(whiteSide, Coords.of(7, 1),Coords.of(5, 2) )).isTrue();
        assertThat(game.move(blackSide, Coords.of(1, 3), Coords.of(3, 3))).isTrue();

        Square square = board.getSquare(3, 3);

        assertThat(square.getPiece().isWhite());
        assertThat(square.getPiece().type() == PieceType.PAWN);

        assertThat(game.move(whiteSide, Coords.of(5, 2) , Coords.of(3, 3))).isTrue();

        assertThat(!square.getPiece().isWhite());
        assertThat(square.getPiece().type() == PieceType.ROOK);

    }


    @Test
    public void whiteBishop_Can_Move_OneUpOneLeft() {
        Player blackSide = Player.newBlackSide();
        Player whiteSide = Player.newWhiteSide();
        Game game = DefaultGame.newGame(blackSide, whiteSide);
        Board board = game.board();

        assertThat(game.move(whiteSide, Coords.of(6, 1), Coords.of(5, 1))).isTrue();
        assertThat(game.move(blackSide, Coords.of(1, 4) ,Coords.of(2, 4) )).isTrue();

        assertThat(game.move(whiteSide, Coords.of(7, 2), Coords.of(6, 1))).isTrue();

        assertThat(board.getSquare(6, 1).getPiece()).extracting("white", "type").containsExactly(true, BISHOP);
        assertThat(board.getSquare(5, 1).getPiece()).extracting("white", "type").containsExactly(true, PAWN);

    }


    @Test
    public void whiteBishop_Can_Capture_BlackBishop() {

        Player blackSide = Player.newBlackSide();
        Player whiteSide = Player.newWhiteSide();
        Game game = DefaultGame.newGame(blackSide, whiteSide);
        Board board = game.board();


        assertThat(game.move(whiteSide, Coords.of(6, 1), Coords.of(5, 1)  )).isTrue();
        assertThat(game.move(blackSide, Coords.of(1, 4), Coords.of(2, 4))).isTrue();

        assertThat(game.move(whiteSide, Coords.of(7, 2), Coords.of(5, 0))).isTrue();
        assertThat(game.move(blackSide, Coords.of(2, 4), Coords.of(3, 4))).isTrue();

        assertThat(game.move(whiteSide, Coords.of(5, 0), Coords.of(0, 5))).isTrue();
        assertThat(board.getSquare(7, 2).isEmpty()).isTrue();
        assertThat(board.getSquare(0, 5).getPiece()).extracting("white", "type").containsExactly(true, BISHOP);


    }

    @Test
    public void whiteBishop_Can_not_MoveForwardOneSquare_CapturingPieceSameSide() {
        Player blackSide = Player.newBlackSide();
        Player whiteSide = Player.newWhiteSide();
        Game game = DefaultGame.newGame(blackSide, whiteSide);

        assertThat(game.move(whiteSide, Coords.of(7, 2), Coords.of(6, 1))).isFalse();
    }


    @Test
    public void whiteBishop_Can_not_Jump_pieces() {
        Player blackSide = Player.newBlackSide();
        Player whiteSide = Player.newWhiteSide();
        Game game = DefaultGame.newGame(blackSide, whiteSide);

        assertThat(game.move(whiteSide, Coords.of(7, 2), Coords.of(5, 0))).isFalse();

    }

    public void demo(){
        Queen.newPiece(null, null);
    }

    private void validatePieces(PieceExpectation expectation, List<Square> squares) {

        assertThat(squares).extracting("piece.white")
                .isEqualTo(expectation.color.stream().map(c -> c == PieceColor.WHITE).collect(Collectors.toList()));
        assertThat(squares).extracting("piece.type").containsOnly(expectation.pieceType);
    }

    @Value(staticConstructor = "of")
    static class PieceExpectation {
        List<PieceColor> color;
        PieceType pieceType;
    }


}
