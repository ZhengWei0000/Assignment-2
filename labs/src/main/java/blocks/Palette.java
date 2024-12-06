package blocks;

import java.util.ArrayList;
import java.util.List;

import blocks.BlockShapes.Shape;
import blocks.BlockShapes.ShapeSet;
import blocks.BlockShapes.SpriteState;
import blocks.BlockShapes.Sprite;
import blocks.BlockShapes.PixelLoc;

public class Palette {
    ArrayList<Shape> shapes = new ArrayList<>();
    List<Sprite> sprites;
    int nShapes = 3;
    private int paletteCellSize = 20;

    public Palette() {
        shapes.addAll(new ShapeSet().getShapes());
        sprites = new ArrayList<>();
        replenish();
    }

    public ArrayList<Shape> getShapes() {
        return shapes;
    }

    public ArrayList<Shape> getShapesToPlace() {
        // todo: implement

        // return a list of shapes that are in the palette - could use streams to filter this
        ArrayList<Shape> shapesToPlace = new ArrayList<>();
        for (Sprite sprite : sprites) {
            if (sprite.state == SpriteState.IN_PALETTE) {
                shapesToPlace.add(sprite.shape);
            }
        }
        return shapesToPlace;
    }

    public List<Sprite> getSprites() {
        return sprites;
    }

    // if we have a sprite that contains the point (px, py), return it
    // and the size of the cells - the sprite location is already in pixel coordinates
    public Sprite getSprite(PixelLoc mousePoint, int cellSize) {
        // todo: implement
        for (Sprite sprite : sprites) {
            if (sprite.contains(mousePoint, cellSize)) {
                return sprite; // 返回匹配的 Sprite
            }
        }
        return null;
    }

    private int nReadyPieces() {
        int count = 0;
        for (Sprite sprite : sprites) {
            if (sprite.state == SpriteState.IN_PALETTE || sprite.state == SpriteState.IN_PLAY) {
                count++;
            }
        }
        System.out.println("nReadyPieces: " + count);
        return count;
    }

    public void doLayout(int x0, int y0, int cellSize) {
        // todo: implement
        int x = x0;
        int y = y0;
        int spritesPerRow = 3; // 每行形状的最大数量

        for (Sprite sprite : sprites) {
            sprite.px = x;
            sprite.py = y;

            // 调整下一个 Sprite 的位置
            x += paletteCellSize * 4; // 使用 paletteCellSize
            if (x >= x0 + spritesPerRow * paletteCellSize * 4) {
                x = x0; // 换行
                y += paletteCellSize * 4;
            }
        }




        // layout the sprites in the palette
    }

    public void replenish() {
        // 如果调色板中仍有可用形状，不执行补充
        if (nReadyPieces() > 0) {
            return;
        }
        sprites.clear();
        for (int i = 0; i < nShapes; i++) {
            Shape shape = shapes.get((int) (Math.random() * shapes.size()));
            sprites.add(new Sprite(shape, 0, 0));
        }
        doLayout(5, ModelInterface.height * 40 + 10, paletteCellSize);
    }
        // todo: implement


    public static void main(String[] args) {
        Palette palette = new Palette();
        System.out.println(palette.shapes);
        System.out.println(palette.sprites);
        palette.doLayout(0, 0, 20);
        System.out.println(palette.sprites);
    }
}
