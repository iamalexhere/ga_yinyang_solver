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
}
