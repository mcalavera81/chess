package demo.chess;

import demo.chess.game.Board;
import demo.chess.game.Coords;
import demo.chess.player.Player;

public interface Game {
    Player blackSidePlayer();
    Player whiteSidePlayer();
    Board board();
    boolean move(Player player, Coords start, Coords end);

}

