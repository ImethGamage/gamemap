//20220546 - Imeth Lithmal Gamage

public class GameMap {
    private char[][] grid;

    public GameMap(int rows, int cols) {
        grid = new char[rows][cols];
        // Initialize all cells to '.'
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                grid[i][j] = '.';
            }
        }
    }

    public boolean isValidPoint(int row, int col) {
        int rows = grid.length;
        int cols = grid[0].length;
        return row >= 0 && row < rows && col >= 0 && col < cols;
    }

    public int getRows() {
        return grid.length;
    }

    public int getCols() {
        return grid[0].length;
    }

    public char getCell(int row, int col) {
        return grid[row][col];
    }

    public void setCell(int row, int col, char value) {
        grid[row][col] = value;
    }

    public int[] findSymbol(char symbol) {
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                if (grid[i][j] == symbol) {
                    return new int[]{i, j};
                }
            }
        }
        return null;
    }

    public void printMap() {
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                System.out.print(grid[i][j]);
            }
            System.out.println();
        }
    }
}
