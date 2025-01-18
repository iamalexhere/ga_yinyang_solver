package com.ai.yinyang_solver;

import java.io.File;
import java.io.PrintWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class App {
    public static void main(String[] args) {
        long totalStartTime = System.currentTimeMillis();

        char[][] board = {
            {'0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0'},
            {'0', '0', 'W', '0', 'W', '0', '0', 'W', '0', '0', '0', 'W', '0', '0', '0'},
            {'0', '0', 'W', '0', '0', '0', 'B', '0', '0', '0', 'W', '0', 'B', '0', '0'},
            {'0', '0', 'W', '0', '0', '0', '0', '0', 'W', '0', '0', 'W', '0', '0', 'W'},
            {'0', '0', '0', 'W', 'W', '0', '0', 'B', 'W', '0', '0', '0', '0', '0', '0'},
            {'0', '0', '0', 'B', '0', 'W', '0', '0', 'W', '0', 'W', '0', '0', 'W', '0'},
            {'0', 'B', '0', '0', 'B', '0', 'W', '0', '0', '0', '0', '0', 'W', '0', '0'},
            {'0', '0', '0', 'B', 'B', 'B', 'B', '0', 'B', '0', '0', '0', '0', '0', '0'},
            {'0', '0', 'B', 'W', '0', '0', '0', '0', '0', 'B', '0', '0', 'B', '0', '0'},
            {'B', '0', 'B', '0', '0', 'B', '0', 'B', '0', '0', '0', '0', 'B', '0', '0'},
            {'0', '0', 'W', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', 'B', '0'},
            {'0', 'W', '0', '0', 'W', '0', 'W', '0', 'W', '0', '0', 'B', '0', '0', '0'},
            {'0', '0', '0', '0', '0', 'W', '0', 'W', '0', '0', '0', '0', '0', 'B', '0'},
            {'0', '0', 'B', '0', 'B', '0', '0', '0', 'W', 'W', '0', '0', '0', '0', '0'},
            {'0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0'},
        };

        // Create unique run ID with timestamp
        String runId = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
        
        // Setup output directories
        String path = "src/main/java/com/ai/yinyang_solver/visual";
        String outputBaseDir = path + "/output_" + runId;
        String boardsDir = outputBaseDir + "/boards";
        String plotsDir = outputBaseDir + "/plots";
        String logFile = outputBaseDir + "/execution_log.txt";
        
        // Create directories
        new File(boardsDir).mkdirs();
        new File(plotsDir).mkdirs();

        try (PrintWriter logWriter = new PrintWriter(new FileWriter(logFile))) {
            logWriter.println("=== YinYang Solver Execution Log ===");
            logWriter.println("Run ID: " + runId);
            logWriter.println("Start Time: " + LocalDateTime.now());
            logWriter.println("=====================================\n");

            // Save initial board visualization
            YinYangBoard initialBoard = new YinYangBoard(board);
            BoardVisualizer.saveBoard(initialBoard, boardsDir, "initial_board");

            PerformanceVisualizer visualizer = new PerformanceVisualizer(plotsDir);

            // Test different population sizes
            logWriter.println("\nTesting different population sizes...");
            int[] populationSizes = {5000};
            for (int popSize : populationSizes) {
                logWriter.println("\nTesting population size: " + popSize);
                YinYangSolver solver = new YinYangSolver(board, popSize);
                solver.setLogWriter(logWriter);
                YinYangBoard solution = solver.solve();
                
                // Save solution board
                BoardVisualizer.saveBoard(solution, boardsDir, "solution_pop" + popSize);
                visualizer.addPopulationSizeHistory(popSize, solver.getFitnessHistory());
            }

            // Test different mutation rates
            // logWriter.println("\nTesting different mutation rates...");
            // double[] mutationRates = {0.1, 0.2, 0.4, 0.6};
            // for (double mutationRate : mutationRates) {
            //     logWriter.println("\nTesting mutation rate: " + mutationRate);
            //     YinYangSolver solver = new YinYangSolver(board, 5000, mutationRate, 0.3);
            //     solver.setLogWriter(logWriter);
            //     YinYangBoard solution = solver.solve();
                
            //     // Save solution board
            //     BoardVisualizer.saveBoard(solution, boardsDir, 
            //         "solution_mut" + String.format("%.2f", mutationRate).replace(".", ""));
            //     visualizer.addMutationRateHistory(mutationRate, solver.getFitnessHistory());
            // }

            // Test different crossover rates
            // logWriter.println("\nTesting different crossover rates...");
            // double[] crossoverRates = {0.1, 0.3, 0.5, 0.7};
            // for (double crossoverRate : crossoverRates) {
            //     logWriter.println("\nTesting crossover rate: " + crossoverRate);
            //     YinYangSolver solver = new YinYangSolver(board, 5000, 0.4, crossoverRate);
            //     solver.setLogWriter(logWriter);
            //     YinYangBoard solution = solver.solve();
                
            //     // Save solution board
            //     BoardVisualizer.saveBoard(solution, boardsDir, 
            //         "solution_cross" + String.format("%.2f", crossoverRate).replace(".", ""));
            //     visualizer.addCrossoverRateHistory(crossoverRate, solver.getFitnessHistory());
            // }

            // // Generate all comparison plots
            visualizer.plotPopulationSizeComparison();
            // visualizer.plotMutationRateComparison();
            // visualizer.plotCrossoverRateComparison();

            long totalEndTime = System.currentTimeMillis();
            double totalExecutionTime = (totalEndTime - totalStartTime) / 1000.0;
            
            logWriter.println("\n=== Total Execution Summary ===");
            logWriter.println("Total Runtime: " + String.format("%.2f", totalExecutionTime) + " seconds");
            logWriter.println("============================");

            // System.out.println("\nAll outputs have been saved in: " + outputBaseDir);
            // System.out.println("- Board visualizations: " + boardsDir);
            // System.out.println("- Performance plots: " + plotsDir);
            // System.out.println("- Execution log: " + logFile);

        } catch (IOException e) {
            System.err.println("Error writing to log file: " + e.getMessage());
        }
    }
}
