package com.ai.yinyang_solver;

public class YinYangFitnessFunction implements FitnessFunction<YinYangChromosome, Double> {
    
    // Adjust weights for better balance
    private static final double CONNECTIVITY_WEIGHT = 100.0;  // Increased
    private static final double CROSSING_PATTERN_WEIGHT = 50.0;  // Increased
    private static final double EMPTY_CELL_WEIGHT = 30.0;  // Increased
    private static final double REGION_SIZE_WEIGHT = 20.0;  // New weight
    
    @Override
    public Double calculate(YinYangChromosome chromosome) {
        YinYangBoard board = chromosome.getBoard();
        
        double connectivityPenalty = board.isAllRegionsConnected() ? 0 : CONNECTIVITY_WEIGHT;
        int crossPatternCount = board.slidingWindow();
        int emptyCells = countEmptyCells(board);
        double regionSizeBalance = calculateRegionSizeBalance(board);

        return connectivityPenalty + 
               crossPatternCount * CROSSING_PATTERN_WEIGHT + 
               emptyCells * EMPTY_CELL_WEIGHT +
               regionSizeBalance * REGION_SIZE_WEIGHT;
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
    
    private double calculateRegionSizeBalance(YinYangBoard board) {
        // Calculate difference between black and white region sizes
        int blackCount = 0, whiteCount = 0;
        int size = board.getSize();
        
        for(int i = 0; i < size; i++) {
            for(int j = 0; j < size; j++) {
                if(board.getCell(i,j) == YinYangBoard.BLACK) blackCount++;
                else if(board.getCell(i,j) == YinYangBoard.WHITE) whiteCount++;
            }
        }
        
        return Math.abs(blackCount - whiteCount) / (double)(size * size);
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
        int crossingPatterns = board.slidingWindow();
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
        return calculate(chromosome) <= 0.1;  // Changed from >= 99.9 to <= 0.1
    }

    public YinYangFitnessFunction() {
        
    }

    // private int[] countComponentsForEachColor(char[] board) {
    //     boolean[] visited = new boolean[board.length];
    //     int blackComponents = 0;
    //     int whiteComponents = 0;

    //     for(int i = 0;i < board.length;i++) {
    //         if(!visited[i]) {
    //             if(board[i] == '1' || board[i] == '3') {
    //                 dfs(i, board, visited, board[i]);
    //                 blackComponents += 1;
    //             }else{
    //                 dfs(i, board, visited, board[i]);
    //                 whiteComponents += 1;
    //             }
    //         }
    //     }

    //     int[] result = new int[2];
    //     result[0] = blackComponents;
    //     result[1] = whiteComponents;

    //     return result;
    // }

    // private void dfs(int idx, char[] board, boolean[] visited, char target) {
    //     if(idx < 0 || idx >= board.length || visited[idx] || board[idx] != target) {
    //         return;
    //     }

    //     visited[idx] = true;

    //     int row = idx / width;
    //     int col = idx % width;

    //     if(row > 0) dfs(idx-width, board, visited, target);
    //     if(row < height - 1) dfs(idx+width, board, visited, target);
    //     if(col > 0) dfs(idx-1, board, visited, target);
    //     if(col < width - 1) dfs(idx+1, board, visited, target);
    // }
}
