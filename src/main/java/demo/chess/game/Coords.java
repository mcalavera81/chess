package demo.chess.game;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode
public class Coords {

    public final int X;
    public final int Y;

    private Coords(int x, int y) {
        this.X = x;
        this.Y = y;
    }

    public static Coords of(int x, int y){
        Coords coords = new Coords(x, y);
        return coords;
    }


}
