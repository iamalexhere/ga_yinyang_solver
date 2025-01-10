package com.ai.yinyang_solver;

import java.util.List;
import java.util.Random;

/**
 * Kelas {@code YinYangSolver} mengimplementasikan algoritma genetika untuk menyelesaikan puzzle Yin Yang.
 * Puzzle ini melibatkan pengisian papan dengan simbol 'B' dan 'W' sedemikian rupa sehingga tidak ada baris atau kolom
 * yang memiliki lebih dari setengah simbol yang sama, dan tidak ada tiga simbol berurutan yang sama secara horizontal atau vertikal.
 */
public class YinYangSolver {
    // Konfigurasi GA
    private static final int POPULATION_SIZE = 100;
    private static final int MAX_GENERATIONS = 1000;
    private static final int MAX_STAGNANT_GENERATIONS = 50;
    private static final double PERFECT_FITNESS = 0.99;
    private static final int ELITE_COUNT = 10;
    private static final double MUTATION_RATE = 0.01;
    private static final int LOCAL_SEARCH_ITERATIONS = 5;

    private final YinYangBoard initialBoard;
    private final YinYangPopulation population;
    private final YinYangFitnessFunction fitnessFunction;
    private final Random random;

    private int currentGeneration;
    private int stagnantGenerations;
    private double bestFitness;
    private YinYangChromosome bestSolution;

    public YinYangSolver(char[][] board) {
        this.initialBoard = new YinYangBoard(board);
        this.fitnessFunction = new YinYangFitnessFunction();
        this.population = new YinYangPopulation(fitnessFunction);
        this.random = new Random();
        this.currentGeneration = 0;
        this.stagnantGenerations = 0;
        this.bestFitness = Double.POSITIVE_INFINITY;
        this.bestSolution = null;
    }

    public YinYangBoard solve() {
        population.initializePopulation(initialBoard, POPULATION_SIZE);
        int restartCount = 0;

        while (!shouldTerminate()) {
            List<YinYangChromosome> elite = population.getTopChromosomes(ELITE_COUNT);
            
            // Evolusi dan crossover sekarang sepenuhnya dihandle oleh YinYangPopulation
            population.evolve(MUTATION_RATE);
            
            YinYangChromosome best = population.getBestChromosome();
            localSearch(best);
            updateStats();

            if (stagnantGenerations > MAX_STAGNANT_GENERATIONS / 2 && restartCount < 3) {
                population.reinitializePopulation(initialBoard, POPULATION_SIZE);
                stagnantGenerations = 0;
                restartCount++;
            }

            currentGeneration++;
        }

        return bestSolution != null ? bestSolution.getBoard() : population.getBestChromosome().getBoard();
    }

    private void localSearch(YinYangChromosome chromosome) {
        YinYangBoard board = chromosome.getBoard();
        double currentFitness = fitnessFunction.calculate(chromosome);
        boolean improved;

        for (int i = 0; i < LOCAL_SEARCH_ITERATIONS; i++) {
            improved = false;

            for (int row = 0; row < board.getSize(); row++) {
                for (int col = 0; col < board.getSize(); col++) {
                    if (initialBoard.getCell(row, col) == '0') {
                        char originalValue = board.getCell(row, col);
                        board.setCell(row, col, originalValue == 'B' ? 'W' : 'B');

                        double newFitness = fitnessFunction.calculate(chromosome);
                        if (newFitness < currentFitness) {
                            currentFitness = newFitness;
                            improved = true;
                        } else {
                            board.setCell(row, col, originalValue);
                        }
                    }
                }
            }

            if (!improved) break;
        }
    }

    private boolean shouldTerminate() {
        return bestFitness <= PERFECT_FITNESS ||
               currentGeneration >= MAX_GENERATIONS ||
               stagnantGenerations >= MAX_STAGNANT_GENERATIONS;
    }

    private void updateStats() {
        YinYangChromosome currentBest = population.getBestChromosome();
        double currentFitness = fitnessFunction.calculate(currentBest);

        if (currentFitness < bestFitness) {
            bestFitness = currentFitness;
            bestSolution = currentBest.clone();
            stagnantGenerations = 0;
        } else {
            stagnantGenerations++;
        }

        if (stagnantGenerations > 20) {
            population.reinitializeWorstIndividuals(POPULATION_SIZE / 10);
        }
    }

    public static void main(String[] args) {
        char[][] initialBoard = {
            {'0', '0', '0', 'B', '0', 'W'},
            {'0', '0', 'B', '0', 'B', '0'},
            {'0', '0', 'W', '0', '0', '0'},
            {'B', '0', '0', '0', 'W', '0'},
            {'0', 'B', '0', 'B', '0', '0'},
            {'W', '0', '0', '0', '0', '0'}
        };

        YinYangSolver solver = new YinYangSolver(initialBoard);
        YinYangBoard solution = solver.solve();

        System.out.println("\nPapan Awal:");
        System.out.println(new YinYangBoard(initialBoard).toString());
        System.out.println("\nSolusi Akhir:");
        System.out.println(solution.toString());
    }
}
