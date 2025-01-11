// package com.ai.yinyang_solver;

// import java.util.ArrayList;
// import java.util.List;
// import java.util.Random;
// import java.util.stream.Collectors;

// public class YinYangPopulation extends Population<YinYangChromosome> {

//     // Method to clear all chromosomes in the population
//     private static final Random random = new Random(); // Random generator untuk seleksi dan mutasi
//     private static final int TOURNAMENT_SIZE = 5;  // Ukuran tournament untuk seleksi
//     private final YinYangFitnessFunction fitnessFunction; // Fungsi fitness untuk populasi
    
//     public YinYangPopulation(YinYangFitnessFunction fitnessFunction) {
//         this.fitnessFunction = fitnessFunction; // Inisialisasi fungsi fitness
//     }

//     public void clearChromosomes() {
//         getChromosomes().clear(); // Menghapus semua kromosom dari populasi
//     }
    
//     // Inisialisasi populasi awal
//     public void initializePopulation(YinYangBoard initialBoard, int populationSize) {
//         for (int i = 0; i < populationSize; i++) {
//             YinYangChromosome chromosome = new YinYangChromosome(initialBoard); // Membuat kromosom baru
//             chromosome.initializeRandom(); // Inisialisasi kromosom secara random
//             addChromosome(chromosome); // Menambahkan kromosom ke populasi
//         }
//     }
    
//     // Implementasi Tournament Selection
//     protected YinYangChromosome tournamentSelection() {
//         YinYangChromosome best = null; // Inisialisasi kromosom terbaik
//         double bestFitness = Double.POSITIVE_INFINITY; // Inisialisasi fitness terbaik dengan nilai tak hingga positif
        
//         // Pilih TOURNAMENT_SIZE kromosom secara random dan ambil yang terbaik
//         for (int i = 0; i < TOURNAMENT_SIZE; i++) {
//             YinYangChromosome candidate = getRandomChromosome(); // Memilih kromosom kandidat secara random
//             double fitness = fitnessFunction.calculate(candidate); // Menghitung fitness kandidat
            
//             if (fitness < bestFitness) { // Membandingkan fitness kandidat dengan fitness terbaik
//                 best = candidate; // Mengganti kromosom terbaik dengan kandidat
//                 bestFitness = fitness; // Mengganti fitness terbaik dengan fitness kandidat
//             }
//         }
        
//         return best; // Mengembalikan kromosom terbaik
//     }
    
//     // Implementasi Roulette Wheel Selection
//     protected YinYangChromosome rouletteWheelSelection() {
//         double totalFitness = 0; // Inisialisasi total fitness
//         List<Double> fitnessList = new ArrayList<>(); // List untuk menyimpan nilai fitness
        
//         // Hitung total fitness
//         for (YinYangChromosome chromosome : this) {
//             double fitness = fitnessFunction.calculate(chromosome); // Menghitung fitness kromosom
//             fitnessList.add(fitness); // Menambahkan fitness ke list
//             totalFitness += fitness; // Menambahkan fitness ke total fitness
//         }
        
//         // Jika total fitness 0, gunakan random selection
//         if (totalFitness == 0) {
//             return getRandomChromosome(); // Mengembalikan kromosom random jika total fitness 0
//         }
        
//         // Pilih kromosom berdasarkan proporsi fitness
//         double roulette = random.nextDouble() * totalFitness; // Memilih nilai random dalam rentang total fitness
//         double currentSum = 0; // Inisialisasi jumlah fitness saat ini
        
//         for (int i = 0; i < getSize(); i++) {
//             currentSum += fitnessList.get(i); // Menambahkan fitness kromosom ke jumlah saat ini
//             if (currentSum >= roulette) { // Memeriksa apakah jumlah saat ini melebihi nilai roulette
//                 return getChromosomeByIndex(i); // Mengembalikan kromosom pada index i
//             }
//         }
        
//         // Fallback ke random selection jika terjadi error
//         return getRandomChromosome(); // Mengembalikan kromosom random jika terjadi error
//     }
    
//     // Method untuk mendapatkan kromosom terbaik
//     protected YinYangChromosome getBestChromosome() {
//         YinYangChromosome best = null; // Inisialisasi kromosom terbaik
//         double bestFitness = Double.POSITIVE_INFINITY; // Inisialisasi fitness terbaik dengan nilai tak hingga positif
        
//         for (YinYangChromosome chromosome : this) {
//             double fitness = fitnessFunction.calculate(chromosome); // Menghitung fitness kromosom
//             if (fitness < bestFitness) { // Membandingkan fitness kromosom dengan fitness terbaik
//                 best = chromosome; // Mengganti kromosom terbaik dengan kromosom saat ini
//                 bestFitness = fitness; // Mengganti fitness terbaik dengan fitness kromosom saat ini
//             }
//         }
        
//         return best; // Mengembalikan kromosom terbaik
//     }
    
//     // Method untuk evolusi populasi
//     public void evolve() {
//         List<YinYangChromosome> newPopulation = new ArrayList<>(); // List untuk menyimpan populasi baru
//         int currentSize = getSize(); // Mendapatkan ukuran populasi saat ini
        
//         // Elitism: pertahankan kromosom terbaik
//         YinYangChromosome best = getBestChromosome(); // Mendapatkan kromosom terbaik
//         newPopulation.add(best.clone()); // Menambahkan kromosom terbaik ke populasi baru
        
//         // Generate offspring sampai ukuran populasi sama
//         while (newPopulation.size() < currentSize) {
//             // Pilih parents menggunakan tournament selection
//             YinYangChromosome parent1 = tournamentSelection(); // Memilih parent 1 menggunakan tournament selection
//             YinYangChromosome parent2 = tournamentSelection(); // Memilih parent 2 menggunakan tournament selection
            
//             // Lakukan crossover
//             List<YinYangChromosome> offspring = parent1.crossover(parent2); // Melakukan crossover antara parent 1 dan parent 2
            
//             // Lakukan mutasi pada offspring dan tambahkan ke populasi baru
//             for (YinYangChromosome child : offspring) {
//                 if (newPopulation.size() < currentSize) {
//                     newPopulation.add(child.mutate()); // Melakukan mutasi pada offspring dan menambahkannya ke populasi baru
//                 }
//             }
//         }
        
//         // Ganti populasi lama dengan populasi baru
//         setChromosomes(newPopulation); // Mengganti populasi lama dengan populasi baru
//     }

//     public void evolve(double mutationRate) {
//         List<YinYangChromosome> newPopulation = new ArrayList<>(); // List untuk menyimpan populasi baru
        
//         // untuk sementara 1 populasi ukurannya fix
//         while (newPopulation.size() < getChromosomes().size()) {
//             YinYangChromosome parent1 = tournamentSelection(); // Memilih parent 1 menggunakan tournament selection
//             YinYangChromosome parent2 = tournamentSelection(); // Memilih parent 2 menggunakan tournament selection
            
//             List<YinYangChromosome> offspring = parent1.crossover(parent2); // Melakukan crossover antara parent 1 dan parent 2
            
//             // Apply mutation based on rate
//             if (random.nextDouble() < mutationRate) {   //simpen seed & samain angka
//                 offspring.get(0).mutate(); // Melakukan mutasi pada offspring 1 berdasarkan mutation rate
//             }
//             if (random.nextDouble() < mutationRate) {
//                 offspring.get(1).mutate(); // Melakukan mutasi pada offspring 2 berdasarkan mutation rate
//             }
            
//             newPopulation.addAll(offspring); // Menambahkan offspring ke populasi baru
//         }
        
//         // Replace old population
//         setChromosomes(newPopulation); // Mengganti populasi lama dengan populasi baru
//     }

//     public List<YinYangChromosome> getTopChromosomes(int count) {
//         return getChromosomes().stream() // Mendapatkan stream dari kromosom
//             .sorted((c1, c2) -> Double.compare( // Mengurutkan kromosom berdasarkan fitness
//                 fitnessFunction.calculate(c1), // Menghitung fitness kromosom 1
//                 fitnessFunction.calculate(c2))) // Menghitung fitness kromosom 2
//             .limit(count) // Membatasi jumlah kromosom yang diambil
//             .map(YinYangChromosome::clone) // Mengkloning kromosom
//             .collect(Collectors.toList()); // Mengumpulkan kromosom ke dalam list
//     }

//     public void replaceWorstWithElite(List<YinYangChromosome> elite) {
//         // Get current chromosomes and sort by fitness (worst to best)
//         List<YinYangChromosome> currentChromosomes = new ArrayList<>(getChromosomes()); // Membuat list baru dari kromosom saat ini
//         currentChromosomes.sort((c1, c2) -> Double.compare( // Mengurutkan kromosom berdasarkan fitness (terburuk ke terbaik)
//             fitnessFunction.calculate(c2), // Menghitung fitness kromosom 2
//             fitnessFunction.calculate(c1))); // Menghitung fitness kromosom 1
        
//         // Replace worst individuals with elite
//         for (int i = 0; i < elite.size(); i++) {
//             if (i < currentChromosomes.size()) {
//                 currentChromosomes.set(i, elite.get(i).clone()); // Mengganti kromosom terburuk dengan kromosom elite
//             }
//         }
        
//         setChromosomes(currentChromosomes); // Mengganti populasi lama dengan populasi baru
//     }

//     public void reinitializeWorstIndividuals(int count) {
//         // Get current chromosomes and sort by fitness (worst to best)
//         List<YinYangChromosome> currentChromosomes = new ArrayList<>(getChromosomes()); // Membuat list baru dari kromosom saat ini
//         currentChromosomes.sort((c1, c2) -> Double.compare( // Mengurutkan kromosom berdasarkan fitness (terburuk ke terbaik)
//             fitnessFunction.calculate(c2), // Menghitung fitness kromosom 2
//             fitnessFunction.calculate(c1))); // Menghitung fitness kromosom 1
        
//         // Reinitialize worst individuals
//         for (int i = 0; i < count && i < currentChromosomes.size(); i++) {
//             YinYangChromosome newChromosome = new YinYangChromosome(currentChromosomes.get(i).getBoard().getSize()); // Membuat kromosom baru
//             newChromosome.initializeRandom(); // Inisialisasi kromosom baru secara random
//             currentChromosomes.set(i, newChromosome); // Mengganti kromosom terburuk dengan kromosom baru
//         }
        
//         setChromosomes(currentChromosomes); // Mengganti populasi lama dengan populasi baru
//     }
    
//     // Method untuk mendapatkan statistik populasi
//     public String getPopulationStats() {
//         double totalFitness = 0; // Inisialisasi total fitness
//         double bestFitness = Double.NEGATIVE_INFINITY; // Inisialisasi fitness terbaik dengan nilai tak hingga negatif
//         double worstFitness = Double.POSITIVE_INFINITY; // Inisialisasi fitness terburuk dengan nilai tak hingga positif
        
//         for (YinYangChromosome chromosome : this) {
//             double fitness = fitnessFunction.calculate(chromosome); // Menghitung fitness kromosom
//             totalFitness += fitness; // Menambahkan fitness ke total fitness
//             bestFitness = Math.max(bestFitness, fitness); // Membandingkan fitness dengan fitness terbaik
//             worstFitness = Math.min(worstFitness, fitness); // Membandingkan fitness dengan fitness terburuk
//         }
        
//         double avgFitness = totalFitness / getSize(); // Menghitung rata-rata fitness
        
//         return """
//             Population Stats:
//             Size: %d
//             Best Fitness: %.2f
//             Worst Fitness: %.2f
//             Average Fitness: %.2f
//             """.formatted(getSize(), bestFitness, worstFitness, avgFitness); // Mengembalikan string statistik populasi
//     }

//     public void reinitializePopulation(YinYangBoard initialBoard, int populationSize) {
//         // Clear existing population
//         clearChromosomes(); // Menghapus semua kromosom dari populasi
        
//         // Create new population
//         initializePopulation(initialBoard, populationSize); // Membuat populasi baru
//     }

//     public YinYangChromosome selectByTournament(int tournamentSize) {
//         YinYangChromosome best = null; // Inisialisasi kromosom terbaik
//         double bestFitness = Double.POSITIVE_INFINITY; // Inisialisasi fitness terbaik dengan nilai tak hingga positif
        
//         for (int i = 0; i < tournamentSize; i++) {
//             YinYangChromosome candidate = getRandomChromosome(); // Memilih kromosom kandidat secara random
//             double fitness = fitnessFunction.calculate(candidate); // Menghitung fitness kandidat
            
//             if (fitness < bestFitness) { // Membandingkan fitness kandidat dengan fitness terbaik
//                 best = candidate; // Mengganti kromosom terbaik dengan kandidat
//                 bestFitness = fitness; // Mengganti fitness terbaik dengan fitness kandidat
//             }
//         }
        
//         return best; // Mengembalikan kromosom terbaik
//     }

//     public void crossover(YinYangChromosome parent1, YinYangChromosome parent2) {
//         List<YinYangChromosome> offspring = parent1.crossover(parent2); // Melakukan crossover antara parent 1 dan parent 2
//         replaceWorstWithElite(offspring); // Mengganti kromosom terburuk dengan offspring
//     }
// }

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

    // Initialize the population with random chromosomes
    public void initialize(YinYangBoard initialBoard, int populationSize) {
        for (int i = 0; i < populationSize; i++) {
            YinYangChromosome chromosome = new YinYangChromosome(initialBoard);
            chromosome.initializeRandom();
            addChromosome(chromosome);
        }
    }

    // Select a chromosome using tournament selection
    private YinYangChromosome tournamentSelection() {
        return getChromosomes().stream()
            .sorted((c1, c2) -> Double.compare(
                fitnessFunction.calculate(c1),
                fitnessFunction.calculate(c2)))
            .limit(TOURNAMENT_SIZE)
            .min((c1, c2) -> Double.compare(
                fitnessFunction.calculate(c1),
                fitnessFunction.calculate(c2)))
            .orElse(getRandomChromosome());
    }

    // Evolve the population
    public void evolve(double mutationRate, double crossoverRate) {
        List<YinYangChromosome> newPopulation = new ArrayList<>();
        newPopulation.add(getBestChromosome().clone()); // Elitism

        while (newPopulation.size() < getSize()) {
            YinYangChromosome parent1 = tournamentSelection();
            YinYangChromosome parent2 = tournamentSelection();

            List<YinYangChromosome> offspring;
            double seed = RANDOM.nextDouble();
            //System.out.println("seed :" + seed);
            if (seed < crossoverRate) {
                offspring = parent1.crossover(parent2);
            } else {
                offspring = List.of(parent1.clone(), parent2.clone());
            }

            for (YinYangChromosome child : offspring) {
                if (seed < mutationRate) {
                    child.mutate();
                }
                if (newPopulation.size() < getSize()) {
                    newPopulation.add(child);
                }
            }
        }

        setChromosomes(newPopulation);
    }

    // Reinitialize the population
    public void reinitialize(YinYangBoard initialBoard) {
        clear();
        initialize(initialBoard, getSize());
    }

    // Get the best chromosome in the population
    public YinYangChromosome getBest() {
        return getChromosomes().stream()
            .min((c1, c2) -> Double.compare(
                fitnessFunction.calculate(c1),
                fitnessFunction.calculate(c2)))
            .orElse(null);
    }

    // Get statistics for the population
    public String getPopulationStats() {
        double totalFitness = 0;
        double bestFitness = Double.NEGATIVE_INFINITY;
        double worstFitness = Double.POSITIVE_INFINITY;

        for (YinYangChromosome chromosome : getChromosomes()) {
            double fitness = fitnessFunction.calculate(chromosome);
            totalFitness += fitness;
            bestFitness = Math.max(bestFitness, fitness);
            worstFitness = Math.min(worstFitness, fitness);
        }

        double avgFitness = totalFitness / getSize();
        return String.format("Best: %.2f, Worst: %.2f, Avg: %.2f",
            bestFitness, worstFitness, avgFitness);
    }

    // Helper method to clear the population
    private void clear() {
        getChromosomes().clear();
    }

    private YinYangChromosome getBestChromosome() {
        return getChromosomes().stream()
            .min((c1, c2) -> Double.compare(
                fitnessFunction.calculate(c1),
                fitnessFunction.calculate(c2)))
            .orElse(null);
    }
}
