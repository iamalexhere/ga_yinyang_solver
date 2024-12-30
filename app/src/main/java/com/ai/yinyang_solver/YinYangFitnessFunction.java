package com.ai.yinyang_solver;

public class YinYangFitnessFunction implements FitnessFunction<YinYangChromosome, Double> {
    
    // Bobot untuk berbagai aspek fitness
    private static final double CONNECTIVITY_WEIGHT = 50.0;  // Bobot untuk konektivitas region
    private static final double CROSSING_PATTERN_WEIGHT = 30.0;  // Bobot untuk pola menyilang
    private static final double EMPTY_CELL_WEIGHT = 20.0;  // Bobot untuk sel kosong
    
    @Override
    public Double calculate(YinYangChromosome chromosome) {
        YinYangBoard board = chromosome.getBoard();
        double score = 100.0;  // Nilai awal maksimum
        
        // 1. Penalti untuk region yang tidak terhubung
        if (!board.isAllRegionsConnected()) {
            score -= CONNECTIVITY_WEIGHT;
        }
        
        // 2. Penalti untuk pola menyilang dalam kotak 2x2
        int crossingPatterns = board.countCrossingPatterns();
        score -= crossingPatterns * CROSSING_PATTERN_WEIGHT;
        
        // 3. Penalti untuk sel yang masih kosong
        int emptyCells = countEmptyCells(board);
        score -= emptyCells * EMPTY_CELL_WEIGHT;
        
        // Pastikan score tidak negatif
        return Math.max(0.0, score);
    }
    
    // Hitung jumlah sel kosong
    private int countEmptyCells(YinYangBoard board) {
        int count = 0;
        int size = board.getSize();
        
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (board.getCell(i, j) == YinYangBoard.EMPTY) {
                    count++;
                }
            }
        }
        
        return count;
    }
    
    // Method untuk mendapatkan deskripsi detail fitness
    public String getFitnessDescription(YinYangChromosome chromosome) {
        YinYangBoard board = chromosome.getBoard();
        StringBuilder description = new StringBuilder();
        
        // Cek konektivitas
        boolean connected = board.isAllRegionsConnected();
        description.append("Connectivity: ").append(connected ? "OK" : "Failed")
                  .append(" (").append(connected ? 0 : -CONNECTIVITY_WEIGHT).append(" points)\n");
        
        // Cek pola menyilang
        int crossingPatterns = board.countCrossingPatterns();
        description.append("Crossing Patterns: ").append(crossingPatterns)
                  .append(" (").append(-crossingPatterns * CROSSING_PATTERN_WEIGHT).append(" points)\n");
        
        // Cek sel kosong
        int emptyCells = countEmptyCells(board);
        description.append("Empty Cells: ").append(emptyCells)
                  .append(" (").append(-emptyCells * EMPTY_CELL_WEIGHT).append(" points)\n");
        
        // Total score
        double totalScore = calculate(chromosome);
        description.append("Total Fitness Score: ").append(totalScore);
        
        return description.toString();
    }
    
    // Method untuk mengecek apakah solusi sudah optimal
    public boolean isOptimalSolution(YinYangChromosome chromosome) {
        return calculate(chromosome) >= 99.9;  // Menggunakan threshold untuk floating point
    }
}
