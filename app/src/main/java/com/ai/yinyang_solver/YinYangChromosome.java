package com.ai.yinyang_solver;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class YinYangChromosome implements Chromosome<YinYangChromosome> {
    private YinYangBoard board;
    private static final Random random = new Random();
    private static final double MUTATION_RATE = 0.1;

    // Constructor dengan board kosong
    public YinYangChromosome(int size) {
        this.board = new YinYangBoard(size);
    }

    // Constructor dengan initial board
    public YinYangChromosome(YinYangBoard board) {
        this.board = board.clone();
    }

    // Inisialisasi random untuk sel kosong
    public void initializeRandom() {
        int size = board.getSize();
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (board.getCell(i, j) == YinYangBoard.EMPTY) {
                    // Random antara BLACK atau WHITE
                    char value = random.nextBoolean() ? YinYangBoard.BLACK : YinYangBoard.WHITE;
                    board.setCell(i, j, value);
                }
            }
        }
    }

    // Implementasi crossover
    @Override
    public List<YinYangChromosome> crossover(YinYangChromosome other) {
        List<YinYangChromosome> offspring = new ArrayList<>();
        
        // Single point crossover
        char[] parent1Genes = this.board.to1DArray();
        char[] parent2Genes = other.board.to1DArray();
        
        int size = board.getSize();
        int totalGenes = size * size;
        
        // Pilih titik crossover secara random
        int crossoverPoint = random.nextInt(totalGenes);
        
        // Buat dua offspring
        char[] offspring1Genes = new char[totalGenes];
        char[] offspring2Genes = new char[totalGenes];
        
        // Copy genes
        for (int i = 0; i < totalGenes; i++) {
            if (i < crossoverPoint) {
                offspring1Genes[i] = parent1Genes[i];
                offspring2Genes[i] = parent2Genes[i];
            } else {
                offspring1Genes[i] = parent2Genes[i];
                offspring2Genes[i] = parent1Genes[i];
            }
        }
        
        // Buat chromosome baru
        YinYangChromosome child1 = new YinYangChromosome(size);
        YinYangChromosome child2 = new YinYangChromosome(size);
        
        // Set genes ke board
        child1.board.from1DArray(offspring1Genes);
        child2.board.from1DArray(offspring2Genes);
        
        // Tambahkan ke list offspring
        offspring.add(child1);
        offspring.add(child2);
        
        return offspring;
    }

    // Implementasi mutasi
    @Override
    public YinYangChromosome mutate() {
        System.out.println("\n=== Mutation Process Debug ===");
        int size = board.getSize();
        int attempts = 0;
        boolean mutated = false;
        
        while (!mutated && attempts < size * size) {
            int i = random.nextInt(size);
            int j = random.nextInt(size);
            
            System.out.printf("Attempt %d: Trying to mutate cell[%d,%d]\n", 
                attempts + 1, i, j);
            System.out.printf("Current value: %c, IsFixed: %b\n", 
                board.getCell(i, j), board.isFixedCell(i, j));
            
            if (!board.isFixedCell(i, j)) {
                char currentValue = board.getCell(i, j);
                char newValue = (currentValue == YinYangBoard.BLACK) ? 
                    YinYangBoard.WHITE : YinYangBoard.BLACK;
                
                board.setCell(i, j, newValue);
                mutated = true;
                
                System.out.printf("Successfully mutated to: %c\n", newValue);
            }
            attempts++;
        }
        
        System.out.printf("Mutation %s after %d attempts\n", 
            mutated ? "succeeded" : "failed", attempts);
        
        return this;
    }

    // Getter untuk board
    public YinYangBoard getBoard() {
        return board;
    }

    // Clone chromosome
    public YinYangChromosome clone() {
        return new YinYangChromosome(this.board);
    }

    // ToString untuk debugging
    @Override
    public String toString() {
        return board.toString();
    }
}
