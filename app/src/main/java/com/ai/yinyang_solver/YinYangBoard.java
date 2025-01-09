package com.ai.yinyang_solver;

public class YinYangBoard {
    private char[][] board;
    private int size;

    // Constants untuk representasi sel
    public static final char EMPTY = '0';
    public static final char BLACK = 'B';
    public static final char WHITE = 'W';
    
    // Constructor
    public YinYangBoard(int size) {
        this.size = size;
        this.board = new char[size][size];
        // Inisialisasi board kosong
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                board[i][j] = EMPTY;
            }
        }
    }
    
    // Constructor dengan initial board
    public YinYangBoard(char[][] initialBoard) {
        this.size = initialBoard.length;
        this.board = new char[size][size];
        for (int i = 0; i < size; i++) {
            System.arraycopy(initialBoard[i], 0, board[i], 0, size);
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
        if(Math.abs(isRegionConnected(BLACK) - isRegionConnected(WHITE)) == 0) {
            return true;
        }
        return false;
    }
    
    // Helper method untuk cek konektivitas satu warna
    protected int isRegionConnected(char color) {
        // Temporary array untuk marking
        boolean[][] visited = new boolean[size][size];
        int componentCount = 1;
        
        // Cari sel pertama dengan warna yang dicari
        int startI = -1, startJ = -1;
        for (int i = 0; i < size && startI == -1; i++) {
            for (int j = 0; j < size; j++) {
                if (board[i][j] == color) {
                    startI = i;
                    startJ = j;
                    break;
                }
            }
        }
        
        // Jika tidak ada sel dengan warna tersebut
        if (startI == -1) return 0;
        
        // DFS dari sel pertama
        dfs(startI, startJ, color, visited);
        
        // Cek apakah semua sel dengan warna yang sama sudah dikunjungi
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (board[i][j] == color && !visited[i][j]) {
                    dfs(i, j, color, visited);
                    componentCount++;
                }
            }
        }
        
        return componentCount;
    }
    
    // DFS helper
    private void dfs(int i, int j, char color, boolean[][] visited) {
        if (i < 0 || i >= size || j < 0 || j >= size || 
            visited[i][j] || board[i][j] != color) {
            return;
        }
        
        visited[i][j] = true;
        
        // Check 4 directions
        dfs(i+1, j, color, visited);
        dfs(i-1, j, color, visited);
        dfs(i, j+1, color, visited);
        dfs(i, j-1, color, visited);
    }

    public int slidingWindow() {
        int crossCount = 0;

        for(int i = 1;i < size-1;i++) {
            for(int j = 1;j < size-1;j++) {
                if(isColorCross(i, j)) {
                    crossCount += 1;
                }
            }
        }

        return crossCount;
    }

    private boolean isColorCross(int i, int j) {
        char topLeft = board[i - 1][j - 1];
        char topRight = board[i - 1][j];
        char bottomLeft = board[i][j-1];
        char bottomRight = board[i][j];

        return (topLeft == 'B' && topRight == 'W' && bottomLeft == 'W' && bottomRight == 'B') || (topLeft == 'W' && topRight == 'B' && bottomLeft == 'B' && bottomRight == 'W');
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
        return new YinYangBoard(newBoard);
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
