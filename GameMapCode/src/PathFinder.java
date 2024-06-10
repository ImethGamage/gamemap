//20220546 - Imeth Lithmal Gamage

import java.awt.Point;
import java.util.*;

public class PathFinder {
    private static final int[][] DIRECTIONS = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};

    public static List<String> findShortestPath(GameMap map, int startRow, int startCol, int finishRow, int finishCol) {
        int rows = map.getRows();
        int cols = map.getCols();

        // Initialize distances, parent map, and heuristic scores
        int[][] distances = new int[rows][cols];
        for (int i = 0; i < rows; i++) {
            Arrays.fill(distances[i], Integer.MAX_VALUE);
        }
        distances[startRow][startCol] = 0;

        HashMap<Integer, int[]> parent = new HashMap<>();

        // Priority queue to store cells to visit next based on their priority
        PriorityQueue<int[]> pq = new PriorityQueue<>((a, b) -> {
            int f1 = distances[a[0]][a[1]] + heuristic(a[0], a[1], finishRow, finishCol);
            int f2 = distances[b[0]][b[1]] + heuristic(b[0], b[1], finishRow, finishCol);
            return f1 - f2;
        });
        pq.offer(new int[]{startRow, startCol});

        while (!pq.isEmpty()) {
            int[] current = pq.poll();
            int row = current[0];
            int col = current[1];

            // If reached the finish cell, break
            if (row == finishRow && col == finishCol) {
                return buildPath(parent, startRow, startCol, finishRow, finishCol, cols);
            }

            for (int[] dir : DIRECTIONS) {
                int newRow = row;
                int newCol = col;

                // Simulate sliding in the chosen direction until hitting an obstacle or reaching the finish
                while (isValidMove(new Point(newCol + dir[1], newRow + dir[0]), map)) {
                    newRow += dir[0];
                    newCol += dir[1];

                    // Check if the current position matches the finish ('F')
                    if (map.getCell(newRow, newCol) == 'F') {
                        newRow = finishRow; // Set newRow to finishRow
                        newCol = finishCol; // Set newCol to finishCol
                        break; // Stop sliding loop
                    }
                }

                // Calculate the new distance
                int newDistance = distances[row][col] + Math.abs(newRow - row) + Math.abs(newCol - col);

                // If the new distance is less than the current distance, update it
                if (newDistance < distances[newRow][newCol]) {
                    distances[newRow][newCol] = newDistance;
                    pq.offer(new int[]{newRow, newCol});
                    parent.put(newRow * cols + newCol, new int[]{row, col});
                }
            }
        }

        return Collections.emptyList(); // No path found
    }

    private static boolean isValidMove(Point point, GameMap map) {
        int row = point.y;
        int col = point.x;
        return map.isValidPoint(row, col) && map.getCell(row, col) != '0';
    }

    private static int heuristic(int row, int col, int finishRow, int finishCol) {
        // Use Manhattan distance as heuristic
        return Math.abs(row - finishRow) + Math.abs(col - finishCol);
    }

    private static List<String> buildPath(HashMap<Integer, int[]> parent, int startRow, int startCol, int finishRow, int finishCol, int cols) {
        List<String> path = new ArrayList<>();
        int[] current = {finishRow, finishCol};

        while (current[0] != startRow || current[1] != startCol) {
            int parentIndex = current[0] * cols + current[1];
            int[] parentPos = parent.get(parentIndex);
            path.add(String.format("Move to (%d,%d)", parentPos[1] + 1, parentPos[0] + 1)); // Swap row and column indices
            current = parentPos;
        }
        return path;
    }
}
