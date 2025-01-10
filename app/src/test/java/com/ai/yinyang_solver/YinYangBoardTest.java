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
    public void testSlidingWindow() {
        // Test case with crossing pattern
        char[][] crossingBoard = {
            {'B', 'W', '0'},
            {'W', 'B', '0'},
            {'0', '0', '0'}
        };
        YinYangBoard board = new YinYangBoard(crossingBoard); // Membuat board dari array 2D
        assertEquals(1, board.slidingWindow()); // Memastikan jumlah pola menyilang adalah 1
        
        // Test case without crossing pattern
        char[][] noCrossingBoard = {
            {'B', 'B', 'W'},
            {'B', 'W', 'W'},
            {'W', 'W', 'W'}
        };
        YinYangBoard board2 = new YinYangBoard(noCrossingBoard); // Membuat board dari array 2D
        assertEquals(0, board2.slidingWindow()); // Memastikan jumlah pola menyilang adalah 0
    }

    @Test 
    public void testSlidingWindowNoCrossPattern() {
        char[][] board = {
            {'B', 'B', 'W'},
            {'B', 'B', 'W'},
            {'W', 'W', 'W'}
        };
        YinYangBoard yinYangBoard = new YinYangBoard(board); // Membuat board dari array 2D
        assertEquals(0, yinYangBoard.slidingWindow()); // Memastikan jumlah pola menyilang adalah 0
    }

    @Test 
    public void testSlidingWindowSingleCrossPattern() {
        char[][] board = {
            {'B', 'W', 'B'},
            {'W', 'B', 'W'},
            {'B', 'W', 'B'}
        };
        YinYangBoard yinYangBoard = new YinYangBoard(board); // Membuat board dari array 2D
        assertEquals(4, yinYangBoard.slidingWindow()); // Memastikan jumlah pola menyilang adalah 4
    }

    @Test 
    public void testSlidingWindowMultipleCrossPatterns() {
        char[][] board = {
            {'B', 'W', 'B', 'W'},
            {'W', 'B', 'W', 'B'},
            {'B', 'W', 'B', 'W'},
            {'W', 'B', 'W', 'B'}
        };
        YinYangBoard yinYangBoard = new YinYangBoard(board); // Membuat board dari array 2D
        assertEquals(9, yinYangBoard.slidingWindow()); // Memastikan jumlah pola menyilang adalah 9
    }

    @Test 
    public void testSlidingWindowBorderCrossPattern() {
        char[][] board = {
            {'B', 'W'},
            {'W', 'B'}
        };
        YinYangBoard yinYangBoard = new YinYangBoard(board); // Membuat board dari array 2D
        assertEquals(1, yinYangBoard.slidingWindow()); // Memastikan jumlah pola menyilang adalah 1
    }

    @Test 
    public void testSlidingWindowWithEmptyCells() {
        char[][] board = {
            {'B', 'W', '0'},
            {'W', 'B', '0'},
            {'0', '0', '0'}
        };
        YinYangBoard yinYangBoard = new YinYangBoard(board); // Membuat board dari array 2D
        assertEquals(1, yinYangBoard.slidingWindow()); // Memastikan jumlah pola menyilang adalah 1
    }

    @Test 
    public void testSlidingWindowAllSameColor() {
        char[][] board = {
            {'B', 'B', 'B'},
            {'B', 'B', 'B'},
            {'B', 'B', 'B'}
        };
        YinYangBoard yinYangBoard = new YinYangBoard(board); // Membuat board dari array 2D
        assertEquals(0, yinYangBoard.slidingWindow()); // Memastikan jumlah pola menyilang adalah 0
    }

    @Test 
    public void testSlidingWindowAlternatingRows() {
        char[][] board = {
            {'B', 'B', 'B'},
            {'W', 'W', 'W'},
            {'B', 'B', 'B'}
        };
        YinYangBoard yinYangBoard = new YinYangBoard(board); // Membuat board dari array 2D
        assertEquals(0, yinYangBoard.slidingWindow()); // Memastikan jumlah pola menyilang adalah 0
    }

    @Test 
    public void testSlidingWindowDiagonalPattern() {
        char[][] board = {
            {'B', 'W', 'B'},
            {'W', 'B', 'W'},
            {'B', 'W', 'B'}
        };
        YinYangBoard yinYangBoard = new YinYangBoard(board); // Membuat board dari array 2D
        assertEquals(4, yinYangBoard.slidingWindow()); // Memastikan jumlah pola menyilang adalah 4
    }

    @Test 
    public void testSlidingWindowSingleCell() {
        char[][] board = {{'B'}};
        YinYangBoard yinYangBoard = new YinYangBoard(board); // Membuat board dari array 2D
        assertEquals(0, yinYangBoard.slidingWindow()); // Memastikan jumlah pola menyilang adalah 0
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
        YinYangBoard yinYangBoard = new YinYangBoard(board); // Membuat board dari array 2D
        assertEquals(16, yinYangBoard.slidingWindow()); // Memastikan jumlah pola menyilang adalah 16
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
