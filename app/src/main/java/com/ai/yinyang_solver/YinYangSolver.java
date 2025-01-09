package com.ai.yinyang_solver;

import java.util.List;
import java.util.Random;

public class YinYangSolver {
    // Adjusted parameters
    private static final int POPULATION_SIZE = 400; // Doubled
    private static final int MAX_GENERATIONS = 2000; // Doubled
    private static final int MAX_STAGNANT_GENERATIONS = 100; // Increased
    private static final double PERFECT_FITNESS = 0.1; // Relaxed threshold
    private static final double MUTATION_RATE = 0.3; // Increased
    private static final int TOURNAMENT_SIZE = 5; // Increased
    private static final int ELITE_COUNT = 8; // Doubled
    private static final int LOCAL_SEARCH_ITERATIONS = 200; // Doubled
    private static final double CROSSOVER_RATE = 0.8; // Added crossover

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
            
            // Enhanced evolution
            population.evolve(MUTATION_RATE);
            applyCrossover();
            population.replaceWorstWithElite(elite);
            
            YinYangChromosome best = population.getBestChromosome();
            localSearch(best);
            
            updateStats();
            
            // Restart if stuck
            if (stagnantGenerations > MAX_STAGNANT_GENERATIONS / 2 && restartCount < 3) {
                population.reinitializePopulation(initialBoard, POPULATION_SIZE);
                stagnantGenerations = 0;
                restartCount++;
            }
            
            currentGeneration++;
        }

        return bestSolution != null ? bestSolution.getBoard() : population.getBestChromosome().getBoard();
    }

    private void applyCrossover() {
        for (int i = 0; i < POPULATION_SIZE / 2; i++) {
            if (random.nextDouble() < CROSSOVER_RATE) {
                YinYangChromosome parent1 = population.selectByTournament(TOURNAMENT_SIZE);
                YinYangChromosome parent2 = population.selectByTournament(TOURNAMENT_SIZE);
                population.crossover(parent1, parent2);
            }
        }
    }

    private void localSearch(YinYangChromosome chromosome) {
        YinYangBoard board = chromosome.getBoard();
        double currentFitness = fitnessFunction.calculate(chromosome);
        boolean improved;
        
        for (int i = 0; i < LOCAL_SEARCH_ITERATIONS; i++) {
            improved = false;
            
            // Try all possible moves systematically
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
            
            if (!improved) break; // Stop if no improvement found
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

        // Periodically reinitialize worst individuals if stuck
        if (stagnantGenerations > 20) {
            population.reinitializeWorstIndividuals(POPULATION_SIZE / 10);
        }
    }

    // Main method untuk testing
    public static void main(String[] args) {
        // Contoh board 6x6
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

        System.out.println("\nInitial Board:");
        System.out.println(new YinYangBoard(initialBoard).toString());
        System.out.println("\nFinal Solution:");
        System.out.println(solution.toString());
    }
}