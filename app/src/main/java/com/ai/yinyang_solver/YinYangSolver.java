package com.ai.yinyang_solver;

import java.util.Random;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.io.IOException;
import org.knowm.xchart.*;
import org.knowm.xchart.style.Styler;
import java.awt.Color;

/**
 * Class {@code YinYangSolver} implements a genetic algorithm to solve the Yin Yang puzzle.
 */
public class YinYangSolver {
    private static final int MAX_GENERATIONS = 500;
    private static final int MAX_STAGNANT_GENERATIONS = 3;
    private static final double PERFECT_FITNESS = 0.99;
    private static final double MUTATION_RATE = 0.4;
    private static final double CROSSOVER_RATE = 0.3;
    private static final double ELITISM_RATE = 0.1;

    private final YinYangBoard initialBoard;
    private final YinYangPopulation population;
    private final YinYangFitnessFunction fitnessFunction;
    private final long seed;
    private final List<Double> fitnessHistory;
    private final int populationSize;

    private int currentGeneration;
    private int stagnantGenerations;
    private double bestFitness;
    private YinYangChromosome bestSolution;

    // Static map to store fitness histories for different population sizes
    private static final Map<Integer, List<Double>> allFitnessHistories = new HashMap<>();
    private static final Color[] COLORS = {Color.BLUE, Color.RED, Color.GREEN, Color.ORANGE, Color.MAGENTA};

    public YinYangSolver(char[][] board, int populationSize) {
        this(board, System.currentTimeMillis(), populationSize);
    }

    public YinYangSolver(char[][] board, long seed, int populationSize) {
        this.initialBoard = new YinYangBoard(board);
        this.fitnessFunction = new YinYangFitnessFunction();
        this.population = new YinYangPopulation(fitnessFunction, seed);
        this.seed = seed;
        this.currentGeneration = 0;
        this.stagnantGenerations = 0;
        this.bestFitness = Double.POSITIVE_INFINITY;
        this.bestSolution = null;
        this.fitnessHistory = new ArrayList<>();
        this.populationSize = populationSize;
    }

    public YinYangBoard solve() {
        population.initialize(initialBoard, populationSize);

        while (!shouldTerminate()) {
            // 1. Simpan elite chromosomes sebelum evolusi
            int eliteCount = (int)(populationSize * ELITISM_RATE);
            List<YinYangChromosome> elites = population.getTopChromosomes(eliteCount);
            
            // 2. Evolusi populasi
            population.evolve(MUTATION_RATE, CROSSOVER_RATE);
            
            // 3. Kembalikan elite chromosomes
            population.replaceWorstWithElite(elites);

            // Update stats dan cek stagnasi
            updateStats();

            // Reinisialisasi jika stagnant, tapi pertahankan elite
            if (stagnantGenerations > MAX_STAGNANT_GENERATIONS) {
                population.reinitialize(initialBoard);
                stagnantGenerations = 0;
                population.replaceWorstWithElite(elites);
            }

            System.out.println("Population Size: " + populationSize);
            System.out.println("Current generation: " + currentGeneration);
            System.out.println("Current best fitness: " + bestFitness);
            System.out.println(bestSolution.toString());
            currentGeneration++;
        }

        // Store fitness history for this population size
        allFitnessHistories.put(populationSize, new ArrayList<>(fitnessHistory));

        // Return the best solution
        return bestSolution != null ? bestSolution.getBoard() : population.getBest().getBoard();
    }

    private boolean shouldTerminate() {
        return bestFitness <= PERFECT_FITNESS ||
               currentGeneration >= MAX_GENERATIONS;
    }

    private void updateStats() {
        YinYangChromosome currentBest = population.getBest();
        double currentFitness = fitnessFunction.calculate(currentBest);

        if (currentFitness < bestFitness) {
            bestFitness = currentFitness;
            bestSolution = currentBest.clone();
            stagnantGenerations = 0;
        } else {
            stagnantGenerations++;
        }
        
        fitnessHistory.add(currentFitness);
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
