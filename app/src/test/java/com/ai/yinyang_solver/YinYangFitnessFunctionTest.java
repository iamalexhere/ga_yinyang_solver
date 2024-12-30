package com.ai.yinyang_solver;

import org.junit.Test;
import static org.junit.Assert.*;

public class YinYangFitnessFunctionTest {
    
    private final YinYangFitnessFunction fitnessFunction = new YinYangFitnessFunction();
    
    @Test
    public void testPerfectSolution() {
        // Perfect solution: connected regions, no crossing patterns
        char[][] perfectBoard = {
            {'B', 'B', 'W'},
            {'B', 'W', 'W'},
            {'W', 'W', 'W'}
        };
        
        YinYangChromosome chromosome = new YinYangChromosome(new YinYangBoard(perfectBoard));
        double fitness = fitnessFunction.calculate(chromosome);
        
        // Should have maximum fitness
        assertEquals(100.0, fitness, 0.1);
        assertTrue(fitnessFunction.isOptimalSolution(chromosome));
    }
    
    @Test
    public void testDisconnectedRegions() {
        // Solution with disconnected regions
        char[][] disconnectedBoard = {
            {'B', 'W', 'B'},
            {'W', 'W', 'W'},
            {'B', 'W', 'B'}
        };
        
        YinYangChromosome chromosome = new YinYangChromosome(new YinYangBoard(disconnectedBoard));
        double fitness = fitnessFunction.calculate(chromosome);
        
        // Should have lower fitness due to disconnected regions
        assertTrue(fitness < 100.0);
        assertFalse(fitnessFunction.isOptimalSolution(chromosome));
    }
    
    @Test
    public void testCrossingPatterns() {
        // Solution with crossing patterns
        char[][] crossingBoard = {
            {'B', 'W', 'B'},
            {'W', 'B', 'W'},
            {'B', 'W', 'B'}
        };
        
        YinYangChromosome chromosome = new YinYangChromosome(new YinYangBoard(crossingBoard));
        double fitness = fitnessFunction.calculate(chromosome);
        
        // Should have lower fitness due to crossing patterns
        assertTrue(fitness < 100.0);
        assertFalse(fitnessFunction.isOptimalSolution(chromosome));
    }
    
    @Test
    public void testEmptyCells() {
        // Solution with empty cells
        char[][] emptyBoard = {
            {'B', 'W', '0'},
            {'W', 'B', 'W'},
            {'B', 'W', 'B'}
        };
        
        YinYangChromosome chromosome = new YinYangChromosome(new YinYangBoard(emptyBoard));
        double fitness = fitnessFunction.calculate(chromosome);
        
        // Should have lower fitness due to empty cells
        assertTrue(fitness < 100.0);
        assertFalse(fitnessFunction.isOptimalSolution(chromosome));
    }
    
    @Test
    public void testFitnessDescription() {
        char[][] board = {
            {'B', 'W', 'B'},
            {'W', 'B', 'W'},
            {'B', 'W', 'B'}
        };
        
        YinYangChromosome chromosome = new YinYangChromosome(new YinYangBoard(board));
        String description = fitnessFunction.getFitnessDescription(chromosome);
        
        // Check that description contains all necessary information
        assertTrue(description.contains("Connectivity"));
        assertTrue(description.contains("Crossing Patterns"));
        assertTrue(description.contains("Empty Cells"));
        assertTrue(description.contains("Total Fitness Score"));
    }
}
