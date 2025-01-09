package com.ai.yinyang_solver;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

public class YinYangSolverTest {
    
    @Test
    public void testSimplePuzzle() {
        // Simple 3x3 puzzle
        char[][] board = {
            {'B', '0', 'W'},
            {'0', '0', '0'},
            {'W', '0', 'B'}
        };
        
        YinYangSolver solver = new YinYangSolver(board);
        YinYangBoard solution = solver.solve();
        
        // Verify solution properties
        assertTrue(solution.isAllRegionsConnected());
        assertEquals(0, solution.slidingWindow());
    }
    
    @Test
    public void testMediumPuzzle() {
        // Medium 4x4 puzzle
        char[][] board = {
            {'B', '0', '0', 'W'},
            {'0', 'B', 'W', '0'},
            {'0', 'W', 'B', '0'},
            {'W', '0', '0', 'B'}
        };
        
        YinYangSolver solver = new YinYangSolver(board);
        YinYangBoard solution = solver.solve();
        
        // Verify solution properties
        assertTrue(solution.isAllRegionsConnected());
        assertEquals(0, solution.slidingWindow());
    }
    
    @Test
    public void testEmptyBoard() {
        // Test with completely empty board
        char[][] board = {
            {'0', '0', '0'},
            {'0', '0', '0'},
            {'0', '0', '0'}
        };
        
        YinYangSolver solver = new YinYangSolver(board);
        YinYangBoard solution = solver.solve();
        
        // Verify that solution follows basic rules
        assertTrue(solution.isAllRegionsConnected());
        assertEquals(0, solution.slidingWindow());
    }
    
    @Test
    public void testEasy6x6Puzzle() {
        // Problem setup
        char[][] initialBoard = {
            {'0', '0', '0', 'B', '0', 'W'},
            {'0', '0', 'B', '0', 'B', '0'},
            {'0', '0', 'W', '0', '0', '0'},
            {'B', '0', '0', '0', 'W', '0'},
            {'0', 'B', '0', 'B', '0', '0'},
            {'W', '0', '0', '0', '0', '0'}
        };

        // Known solution
        char[][] expectedSolution = {
            {'B', 'B', 'B', 'B', 'B', 'W'},
            {'B', 'W', 'B', 'W', 'B', 'W'},
            {'B', 'W', 'W', 'W', 'B', 'W'},
            {'B', 'W', 'B', 'W', 'W', 'W'},
            {'B', 'B', 'B', 'B', 'B', 'W'},
            {'W', 'W', 'W', 'W', 'W', 'W'}
        };

        YinYangSolver solver = new YinYangSolver(initialBoard);
        YinYangBoard solution = solver.solve();

        // Verify solution properties
        assertTrue("All regions should be connected", solution.isAllRegionsConnected());
        assertEquals("Should not have crossing patterns", 0, solution.slidingWindow());

        // Verify that fixed cells from initial board are maintained
        assertEquals('B', solution.getCell(0, 3));  // Fixed B in initial board
        assertEquals('W', solution.getCell(0, 5));  // Fixed W in initial board
        assertEquals('B', solution.getCell(1, 2));  // Fixed B in initial board
        assertEquals('B', solution.getCell(1, 4));  // Fixed B in initial board
        assertEquals('W', solution.getCell(2, 2));  // Fixed W in initial board
        assertEquals('B', solution.getCell(3, 0));  // Fixed B in initial board
        assertEquals('W', solution.getCell(3, 4));  // Fixed W in initial board
        assertEquals('B', solution.getCell(4, 1));  // Fixed B in initial board
        assertEquals('B', solution.getCell(4, 3));  // Fixed B in initial board
        assertEquals('W', solution.getCell(5, 0));  // Fixed W in initial board

        // Optional: Compare with known solution
        // Note: There might be multiple valid solutions, so this check is optional
        boolean matchesKnownSolution = true;
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 6; j++) {
                if (solution.getCell(i, j) != expectedSolution[i][j]) {
                    matchesKnownSolution = false;
                    break;
                }
            }
            if (!matchesKnownSolution) break;
        }

        // Print solution for comparison
        System.out.println("\nEasy 6x6 Puzzle Solution:");
        System.out.println(solution.toString());
        
        if (!matchesKnownSolution) {
            System.out.println("Note: Found a different valid solution than the known solution.");
            System.out.println("Known solution:");
            System.out.println(new YinYangBoard(expectedSolution).toString());
        }

        // Additional validation of solution properties
        validateSolutionProperties(solution);
    }

    private void validateSolutionProperties(YinYangBoard solution) {
        // 1. Check that all cells are filled
        boolean allCellsFilled = true;
        for (int i = 0; i < solution.getSize(); i++) {
            for (int j = 0; j < solution.getSize(); j++) {
                if (solution.getCell(i, j) == '0') {
                    allCellsFilled = false;
                    break;
                }
            }
        }
        assertTrue("All cells should be filled", allCellsFilled);

        // 2. Check that regions are connected
        assertTrue("Black region should be connected", isRegionConnected(solution, 'B'));
        assertTrue("White region should be connected", isRegionConnected(solution, 'W'));

        // 3. Check for no crossing patterns
        assertEquals("Should not have crossing patterns", 0, solution.slidingWindow());
    }

    private boolean isRegionConnected(YinYangBoard board, char color) {
        int size = board.getSize();
        boolean[][] visited = new boolean[size][size];
        
        // Find first cell of the color
        int startI = -1, startJ = -1;
        for (int i = 0; i < size && startI == -1; i++) {
            for (int j = 0; j < size; j++) {
                if (board.getCell(i, j) == color) {
                    startI = i;
                    startJ = j;
                    break;
                }
            }
        }
        
        if (startI == -1) return true; // No cells of this color
        
        // DFS from first cell
        dfs(board, startI, startJ, color, visited);
        
        // Check if all cells of this color were visited
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (board.getCell(i, j) == color && !visited[i][j]) {
                    return false;
                }
            }
        }
        
        return true;
    }

    private void dfs(YinYangBoard board, int i, int j, char color, boolean[][] visited) {
        if (i < 0 || i >= board.getSize() || j < 0 || j >= board.getSize() || 
            visited[i][j] || board.getCell(i, j) != color) {
            return;
        }
        
        visited[i][j] = true;
        
        // Check 4 directions
        dfs(board, i+1, j, color, visited);
        dfs(board, i-1, j, color, visited);
        dfs(board, i, j+1, color, visited);
        dfs(board, i, j-1, color, visited);
    }
}
