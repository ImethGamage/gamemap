//20220546 - Imeth Lithmal Gamage

import java.util.*;

public class Main {
    public static void main(String[] args) {
        try {
            // Read the map from the input file using MapParser
            GameMap map = MapParser.parseMapFromFile("puzzle.txt");

            // Print the original map
           // System.out.println("Original Map:");
           //map.printMap();

            // Find the shortest path from start to finish
            int[] start = map.findSymbol('S');
            if (start == null) {
                System.out.println("Start symbol 'S' not found in the map.");
                return;
            }

            int[] finish = map.findSymbol('F');
            if (finish == null) {
                System.out.println("Finish symbol 'F' not found in the map.");
                return;
            }

            // Output the steps to reach the finish
            System.out.println("\nSteps to reach the finish:");
            List<String> path = PathFinder.findShortestPath(map, start[0], start[1], finish[0], finish[1]);
            if (path.isEmpty()) {
                System.out.println("No path found to reach the finish.");
            } else {
                Collections.reverse(path); // Reverse the order of steps

                List<String> numberedPath = new ArrayList<>();
                int stepCount = 1;
                int[] current = {start[0], start[1]};
                for (String step : path) {
                    int[] next = parseCoordinates(step.substring(step.indexOf("(") + 1, step.indexOf(")")));
                    String stepText;
                    if (step.startsWith("Stay")) {
                        continue; // Skip printing "Stay at" steps
                    }
                    stepText = stepCount + ". " + getDirection(current, next, stepCount == 1) + " to " + step.substring(step.indexOf("("), step.indexOf(")") + 1);
                    numberedPath.add(stepText);
                    stepCount++;
                    current = next;
                }

                // Print the final step
                String finalStepText;
                if (current[0] == finish[0] && current[1] == finish[1]) {
                    finalStepText = stepCount + ". Move to F";
                } else {
                    finalStepText = stepCount + ". Move to (" + (finish[1] + 1) + "," +(finish[0] + 1)  + ")";
                }
                numberedPath.add(finalStepText);

                for (String numberedStep : numberedPath) {
                    System.out.println(numberedStep);
                }

                System.out.println("Done!");

            }

        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static int[] parseCoordinates(String coordinateString) {
        String[] parts = coordinateString.split(",");
        return new int[]{Integer.parseInt(parts[0]), Integer.parseInt(parts[1])};
    }

    private static String getDirection(int[] current, int[] next, boolean isFirstStep) {
        if (isFirstStep) {
            return "Start at";
        } else if (next[1] < current[1]) {
            return "Move up";
        } else if (next[1] > current[1]) {
            return "Move down";
        } else if (next[0] < current[0]) {
            return "Move left";
        } else if (next[0] > current[0]) {
            return "Move right";
        } else {
            return "Stay at";
        }
    }
}


