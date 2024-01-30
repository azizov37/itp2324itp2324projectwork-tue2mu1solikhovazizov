package de.tum.cit.ase.maze;


import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;


/**
 * The Maze class represents the game maze, containing cells with different types.
 * It can be loaded from a given file.
 */
public class Maze {
    private Cell[][] cells;



    /**
     * Constructs a new Maze instance by either loading from a file
     *
     * @param filePath The path to the file containing maze data.
     */
    public Maze(String filePath) {
        if (filePath != null && !filePath.isEmpty()) {
            loadMazeFromFileWithBG(filePath);
        }
    }
    /**
     * Loads the maze from a file.
     *
     * @param filePath The path to the file containing maze and adds background data.
     */
    private void loadMazeFromFileWithBG(String filePath) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            Properties properties = new Properties();
            properties.load(reader);
            int maxX = 0, maxY = 0;
            // Determine the size of the maze
            for (String key : properties.stringPropertyNames()) {
                String[] parts = key.split(",");
                int x = Integer.parseInt(parts[0]);
                int y = Integer.parseInt(parts[1]);
                if (x > maxX) maxX = x;
                if (y > maxY) maxY = y;
            }

            // Initialize the cells array
            cells = new Cell[maxX + 1][maxY + 1];
            for (String key : properties.stringPropertyNames()) {
                String[] parts = key.split(",");
                int x = Integer.parseInt(parts[0]);
                int y = Integer.parseInt(parts[1]);
                int cellType = Integer.parseInt(properties.getProperty(key));
                cells[x][y] = new Cell(cellType, x, y);
            }


            // Fill any remaining null cells with the value 7
            for (int x = 0; x <= maxX; x++) {
                for (int y = 0; y <= maxY; y++) {
                    if (cells[x][y] == null) {
                        cells[x][y] = new Cell(7, x, y);
                    }
                }
            }

            // Update wall types based on specified conditions
            for (int x = 0; x <= maxX; x++) {
                for (int y = 1; y <= maxY; y++) {  // Start from y = 1 because we're checking the previous cell
                    if (cells[x][y] != null && cells[x][y].getType() == 0) {
                        if (cells[x][y-1] != null && (cells[x][y-1].getType() == 0 || cells[x][y-1].getType() == 6)) {
                            cells[x][y].setType(6);
                        }
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("Error loading maze file: " + e.getMessage());
        }
    }

        /**
     * Gets a specific cell in the maze.
     *
     * @param x The x-coordinate of the cell.
     * @param y The y-coordinate of the cell.
     * @return The cell at the specified coordinates or null if out of bounds.
     */
    public Cell getCell(int x, int y) {
        if (x >= 0 && x < getWidth() && y >= 0 && y < getHeight()) {
            return cells[x][y];
        }
        return null;
    }

    // Method to get the width of the maze
    public int getWidth() {
        if (cells != null && cells.length > 0) {
            return cells.length;
        }
        return 0;
    }

    // Method to get the height of the maze
    public int getHeight() {
        if (cells != null && cells.length > 0 && cells[0] != null) {
            return cells[0].length;
        }
        return 0;
    }
}

