package com.ai.yinyang_solver;

public class YinYangFitnessFunction implements FitnessFunction<YinYangChromosome, Double> {
    
    // Bobot untuk berbagai aspek fitness
    private static final double CONNECTIVITY_WEIGHT = 50.0;  // Bobot untuk konektivitas region
    private static final double CROSSING_PATTERN_WEIGHT = 30.0;  // Bobot untuk pola menyilang
    private static final double EMPTY_CELL_WEIGHT = 20.0;  // Bobot untuk sel kosong
    
    @Override
    public Double calculate(YinYangChromosome chromosome) {
        YinYangBoard board = chromosome.getBoard();
        int white = board.countConnectedComponents(YinYangBoard.WHITE)*(board.countConnectedComponents(YinYangBoard.WHITE)-1);
        int black = board.countConnectedComponents(YinYangBoard.BLACK)*(board.countConnectedComponents(YinYangBoard.BLACK)-1);
         
        double connectivity = white + black;
        int crossPatternCount = board.slidingWindow();

        double fitness = connectivity*CONNECTIVITY_WEIGHT + crossPatternCount*CROSSING_PATTERN_WEIGHT;
        return fitness;
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
