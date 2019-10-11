package demo.chess.player;

import demo.chess.game.piece.Piece;

public class Player {
    private final boolean white;

    private Player(boolean white){
        this.white = white;
    }

    public static Player newWhiteSide(){
        return  new Player(true);
    }

    public static Player newBlackSide(){
        return  new Player(false);
    }


    public boolean isWhiteSide() {
        return white;
    }



    public boolean owns(Piece piece){
        return this.isWhiteSide() == piece.isWhite();
    }

}
