package com.ai.yinyang_solver;

import java.util.Random;
import java.util.List;

/**
 * Class {@code YinYangSolver} implements a genetic algorithm to solve the Yin Yang puzzle.
 */
public class YinYangSolver {
    private static final int POPULATION_SIZE = 200;
    private static final int MAX_GENERATIONS = 20;
    private static final int MAX_STAGNANT_GENERATIONS = 3;
    private static final double PERFECT_FITNESS = 0.99;
    private static final double MUTATION_RATE = 0.4;
    private static final double CROSSOVER_RATE = 0.3;
    private static final double ELITISM_RATE = 0.1;

    private final YinYangBoard initialBoard;
    private final YinYangPopulation population;
    private final YinYangFitnessFunction fitnessFunction;
    private final long seed;

    private int currentGeneration;
    private int stagnantGenerations;
    private double bestFitness;
    private YinYangChromosome bestSolution;

    public YinYangSolver(char[][] board) {
        this(board, System.currentTimeMillis()); // Default random seed
    }

    public YinYangSolver(char[][] board, long seed) {
        this.initialBoard = new YinYangBoard(board);
        this.fitnessFunction = new YinYangFitnessFunction();
        this.population = new YinYangPopulation(fitnessFunction, seed);
        this.seed = seed;
        this.currentGeneration = 0;
        this.stagnantGenerations = 0;
        this.bestFitness = Double.POSITIVE_INFINITY;
        this.bestSolution = null;
    }

    public YinYangBoard solve() {
        population.initialize(initialBoard, POPULATION_SIZE);

        while (!shouldTerminate()) {
            // 1. Simpan elite chromosomes sebelum evolusi
            int eliteCount = (int)(POPULATION_SIZE * ELITISM_RATE);
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

            System.out.println("current generation : " + currentGeneration);
            System.out.println("current best fitness : " + bestFitness);
            System.out.println(bestSolution.toString());
            currentGeneration++;
        }

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
    }

    // Getter for seed to verify the used seed
    public long getSeed() {
        return seed;
    }
}
