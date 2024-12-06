package shapes;

import geometry.Vec2d;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class PuzzleModelTest {

    @Test
    public void testIsSolved() {
        ArrayList<Vec2d> boxVertices = new ArrayList<>();
        boxVertices.add(new Vec2d(0, 0));
        boxVertices.add(new Vec2d(100, 0));
        boxVertices.add(new Vec2d(100, 100));
        boxVertices.add(new Vec2d(0, 100));
        Tile box = new Tile(new Vec2d(0, 0), boxVertices, Color.BLACK);

        ArrayList<Tile> tiles = new ArrayList<>();
        ArrayList<Vec2d> tileVertices = new ArrayList<>();
        tileVertices.add(new Vec2d(10, 10));
        tileVertices.add(new Vec2d(20, 10));
        tileVertices.add(new Vec2d(20, 20));
        tileVertices.add(new Vec2d(10, 20));
        tiles.add(new Tile(new Vec2d(0, 0), tileVertices, Color.RED));

        PuzzleModel model = new PuzzleModel(box, tiles);

        assertFalse(model.isSolved());
    }
}
