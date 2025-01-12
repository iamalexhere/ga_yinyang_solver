package com.ai.yinyang_solver;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class YinYangChromosome implements Chromosome<YinYangChromosome> {
    private YinYangBoard board; // Papan Yin Yang yang direpresentasikan oleh kromosom
    private static final Random random = new Random(); // Random generator untuk inisialisasi dan mutasi

    // Constructor dengan board kosong
    public YinYangChromosome(int size) {
        this.board = new YinYangBoard(size); // Membuat board baru dengan ukuran yang diberikan
    }

    // Constructor dengan initial board
    public YinYangChromosome(YinYangBoard board) {
        this.board = board.clone(); // Membuat salinan board yang diberikan
    }

    // Inisialisasi random untuk sel kosong
    public void initializeRandom() {
        int size = board.getSize(); // Mendapatkan ukuran board
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (board.getCell(i, j) == YinYangBoard.EMPTY) {
                    // Random antara BLACK atau WHITE
                    char value = random.nextBoolean() ? YinYangBoard.BLACK : YinYangBoard.WHITE; // Menentukan nilai sel secara random
                    board.setCell(i, j, value); // Mengisi sel dengan nilai random
                }
            }
        }
    }

    // Implementasi crossover
    @Override
    public List<YinYangChromosome> crossover(YinYangChromosome other) {
        List<YinYangChromosome> offspring = new ArrayList<>(); // List untuk menyimpan offspring
        
        // Single point crossover
        char[] parent1Genes = this.board.to1DArray(); // Mengubah board menjadi array 1D
        char[] parent2Genes = other.board.to1DArray(); // Mengubah board parent lain menjadi array 1D
        
        int size = board.getSize(); // Mendapatkan ukuran board
        int totalGenes = size * size; // Menghitung total gen
        
        // Pilih titik crossover secara random
        int crossoverPoint = random.nextInt(totalGenes); // Memilih titik crossover secara random
        
        // Buat dua offspring
        char[] offspring1Genes = new char[totalGenes]; // Array untuk gen offspring 1
        char[] offspring2Genes = new char[totalGenes]; // Array untuk gen offspring 2
        
        // Copy genes
        for (int i = 0; i < totalGenes; i++) {
            if (i < crossoverPoint) {
                offspring1Genes[i] = parent1Genes[i]; // Copy gen dari parent 1
                offspring2Genes[i] = parent2Genes[i]; // Copy gen dari parent 2
            } else {
                offspring1Genes[i] = parent2Genes[i]; // Copy gen dari parent 2
                offspring2Genes[i] = parent1Genes[i]; // Copy gen dari parent 1
            }
        }
        
        // Buat chromosome baru
        YinYangChromosome child1 = new YinYangChromosome(size); // Membuat kromosom baru untuk offspring 1
        YinYangChromosome child2 = new YinYangChromosome(size); // Membuat kromosom baru untuk offspring 2
        
        // Set genes ke board
        child1.board.from1DArray(offspring1Genes); // Mengisi board offspring 1 dengan gen
        child2.board.from1DArray(offspring2Genes); // Mengisi board offspring 2 dengan gen
        
        // Tambahkan ke list offspring
        offspring.add(child1); // Menambahkan offspring 1 ke list
        offspring.add(child2); // Menambahkan offspring 2 ke list
        
        return offspring; // Mengembalikan list offspring
    }

    // Implementasi mutasi
    @Override
    public YinYangChromosome mutate() {
        int size = board.getSize();
        int i = random.nextInt(size);
        int j = random.nextInt(size);

        if (!board.isFixedCell(i, j)) {
            char currentValue = board.getCell(i, j);
            char newValue = (currentValue == YinYangBoard.BLACK) ?
                    YinYangBoard.WHITE : YinYangBoard.BLACK;
            board.setCell(i, j, newValue);
        }
        return this;
    }


    // Getter untuk board
    public YinYangBoard getBoard() {
        return board; // Mengembalikan board
    }

    // Clone chromosome
    public YinYangChromosome clone() {
        return new YinYangChromosome(this.board); // Membuat salinan kromosom
    }

    // ToString untuk debugging
    @Override
    public String toString() {
        return board.toString(); // Mengembalikan representasi string dari board
    }
}
