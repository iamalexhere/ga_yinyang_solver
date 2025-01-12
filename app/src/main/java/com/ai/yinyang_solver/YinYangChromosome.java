package com.ai.yinyang_solver;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class YinYangChromosome implements Chromosome<YinYangChromosome> {
    private YinYangBoard board;
    private final Random random;

    public YinYangChromosome(int size) {
        this(size, System.currentTimeMillis());
    }

    public YinYangChromosome(int size, long seed) {
        this.board = new YinYangBoard(size);
        this.random = new Random(seed);
    }

    public YinYangChromosome(YinYangBoard board) {
        this(board, System.currentTimeMillis());
    }

    public YinYangChromosome(YinYangBoard board, long seed) {
        this.board = board.clone();
        this.random = new Random(seed);
    }

    public void initializeRandom() {
        int size = board.getSize();
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (board.getCell(i, j) == YinYangBoard.EMPTY) {
                    char value = random.nextBoolean() ? YinYangBoard.BLACK : YinYangBoard.WHITE;
                    board.setCell(i, j, value);
                }
            }
        }
    }

    @Override
    public List<YinYangChromosome> crossover(YinYangChromosome other) {
        List<YinYangChromosome> offspring = new ArrayList<>();
        
        char[] parent1Genes = this.board.to1DArray();
        char[] parent2Genes = other.board.to1DArray();
        
        int size = board.getSize();
        int totalGenes = size * size;
        
        int crossoverPoint = random.nextInt(totalGenes);
        
        char[] offspring1Genes = new char[totalGenes];
        char[] offspring2Genes = new char[totalGenes];
        
        for (int i = 0; i < totalGenes; i++) {
            if (i < crossoverPoint) {
                offspring1Genes[i] = parent1Genes[i];
                offspring2Genes[i] = parent2Genes[i];
            } else {
                offspring1Genes[i] = parent2Genes[i];
                offspring2Genes[i] = parent1Genes[i];
            }
        }
        
        YinYangChromosome child1 = new YinYangChromosome(size, random.nextLong());
        YinYangChromosome child2 = new YinYangChromosome(size, random.nextLong());
        
        child1.board.from1DArray(offspring1Genes);
        child2.board.from1DArray(offspring2Genes);
        
        offspring.add(child1);
        offspring.add(child2);
        
        return offspring;
    }

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

    @Override
    public YinYangChromosome clone() {
        YinYangChromosome clone = new YinYangChromosome(this.board, random.nextLong());
        return clone;
    }

    public YinYangBoard getBoard() {
        return board;
    }

    @Override
    public String toString() {
        return board.toString();
    }
}
