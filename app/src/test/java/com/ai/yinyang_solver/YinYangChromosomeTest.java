package com.ai.yinyang_solver;

import org.junit.Test;
import static org.junit.Assert.*;
import java.util.List;

public class YinYangChromosomeTest {
    
    @Test
    public void testChromosomeInitialization() {
        char[][] initialBoard = {
            {'B', 'W', '0'},
            {'0', 'B', 'W'},
            {'W', '0', 'B'}
        };
        YinYangBoard board = new YinYangBoard(initialBoard);
        YinYangChromosome chromosome = new YinYangChromosome(board);
        
        // Test that fixed cells remain unchanged
        assertEquals('B', chromosome.getBoard().getCell(0, 0));
        assertEquals('W', chromosome.getBoard().getCell(0, 1));
        
        // Initialize random values for empty cells
        chromosome.initializeRandom();
        
        // Check that all cells are filled
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                char cell = chromosome.getBoard().getCell(i, j);
                assertTrue(cell == 'B' || cell == 'W');
            }
        }
    }
    
    @Test
    public void testCrossover() {
        char[][] board1 = {
            {'B', 'W', 'B'},
            {'W', 'B', 'W'},
            {'B', 'W', 'B'}
        };
        char[][] board2 = {
            {'W', 'B', 'W'},
            {'B', 'W', 'B'},
            {'W', 'B', 'W'}
        };
        
        YinYangChromosome parent1 = new YinYangChromosome(new YinYangBoard(board1));
        YinYangChromosome parent2 = new YinYangChromosome(new YinYangBoard(board2));
        
        List<YinYangChromosome> offspring = parent1.crossover(parent2);
        
        // Test that crossover produces 2 children
        assertEquals(2, offspring.size());
        
        // Test that children are different from parents
        assertNotEquals(parent1.toString(), offspring.get(0).toString());
        assertNotEquals(parent2.toString(), offspring.get(1).toString());
    }
    
    @Test
    public void testMutation() {
        char[][] board = {
            {'B', 'W', 'B'},
            {'W', 'B', 'W'},
            {'B', 'W', 'B'}
        };
        
        YinYangChromosome original = new YinYangChromosome(new YinYangBoard(board));
        YinYangChromosome mutated = original.mutate();
        
        // Test that mutation produces a different chromosome
        assertNotEquals(original.toString(), mutated.toString());
    }
    
    @Test
    public void testClone() {
        char[][] board = {
            {'B', 'W', 'B'},
            {'W', 'B', 'W'},
            {'B', 'W', 'B'}
        };
        
        YinYangChromosome original = new YinYangChromosome(new YinYangBoard(board));
        YinYangChromosome cloned = original.clone();
        
        // Test that clone is equal but not the same object
        assertEquals(original.toString(), cloned.toString());
        assertNotSame(original, cloned);
    }
}
