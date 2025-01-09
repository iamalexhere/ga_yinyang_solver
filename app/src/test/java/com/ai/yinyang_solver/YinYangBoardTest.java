package com.ai.yinyang_solver;

import org.junit.Test;
import static org.junit.Assert.*;

public class YinYangBoardTest {
    
    @Test
    public void testBoardInitialization() {
        char[][] initialBoard = {
            {'B', 'W', '0'},
            {'0', 'B', 'W'},
            {'W', '0', 'B'}
        };
        YinYangBoard board = new YinYangBoard(initialBoard);
        
        assertEquals(3, board.getSize());
        assertEquals('B', board.getCell(0, 0));
        assertEquals('W', board.getCell(0, 1));
        assertEquals('0', board.getCell(0, 2));
    }
    
    @Test
    public void testIsAllRegionsConnected() {
        // Test case 1: Connected regions
        char[][] connectedBoard = {
            {'B', 'B', 'W'},
            {'B', 'W', 'W'},
            {'W', 'W', 'W'}
        };
        YinYangBoard board1 = new YinYangBoard(connectedBoard);
        assertTrue(board1.isAllRegionsConnected());
        
        // Test case 2: Disconnected regions
        char[][] disconnectedBoard = {
            {'B', 'W', 'B'},
            {'W', 'W', 'W'},
            {'B', 'W', 'B'}
        };
        YinYangBoard board2 = new YinYangBoard(disconnectedBoard);
        assertFalse(board2.isAllRegionsConnected());
    }
    
    @Test
    public void testSlidingWindow() {
        // Test case with crossing pattern
        char[][] crossingBoard = {
            {'B', 'W', '0'},
            {'W', 'B', '0'},
            {'0', '0', '0'}
        };
        YinYangBoard board = new YinYangBoard(crossingBoard);
        assertEquals(1, board.slidingWindow());
        
        // Test case without crossing pattern
        char[][] noCrossingBoard = {
            {'B', 'B', 'W'},
            {'B', 'W', 'W'},
            {'W', 'W', 'W'}
        };
        YinYangBoard board2 = new YinYangBoard(noCrossingBoard);
        assertEquals(0, board2.slidingWindow());
    }

    @Test
    public void testSlidingWindowNoCrossPattern() {
        char[][] board = {
            {'B', 'B', 'W'},
            {'B', 'B', 'W'},
            {'W', 'W', 'W'}
        };
        YinYangBoard yinYangBoard = new YinYangBoard(board);
        assertEquals(0, yinYangBoard.slidingWindow());
    }

    @Test
    public void testSlidingWindowSingleCrossPattern() {
        char[][] board = {
            {'B', 'W', 'B'},
            {'W', 'B', 'W'},
            {'B', 'W', 'B'}
        };
        YinYangBoard yinYangBoard = new YinYangBoard(board);
        assertEquals(4, yinYangBoard.slidingWindow());
    }

    @Test
    public void testSlidingWindowMultipleCrossPatterns() {
        char[][] board = {
            {'B', 'W', 'B', 'W'},
            {'W', 'B', 'W', 'B'},
            {'B', 'W', 'B', 'W'},
            {'W', 'B', 'W', 'B'}
        };
        YinYangBoard yinYangBoard = new YinYangBoard(board);
        assertEquals(9, yinYangBoard.slidingWindow());
    }

    @Test
    public void testSlidingWindowBorderCrossPattern() {
        char[][] board = {
            {'B', 'W'},
            {'W', 'B'}
        };
        YinYangBoard yinYangBoard = new YinYangBoard(board);
        assertEquals(1, yinYangBoard.slidingWindow());
    }

    @Test
    public void testSlidingWindowWithEmptyCells() {
        char[][] board = {
            {'B', 'W', '0'},
            {'W', 'B', '0'},
            {'0', '0', '0'}
        };
        YinYangBoard yinYangBoard = new YinYangBoard(board);
        assertEquals(1, yinYangBoard.slidingWindow());
    }

    @Test
    public void testSlidingWindowAllSameColor() {
        char[][] board = {
            {'B', 'B', 'B'},
            {'B', 'B', 'B'},
            {'B', 'B', 'B'}
        };
        YinYangBoard yinYangBoard = new YinYangBoard(board);
        assertEquals(0, yinYangBoard.slidingWindow());
    }

    @Test
    public void testSlidingWindowAlternatingRows() {
        char[][] board = {
            {'B', 'B', 'B'},
            {'W', 'W', 'W'},
            {'B', 'B', 'B'}
        };
        YinYangBoard yinYangBoard = new YinYangBoard(board);
        assertEquals(0, yinYangBoard.slidingWindow());
    }

    @Test
    public void testSlidingWindowDiagonalPattern() {
        char[][] board = {
            {'B', 'W', 'B'},
            {'W', 'B', 'W'},
            {'B', 'W', 'B'}
        };
        YinYangBoard yinYangBoard = new YinYangBoard(board);
        assertEquals(4, yinYangBoard.slidingWindow());
    }

    @Test
    public void testSlidingWindowSingleCell() {
        char[][] board = {{'B'}};
        YinYangBoard yinYangBoard = new YinYangBoard(board);
        assertEquals(0, yinYangBoard.slidingWindow());
    }

    @Test
    public void testSlidingWindowLargeBoard() {
        // Create a 5x5 board with alternating pattern
        char[][] board = {
            {'B', 'W', 'B', 'W', 'B'},
            {'W', 'B', 'W', 'B', 'W'},
            {'B', 'W', 'B', 'W', 'B'},
            {'W', 'B', 'W', 'B', 'W'},
            {'B', 'W', 'B', 'W', 'B'}
        };
        YinYangBoard yinYangBoard = new YinYangBoard(board);
        assertEquals(16, yinYangBoard.slidingWindow());
    }
    
    @Test
    public void testConversion1D2D() {
        char[][] initial2D = {
            {'B', 'W', '0'},
            {'0', 'B', 'W'},
            {'W', '0', 'B'}
        };
        YinYangBoard board = new YinYangBoard(initial2D);
        
        // Test 2D to 1D conversion
        char[] array1D = board.to1DArray();
        assertEquals(9, array1D.length);
        assertEquals('B', array1D[0]);
        assertEquals('W', array1D[1]);
        assertEquals('0', array1D[2]);
        
        // Test 1D to 2D conversion
        YinYangBoard newBoard = new YinYangBoard(3);
        newBoard.from1DArray(array1D);
        assertEquals('B', newBoard.getCell(0, 0));
        assertEquals('W', newBoard.getCell(0, 1));
        assertEquals('0', newBoard.getCell(0, 2));
    }

    @Test
    public void testConnectedRegionsVariousCases() {
        // Test case 1: Single connected region for each color
        char[][] case1 = {
            {'B', 'B', 'W'},
            {'B', 'W', 'W'},
            {'W', 'W', 'W'}
        };
        YinYangBoard board1 = new YinYangBoard(case1);
        assertTrue("Single connected regions should be valid", board1.isAllRegionsConnected());

        // Test case 2: Disconnected black regions
        char[][] case2 = {
            {'B', 'W', 'B'},
            {'W', 'W', 'W'},
            {'B', 'W', 'B'}
        };
        YinYangBoard board2 = new YinYangBoard(case2);
        assertFalse("Disconnected black regions should be invalid", board2.isAllRegionsConnected());

        // Test case 3: Disconnected white regions
        char[][] case3 = {
            {'B', 'B', 'B'},
            {'B', 'W', 'B'},
            {'B', 'B', 'W'}
        };
        YinYangBoard board3 = new YinYangBoard(case3);
        assertFalse("Disconnected white regions should be invalid", board3.isAllRegionsConnected());

        // Test case 4: Diagonal connection (should not be considered connected)
        char[][] case4 = {
            {'B', 'W', 'B'},
            {'W', 'B', 'W'},
            {'B', 'W', 'B'}
        };
        YinYangBoard board4 = new YinYangBoard(case4);
        assertFalse("Diagonal connections should not be considered connected", board4.isAllRegionsConnected());

        // Test case 5: Snake-like pattern (valid)
        char[][] case5 = {
            {'B', 'B', 'B'},
            {'W', 'W', 'B'},
            {'B', 'B', 'B'}
        };
        YinYangBoard board5 = new YinYangBoard(case5);
        assertTrue("Snake-like pattern should be valid", board5.isAllRegionsConnected());
        
        // Test case 6: U-shaped pattern (valid)
        char[][] case6 = {
            {'B', 'B', 'B'},
            {'B', 'W', 'B'},
            {'B', 'W', 'W'}
        };
        YinYangBoard board6 = new YinYangBoard(case6);
        assertTrue("U-shaped pattern should be valid", board6.isAllRegionsConnected());
    }

    @Test
    public void testLargerBoardConnectivity() {
        // Fixed test case with connected regions (single region per color)
        char[][] connectedBoard = {
            {'B', 'B', 'B', 'W'},
            {'B', 'B', 'W', 'W'},
            {'B', 'W', 'W', 'W'},
            {'B', 'W', 'W', 'W'}
        };
        YinYangBoard board1 = new YinYangBoard(connectedBoard);
        
        // Debug info
        System.out.println("\nTesting Larger Board Connectivity:");
        System.out.println("Connected board configuration:");
        System.out.println(board1.toString());
        System.out.println("Black regions count: " + countRegions(board1, 'B'));
        System.out.println("White regions count: " + countRegions(board1, 'W'));
        
        assertTrue("Connected regions should be valid", board1.isAllRegionsConnected());

        // Test case with disconnected regions
        char[][] disconnectedBoard = {
            {'B', 'B', 'W', 'W'},
            {'B', 'B', 'W', 'W'},
            {'W', 'W', 'B', 'B'},
            {'W', 'W', 'B', 'B'}
        };
        YinYangBoard board2 = new YinYangBoard(disconnectedBoard);
        assertFalse("Disconnected regions should be invalid", board2.isAllRegionsConnected());

        // Test case with snake-like pattern
        char[][] snakeBoard = {
            {'B', 'B', 'B', 'B'},
            {'W', 'W', 'W', 'B'},
            {'B', 'B', 'W', 'B'},
            {'W', 'W', 'W', 'W'}
        };
        YinYangBoard board3 = new YinYangBoard(snakeBoard);
        assertTrue("Snake-like pattern should be valid", board3.isAllRegionsConnected());
    }

    // Add more comprehensive test cases
    @Test
    public void testComplexConnectivityPatterns() {
        // Fixed spiral pattern with single connected region for each color
        char[][] spiralBoard = {
            {'B', 'B', 'B', 'B'},
            {'W', 'W', 'W', 'B'},
            {'W', 'B', 'B', 'B'},
            {'W', 'W', 'W', 'W'}
        };
        YinYangBoard board = new YinYangBoard(spiralBoard);
        
        // Debug info
        System.out.println("\nTesting Complex Connectivity Pattern:");
        System.out.println("Board configuration:");
        System.out.println(board.toString());
        System.out.println("Black regions count: " + countRegions(board, 'B'));
        System.out.println("White regions count: " + countRegions(board, 'W'));
        
        assertTrue("Spiral pattern should be valid", board.isAllRegionsConnected());
    }

    @Test
    public void testEdgeCases() {
        // Test case 1: 2x2 minimal board
        char[][] minimal = {
            {'B', 'W'},
            {'B', 'W'}
        };
        YinYangBoard minBoard = new YinYangBoard(minimal);
        assertTrue("2x2 board with connected regions should be valid", minBoard.isAllRegionsConnected());

        // Test case 2: Single color board
        char[][] singleColor = {
            {'B', 'B'},
            {'B', 'B'}
        };
        YinYangBoard singleColorBoard = new YinYangBoard(singleColor);
        assertFalse("Single color board should be invalid", singleColorBoard.isAllRegionsConnected());
    }

    // Helper method to count regions
    private int countRegions(YinYangBoard board, char color) {
        boolean[][] visited = new boolean[board.getSize()][board.getSize()];
        int regions = 0;
        
        for (int i = 0; i < board.getSize(); i++) {
            for (int j = 0; j < board.getSize(); j++) {
                if (!visited[i][j] && board.getCell(i, j) == color) {
                    regions++;
                    floodFillCount(board, i, j, color, visited);
                }
            }
        }
        return regions;
    }

    private void floodFillCount(YinYangBoard board, int row, int col, char color, boolean[][] visited) {
        if (row < 0 || row >= board.getSize() || col < 0 || col >= board.getSize() ||
            visited[row][col] || board.getCell(row, col) != color) {
            return;
        }
        
        visited[row][col] = true;
        
        floodFillCount(board, row + 1, col, color, visited);
        floodFillCount(board, row - 1, col, color, visited);
        floodFillCount(board, row, col + 1, color, visited);
        floodFillCount(board, row, col - 1, color, visited);
    }
}
