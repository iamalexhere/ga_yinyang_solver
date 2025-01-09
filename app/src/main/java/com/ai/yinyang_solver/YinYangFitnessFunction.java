package com.ai.yinyang_solver;
// Package untuk kelas-kelas yang berhubungan dengan fungsi fitness Yin Yang

public class YinYangFitnessFunction implements FitnessFunction<YinYangChromosome, Double> {
    
    // Adjust weights for better balance
    private static final double CONNECTIVITY_WEIGHT = 100.0;
    private static final double CROSSING_PATTERN_WEIGHT = 25.0;
    private static final double EMPTY_CELL_WEIGHT = 15.0;
    private static final double REGION_SIZE_WEIGHT = 5.0;
    
    @Override
    public Double calculate(YinYangChromosome chromosome) {
        YinYangBoard board = chromosome.getBoard(); // Mendapatkan board dari kromosom
        
        // If any critical criteria fails, return high penalty
        if (!board.isAllRegionsConnected() ||  // Jika region tidak terhubung
            board.slidingWindow() > 0 ||  // Jika ada pola menyilang
            countEmptyCells(board) > 0) { // Jika ada sel kosong
            // Mengembalikan penalti tinggi jika salah satu kriteria utama gagal
            return CONNECTIVITY_WEIGHT * (!board.isAllRegionsConnected() ? 1 : 0) + // Penalti konektivitas
                   CROSSING_PATTERN_WEIGHT * board.slidingWindow() + // Penalti pola menyilang
                   EMPTY_CELL_WEIGHT * countEmptyCells(board); // Penalti sel kosong
        }
        
        // If all critical criteria pass, only consider region balance with reduced weight
        return calculateRegionSizeBalance(board) * REGION_SIZE_WEIGHT; // Jika semua kriteria utama terpenuhi, hanya hitung keseimbangan region
    }
    
    // Hitung jumlah sel kosong
    private int countEmptyCells(YinYangBoard board) {
        int count = 0; // Inisialisasi jumlah sel kosong
        int size = board.getSize(); // Mendapatkan ukuran board
        
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (board.getCell(i, j) == YinYangBoard.EMPTY) { // Jika sel kosong
                    count++; // Increment jumlah sel kosong
                }
            }
        }
        
        return count; // Mengembalikan jumlah sel kosong
    }
    
    // Method untuk menghitung keseimbangan ukuran region
    private double calculateRegionSizeBalance(YinYangBoard board) {
        // Calculate difference between black and white region sizes
        int blackCount = 0, whiteCount = 0; // Inisialisasi jumlah sel hitam dan putih
        int size = board.getSize(); // Mendapatkan ukuran board
        
        for(int i = 0; i < size; i++) {
            for(int j = 0; j < size; j++) {
                if(board.getCell(i,j) == YinYangBoard.BLACK) blackCount++; // Jika sel hitam, increment jumlah sel hitam
                else if(board.getCell(i,j) == YinYangBoard.WHITE) whiteCount++; // Jika sel putih, increment jumlah sel putih
            }
        }
        
        // Mengembalikan selisih absolut antara jumlah sel hitam dan putih, dinormalisasi dengan ukuran board
        return Math.abs(blackCount - whiteCount) / (double)(size * size);
    }
    
    // Method untuk mendapatkan deskripsi detail fitness
    public String getFitnessDescription(YinYangChromosome chromosome) {
        YinYangBoard board = chromosome.getBoard(); // Mendapatkan board dari kromosom
        StringBuilder description = new StringBuilder(); // Membuat string builder untuk deskripsi
        
        // Cek konektivitas
        boolean connected = board.isAllRegionsConnected(); // Cek apakah semua region terhubung
        double connectivityPenalty = connected ? 0 : CONNECTIVITY_WEIGHT; // Penalti konektivitas, 0 jika terhubung, CONNECTIVITY_WEIGHT jika tidak
        description.append("1. Connectivity: ").append(connected ? "OK" : "Failed") // Menambahkan status konektivitas ke deskripsi
                  .append(" (").append(-connectivityPenalty).append(" points)\n"); // Menambahkan penalti konektivitas ke deskripsi
        
        // Cek pola menyilang
        int crossingPatterns = board.slidingWindow(); // Menghitung jumlah pola menyilang
        double crossingPenalty = crossingPatterns * CROSSING_PATTERN_WEIGHT; // Menghitung penalti pola menyilang
        description.append("2. Crossing Patterns: ").append(crossingPatterns) // Menambahkan jumlah pola menyilang ke deskripsi
                  .append(" (").append(-crossingPenalty).append(" points)\n"); // Menambahkan penalti pola menyilang ke deskripsi
        
        // Cek sel kosong
        int emptyCells = countEmptyCells(board); // Menghitung jumlah sel kosong
        double emptyPenalty = emptyCells * EMPTY_CELL_WEIGHT; // Menghitung penalti sel kosong
        description.append("3. Empty Cells: ").append(emptyCells) // Menambahkan jumlah sel kosong ke deskripsi
                  .append(" (").append(-emptyPenalty).append(" points)\n"); // Menambahkan penalti sel kosong ke deskripsi
        
        // Cek keseimbangan region
        double regionBalance = calculateRegionSizeBalance(board); // Menghitung keseimbangan region
        double regionPenalty = regionBalance * REGION_SIZE_WEIGHT; // Menghitung penalti keseimbangan region
        description.append("4. Region Balance: ").append(String.format("%.2f", regionBalance)) // Menambahkan keseimbangan region ke deskripsi
                  .append(" (").append(String.format("%.2f", -regionPenalty)).append(" points)\n"); // Menambahkan penalti keseimbangan region ke deskripsi
        
        return description.toString(); // Mengembalikan deskripsi fitness
    }
    
    // Method untuk mengecek apakah solusi sudah optimal
    public boolean isOptimalSolution(YinYangChromosome chromosome) {
        return calculate(chromosome) <= 0.1;  // Mengembalikan true jika fitness kurang dari atau sama dengan 0.1, menandakan solusi optimal
    }

    public YinYangFitnessFunction() {
        // Constructor kosong
    }
}
