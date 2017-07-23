import java.io.*;
import java.util.Scanner;

public class SudokuSolver 
{
    /**
    * This is the main method which takes in input, calls the solveSudoku method and prints the result.
    * @param args - Contains the name of the input file.
    * @return nothing.
    */
    public static void main(String args[]) throws IOException {
        
        String inputFilename = args[0];
        Scanner inputFile = new Scanner (new File(inputFilename));
        
        // Assuming it is a standard 9*9 sudoku.
        int sudokuGrid[][] = new int[9][9];
        
        for (int i = 0; i < sudokuGrid.length; i++) {
            // Reading a line from the inputFile corresponding to a row in the grid.
            char inpulLine[] = inputFile.next().toCharArray();
            for (int j = 0; j < sudokuGrid[0].length; j++) {
                // Reading each cell.
                char ch = inpulLine[j];                    
                if (ch == ';') {
                    // If cell is empty (semicolon) initialize it 0.
                    sudokuGrid[i][j] = 0;
                }
                else {
                    sudokuGrid[i][j] = Character.getNumericValue(ch);
                }
            }
        }
        // Print Unsolved Grid.
        printGrid(sudokuGrid);
        boolean result = solveSudoku(sudokuGrid);
        if (result) {
            System.out.println("\nSolved Sudoku");
            printGrid(sudokuGrid);
        }
        else {
            System.out.println("Sudoku is unsolvable");
        }
    } 
    
    /**
    * This method solves the sudoku recursively and using backtracking.
    * @param sudokuGrid - The grid to be solved.
    * @return boolean - true if it can be solved.
    */
    public static boolean solveSudoku(int sudokuGrid[][]) {
        for (int i = 0; i < sudokuGrid.length; i++) {
            for (int j = 0; j < sudokuGrid[0].length; j++) {
                // Check if cell is empty.
                if (sudokuGrid[i][j] == 0) {
                    // Assign number to empty cell and check if the sudoku will have valid result. 
                    for (int numCounter = 1; numCounter <= 9; numCounter++) {
                        // Check if the number can inserted into the cell.
                        if (isSafe(sudokuGrid, numCounter, i, j)) {
                            sudokuGrid[i][j] = numCounter;
                            
                            // Run the solveSudoku to solve further recursively.
                            if (solveSudoku(sudokuGrid)) {
                                return true;
                            }
                            
                            // If it cannot be solved further, initial assignment was invalid.
                            else {
                                sudokuGrid[i][j] = 0;
                            }
                        }                            
                    }
                    // If none of the number can be inserted into the cell then the sudoku is unsolvable.
                    // Backtracking.
                    return false;
                }
            }
        }
        // If the loop ends, it means that all cells can be filled safely.
        return true;
    }
    
    /**
    * This method checks if it is safe to enter a number in a particular cell.
    * @param sudokuGrid - The grid to be checked.
    * @param num - The number to be checked.
    * @param row - The row to be checked.
    * @param col - The col to be checked.
    * @return boolean - true if it is safe.
    */
    public static boolean isSafe(int sudokuGrid[][], int num, int row, int col) {
        if (inRowSafe(sudokuGrid, num, row) && inColSafe(sudokuGrid, num, col) && inBoxSafe(sudokuGrid, num, row, col)) {
            return true;
        }
        else {
            return false;
        }
    }
    
    /**
    * This method checks if it is safe to enter a number in a particular cell based on the row it is in.
    * @param sudokuGrid - The grid to be checked.
    * @param num - The number to be checked.
    * @param row - The row to be checked.
    * @return boolean - true if it is safe.
    */
    public static boolean inRowSafe(int sudokuGrid[][], int num, int row) {
        for (int i = 0; i < sudokuGrid[0].length; i++) {
            if(sudokuGrid[row][i] == num) {
                return false;
            }
        }
        return true; 
    }
    
    /**
    * This method checks if it is safe to enter a number in a particular cell based on the column it is in.
    * @param sudokuGrid - The grid to be checked.
    * @param num - The number to be checked.
    * @param col - The column to be checked.
    * @return boolean - true if it is safe.
    */
    public static boolean inColSafe(int sudokuGrid[][], int num, int col) {
        for (int i = 0; i < sudokuGrid.length; i++) {
            if(sudokuGrid[i][col] == num) {
                return false;
            }
        }
        return true; 
    }
    
    /**
    * This method checks if it is safe to enter a number in a particular cell based on the box it is in.
    * @param sudokuGrid - The grid to be checked.
    * @param num - The number to be checked.
    * @param row - The row to be checked.
    * @param col - The column to be checked.
    * @return boolean - true if it is safe.
    */
    public static boolean inBoxSafe(int sudokuGrid[][], int num, int row, int col) {
        // Get the starting row of the box.
        int boxRow = row - row % 3;
        // Get the starting column of the box.
        int boxCol = col - col % 3;
        for (int i = boxRow; i < boxRow + 3; i++) {
            for (int j = boxCol; j < boxCol + 3; j++) {
                if(sudokuGrid[i][j] == num) {
                    return false;
                }
            }
        }   
        return true;                
    }
    
    /**
    * This method displays a sudokuGrid in a human readable form.
    * @param sudokuGrid - The grid to be displayed.
    * @return Nothing.
    */
    public static void printGrid(int sudokuGrid[][]) {
        System.out.println();
        for (int i = 0; i < sudokuGrid.length; i++) {
            if (i % 3 == 0) {
                // Box separators.
                System.out.println("+-------+-------+-------+");
            }
            for (int j = 0; j < sudokuGrid[0].length; j++) {
                if (j % 3 == 0) {
                    // Delimiters. 
                    System.out.print("| ");
                }
                if (sudokuGrid[i][j] == 0) {
                    System.out.print(';');
                }
                else {
                    System.out.print(sudokuGrid[i][j]);
                }
                System.out.print(" ");
            }
            // Ending delimiter.
            System.out.print("|");
            System.out.println();
        }
        // Ending box separator.
        System.out.println("+-------+-------+-------+");
    }
}