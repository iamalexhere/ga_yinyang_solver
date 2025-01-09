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
    
//     @Test
//     public void testMutation() {
//         char[][] board = {
//             {'B', 'W', 'B'},
//             {'W', 'B', 'W'},
//             {'B', 'W', 'B'}
//         };
        
//         YinYangChromosome chromosome = new YinYangChromosome(new YinYangBoard(board));
//         YinYangChromosome mutated = chromosome.mutate();
        
//         // Test bahwa hasil mutasi berbeda dengan original
//         assertNotEquals("Values should be different", chromosome.toString(), mutated.toString());
        
//         // Verifikasi bahwa mutasi masih menghasilkan board yang valid
//         YinYangBoard mutatedBoard = mutated.getBoard();
//         for (int i = 0; i < mutatedBoard.getSize(); i++) {
//             for (int j = 0; j < mutatedBoard.getSize(); j++) {
//                 char cell = mutatedBoard.getCell(i, j);
//                 assertTrue("Cell should be either B or W", cell == 'B' || cell == 'W');
//             }
//         }
//     }
    
//     @Test
//     public void testClone() {
//         char[][] board = {
//             {'B', 'W', 'B'},
//             {'W', 'B', 'W'},
//             {'B', 'W', 'B'}
//         };
        
//         YinYangChromosome original = new YinYangChromosome(new YinYangBoard(board));
//         YinYangChromosome cloned = original.clone();
        
//         // Test that clone is equal but not the same object
//         assertEquals(original.toString(), cloned.toString());
//         assertNotSame(original, cloned);
//     }
// }
    @Test
    public void testMutation() {
        // Create board with some empty cells
        char[][] board = {
            {'B', '0', 'B'},
            {'0', 'B', '0'},
            {'B', '0', 'B'}
        };
        
        YinYangChromosome chromosome = new YinYangChromosome(new YinYangBoard(board));
        chromosome.initializeRandom(); // Fill empty cells first
        YinYangChromosome original = chromosome.clone(); // Keep original state
        
        // Perform mutation
        YinYangChromosome mutated = chromosome.mutate();
        
        // Test that mutation produced different board
        assertNotEquals("Mutation should change at least one cell", 
            original.toString(), mutated.toString());
        
        // Verify fixed cells remained unchanged
        for(int i = 0; i < board.length; i++) {
            for(int j = 0; j < board[i].length; j++) {
                if(board[i][j] != '0') {
                    assertEquals("Fixed cells should not change",
                        original.getBoard().getCell(i, j), 
                        mutated.getBoard().getCell(i, j));
                }
            }
        }
        
        // Verify all cells are still valid (B or W)
        for(int i = 0; i < board.length; i++) {
            for(int j = 0; j < board[i].length; j++) {
                char cell = mutated.getBoard().getCell(i, j);
                assertTrue("Cell should be either B or W", 
                    cell == 'B' || cell == 'W');
            }
        }
    }
}
