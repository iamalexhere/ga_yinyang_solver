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
        char[][] initial = {
            {'B', 'W', '0'},
            {'B', '0', 'W'},
            {'0', 'W', 'B'}
        };
        YinYangChromosome chromosome = new YinYangChromosome(new YinYangBoard(initial));
        
        System.out.println("\n=== Mutation Test Debug ===");
        System.out.println("Initial board:");
        System.out.println(chromosome.getBoard().toString());
        
        String beforeMutation = chromosome.getBoard().toString();
        chromosome.mutate();
        String afterMutation = chromosome.getBoard().toString();
        
        System.out.println("\nAfter mutation:");
        System.out.println(afterMutation);
        
        System.out.println("\nFixed cells status:");
        YinYangBoard board = chromosome.getBoard();
        for(int i = 0; i < board.getSize(); i++) {
            for(int j = 0; j < board.getSize(); j++) {
                System.out.printf("Cell[%d,%d]=%c isFixed=%b\n", 
                    i, j, board.getCell(i, j), board.isFixedCell(i, j));
            }
        }
        System.out.println("==============================\n");
        
        assertNotEquals("Mutation should change at least one cell", beforeMutation, afterMutation);
    }

    // ...existing code...
}
