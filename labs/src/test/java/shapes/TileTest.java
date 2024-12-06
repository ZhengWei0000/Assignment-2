package shapes;

import geometry.Vec2d;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class TileTest {

    @Test
    public void testContainsPoint() {
        ArrayList<Vec2d> vertices = new ArrayList<>();
        vertices.add(new Vec2d(0, 0));
        vertices.add(new Vec2d(10, 0));
        vertices.add(new Vec2d(10, 10));
        vertices.add(new Vec2d(0, 10));
        Tile tile = new Tile(new Vec2d(0, 0), vertices, Color.RED);

        assertTrue(tile.contains(new Vec2d(5, 5)));
        assertFalse(tile.contains(new Vec2d(15, 15)));
    }

    @Test
    public void testContainsTile() {
        ArrayList<Vec2d> vertices1 = new ArrayList<>();
        vertices1.add(new Vec2d(0, 0));
        vertices1.add(new Vec2d(10, 0));
        vertices1.add(new Vec2d(10, 10));
        vertices1.add(new Vec2d(0, 10));
        Tile tile1 = new Tile(new Vec2d(0, 0), vertices1, Color.RED);

        ArrayList<Vec2d> vertices2 = new ArrayList<>();
        vertices2.add(new Vec2d(2, 2));
        vertices2.add(new Vec2d(8, 2));
        vertices2.add(new Vec2d(8, 8));
        vertices2.add(new Vec2d(2, 8));
        Tile tile2 = new Tile(new Vec2d(0, 0), vertices2, Color.BLUE);

        assertTrue(tile1.contains(tile2));
        assertFalse(tile2.contains(tile1));
    }

    @Test
    public void testIntersectsTile() {
        ArrayList<Vec2d> vertices1 = new ArrayList<>();
        vertices1.add(new Vec2d(0, 0));
        vertices1.add(new Vec2d(10, 0));
        vertices1.add(new Vec2d(10, 10));
        vertices1.add(new Vec2d(0, 10));
        Tile tile1 = new Tile(new Vec2d(0, 0), vertices1, Color.RED);

        ArrayList<Vec2d> vertices2 = new ArrayList<>();
        vertices2.add(new Vec2d(5, 5));
        vertices2.add(new Vec2d(15, 5));
        vertices2.add(new Vec2d(15, 15));
        vertices2.add(new Vec2d(5, 15));
        Tile tile2 = new Tile(new Vec2d(0, 0), vertices2, Color.BLUE);

        assertTrue(tile1.intersects(tile2));

        ArrayList<Vec2d> vertices3 = new ArrayList<>();
        vertices3.add(new Vec2d(20, 20));
        vertices3.add(new Vec2d(30, 20));
        vertices3.add(new Vec2d(30, 30));
        vertices3.add(new Vec2d(20, 30));
        Tile tile3 = new Tile(new Vec2d(0, 0), vertices3, Color.GREEN);

        assertFalse(tile1.intersects(tile3));
    }
}
