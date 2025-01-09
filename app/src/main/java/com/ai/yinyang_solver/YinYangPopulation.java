package com.ai.yinyang_solver;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class YinYangPopulation extends Population<YinYangChromosome> {

    // Method to clear all chromosomes in the population
    public void clearChromosomes() {
        getChromosomes().clear();
    }
    private static final Random random = new Random();
    private static final int TOURNAMENT_SIZE = 5;  // Ukuran tournament untuk seleksi
    private final YinYangFitnessFunction fitnessFunction;
    
    public YinYangPopulation(YinYangFitnessFunction fitnessFunction) {
        this.fitnessFunction = fitnessFunction;
    }
    
    // Inisialisasi populasi awal
    public void initializePopulation(YinYangBoard initialBoard, int populationSize) {
        for (int i = 0; i < populationSize; i++) {
            YinYangChromosome chromosome = new YinYangChromosome(initialBoard);
            chromosome.initializeRandom();
            addChromosome(chromosome);
        }
    }
    
    // Implementasi Tournament Selection
    protected YinYangChromosome tournamentSelection() {
        YinYangChromosome best = null;
        double bestFitness = Double.POSITIVE_INFINITY; // Changed from NEGATIVE_INFINITY
        
        // Pilih TOURNAMENT_SIZE kromosom secara random dan ambil yang terbaik
        for (int i = 0; i < TOURNAMENT_SIZE; i++) {
            YinYangChromosome candidate = getRandomChromosome();
            double fitness = fitnessFunction.calculate(candidate);
            
            if (fitness < bestFitness) { // Changed from > to <
                best = candidate;
                bestFitness = fitness;
            }
        }
        
        return best;
    }
    
    // Implementasi Roulette Wheel Selection
    protected YinYangChromosome rouletteWheelSelection() {
        double totalFitness = 0;
        List<Double> fitnessList = new ArrayList<>();
        
        // Hitung total fitness
        for (YinYangChromosome chromosome : this) {
            double fitness = fitnessFunction.calculate(chromosome);
            fitnessList.add(fitness);
            totalFitness += fitness;
        }
        
        // Jika total fitness 0, gunakan random selection
        if (totalFitness == 0) {
            return getRandomChromosome();
        }
        
        // Pilih kromosom berdasarkan proporsi fitness
        double roulette = random.nextDouble() * totalFitness;
        double currentSum = 0;
        
        for (int i = 0; i < getSize(); i++) {
            currentSum += fitnessList.get(i);
            if (currentSum >= roulette) {
                return getChromosomeByIndex(i);
            }
        }
        
        // Fallback ke random selection jika terjadi error
        return getRandomChromosome();
    }
    
    // Method untuk mendapatkan kromosom terbaik
    protected YinYangChromosome getBestChromosome() {
        YinYangChromosome best = null;
        double bestFitness = Double.POSITIVE_INFINITY; // Changed from NEGATIVE_INFINITY
        
        for (YinYangChromosome chromosome : this) {
            double fitness = fitnessFunction.calculate(chromosome);
            if (fitness < bestFitness) { // Changed from > to <
                best = chromosome;
                bestFitness = fitness;
            }
        }
        
        return best;
    }
    
    // Method untuk evolusi populasi
    public void evolve() {
        List<YinYangChromosome> newPopulation = new ArrayList<>();
        int currentSize = getSize();
        
        // Elitism: pertahankan kromosom terbaik
        YinYangChromosome best = getBestChromosome();
        newPopulation.add(best.clone());
        
        // Generate offspring sampai ukuran populasi sama
        while (newPopulation.size() < currentSize) {
            // Pilih parents menggunakan tournament selection
            YinYangChromosome parent1 = tournamentSelection();
            YinYangChromosome parent2 = tournamentSelection();
            
            // Lakukan crossover
            List<YinYangChromosome> offspring = parent1.crossover(parent2);
            
            // Lakukan mutasi pada offspring dan tambahkan ke populasi baru
            for (YinYangChromosome child : offspring) {
                if (newPopulation.size() < currentSize) {
                    newPopulation.add(child.mutate());
                }
            }
        }
        
        // Ganti populasi lama dengan populasi baru
        setChromosomes(newPopulation);
    }

    public void evolve(double mutationRate) {
        List<YinYangChromosome> newPopulation = new ArrayList<>();
        
        // Perform tournament selection and crossover
        while (newPopulation.size() < getChromosomes().size()) {
            YinYangChromosome parent1 = tournamentSelection();
            YinYangChromosome parent2 = tournamentSelection();
            
            List<YinYangChromosome> offspring = parent1.crossover(parent2);
            
            // Apply mutation based on rate
            if (random.nextDouble() < mutationRate) {
                offspring.get(0).mutate();
            }
            if (random.nextDouble() < mutationRate) {
                offspring.get(1).mutate();
            }
            
            newPopulation.addAll(offspring);
        }
        
        // Replace old population
        setChromosomes(newPopulation);
    }

    public List<YinYangChromosome> getTopChromosomes(int count) {
        return getChromosomes().stream()
            .sorted((c1, c2) -> Double.compare(
                fitnessFunction.calculate(c1),
                fitnessFunction.calculate(c2)))
            .limit(count)
            .map(YinYangChromosome::clone)
            .collect(Collectors.toList());
    }

    public void replaceWorstWithElite(List<YinYangChromosome> elite) {
        // Get current chromosomes and sort by fitness (worst to best)
        List<YinYangChromosome> currentChromosomes = new ArrayList<>(getChromosomes());
        currentChromosomes.sort((c1, c2) -> Double.compare(
            fitnessFunction.calculate(c2),
            fitnessFunction.calculate(c1)));
        
        // Replace worst individuals with elite
        for (int i = 0; i < elite.size(); i++) {
            if (i < currentChromosomes.size()) {
                currentChromosomes.set(i, elite.get(i).clone());
            }
        }
        
        setChromosomes(currentChromosomes);
    }

    public void reinitializeWorstIndividuals(int count) {
        // Get current chromosomes and sort by fitness (worst to best)
        List<YinYangChromosome> currentChromosomes = new ArrayList<>(getChromosomes());
        currentChromosomes.sort((c1, c2) -> Double.compare(
            fitnessFunction.calculate(c2),
            fitnessFunction.calculate(c1)));
        
        // Reinitialize worst individuals
        for (int i = 0; i < count && i < currentChromosomes.size(); i++) {
            YinYangChromosome newChromosome = new YinYangChromosome(currentChromosomes.get(i).getBoard().getSize());
            newChromosome.initializeRandom();
            currentChromosomes.set(i, newChromosome);
        }
        
        setChromosomes(currentChromosomes);
    }
    
    // Method untuk mendapatkan statistik populasi
    public String getPopulationStats() {
        double totalFitness = 0;
        double bestFitness = Double.NEGATIVE_INFINITY;
        double worstFitness = Double.POSITIVE_INFINITY;
        
        for (YinYangChromosome chromosome : this) {
            double fitness = fitnessFunction.calculate(chromosome);
            totalFitness += fitness;
            bestFitness = Math.max(bestFitness, fitness);
            worstFitness = Math.min(worstFitness, fitness);
        }
        
        double avgFitness = totalFitness / getSize();
        
        return """
            Population Stats:
            Size: %d
            Best Fitness: %.2f
            Worst Fitness: %.2f
            Average Fitness: %.2f
            """.formatted(getSize(), bestFitness, worstFitness, avgFitness);
    }

    public void reinitializePopulation(YinYangBoard initialBoard, int populationSize) {
        // Clear existing population
        clearChromosomes();
        
        // Create new population
        initializePopulation(initialBoard, populationSize);
    }

    public YinYangChromosome selectByTournament(int tournamentSize) {
        YinYangChromosome best = null;
        double bestFitness = Double.POSITIVE_INFINITY;
        
        for (int i = 0; i < tournamentSize; i++) {
            YinYangChromosome candidate = getRandomChromosome();
            double fitness = fitnessFunction.calculate(candidate);
            
            if (fitness < bestFitness) {
                best = candidate;
                bestFitness = fitness;
            }
        }
        
        return best;
    }

    public void crossover(YinYangChromosome parent1, YinYangChromosome parent2) {
        List<YinYangChromosome> offspring = parent1.crossover(parent2);
        replaceWorstWithElite(offspring);
    }
}
