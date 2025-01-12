package com.ai.yinyang_solver;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class YinYangPopulation extends Population<YinYangChromosome> {
    private static final Random RANDOM = new Random();
    private static final int TOURNAMENT_SIZE = 5;
    private final YinYangFitnessFunction fitnessFunction;

    public YinYangPopulation(YinYangFitnessFunction fitnessFunction) {
        this.fitnessFunction = fitnessFunction;
    }

    public void initialize(YinYangBoard initialBoard, int populationSize) {
        // Instead of clearing, we'll create a new list and set it
        List<YinYangChromosome> newPopulation = new ArrayList<>();
        for (int i = 0; i < populationSize; i++) {
            YinYangChromosome chromosome = new YinYangChromosome(initialBoard);
            chromosome.initializeRandom();
            newPopulation.add(chromosome);
        }
        setChromosomes(newPopulation);
    }

    protected YinYangChromosome tournamentSelection() {
        YinYangChromosome best = null;
        double bestFitness = Double.POSITIVE_INFINITY;
        
        for (int i = 0; i < TOURNAMENT_SIZE; i++) {
            YinYangChromosome candidate = getRandom();
            double fitness = fitnessFunction.calculate(candidate);
            
            if (fitness < bestFitness) {
                best = candidate;
                bestFitness = fitness;
            }
        }
        
        return best;
    }

    public void evolve(double mutationRate, double crossoverRate) {
        List<YinYangChromosome> newPopulation = new ArrayList<>();
        
        while (newPopulation.size() < getChromosomes().size()) {
            YinYangChromosome parent1 = tournamentSelection();
            YinYangChromosome parent2 = tournamentSelection();
            
            if (RANDOM.nextDouble() < crossoverRate) {
                List<YinYangChromosome> offspring = parent1.crossover(parent2);
                
                if (RANDOM.nextDouble() < mutationRate) {
                    offspring.get(0).mutate();
                }
                if (RANDOM.nextDouble() < mutationRate) {
                    offspring.get(1).mutate();
                }
                
                newPopulation.addAll(offspring);
            } else {
                newPopulation.add(parent1.clone());
                if (newPopulation.size() < getChromosomes().size()) {
                    newPopulation.add(parent2.clone());
                }
            }
        }
        
        setChromosomes(newPopulation);
    }

    public void reinitialize(YinYangBoard initialBoard) {
        initialize(initialBoard, getChromosomes().size());
    }

    // Get the best chromosome in the population
    public YinYangChromosome getBest() {
        return getChromosomes().stream()
            .min((c1, c2) -> Double.compare(
                fitnessFunction.calculate(c1),
                fitnessFunction.calculate(c2)))
            .orElse(null);
    }

    public YinYangChromosome getRandom() {
        int index = RANDOM.nextInt(getChromosomes().size());
        return getChromosomes().get(index);
    }

    // Get top N chromosomes sorted by fitness
    public List<YinYangChromosome> getTopChromosomes(int count) {
        return getChromosomes().stream()
            .sorted((c1, c2) -> Double.compare(
                fitnessFunction.calculate(c1),
                fitnessFunction.calculate(c2)))
            .limit(count)
            .map(YinYangChromosome::clone)
            .collect(Collectors.toList());
    }

    // Replace worst chromosomes with elite ones
    public void replaceWorstWithElite(List<YinYangChromosome> elite) {
        List<YinYangChromosome> currentChromosomes = new ArrayList<>(getChromosomes());
        currentChromosomes.sort((c1, c2) -> Double.compare(
            fitnessFunction.calculate(c2),
            fitnessFunction.calculate(c1)));
        
        for (int i = 0; i < elite.size() && i < currentChromosomes.size(); i++) {
            currentChromosomes.set(i, elite.get(i).clone());
        }
        
        setChromosomes(currentChromosomes);
    }

    public String getPopulationStats() {
        double totalFitness = 0;
        double bestFitness = Double.POSITIVE_INFINITY;
        double worstFitness = Double.NEGATIVE_INFINITY;
        
        for (YinYangChromosome chromosome : getChromosomes()) {
            double fitness = fitnessFunction.calculate(chromosome);
            totalFitness += fitness;
            bestFitness = Math.min(bestFitness, fitness);
            worstFitness = Math.max(worstFitness, fitness);
        }
        
        double avgFitness = totalFitness / getChromosomes().size();
        
        return String.format("""
            Population Stats:
            Size: %d
            Best Fitness: %.2f
            Worst Fitness: %.2f
            Average Fitness: %.2f
            """, getChromosomes().size(), bestFitness, worstFitness, avgFitness);
    }
}
