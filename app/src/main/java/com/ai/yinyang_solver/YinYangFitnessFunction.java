package com.ai.yinyang_solver;
// Package untuk kelas-kelas yang berhubungan dengan fungsi fitness Yin Yang

public class YinYangFitnessFunction implements FitnessFunction<YinYangChromosome, Double> {
    
    // Adjust weights for better balance
    private static final double CONNECTIVITY_WEIGHT = 9.0;
    private static final double CROSSING_PATTERN_WEIGHT = 5.0;
    private static final double MONO_COLOR__WEIGHT = 15.0;
    
    @Override
    public Double calculate(YinYangChromosome chromosome) {
        YinYangBoard board = chromosome.getBoard();
        int white = board.countConnectedComponents(YinYangBoard.WHITE)*(board.countConnectedComponents(YinYangBoard.WHITE)-1);
        int black = board.countConnectedComponents(YinYangBoard.BLACK)*(board.countConnectedComponents(YinYangBoard.BLACK)-1);
        
        double connectivity = white + black;
        int crossPatternCount = board.slidingWindowForColorCross();
        int monoColorCount = board.slidingWindowForMonoColor();

        double fitness = connectivity*CONNECTIVITY_WEIGHT + crossPatternCount*CROSSING_PATTERN_WEIGHT + monoColorCount*MONO_COLOR__WEIGHT;
        return fitness;
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
        int crossingPatterns = board.slidingWindowForColorCross(); // Menghitung jumlah pola menyilang
        double crossingPenalty = crossingPatterns * CROSSING_PATTERN_WEIGHT; // Menghitung penalti pola menyilang
        description.append("2. Crossing Patterns: ").append(crossingPatterns) // Menambahkan jumlah pola menyilang ke deskripsi
                  .append(" (").append(-crossingPenalty).append(" points)\n"); // Menambahkan penalti pola menyilang ke deskripsi
        
        
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
