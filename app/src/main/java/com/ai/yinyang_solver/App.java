package com.ai.yinyang_solver;

import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class App {
    public static void main(String[] args) {
        char[][] board = {
            {'0', '0', '0', '0', '0', '0'},
            {'0', '0', 'W', 'B', 'B', '0'},
            {'0', 'B', '0', '0', 'B', '0'},
            {'0', '0', 'B', '0', '0', '0'},
            {'0', '0', 'W', 'B', '0', 'B'},
            {'0', '0', '0', '0', '0', 'W'},
        };

        // Create unique run ID with timestamp
        String runId = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
        
        // Setup output directories
        String path = "src/main/java/com/ai/yinyang_solver/visual";
        String outputBaseDir = path + "/output_" + runId;
        String boardsDir = outputBaseDir + "/boards";
        String plotsDir = outputBaseDir + "/plots";
        
        // Create directories
        new File(boardsDir).mkdirs();
        new File(plotsDir).mkdirs();

        // Save initial board visualization
        YinYangBoard initialBoard = new YinYangBoard(board);
        BoardVisualizer.saveBoard(initialBoard, boardsDir, "initial_board");

        PerformanceVisualizer visualizer = new PerformanceVisualizer(plotsDir);

        // Test different population sizes
        System.out.println("\nTesting different population sizes...");
        int[] populationSizes = {1000, 2000, 5000, 10000};
        for (int popSize : populationSizes) {
            System.out.println("\nTesting population size: " + popSize);
            YinYangSolver solver = new YinYangSolver(board, popSize);
            YinYangBoard solution = solver.solve();
            
            // Save solution board
            BoardVisualizer.saveBoard(solution, boardsDir, "solution_pop" + popSize);
            visualizer.addPopulationSizeHistory(popSize, solver.getFitnessHistory());
        }

        // Test different mutation rates
        System.out.println("\nTesting different mutation rates...");
        double[] mutationRates = {0.1, 0.2, 0.4, 0.6};
        for (double mutationRate : mutationRates) {
            System.out.println("\nTesting mutation rate: " + mutationRate);
            YinYangSolver solver = new YinYangSolver(board, 5000, mutationRate, 0.3);
            YinYangBoard solution = solver.solve();
            
            // Save solution board
            BoardVisualizer.saveBoard(solution, boardsDir, 
                "solution_mut" + String.format("%.2f", mutationRate).replace(".", ""));
            visualizer.addMutationRateHistory(mutationRate, solver.getFitnessHistory());
        }

        // Test different crossover rates
        System.out.println("\nTesting different crossover rates...");
        double[] crossoverRates = {0.1, 0.3, 0.5, 0.7};
        for (double crossoverRate : crossoverRates) {
            System.out.println("\nTesting crossover rate: " + crossoverRate);
            YinYangSolver solver = new YinYangSolver(board, 5000, 0.4, crossoverRate);
            YinYangBoard solution = solver.solve();
            
            // Save solution board
            BoardVisualizer.saveBoard(solution, boardsDir, 
                "solution_cross" + String.format("%.2f", crossoverRate).replace(".", ""));
            visualizer.addCrossoverRateHistory(crossoverRate, solver.getFitnessHistory());
        }

        // Generate all comparison plots
        visualizer.plotPopulationSizeComparison();
        visualizer.plotMutationRateComparison();
        visualizer.plotCrossoverRateComparison();

        System.out.println("\nAll outputs have been saved in: " + outputBaseDir);
        System.out.println("- Board visualizations: " + boardsDir);
        System.out.println("- Performance plots: " + plotsDir);
    }
}
