package com.ai.yinyang_solver;

public class YinYangSolver {

    // Konfigurasi GA
    private static final int POPULATION_SIZE = 200; // Increased
    private static final int MAX_GENERATIONS = 2000; // Increased
    private static final int MAX_STAGNANT_GENERATIONS = 50;
    private static final double PERFECT_FITNESS = 0.0; // Changed to 0.0 since we want minimum
    private static final double MUTATION_RATE = 0.2; // Higher mutation rate
    private static final int TOURNAMENT_SIZE = 7; // Larger tournament size

    private final YinYangBoard initialBoard;
    private final YinYangPopulation population;
    private final YinYangFitnessFunction fitnessFunction;

    private int currentGeneration;
    private int stagnantGenerations;
    private double bestFitness;

    // Add adaptive parameters
    private double adaptiveMutationRate;
    private int stagnantCount;

    // Constructor
    public YinYangSolver(char[][] board) {
        this.initialBoard = new YinYangBoard(board);
        this.fitnessFunction = new YinYangFitnessFunction();
        this.population = new YinYangPopulation(fitnessFunction);
        this.currentGeneration = 0;
        this.stagnantGenerations = 0;
        this.bestFitness = Double.POSITIVE_INFINITY; // Changed to POSITIVE_INFINITY
        this.adaptiveMutationRate = 0.1;
        this.stagnantCount = 0;
    }

    // Method utama untuk menjalankan solver
    public YinYangBoard solve() {
        // Inisialisasi populasi
        population.initializePopulation(initialBoard, POPULATION_SIZE);
        System.out.println("Initial population created.");
        System.out.println(population.getPopulationStats());

        // Main loop
        while (!shouldTerminate()) {
            // Evolusi populasi
            population.evolve();
            currentGeneration++;

            // Update statistik
            YinYangChromosome bestChromosome = population.getBestChromosome();
            double currentFitness = fitnessFunction.calculate(bestChromosome);

            // Update stagnant generations
            if (currentFitness < bestFitness) { // Changed from > to <
                bestFitness = currentFitness;
                stagnantGenerations = 0;

                // Print progress
                System.out.println("\nGeneration " + currentGeneration);
                System.out.println("Best Fitness: " + bestFitness);
                System.out.println("Current best solution:");
                System.out.println(bestChromosome.toString());
                System.out.println(fitnessFunction.getFitnessDescription(bestChromosome));
            } else {
                stagnantGenerations++;
            }

            // Adjust parameters
            adjustParameters();

            // Print progress setiap 10 generasi
            if (currentGeneration % 10 == 0) {
                System.out.println("\nGeneration " + currentGeneration);
                System.out.println(population.getPopulationStats());
            }
        }

        // Return solusi terbaik
        YinYangChromosome bestSolution = population.getBestChromosome();
        System.out.println("\nFinal Solution:");
        System.out.println("Generation: " + currentGeneration);
        System.out.println("Fitness: " + bestFitness);
        System.out.println(fitnessFunction.getFitnessDescription(bestSolution));
        System.out.println(bestSolution.toString());

        return bestSolution.getBoard();
    }

    // Cek apakah algoritma harus berhenti
    private boolean shouldTerminate() {
        // 1. Solusi optimal ditemukan
        if (bestFitness <= PERFECT_FITNESS) { // Changed from >= to <=
            System.out.println("Perfect solution found!");
            return true;
        }

        // 2. Mencapai maksimum generasi
        if (currentGeneration >= MAX_GENERATIONS) {
            System.out.println("Maximum generations reached.");
            return true;
        }

        // 3. Fitness stagnant
        if (stagnantGenerations >= MAX_STAGNANT_GENERATIONS) {
            System.out.println("Fitness stagnant for " + MAX_STAGNANT_GENERATIONS + " generations.");
            return true;
        }

        return false;
    }

    private void adjustParameters() {
        // Increase mutation rate if stuck
        if (stagnantGenerations > 20) {
            adaptiveMutationRate = Math.min(0.4, adaptiveMutationRate + 0.05);
        } else {
            adaptiveMutationRate = 0.1;
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