package com.ai.yinyang_solver;

import org.junit.Test; 
import static org.junit.Assert.*; 

public class YinYangBoardTest {
    
    @Test 
    public void testBoardInitialization() {
        // Inisialisasi papan dengan nilai awal
        char[][] initialBoard = {
            {'B', 'W', '0'},
            {'0', 'B', 'W'},
            {'W', '0', 'B'}
        };
        YinYangBoard board = new YinYangBoard(initialBoard); // Membuat board dari array 2D
        
        assertEquals(3, board.getSize()); // Memastikan ukuran board adalah 3
        assertEquals('B', board.getCell(0, 0)); // Memastikan sel (0,0) adalah 'B'
        assertEquals('W', board.getCell(0, 1)); // Memastikan sel (0,1) adalah 'W'
        assertEquals('0', board.getCell(0, 2)); // Memastikan sel (0,2) adalah '0'
    }
    
    @Test 
    public void testIsAllRegionsConnected() {
        // Test case 1: Connected regions
        char[][] connectedBoard = {
            {'B', 'B', 'W'},
            {'B', 'W', 'W'},
            {'W', 'W', 'W'}
        };
        YinYangBoard board1 = new YinYangBoard(connectedBoard); // Membuat board dari array 2D
        assertTrue(board1.isAllRegionsConnected()); // Memastikan semua region terhubung
        
        // Test case 2: Disconnected regions
        char[][] disconnectedBoard = {
            {'B', 'W', 'B'},
            {'W', 'W', 'W'},
            {'B', 'W', 'B'}
        };
        YinYangBoard board2 = new YinYangBoard(disconnectedBoard); // Membuat board dari array 2D
        assertFalse(board2.isAllRegionsConnected()); // Memastikan ada region yang tidak terhubung
    }
    
    @Test 
    public void testSlidingWindowColorCross() {
        // Test case 1: Single color cross pattern
        char[][] singleCrossBoard = {
            {'B', 'W', '0'},
            {'W', 'B', '0'},
            {'0', '0', '0'}
        };
        YinYangBoard board1 = new YinYangBoard(singleCrossBoard);
        assertEquals(1, board1.slidingWindowForColorCross());
        
        // Test case 2: Multiple color cross patterns
        char[][] multipleCrossBoard = {
            {'B', 'W', 'B', 'W'},
            {'W', 'B', 'W', 'B'},
            {'B', 'W', 'B', 'W'},
            {'W', 'B', 'W', 'B'}
        };
        YinYangBoard board2 = new YinYangBoard(multipleCrossBoard);
        assertEquals(9, board2.slidingWindowForColorCross());
        
        // Test case 3: No color cross pattern
        char[][] noCrossBoard = {
            {'B', 'B', 'B'},
            {'W', 'W', 'W'},
            {'B', 'B', 'B'}
        };
        YinYangBoard board3 = new YinYangBoard(noCrossBoard);
        assertEquals(0, board3.slidingWindowForColorCross());
    }

    @Test
    public void testSlidingWindowMonoColor() {
        // Test case 1: Single mono color pattern (all black)
        char[][] monoBlackBoard = {
            {'B', 'B', '0'},
            {'B', 'B', '0'},
            {'0', '0', '0'}
        };
        YinYangBoard board1 = new YinYangBoard(monoBlackBoard);
        assertEquals(1, board1.slidingWindowForMonoColor());
        
        // Test case 2: Multiple mono color patterns
        char[][] multipleMonoBoard = {
            {'W', 'W', 'B', 'B'},
            {'W', 'W', 'B', 'B'},
            {'B', 'B', 'W', 'W'},
            {'B', 'B', 'W', 'W'}
        };
        YinYangBoard board2 = new YinYangBoard(multipleMonoBoard);
        assertEquals(4, board2.slidingWindowForMonoColor());
        
        // Test case 3: No mono color pattern
        char[][] noMonoBoard = {
            {'B', 'W', 'B'},
            {'W', 'B', 'W'},
            {'B', 'W', 'B'}
        };
        YinYangBoard board3 = new YinYangBoard(noMonoBoard);
        assertEquals(0, board3.slidingWindowForMonoColor());
    }

    @Test
    public void testSlidingWindowEdgeCases() {
        // Test case 1: 1x1 board (too small for sliding window)
        char[][] singleCellBoard = {{'B'}};
        YinYangBoard board1 = new YinYangBoard(singleCellBoard);
        assertEquals(0, board1.slidingWindowForColorCross());
        assertEquals(0, board1.slidingWindowForMonoColor());
        
        // Test case 2: 2x2 board
        char[][] smallBoard = {
            {'B', 'W'},
            {'W', 'B'}
        };
        YinYangBoard board2 = new YinYangBoard(smallBoard);
        assertEquals(1, board2.slidingWindowForColorCross());
        assertEquals(0, board2.slidingWindowForMonoColor());
        
        // Test case 3: Empty cells in sliding window
        char[][] emptyBoard = {
            {'B', 'W', '0'},
            {'W', '0', 'B'},
            {'0', 'B', 'W'}
        };
        YinYangBoard board3 = new YinYangBoard(emptyBoard);
        assertEquals(0, board3.slidingWindowForColorCross());
        assertEquals(0, board3.slidingWindowForMonoColor());
    }

    @Test
    public void testSlidingWindowLargePatterns() {
        // Test case 1: 5x5 alternating pattern
        char[][] largeBoard = {
            {'B', 'W', 'B', 'W', 'B'},
            {'W', 'B', 'W', 'B', 'W'},
            {'B', 'W', 'B', 'W', 'B'},
            {'W', 'B', 'W', 'B', 'W'},
            {'B', 'W', 'B', 'W', 'B'}
        };
        YinYangBoard board = new YinYangBoard(largeBoard);
        assertEquals(16, board.slidingWindowForColorCross());
        assertEquals(0, board.slidingWindowForMonoColor());
    }

    @Test 
    public void testConversion1D2D() {
        char[][] initial2D = {
            {'B', 'W', '0'},
            {'0', 'B', 'W'},
            {'W', '0', 'B'}
        };
        YinYangBoard board = new YinYangBoard(initial2D); // Membuat board dari array 2D
        
        // Test 2D to 1D conversion
        char[] array1D = board.to1DArray(); // Mengubah board menjadi array 1D
        assertEquals(9, array1D.length); // Memastikan panjang array 1D adalah 9
        assertEquals('B', array1D[0]); // Memastikan elemen pertama array 1D adalah 'B'
        assertEquals('W', array1D[1]); // Memastikan elemen kedua array 1D adalah 'W'
        assertEquals('0', array1D[2]); // Memastikan elemen ketiga array 1D adalah '0'
        
        // Test 1D to 2D conversion
        YinYangBoard newBoard = new YinYangBoard(3); // Membuat board baru dengan ukuran 3
        newBoard.from1DArray(array1D); // Mengubah array 1D menjadi board 2D
        assertEquals('B', newBoard.getCell(0, 0)); // Memastikan sel (0,0) dari board baru adalah 'B'
        assertEquals('W', newBoard.getCell(0, 1)); // Memastikan sel (0,1) dari board baru adalah 'W'
        assertEquals('0', newBoard.getCell(0, 2)); // Memastikan sel (0,2) dari board baru adalah '0'
    }

    @Test 
    public void testConnectedRegionsVariousCases() {
        // Test case 1: Single connected region for each color
        char[][] case1 = {
            {'B', 'B', 'W'},
            {'B', 'W', 'W'},
            {'W', 'W', 'W'}
        };
        YinYangBoard board1 = new YinYangBoard(case1); // Membuat board dari array 2D
        assertTrue("Single connected regions should be valid", board1.isAllRegionsConnected()); // Memastikan board dengan satu region terhubung valid

        // Test case 2: Disconnected black regions
        char[][] case2 = {
            {'B', 'W', 'B'},
            {'W', 'W', 'W'},
            {'B', 'W', 'B'}
        };
        YinYangBoard board2 = new YinYangBoard(case2); // Membuat board dari array 2D
        assertFalse("Disconnected black regions should be invalid", board2.isAllRegionsConnected()); // Memastikan board dengan region hitam tidak terhubung tidak valid

        // Test case 3: Disconnected white regions
        char[][] case3 = {
            {'B', 'B', 'B'},
            {'B', 'W', 'B'},
            {'B', 'B', 'W'}
        };
        YinYangBoard board3 = new YinYangBoard(case3); // Membuat board dari array 2D
        assertFalse("Disconnected white regions should be invalid", board3.isAllRegionsConnected()); // Memastikan board dengan region putih tidak terhubung tidak valid

        // Test case 4: Diagonal connection (should not be considered connected)
        char[][] case4 = {
            {'B', 'W', 'B'},
            {'W', 'B', 'W'},
            {'B', 'W', 'B'}
        };
        YinYangBoard board4 = new YinYangBoard(case4); // Membuat board dari array 2D
        assertFalse("Diagonal connections should not be considered connected", board4.isAllRegionsConnected()); // Memastikan koneksi diagonal tidak dianggap terhubung

        // Test case 5: Snake-like pattern (valid)
        char[][] case5 = {
            {'B', 'B', 'B'},
            {'W', 'W', 'B'},
            {'B', 'B', 'B'}
        };
        YinYangBoard board5 = new YinYangBoard(case5); // Membuat board dari array 2D
        assertTrue("Snake-like pattern should be valid", board5.isAllRegionsConnected()); // Memastikan pola snake-like valid
        
        // Test case 6: U-shaped pattern (valid)
        char[][] case6 = {
            {'B', 'B', 'B'},
            {'B', 'W', 'B'},
            {'B', 'W', 'W'}
        };
        YinYangBoard board6 = new YinYangBoard(case6); // Membuat board dari array 2D
        assertTrue("U-shaped pattern should be valid", board6.isAllRegionsConnected()); // Memastikan pola U-shaped valid
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
        YinYangBoard board1 = new YinYangBoard(connectedBoard); // Membuat board dari array 2D
        assertTrue("Connected regions should be valid", board1.isAllRegionsConnected()); // Memastikan board dengan region terhubung valid

        // Test case with disconnected regions
        char[][] disconnectedBoard = {
            {'B', 'B', 'W', 'W'},
            {'B', 'B', 'W', 'W'},
            {'W', 'W', 'B', 'B'},
            {'W', 'W', 'B', 'B'}
        };
        YinYangBoard board2 = new YinYangBoard(disconnectedBoard); // Membuat board dari array 2D
        assertFalse("Disconnected regions should be invalid", board2.isAllRegionsConnected()); // Memastikan board dengan region tidak terhubung tidak valid

        // Test case with snake-like pattern (corrected pattern to ensure connectivity)
        char[][] snakeBoard = {
            {'B', 'B', 'B', 'B'},
            {'W', 'W', 'W', 'B'},
            {'W', 'B', 'B', 'B'},
            {'W', 'W', 'W', 'W'}
        };
        YinYangBoard board3 = new YinYangBoard(snakeBoard); // Membuat board dari array 2D
        assertTrue("Snake-like pattern should be valid", board3.isAllRegionsConnected()); // Memastikan pola snake-like valid
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
        YinYangBoard board = new YinYangBoard(spiralBoard); // Membuat board dari array 2D
        
        // Debug info
        System.out.println("\nTesting Complex Connectivity Pattern:");
        System.out.println("Board configuration:");
        System.out.println(board.toString()); // Menampilkan konfigurasi board
        System.out.println("Black regions count: " + countRegions(board, 'B')); // Menampilkan jumlah region hitam
        System.out.println("White regions count: " + countRegions(board, 'W')); // Menampilkan jumlah region putih
        
        assertTrue("Spiral pattern should be valid", board.isAllRegionsConnected()); // Memastikan pola spiral valid
    }

    @Test 
    public void testEdgeCases() {
        // Test case 1: 2x2 minimal board
        char[][] minimal = {
            {'B', 'W'},
            {'B', 'W'}
        };
        YinYangBoard minBoard = new YinYangBoard(minimal); // Membuat board dari array 2D
        assertTrue("2x2 board with connected regions should be valid", minBoard.isAllRegionsConnected()); // Memastikan board 2x2 dengan region terhubung valid

        // Test case 2: Single color board
        char[][] singleColor = {
            {'B', 'B'},
            {'B', 'B'}
        };
        YinYangBoard singleColorBoard = new YinYangBoard(singleColor); // Membuat board dari array 2D
        assertFalse("Single color board should be invalid", singleColorBoard.isAllRegionsConnected()); // Memastikan board dengan satu warna tidak valid
    }

    @Test
    public void testFixedCells() {
        // Test case with fixed cells
        char[][] initialBoard = {
            {'B', 'W', '0'},
            {'0', 'B', 'W'},
            {'W', '0', 'B'}
        };
        YinYangBoard board = new YinYangBoard(initialBoard);
        
        // Check fixed cells
        assertTrue("Cell (0,0) should be fixed", board.isFixedCell(0, 0));
        assertTrue("Cell (0,1) should be fixed", board.isFixedCell(0, 1));
        assertFalse("Cell (0,2) should not be fixed", board.isFixedCell(0, 2));
        assertFalse("Cell (1,0) should not be fixed", board.isFixedCell(1, 0));
    }

    @Test
    public void testBoardInitializationWithDifferentSizes() {
        // Test small board (1x1)
        YinYangBoard smallBoard = new YinYangBoard(1);
        assertEquals("Small board size should be 1", 1, smallBoard.getSize());
        assertEquals("Empty cell expected", YinYangBoard.EMPTY, smallBoard.getCell(0, 0));

        // Test medium board (5x5)
        YinYangBoard mediumBoard = new YinYangBoard(5);
        assertEquals("Medium board size should be 5", 5, mediumBoard.getSize());
        
        // Test large board (10x10)
        YinYangBoard largeBoard = new YinYangBoard(10);
        assertEquals("Large board size should be 10", 10, largeBoard.getSize());
    }

    @Test
    public void testInvalidBoardConfigurations() {
        // Test case 1: Board with only one color
        char[][] singleColorBoard = {
            {'B', 'B', 'B'},
            {'B', 'B', 'B'},
            {'B', 'B', 'B'}
        };
        YinYangBoard board1 = new YinYangBoard(singleColorBoard);
        assertFalse("Board with single color should be invalid", board1.isAllRegionsConnected());

        // Test case 2: Board with no colors (all empty)
        char[][] emptyBoard = {
            {'0', '0', '0'},
            {'0', '0', '0'},
            {'0', '0', '0'}
        };
        YinYangBoard board2 = new YinYangBoard(emptyBoard);
        assertFalse("Board with no colors should be invalid", board2.isAllRegionsConnected());
    }

    @Test
    public void testBoundaryConditionsForSlidingWindow() {
        // Test case 1: Board with alternating colors on edges
        char[][] edgeBoard = {
            {'B', 'W', 'B', 'W'},
            {'W', 'B', 'W', 'B'},
            {'B', 'W', 'B', 'W'},
            {'W', 'B', 'W', 'B'}
        };
        YinYangBoard board1 = new YinYangBoard(edgeBoard);
        assertEquals("Should find 9 cross patterns", 9, board1.slidingWindowForColorCross());
        assertEquals("Should find 0 mono patterns", 0, board1.slidingWindowForMonoColor());

        // Test case 2: Board with mono color on edges
        char[][] edgeMonoBoard = {
            {'B', 'B', 'W', 'W'},
            {'B', 'B', 'W', 'W'},
            {'W', 'W', 'B', 'B'},
            {'W', 'W', 'B', 'B'}
        };
        YinYangBoard board2 = new YinYangBoard(edgeMonoBoard);
        assertEquals("Should find 0 cross patterns", 0, board2.slidingWindowForColorCross());
        assertEquals("Should find 4 mono patterns", 4, board2.slidingWindowForMonoColor());
    }

    @Test
    public void testSlidingWindowWithEmptyCells() {
        // Test case 1: Empty cells breaking patterns
        char[][] boardWithEmpty = {
            {'B', 'W', 'B', 'W'},
            {'W', '0', 'W', 'B'},
            {'B', 'W', '0', 'W'},
            {'W', 'B', 'W', 'B'}
        };
        YinYangBoard board1 = new YinYangBoard(boardWithEmpty);
        assertEquals("Empty cells should break cross patterns", 4, board1.slidingWindowForColorCross());
        assertEquals("Empty cells should break mono patterns", 0, board1.slidingWindowForMonoColor());

        // Test case 2: Empty cells in corners
        char[][] emptyCorners = {
            {'0', 'B', 'B', '0'},
            {'B', 'B', 'B', 'B'},
            {'B', 'B', 'B', 'B'},
            {'0', 'B', 'B', '0'}
        };
        YinYangBoard board2 = new YinYangBoard(emptyCorners);
        assertEquals("Should find correct number of mono patterns with empty corners", 4, board2.slidingWindowForMonoColor());
    }

    @Test
    public void testSlidingWindowOverlappingPatterns() {
        // Test case 1: Overlapping cross patterns
        char[][] overlappingCross = {
            {'B', 'W', 'B', 'W', 'B'},
            {'W', 'B', 'W', 'B', 'W'},
            {'B', 'W', 'B', 'W', 'B'},
            {'W', 'B', 'W', 'B', 'W'},
            {'B', 'W', 'B', 'W', 'B'}
        };
        YinYangBoard board1 = new YinYangBoard(overlappingCross);
        assertEquals("Should count all overlapping cross patterns", 16, board1.slidingWindowForColorCross());

        // Test case 2: Overlapping mono patterns
        char[][] overlappingMono = {
            {'B', 'B', 'B', 'W', 'W'},
            {'B', 'B', 'B', 'W', 'W'},
            {'B', 'B', 'B', 'W', 'W'},
            {'W', 'W', 'W', 'B', 'B'},
            {'W', 'W', 'W', 'B', 'B'}
        };
        YinYangBoard board2 = new YinYangBoard(overlappingMono);
        assertEquals("Should count all overlapping mono patterns", 8, board2.slidingWindowForMonoColor());
    }

    @Test
    public void testSlidingWindowMixedPatterns() {
        // Test case: Mixed mono and cross patterns
        char[][] mixedPatterns = {
            {'B', 'B', 'W', 'B'},
            {'B', 'B', 'B', 'W'},
            {'W', 'B', 'W', 'B'},
            {'B', 'W', 'B', 'W'}
        };
        YinYangBoard board = new YinYangBoard(mixedPatterns);
        assertEquals("Should find correct number of cross patterns", 3, board.slidingWindowForColorCross());
        assertEquals("Should find correct number of mono patterns", 1, board.slidingWindowForMonoColor());
    }

    @Test
    public void testSlidingWindowPartialPatterns() {
        // Test case 1: Partial patterns at edges
        char[][] partialPatterns = {
            {'B', 'W', 'B'},
            {'W', 'B', 'W'},
            {'B', 'W', 'B'}
        };
        YinYangBoard board1 = new YinYangBoard(partialPatterns);
        // Patterns must be complete 2x2, partial patterns at edges don't count
        assertEquals("Partial patterns at edges should not be counted", 0, board1.slidingWindowForColorCross());
        assertEquals("Partial patterns at edges should not be counted", 0, board1.slidingWindowForMonoColor());

        // Test case 2: Almost complete patterns
        char[][] almostComplete = {
            {'B', 'W', 'B', 'W'},
            {'W', 'B', 'W', 'B'},
            {'B', 'W', 'B', '0'},
            {'W', 'B', '0', 'B'}
        };
        YinYangBoard board2 = new YinYangBoard(almostComplete);
        assertEquals("Should only count complete patterns", 4, board2.slidingWindowForColorCross());
    }

    @Test
    public void testSlidingWindowSinglePattern() {
        // Test case untuk satu pola yang jelas dan terisolasi
        char[][] singlePattern = {
            {'0', '0', '0', '0'},
            {'0', 'B', 'W', '0'},
            {'0', 'W', 'B', '0'},
            {'0', '0', '0', '0'}
        };
        YinYangBoard board = new YinYangBoard(singlePattern);
        assertEquals("Should find exactly one cross pattern in isolation", 1, board.slidingWindowForColorCross());
        assertEquals("Should find no mono patterns", 0, board.slidingWindowForMonoColor());
    }

    @Test
    public void testSlidingWindowEmptyBoundary() {
        // Test case untuk pola dengan batas kosong
        char[][] boundaryPattern = {
            {'0', '0', '0', '0', '0'},
            {'0', 'B', 'W', 'B', '0'},
            {'0', 'W', 'B', 'W', '0'},
            {'0', 'B', 'W', 'B', '0'},
            {'0', '0', '0', '0', '0'}
        };
        YinYangBoard board = new YinYangBoard(boundaryPattern);
        assertEquals("Should find 4 cross patterns with empty boundary", 4, board.slidingWindowForColorCross());
        assertEquals("Should find no mono patterns with empty boundary", 0, board.slidingWindowForMonoColor());
    }

    @Test
    public void testSlidingWindowCornerPatterns() {
        // Test case untuk pola di sudut papan
        char[][] cornerPattern = {
            {'B', 'W', '0', '0'},
            {'W', 'B', '0', '0'},
            {'0', '0', 'B', 'W'},
            {'0', '0', 'W', 'B'}
        };
        YinYangBoard board = new YinYangBoard(cornerPattern);
        assertEquals("Should find 2 cross patterns in corners", 2, board.slidingWindowForColorCross());
        assertEquals("Should find no mono patterns in corners", 0, board.slidingWindowForMonoColor());
    }

    @Test
    public void testSlidingWindowPartialEmpty() {
        // Test case untuk pola dengan beberapa sel kosong
        char[][] partialEmpty = {
            {'B', 'W', 'B', 'W'},
            {'W', '0', 'W', 'B'},
            {'B', 'W', '0', 'W'},
            {'W', 'B', 'W', '0'}
        };
        YinYangBoard board = new YinYangBoard(partialEmpty);
        assertEquals("Should handle partial patterns with empty cells correctly", 2, board.slidingWindowForColorCross());
        assertEquals("Should not count patterns with empty cells", 0, board.slidingWindowForMonoColor());
    }

    @Test
    public void testSlidingWindowAdjacentPatterns() {
        // Test case untuk pola yang berdekatan tapi tidak tumpang tindih
        char[][] adjacentPatterns = {
            {'B', 'W', 'B', 'W'},
            {'W', 'B', 'W', 'B'},
            {'B', 'W', 'B', 'W'},
            {'W', 'B', 'W', 'B'}
        };
        YinYangBoard board = new YinYangBoard(adjacentPatterns);
        assertEquals("Should count adjacent non-overlapping cross patterns", 9, board.slidingWindowForColorCross());
        assertEquals("Should find no mono patterns", 0, board.slidingWindowForMonoColor());
    }

    // Helper method to count regions
    private int countRegions(YinYangBoard board, char color) {
        boolean[][] visited = new boolean[board.getSize()][board.getSize()]; // Membuat array untuk menandai sel yang sudah dikunjungi
        int regions = 0; // Inisialisasi jumlah region
        
        for (int i = 0; i < board.getSize(); i++) {
            for (int j = 0; j < board.getSize(); j++) {
                if (!visited[i][j] && board.getCell(i, j) == color) { // Jika sel belum dikunjungi dan sesuai warna
                    regions++; // Increment jumlah region
                    floodFillCount(board, i, j, color, visited); // Lakukan flood fill untuk menandai semua sel yang terhubung
                }
            }
        }
        return regions; // Mengembalikan jumlah region
    }

    // Implementasi algoritma flood fill untuk menghitung jumlah region
    private void floodFillCount(YinYangBoard board, int row, int col, char color, boolean[][] visited) {
        if (row < 0 || row >= board.getSize() || col < 0 || col >= board.getSize() ||
            visited[row][col] || board.getCell(row, col) != color) { // Jika sel di luar batas, sudah dikunjungi, atau tidak sesuai warna, maka berhenti
            return;
        }
        
        visited[row][col] = true; // Tandai sel sebagai sudah dikunjungi
        
        floodFillCount(board, row + 1, col, color, visited); // Panggil flood fill untuk sel di bawah
        floodFillCount(board, row - 1, col, color, visited); // Panggil flood fill untuk sel di atas
        floodFillCount(board, row, col + 1, color, visited); // Panggil flood fill untuk sel di kanan
        floodFillCount(board, row, col - 1, color, visited); // Panggil flood fill untuk sel di kiri
    }
}
