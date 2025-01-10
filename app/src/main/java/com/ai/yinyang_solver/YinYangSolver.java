package com.ai.yinyang_solver;

import java.util.List;

public class YinYangSolver {
    private YinYangBoard initialBoard;
    private YinYangPopulation population;
    private YinYangFitnessFunction fitnessFunction;
    private int populationSize;
    private int maxIterations;
    private double mutationRate;
    private int eliteCount;
    private int reinitializeCount;
    private int tournamentSize;

    public YinYangSolver(YinYangBoard initialBoard, int populationSize, int maxIterations, double mutationRate, int eliteCount, int reinitializeCount, int tournamentSize) {
        this.initialBoard = initialBoard;
        this.populationSize = populationSize;
        this.maxIterations = maxIterations;
        this.mutationRate = mutationRate;
        this.eliteCount = eliteCount;
        this.reinitializeCount = reinitializeCount;
        this.tournamentSize = tournamentSize;
        this.fitnessFunction = new YinYangFitnessFunction();
        this.population = new YinYangPopulation(fitnessFunction);
    }

    public YinYangChromosome solve() {
        population.initializePopulation(initialBoard, populationSize);

        for (int i = 0; i < maxIterations; i++) {
            population.evolve(mutationRate);
            
            if (i % 10 == 0) {
                System.out.println("Iteration: " + i + ", " + population.getPopulationStats());
            }
            
            if (fitnessFunction.isOptimalSolution(population.getBestChromosome())) {
                System.out.println("Optimal solution found at iteration: " + i);
                break;
            }
            
            List<YinYangChromosome> elite = population.getTopChromosomes(eliteCount);
            population.replaceWorstWithElite(elite);
            
            population.reinitializeWorstIndividuals(reinitializeCount);
        }
        
        // Use tournament selection with the provided tournamentSize
        YinYangChromosome bestChromosome = population.selectByTournament(tournamentSize);
        return bestChromosome;
    }
}
