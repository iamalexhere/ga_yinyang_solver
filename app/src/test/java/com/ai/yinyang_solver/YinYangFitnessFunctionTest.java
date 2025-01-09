package com.ai.yinyang_solver;

import static org.junit.Assert.*;
import org.junit.Test;

public class YinYangFitnessFunctionTest {
    
    private final YinYangFitnessFunction fitnessFunction = new YinYangFitnessFunction();
    
    @Test
    public void testPerfectSolution() {
        char[][] perfectBoard = {
            {'B', 'B', 'W'},
            {'B', 'W', 'W'},
            {'W', 'W', 'W'}
        };
        
        YinYangChromosome chromosome = new YinYangChromosome(new YinYangBoard(perfectBoard));
        double fitness = fitnessFunction.calculate(chromosome);
        
        // Perfect solution should have fitness close to 0
        assertTrue("Perfect solution should have minimal fitness", fitness <= 0.1);
    }
    
    @Test
    public void testBadSolution() {
        char[][] badBoard = {
            {'B', 'W', 'B'},
            {'W', 'B', 'W'},
            {'B', 'W', 'B'}
        };
        
        YinYangChromosome chromosome = new YinYangChromosome(new YinYangBoard(badBoard));
        double fitness = fitnessFunction.calculate(chromosome);
        
        // Bad solution should have high fitness value
        assertTrue("Bad solution should have high fitness", fitness > 50.0);
    }
    
    @Test
    public void testFitnessComparison() {
        char[][] betterBoard = {
            {'B', 'B', 'W'},
            {'B', 'W', 'W'},
            {'W', 'W', 'W'}
        };
        
        char[][] worseBoard = {
            {'B', 'W', 'B'},
            {'W', 'B', 'W'},
            {'B', 'W', 'B'}
        };
        
        YinYangChromosome betterChromosome = new YinYangChromosome(new YinYangBoard(betterBoard));
        YinYangChromosome worseChromosome = new YinYangChromosome(new YinYangBoard(worseBoard));
        
        double betterFitness = fitnessFunction.calculate(betterChromosome);
        double worseFitness = fitnessFunction.calculate(worseChromosome);
        
        // Better solution should have lower fitness
        assertTrue("Better solution should have lower fitness", betterFitness < worseFitness);
    }
}
