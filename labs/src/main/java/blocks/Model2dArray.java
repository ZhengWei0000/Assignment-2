package blocks;

/**
 * Logical model for the Blocks Puzzle
 * This handles the game logic, such as the grid, the pieces, and the rules for
 * placing pieces and removing lines and subgrids.
 * <p>
 * Note this has no dependencies on the UI or the game view, and no
 * concept of pixel-space or screen coordinates.
 * <p>
 * The standard block puzzle is on a 9x9 grid, so all placeable shapes will have
 * cells in that range.
 */

import blocks.BlockShapes.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Model2dArray extends State2dArray implements ModelInterface {
    List<Shape> regions = new RegionHelper().allRegions();

    public Model2dArray() {
        grid = new boolean[width][height];
        // initially all cells are empty (false) - they would be by default anyway
        // but this makes it explicit
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < 9; j++) {
                grid[i][j] = false;
            }
        }
    }

    public int getScore() {
        return score;
    }


    // interestingly, for canPlace we could also use sets to store the occupied cells
    // and then check if the shape's cells intersect with the occupied cells

    public boolean canPlace(Piece piece) {
        // todo: implement

        // check if the shape can be placed at this loc
        for (Cell cell : piece.cells()) {
            // 检查是否在网格范围内
            if (cell.x() < 0 || cell.x() >= width || cell.y() < 0 || cell.y() >= height) {
                return false;
            }
            // 检查是否未被占用
            if (grid[cell.x()][cell.y()]) {
                return false;
            }
        }
        return true;
    }

    @Override
    public void place(Piece piece) {
        // todo: implement
        for (Cell cell : piece.cells()) {
            grid[cell.x()][cell.y()] = true;
        }

        // 获取可以弹出的区域
        List<Shape> poppableRegions = getPoppableRegions(piece);

        // 移除弹出区域并更新分数
        for (Shape region : poppableRegions) {
            remove(region);
            score += region.size(); // 分数增加弹出区域的单元格数量
        }
    }

    @Override
    public void remove(Shape region) {
        // todo: implement
        for (Cell cell : region) {
            grid[cell.x()][cell.y()] = false;
        }
    }

    public boolean isComplete(Shape region) {
        // todo: implement
        // check if the shape is complete, i.e. all cells are occupied
        for (Cell cell : region) {
            if (!grid[cell.x()][cell.y()]) {
                return false; // 如果有任一单元格未被占用，则返回 false
            }
        }
        return true;
    }

    private boolean wouldBeComplete(Shape region, List<Cell> toAdd) {
        // todo: implement
        // check if the shape is complete, i.e. all cells are occupied
        Set<Cell> tempOccupiedCells = getOccupiedCells();
        tempOccupiedCells.addAll(toAdd); // 模拟添加新的单元格

        for (Cell cell : region) {
            if (!tempOccupiedCells.contains(cell)) {
                return false; // 如果区域内有未被占用的单元格，则返回 false
            }
        }
        return true;
    }

    @Override
    public boolean isGameOver(List<Shape> palettePieces) {
        // todo: implement
        // if any shape in the palette can be placed, the game is not over
        for (Shape shape : palettePieces) {
            if (canPlaceAnywhere(shape)) {
                return false; // 如果有任意形状可以放置，则游戏未结束
            }
        }
        return true;
    }

    public boolean canPlaceAnywhere(Shape shape) {
        // todo: implement
        // check if the shape can be placed anywhere on the grid
        // by checking if it can be placed at any loc
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                Piece piece = new Piece(shape, new Cell(x, y));
                if (canPlace(piece)) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public List<Shape> getPoppableRegions(Piece piece) {
        // todo: implement
        // iterate over the regions
        List<Shape> poppableRegions = new ArrayList<>();

        // 模拟添加当前 Piece 后的占用单元格
        Set<Cell> tempOccupiedCells = getOccupiedCells();
        for (Cell cell : piece.cells()) {
            tempOccupiedCells.add(cell);
        }

        // 检查每个区域是否可以弹出
        for (Shape region : regions) {
            boolean complete = true;
            for (Cell cell : region) {
                if (!tempOccupiedCells.contains(cell)) {
                    complete = false;
                    break;
                }
            }
            if (complete) {
                poppableRegions.add(region);
            }
        }

        return poppableRegions;
    }

    @Override
    public Set<Cell> getOccupiedCells() {
        // todo: implement
        Set<Cell> occupiedCells = new HashSet<>();
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                if (grid[x][y]) {
                    occupiedCells.add(new Cell(x, y));
                }
            }
        }
        return occupiedCells;
    }
}

