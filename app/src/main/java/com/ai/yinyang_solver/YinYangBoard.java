package com.ai.yinyang_solver;

public class YinYangBoard {
    private char[][] board;
    private int size;
    private char[][] initialBoard; // Add this field to store initial board state

    // Constants untuk representasi sel
    public static final char EMPTY = '0';
    public static final char BLACK = 'B';
    public static final char WHITE = 'W';
    
    // Constructor
    public YinYangBoard(int size) {
        this.size = size;
        this.board = new char[size][size];
        this.initialBoard = new char[size][size];
        // Inisialisasi board kosong
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                board[i][j] = EMPTY;
                initialBoard[i][j] = EMPTY;
            }
        }
    }
    
    // Constructor dengan initial board
    public YinYangBoard(char[][] initialBoard) {
        this.size = initialBoard.length;
        this.board = new char[size][size];
        this.initialBoard = new char[size][size];
        for (int i = 0; i < size; i++) {
            System.arraycopy(initialBoard[i], 0, board[i], 0, size);
            System.arraycopy(initialBoard[i], 0, this.initialBoard[i], 0, size);
        }
    }
    
    // Convert board 2D ke array 1D
    public char[] to1DArray() {
        char[] result = new char[size * size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                result[i * size + j] = board[i][j];
            }
        }
        return result;
    }
    
    // Convert array 1D ke board 2D
    public void from1DArray(char[] array) {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                board[i][j] = array[i * size + j];
            }
        }
    }
    
    // Cek apakah semua region terhubung
    public boolean isAllRegionsConnected() {
        // Count the components of each color
        int blackComponents = 0;
        int whiteComponents = 0;
        boolean hasBlack = false;
        boolean hasWhite = false;
        
        // Create visited array
        boolean[][] visited = new boolean[size][size];
        
        // Count connected components for each color
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (!visited[i][j]) {
                    if (board[i][j] == BLACK) {
                        hasBlack = true;
                        blackComponents++;
                        floodFill(i, j, BLACK, visited);
                    } else if (board[i][j] == WHITE) {
                        hasWhite = true;
                        whiteComponents++;
                        floodFill(i, j, WHITE, visited);
                    }
                }
            }
        }
        
        // Both colors must exist and each must have exactly one component
        return hasBlack && hasWhite && blackComponents == 1 && whiteComponents == 1;
    }

    private void floodFill(int row, int col, char color, boolean[][] visited) {
        // Check bounds and if cell is unvisited and matches color
        if (row < 0 || row >= size || col < 0 || col >= size || 
            visited[row][col] || board[row][col] != color) {
            return;
        }
        
        visited[row][col] = true;
        
        // Check four adjacent cells (no diagonals)
        floodFill(row + 1, col, color, visited);
        floodFill(row - 1, col, color, visited);
        floodFill(row, col + 1, color, visited);
        floodFill(row, col - 1, color, visited);
    }

    public int slidingWindow() {
        int crossCount = 0;
        
        // Check each possible 2x2 window
        for(int i = 0; i < size - 1; i++) {
            for(int j = 0; j < size - 1; j++) {
                if(isColorCross(i, j)) {
                    crossCount++;
                }
            }
        }
        return crossCount;
    }

    private boolean isColorCross(int i, int j) {
        // Validate window bounds
        if(i + 1 >= size || j + 1 >= size) return false;
        
        char topLeft = board[i][j];
        char topRight = board[i][j + 1];
        char bottomLeft = board[i + 1][j];
        char bottomRight = board[i + 1][j + 1];

        return (topLeft == BLACK && topRight == WHITE && bottomLeft == WHITE && bottomRight == BLACK) ||
               (topLeft == WHITE && topRight == BLACK && bottomLeft == BLACK && bottomRight == WHITE);
    }
    
    // Cek pola menyilang dalam kotak 2x2
    // public int countCrossingPatterns() {
    //     int count = 0;
    //     for (int i = 0; i < size-1; i++) {
    //         for (int j = 0; j < size-1; j++) {
    //             if (isCrossingPattern(i, j)) {
    //                 count++;
    //             }
    //         }
    //     }
    //     return count;
    // }
    
    // // Helper untuk cek pola menyilang dalam kotak 2x2
    // private boolean isCrossingPattern(int i, int j) {
    //     // Cek apakah membentuk pola menyilang B W
    //     //                                    W B
    //     // atau                              W B
    //     //                                   B W
    //     return (board[i][j] == BLACK && board[i+1][j+1] == BLACK &&
    //             board[i][j+1] == WHITE && board[i+1][j] == WHITE) ||
    //            (board[i][j] == WHITE && board[i+1][j+1] == WHITE &&
    //             board[i][j+1] == BLACK && board[i+1][j] == BLACK);
    // }

    // Add method to check if cell was fixed in initial board
    public boolean isFixedCell(int i, int j) {
        return initialBoard[i][j] != EMPTY;
    }
    
    // Getter dan setter
    public char getCell(int i, int j) {
        return board[i][j];
    }
    
    public void setCell(int i, int j, char value) {
        board[i][j] = value;
    }
    
    public int getSize() {
        return size;
    }
    
    // Clone board
    public YinYangBoard clone() {
        char[][] newBoard = new char[size][size];
        for (int i = 0; i < size; i++) {
            System.arraycopy(board[i], 0, newBoard[i], 0, size);
        }
        YinYangBoard clone = new YinYangBoard(newBoard);
        // Copy initial board to maintain fixed cells information
        for (int i = 0; i < size; i++) {
            System.arraycopy(initialBoard[i], 0, clone.initialBoard[i], 0, size);
        }
        return clone;
    }
    
    // ToString untuk debugging
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                sb.append(board[i][j]);
            }
            sb.append('\n');
        }
        return sb.toString();
    }
}
