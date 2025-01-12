package com.ai.yinyang_solver;

public class YinYangBoard {
    private char[][] board; // Representasi papan permainan dalam array 2D
    private int size; // Ukuran papan (sisi x sisi)
    private char[][] initialBoard; // Menyimpan state awal papan, untuk menandai sel yang fixed

    // Constants untuk representasi sel
    public static final char EMPTY = '0'; // Representasi sel kosong
    public static final char BLACK = 'B'; // Representasi sel hitam
    public static final char WHITE = 'W'; // Representasi sel putih
    
    // Constructor
    public YinYangBoard(int size) {
        this.size = size; // Inisialisasi ukuran papan
        this.board = new char[size][size]; // Membuat papan dengan ukuran yang ditentukan
        this.initialBoard = new char[size][size]; // Membuat papan untuk menyimpan state awal
        // Inisialisasi board kosong
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                board[i][j] = EMPTY; // Mengisi semua sel dengan nilai EMPTY
                initialBoard[i][j] = EMPTY; // Mengisi semua sel initial board dengan nilai EMPTY
            }
        }
    }
    
    // Constructor dengan initial board
    public YinYangBoard(char[][] initialBoard) {
        this.size = initialBoard.length; // Inisialisasi ukuran papan dari initial board
        this.board = new char[size][size]; // Membuat papan dengan ukuran yang ditentukan
        this.initialBoard = new char[size][size]; // Membuat papan untuk menyimpan state awal
        for (int i = 0; i < size; i++) {
            System.arraycopy(initialBoard[i], 0, board[i], 0, size); // Menyalin initial board ke board
            System.arraycopy(initialBoard[i], 0, this.initialBoard[i], 0, size); // Menyalin initial board ke initialBoard
        }
    }
    
    // Convert board 2D ke array 1D
    public char[] to1DArray() {
        char[] result = new char[size * size]; // Membuat array 1D dengan ukuran total sel
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                result[i * size + j] = board[i][j]; // Menyalin nilai sel ke array 1D
            }
        }
        return result; // Mengembalikan array 1D
    }
    
    // Convert array 1D ke board 2D
    public void from1DArray(char[] array) {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                board[i][j] = array[i * size + j]; // Menyalin nilai dari array 1D ke board 2D
            }
        }
    }
    
    // Cek apakah semua region terhubung
    public boolean isAllRegionsConnected() {
        if(countConnectedComponents(BLACK) == 1 && countConnectedComponents(WHITE) == 1) {
            return true;
        }
        return false;
    }
    
    // Helper method untuk cek konektivitas satu warna
    protected int countConnectedComponents(char color) {
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

        floodFill(startI, startJ, color, visited);
        
        // Count connected components for each color
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (board[i][j] == color && !visited[i][j]) { 
                    componentCount += 1;
                    floodFill(i, j, color, visited); // Lakukan flood fill untuk menandai semua sel hitam yang terhubung
                }
            }
        }
        
        // Both colors must exist and each must have exactly one component
        return componentCount; // Memastikan kedua warna ada dan masing-masing hanya memiliki satu komponen
    }

    // Implementasi algoritma flood fill untuk menandai semua sel yang terhubung
    private void floodFill(int row, int col, char color, boolean[][] visited) {
        // Check bounds and if cell is unvisited and matches color
        if (row < 0 || row >= size || col < 0 || col >= size || 
            visited[row][col] || board[row][col] != color) { // Jika sel di luar batas, sudah dikunjungi, atau tidak sesuai warna, maka berhenti
            return;
        }
        
        visited[row][col] = true; // Tandai sel sebagai sudah dikunjungi
        
        // Check four adjacent cells (no diagonals)
        floodFill(row + 1, col, color, visited); // Panggil flood fill untuk sel di bawah
        floodFill(row - 1, col, color, visited); // Panggil flood fill untuk sel di atas
        floodFill(row, col + 1, color, visited); // Panggil flood fill untuk sel di kanan
        floodFill(row, col - 1, color, visited); // Panggil flood fill untuk sel di kiri
    }

    // Implementasi sliding window untuk menghitung pola menyilang
    public int slidingWindowForColorCross() {
        int crossCount = 0; // Inisialisasi jumlah pola menyilang
        
        // Check each possible 2x2 window
        for(int i = 1; i < size; i++) { // Iterasi baris
            for(int j = 1; j < size; j++) { // Iterasi kolom
                if(isColorCross(i, j)) { // Jika ditemukan pola menyilang
                    crossCount++; // Increment jumlah pola menyilang
                }
            }
        }
        return crossCount; // Mengembalikan jumlah pola menyilang
    }

    public int slidingWindowForMonoColor() {
        int monoColorCount = 0; // Inisialisasi jumlah pola menyilang
        
        // Check each possible 2x2 window
        for(int i = 1; i < size; i++) { // Iterasi baris
            for(int j = 1; j < size; j++) { // Iterasi kolom
                if(isMonoColor(i, j)) { // Jika ditemukan pola menyilang
                    monoColorCount++; // Increment jumlah pola menyilang
                }
            }
        }
        return monoColorCount; // Mengembalikan jumlah pola menyilang
    }

    // Helper method untuk cek pola menyilang dalam kotak 2x2
    private boolean isColorCross(int i, int j) {
        char topLeft = board[i-1][j-1]; // Mendapatkan nilai sel kiri atas
        char topRight = board[i-1][j]; // Mendapatkan nilai sel kanan atas
        char bottomLeft = board[i][j-1]; // Mendapatkan nilai sel kiri bawah
        char bottomRight = board[i][j]; // Mendapatkan nilai sel kanan bawah

        // Cek apakah membentuk pola menyilang B W atau W B
        //                                    W B      B W
        return (topLeft == BLACK && topRight == WHITE && bottomLeft == WHITE && bottomRight == BLACK) ||
               (topLeft == WHITE && topRight == BLACK && bottomLeft == BLACK && bottomRight == WHITE);
    }

    private boolean isMonoColor(int i, int j) {
        char topLeft = board[i-1][j-1]; // Mendapatkan nilai sel kiri atas
        char topRight = board[i-1][j]; // Mendapatkan nilai sel kanan atas
        char bottomLeft = board[i][j-1]; // Mendapatkan nilai sel kiri bawah
        char bottomRight = board[i][j]; // Mendapatkan nilai sel kanan bawah

        return (topLeft == BLACK && topRight == BLACK && bottomLeft == BLACK && bottomRight == BLACK) ||
               (topLeft == WHITE && topRight == WHITE && bottomLeft == WHITE && bottomRight == WHITE);
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
        return initialBoard[i][j] != EMPTY; // Mengembalikan true jika sel tidak kosong di initial board
    }
    
    // Getter dan setter
    public char getCell(int i, int j) {
        return board[i][j]; // Mengembalikan nilai sel pada posisi i, j
    }
    
    public void setCell(int i, int j, char value) {
        board[i][j] = value; // Mengatur nilai sel pada posisi i, j
    }
    
    public int getSize() {
        return size; // Mengembalikan ukuran papan
    }
    
    // Clone board
    public YinYangBoard clone() {
        char[][] newBoard = new char[size][size]; // Membuat papan baru
        for (int i = 0; i < size; i++) {
            System.arraycopy(board[i], 0, newBoard[i], 0, size); // Menyalin isi papan ke papan baru
        }
        YinYangBoard clone = new YinYangBoard(newBoard); // Membuat objek YinYangBoard baru dengan papan yang disalin
        // Copy initial board to maintain fixed cells information
        for (int i = 0; i < size; i++) {
            System.arraycopy(initialBoard[i], 0, clone.initialBoard[i], 0, size); // Menyalin initial board ke clone
        }
        return clone; // Mengembalikan objek YinYangBoard yang baru
    }
    
    // ToString untuk debugging
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder(); // Membuat string builder
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                sb.append(board[i][j]); // Menambahkan nilai sel ke string builder
            }
            sb.append('\n'); // Menambahkan baris baru
        }
        return sb.toString(); // Mengembalikan representasi string dari papan
    }
}
