package blocks;


import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.Set;

import blocks.BlockShapes.Piece;
import blocks.BlockShapes.Shape;
import blocks.BlockShapes.Cell;
import blocks.BlockShapes.ShapeSet;
import blocks.BlockShapes.SpriteState;
import blocks.BlockShapes.Sprite;


// class should work in a basic way as provided if all the todo's are implemented in the other classes
// though you need to provide or complete the implementations for the methods in todos below


public class GameView extends JComponent {
    ModelInterface model;
    Palette palette;
    int margin = 5;
    int shapeRegionHeight;
    int cellSize = 40;
    int smallCellSize = 40;
    int paletteCellSize = 20;
    int shrinkSize = 30;
    Piece ghostShape = null;
    int pieceSpacing = 15;
    List<Shape> poppableRegions = null;

    public GameView(ModelInterface model, Palette palette) {
        this.model = model;
        this.palette = palette;
        this.shapeRegionHeight = cellSize * ModelInterface.height / 2;
    }

    private void paintShapePalette(Graphics g, int cellSize) {
        // paint a background colour
        // then get the list of current shapes from the palette
        g.setColor(Color.LIGHT_GRAY);
        g.fillRect(margin, margin + ModelInterface.height * cellSize, ModelInterface.width * cellSize, shapeRegionHeight);

        for (Sprite sprite : palette.getSprites()) {
            // todo: paint the sprite in the palette
            if (sprite.state == SpriteState.IN_PALETTE) {
                g.setColor(Color.BLUE);
                for (Cell cell : sprite.shape) {
                    int x = sprite.px + cell.x() * smallCellSize;
                    int y = sprite.py + cell.y() * smallCellSize;
                    g.setColor(Color.BLUE);
                    g.fillRect(x, y, smallCellSize, smallCellSize);

                    g.setColor(Color.BLACK);
                    g.drawRect(x, y, smallCellSize, smallCellSize);

                }
            }
        }

    }

    private void paintPoppableRegions(Graphics g, int cellSize) {
        // todo: implement
        if (poppableRegions == null) {
            return;
        }

        g.setColor(Color.pink);
        for (Shape region : poppableRegions) {
            for (Cell cell : region) {
                int x = margin + cell.x() * cellSize;
                int y = margin + cell.y() * cellSize;
                g.fill3DRect(x, y, cellSize, cellSize,true);
            }
        }


    }

    private void paintGhostShape(Graphics g, int cellSize) {
        // todo: implement
        int spacing = cellSize / 8;
        if (ghostShape != null) {
            g.setColor(Color.CYAN);
            for (Cell cell : ghostShape.cells()) {
                int x = margin + cell.x() * cellSize;
                int y = margin + cell.y() * cellSize;
                g.fill3DRect(x, y, cellSize, cellSize, true);
            }
        }

        // 绘制拖动中的拼块
        for (Sprite sprite : palette.getSprites()) {
            if (sprite.state == SpriteState.IN_PLAY) {
                g.setColor(Color.BLUE);
                for (Cell cell : sprite.shape) {
                    int x = sprite.px + cell.x() * (smallCellSize+spacing+ + pieceSpacing);
                    int y = sprite.py + cell.y() * (smallCellSize+spacing+ + pieceSpacing);
                    g.fill3DRect(x, y, smallCellSize, smallCellSize, true);
                }
            }
        }


        System.out.println("painting ghost shape: " + ghostShape);
    }

    private void paintGrid(Graphics g) {
        int x0 = margin;
        int y0 = margin;
        int width = ModelInterface.width * cellSize;
        int height = ModelInterface.height * cellSize;
        Set<Cell> occupiedCells = model.getOccupiedCells();
        g.setColor(Color.BLACK);
        g.drawRect(x0, y0, width, height);
        for (int x = 0; x < ModelInterface.width; x++) {
            for (int y = 0; y < ModelInterface.height; y++) {
                // todo: paint the cells that are occupied in a different colour
                int cellX = x0 + x * cellSize;
                int cellY = y0 + y * cellSize;
                if (occupiedCells.contains(new Cell(x, y))) {
                    g.setColor(Color.GREEN);
                } else {
                    g.setColor(Color.WHITE);
                }
                g.fill3DRect(cellX, cellY, cellSize, cellSize, true);
            }
        }
    }

    private void paintMiniGrids(Graphics2D g) {
        // for now we're going to do this based on the cellSize multiple
        int s = ModelInterface.subSize;
        g.setStroke(new BasicStroke(2));
        g.setColor(Color.BLACK);
        for (int x = 0; x < ModelInterface.width; x += s) {
            for (int y = 0; y < ModelInterface.height; y += s) {
                g.drawRect(margin + x * cellSize, margin + y * cellSize, s * cellSize, s * cellSize);
            }
        }
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);

        paintGrid(g);
        paintMiniGrids((Graphics2D) g); // cosmetic
        paintGhostShape(g, cellSize);
        paintPoppableRegions(g, cellSize);
        paintShapePalette(g, cellSize);


    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(
                ModelInterface.width * cellSize + 6 * margin,
                ModelInterface.height * cellSize + 6 * margin + shapeRegionHeight
        );
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Clean Blocks");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ModelInterface model = new ModelSet();
        Shape shape = new ShapeSet().getShapes().get(0);
        Piece piece = new Piece(shape, new Cell(0, 0));
        Palette palette = new Palette();
        model.place(piece);
        frame.add(new GameView(model, palette));
        frame.pack();
        frame.setVisible(true);
    }
}
