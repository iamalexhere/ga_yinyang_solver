package com.ai.yinyang_solver;

import org.junit.Test;
import static org.junit.Assert.*;
import java.util.List;

public class YinYangChromosomeTest {
    
    @Test 
    public void testChromosomeInitialization() {
        // Inisialisasi papan dengan nilai awal
        char[][] initialBoard = {
            {'B', 'W', '0'},
            {'0', 'B', 'W'},
            {'W', '0', 'B'}
        };
        YinYangBoard board = new YinYangBoard(initialBoard); // Membuat board dari array 2D
        YinYangChromosome chromosome = new YinYangChromosome(board); // Membuat kromosom dari board
        
        // Test bahwa sel fixed tidak berubah
        assertEquals('B', chromosome.getBoard().getCell(0, 0)); // Memastikan sel (0,0) adalah 'B'
        assertEquals('W', chromosome.getBoard().getCell(0, 1)); // Memastikan sel (0,1) adalah 'W'
        
        // Inisialisasi nilai random untuk sel kosong
        chromosome.initializeRandom();
        
        // Check bahwa semua sel terisi
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                char cell = chromosome.getBoard().getCell(i, j); // Mendapatkan nilai sel
                assertTrue(cell == 'B' || cell == 'W'); // Memastikan sel berisi 'B' atau 'W'
            }
        }
    }
    
    @Test
    public void testCrossover() {
        // Inisialisasi dua papan untuk parent
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
        
        YinYangChromosome parent1 = new YinYangChromosome(new YinYangBoard(board1)); // Membuat kromosom parent 1
        YinYangChromosome parent2 = new YinYangChromosome(new YinYangBoard(board2)); // Membuat kromosom parent 2
        
        List<YinYangChromosome> offspring = parent1.crossover(parent2); // Melakukan crossover
        
        // Test bahwa crossover menghasilkan 2 anak
        assertEquals(2, offspring.size()); // Memastikan jumlah offspring adalah 2
        
        // Test bahwa anak berbeda dari parent
        assertNotEquals(parent1.toString(), offspring.get(0).toString()); // Memastikan offspring 1 berbeda dari parent 1
        assertNotEquals(parent2.toString(), offspring.get(1).toString()); // Memastikan offspring 2 berbeda dari parent 2
    }
    
    @Test
    public void testMutation() {
        // Inisialisasi papan dengan nilai awal
        char[][] initial = {
            {'B', 'W', '0'},
            {'B', '0', 'W'},
            {'0', 'W', 'B'}
        };
        YinYangChromosome chromosome = new YinYangChromosome(new YinYangBoard(initial)); // Membuat kromosom dari board
        
        System.out.println("\n=== Mutation Test Debug ===");
        System.out.println("Initial board:");
        System.out.println(chromosome.getBoard().toString()); // Menampilkan board awal
        
        String beforeMutation = chromosome.getBoard().toString(); // Menyimpan representasi string board sebelum mutasi
        chromosome.mutate(); // Melakukan mutasi
        String afterMutation = chromosome.getBoard().toString(); // Menyimpan representasi string board setelah mutasi
        
        System.out.println("\nAfter mutation:");
        System.out.println(afterMutation); // Menampilkan board setelah mutasi
        
        System.out.println("\nFixed cells status:");
        YinYangBoard board = chromosome.getBoard(); // Mendapatkan board dari kromosom
        for(int i = 0; i < board.getSize(); i++) {
            for(int j = 0; j < board.getSize(); j++) {
                System.out.printf("Cell[%d,%d]=%c isFixed=%b\n", 
                    i, j, board.getCell(i, j), board.isFixedCell(i, j)); // Menampilkan status fixed setiap sel
            }
        }
        System.out.println("==============================\n");
        
        assertNotEquals("Mutation should change at least one cell", beforeMutation, afterMutation); // Memastikan mutasi mengubah setidaknya satu sel
    }
    
    @Test
    public void testCloneChromosome() {
        // Inisialisasi papan dengan nilai awal
        char[][] initialBoard = {
            {'B', 'W', '0'},
            {'0', 'B', 'W'},
            {'W', '0', 'B'}
        };
        YinYangChromosome original = new YinYangChromosome(new YinYangBoard(initialBoard));
        
        // Clone kromosom
        YinYangChromosome cloned = original.clone();
        
        // Test bahwa clone memiliki nilai yang sama
        assertEquals(original.toString(), cloned.toString());
        
        // Test bahwa clone adalah objek yang berbeda
        assertNotSame(original, cloned);
        assertNotSame(original.getBoard(), cloned.getBoard());
        
        // Test bahwa mengubah clone tidak mengubah original
        cloned.mutate();
        assertNotEquals(original.toString(), cloned.toString());
    }

    @Test
    public void testCrossoverWithFixedCells() {
        // Inisialisasi board dengan fixed cells
        char[][] board1 = {
            {'B', 'W', '0'},
            {'0', 'B', 'W'},
            {'W', '0', 'B'}
        };
        char[][] board2 = {
            {'B', 'W', 'B'},
            {'W', 'B', 'W'},
            {'B', 'W', 'B'}
        };
        
        YinYangChromosome parent1 = new YinYangChromosome(new YinYangBoard(board1));
        YinYangChromosome parent2 = new YinYangChromosome(new YinYangBoard(board2));
        
        // Inisialisasi sel kosong
        parent1.initializeRandom();
        
        List<YinYangChromosome> offspring = parent1.crossover(parent2);
        
        // Test bahwa fixed cells tidak berubah pada offspring
        YinYangChromosome child1 = offspring.get(0);
        assertEquals('B', child1.getBoard().getCell(0, 0));
        assertEquals('W', child1.getBoard().getCell(0, 1));
        
        YinYangChromosome child2 = offspring.get(1);
        assertEquals('B', child2.getBoard().getCell(0, 0));
        assertEquals('W', child2.getBoard().getCell(0, 1));
    }

    @Test
    public void testMultipleMutations() {
        // Inisialisasi board
        char[][] initialBoard = {
            {'0', '0', '0'},
            {'0', '0', '0'},
            {'0', '0', '0'}
        };
        YinYangChromosome chromosome = new YinYangChromosome(new YinYangBoard(initialBoard));
        chromosome.initializeRandom();
        
        // Simpan state awal
        String initialState = chromosome.toString();
        
        // Lakukan multiple mutasi
        for (int i = 0; i < 10; i++) {
            chromosome.mutate();
        }
        
        // Test bahwa board berubah setelah multiple mutasi
        assertNotEquals(initialState, chromosome.toString());
    }

    @Test
    public void testInitializeRandomWithDifferentSizes() {
        // Test small board (2x2)
        YinYangChromosome smallChromosome = new YinYangChromosome(2);
        smallChromosome.initializeRandom();
        assertValidBoard(smallChromosome.getBoard(), 2);
        
        // Test medium board (5x5)
        YinYangChromosome mediumChromosome = new YinYangChromosome(5);
        mediumChromosome.initializeRandom();
        assertValidBoard(mediumChromosome.getBoard(), 5);
        
        // Test large board (8x8)
        YinYangChromosome largeChromosome = new YinYangChromosome(8);
        largeChromosome.initializeRandom();
        assertValidBoard(largeChromosome.getBoard(), 8);
    }

    @Test
    public void testCrossoverPreservesValidValues() {
        // Inisialisasi parents dengan nilai valid
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
        
        // Test bahwa offspring hanya memiliki nilai valid (B atau W)
        for (YinYangChromosome child : offspring) {
            assertValidValues(child.getBoard());
        }
    }

    // Helper method untuk memvalidasi board
    private void assertValidBoard(YinYangBoard board, int expectedSize) {
        assertEquals(expectedSize, board.getSize());
        for (int i = 0; i < expectedSize; i++) {
            for (int j = 0; j < expectedSize; j++) {
                char cell = board.getCell(i, j);
                assertTrue("Cell should be either BLACK or WHITE",
                    cell == YinYangBoard.BLACK || cell == YinYangBoard.WHITE);
            }
        }
    }

    // Helper method untuk memvalidasi nilai-nilai dalam board
    private void assertValidValues(YinYangBoard board) {
        int size = board.getSize();
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                char cell = board.getCell(i, j);
                assertTrue("Cell should be either BLACK or WHITE",
                    cell == YinYangBoard.BLACK || cell == YinYangBoard.WHITE);
            }
        }
    }
}
