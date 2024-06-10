//20220546 - Imeth Lithmal Gamage

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class MapParser {
    public static GameMap parseMapFromFile(String fileName) {
        File file = new File(fileName);
        if (!file.exists()) {
            System.out.println("File not found: " + fileName);
            return null;
        }

        try {
            Scanner scanner = new Scanner(file);
            int rows = 0, cols = 0;

            // Determine the number of rows and columns in the map
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine().trim();
                if (!line.isEmpty()) {
                    if (cols == 0) {
                        cols = line.length();
                    } else if (cols != line.length()) {
                        throw new IllegalArgumentException("Inconsistent column lengths in the map.");
                    }
                    rows++;
                }
            }

            // Reset scanner to read from the beginning of the file
            scanner.close();
            scanner = new Scanner(file);

            // Initialize the map
            GameMap map = new GameMap(rows, cols);
            int row = 0;

            // Populate the map with symbols
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine().trim();
                if (!line.isEmpty()) {
                    for (int col = 0; col < cols; col++) {
                        char symbol = line.charAt(col);
                        map.setCell(row, col, symbol);
                    }
                    row++;
                }
            }

            return map;
        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            System.out.println("Error parsing the map: " + e.getMessage());
        }
        return null; // Return null if parsing fails
    }
}
