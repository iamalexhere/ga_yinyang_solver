package com.ai.yinyang_solver;

import java.util.Random;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.io.IOException;
import java.io.FileWriter;
import java.io.PrintWriter;
import org.knowm.xchart.*;
import org.knowm.xchart.style.Styler;
import java.awt.Color;

/**
 * Class {@code YinYangSolver} implements a genetic algorithm to solve the Yin Yang puzzle.
 */
public class YinYangSolver {
    private static final int MAX_GENERATIONS = 100;
    private static final int MAX_STAGNANT_GENERATIONS = 3;
    private static final double PERFECT_FITNESS = 0.99;
    private static final double ELITISM_RATE = 0.1;

    private final YinYangBoard initialBoard;
    private final YinYangPopulation population;
    private final YinYangFitnessFunction fitnessFunction;
    private final long seed;
    private final List<Double> fitnessHistory;
    private final int populationSize;
    private final double mutationRate;
    private final double crossoverRate;
    private final Random random;

    private int currentGeneration;
    private int stagnantGenerations;
    private double bestFitness;
    private YinYangChromosome bestSolution;

    // Static map to store fitness histories for different population sizes
    private static final Map<Integer, List<Double>> allFitnessHistories = new HashMap<>();
    private static final Color[] COLORS = {Color.BLUE, Color.RED, Color.GREEN, Color.ORANGE, Color.MAGENTA};

    private PrintWriter logWriter;

    public void setLogWriter(PrintWriter writer) {
        this.logWriter = writer;
    }

    private void log(String message) {
        System.out.println(message);
        if (logWriter != null) {
            logWriter.println(message);
            logWriter.flush();
        }
    }

    public YinYangSolver(char[][] board, int populationSize) {
        this(board, populationSize, 0.4, 0.3);
    }

    public YinYangSolver(char[][] board, int populationSize, double mutationRate, double crossoverRate) {
        this(board, populationSize, mutationRate, crossoverRate, System.currentTimeMillis());
    }

    public YinYangSolver(char[][] board, int populationSize, long seed) {
        this(board, populationSize, 0.4, 0.3, seed);
    }

    public YinYangSolver(char[][] board, int populationSize, double mutationRate, double crossoverRate, long seed) {
        this.initialBoard = new YinYangBoard(board);
        this.populationSize = populationSize;
        this.mutationRate = mutationRate;
        this.crossoverRate = crossoverRate;
        this.seed = seed;
        this.random = new Random(seed);
        this.fitnessFunction = new YinYangFitnessFunction();
        this.fitnessHistory = new ArrayList<>();
        this.population = new YinYangPopulation(fitnessFunction, seed);
        this.population.initialize(initialBoard, populationSize);
        
        this.currentGeneration = 0;
        this.stagnantGenerations = 0;
        this.bestFitness = Double.POSITIVE_INFINITY;  // Initialize to worst possible fitness
        this.bestSolution = null;
    }

    public YinYangBoard solve() {
        long startTime = System.currentTimeMillis();

        // Initial fitness evaluation
        updateStats();
        
        while (!shouldTerminate()) {
            // 1. Save elite chromosomes before evolution
            int eliteCount = (int)(populationSize * ELITISM_RATE);
            List<YinYangChromosome> elites = population.getTopChromosomes(eliteCount);
            
            // 2. Evolve population
            population.evolve(mutationRate, crossoverRate);
            
            // 3. Restore elite chromosomes
            population.replaceWorstWithElite(elites);

            // Update stats and check stagnation
            updateStats();

            // Reinitialize if stagnant, but preserve elite
            if (stagnantGenerations >= MAX_STAGNANT_GENERATIONS) {
                population.reinitialize(initialBoard);
                stagnantGenerations = 0;
                population.replaceWorstWithElite(elites);
                updateStats();
            }

            log("Generation: " + currentGeneration);
            log("Population Size: " + populationSize);
            log("Mutation Rate: " + mutationRate);
            log("Crossover Rate: " + crossoverRate);
            log("Current best fitness: " + bestFitness);
            if (bestSolution != null) {
                log(bestSolution.toString());
            }
            
            currentGeneration++;
        }

        long endTime = System.currentTimeMillis();
        double executionTime = (endTime - startTime) / 1000.0;

        log("\n=== Solve Completed ===");
        log("Total Generations: " + currentGeneration);
        log("Final Best Fitness: " + bestFitness);
        log("Execution Time: " + String.format("%.2f", executionTime) + " seconds");
        log("====================\n");

        // Store fitness history for this population size
        allFitnessHistories.put(populationSize, new ArrayList<>(fitnessHistory));

        return bestSolution != null ? bestSolution.getBoard() : population.getBest().getBoard();
    }

    private boolean shouldTerminate() {
        return bestFitness <= PERFECT_FITNESS ||
               currentGeneration >= MAX_GENERATIONS;
    }

    private void updateStats() {
        YinYangChromosome currentBest = population.getBest();
        if (currentBest != null) {
            double currentFitness = fitnessFunction.calculate(currentBest);
            fitnessHistory.add(currentFitness);  // Add current fitness to history

            if (currentFitness < bestFitness) {
                bestFitness = currentFitness;
                bestSolution = currentBest.clone();
                stagnantGenerations = 0;
            } else {
                stagnantGenerations++;
            }
        }
    }

    public List<Double> getFitnessHistory() {
        return new ArrayList<>(fitnessHistory);
    }

    public int getPopulationSize() {
        return populationSize;
    }

    public double getMutationRate() {
        return mutationRate;
    }

    public double getCrossoverRate() {
        return crossoverRate;
    }

    public static void plotAllFitnessHistories() {
        try {
            // Create Chart
            XYChart chart = new XYChartBuilder()
                .width(800)
                .height(600)
                .title("Fitness History Comparison")
                .xAxisTitle("Generation")
                .yAxisTitle("Fitness")
                .build();

            // Customize Chart
            chart.getStyler().setLegendPosition(Styler.LegendPosition.InsideNE);
            chart.getStyler().setDefaultSeriesRenderStyle(XYSeries.XYSeriesRenderStyle.Line);
            chart.getStyler().setPlotMargin(0);
            chart.getStyler().setPlotGridLinesVisible(true);

            // Add series for each population size
            int colorIndex = 0;
            for (Map.Entry<Integer, List<Double>> entry : allFitnessHistories.entrySet()) {
                int popSize = entry.getKey();
                List<Double> history = entry.getValue();
                
                double[] generations = new double[history.size()];
                double[] fitness = new double[history.size()];
                for (int i = 0; i < history.size(); i++) {
                    generations[i] = i;
                    fitness[i] = history.get(i);
                }

                Color lineColor = COLORS[colorIndex % COLORS.length];
                chart.addSeries("Population Size: " + popSize, generations, fitness)
                    .setLineColor(lineColor);
                
                colorIndex++;
            }

            // Save chart
            BitmapEncoder.saveBitmap(chart, "./fitness_history_comparison", BitmapEncoder.BitmapFormat.PNG);
            
        } catch (IOException e) {
            System.err.println("Error saving fitness history plot: " + e.getMessage());
        }
    }

    // Getter for seed to verify the used seed
    public long getSeed() {
        return seed;
    }
}
