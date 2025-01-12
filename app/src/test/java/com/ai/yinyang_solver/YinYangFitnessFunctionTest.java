package com.ai.yinyang_solver;

import static org.junit.Assert.*; 
import org.junit.Test; 

public class YinYangFitnessFunctionTest {
    
    private final YinYangFitnessFunction fitnessFunction = new YinYangFitnessFunction(); // Membuat instance dari YinYangFitnessFunction
    
    @Test 
    public void testPerfectSolution() {
        // Inisialisasi papan dengan solusi sempurna
        char[][] perfectBoard = {
            {'B', 'B', 'W'},
            {'B', 'W', 'W'},
            {'W', 'W', 'W'}
        };
        
        YinYangChromosome chromosome = new YinYangChromosome(new YinYangBoard(perfectBoard)); // Membuat kromosom dari board
        double fitness = fitnessFunction.calculate(chromosome); // Menghitung fitness kromosom
        
        // Add debug information
        System.out.println("\n=== Perfect Solution Test Debug ===");
        System.out.println("Board Configuration:");
        System.out.println(chromosome.getBoard().toString()); // Menampilkan konfigurasi board
        System.out.println("Detailed Fitness Calculation:");
        System.out.println(fitnessFunction.getFitnessDescription(chromosome)); // Menampilkan detail perhitungan fitness
        System.out.println("Final Fitness Score: " + fitness); // Menampilkan skor fitness akhir
        System.out.println("==============================\n");
        
        // Adjust the threshold to account for region balance penalty
        assertTrue("Perfect solution should have minimal fitness", fitness <= 2.0); // Memastikan solusi sempurna memiliki fitness minimal
    }
    
    @Test 
    public void testBadSolution() {
        // Inisialisasi papan dengan solusi buruk
        char[][] badBoard = {
            {'B', 'W', 'B'},
            {'W', 'B', 'W'},
            {'B', 'W', 'B'}
        };
        
        YinYangChromosome chromosome = new YinYangChromosome(new YinYangBoard(badBoard)); // Membuat kromosom dari board
        double fitness = fitnessFunction.calculate(chromosome); // Menghitung fitness kromosom
        
        // Bad solution should have high fitness value
        assertTrue("Bad solution should have high fitness", fitness > 50.0); // Memastikan solusi buruk memiliki fitness tinggi
    }
    
    @Test 
    public void testFitnessComparison() {
        // Inisialisasi papan dengan solusi yang lebih baik
        char[][] betterBoard = {
            {'B', 'B', 'W'},
            {'B', 'W', 'W'},
            {'W', 'W', 'W'}
        };
        
        // Inisialisasi papan dengan solusi yang lebih buruk
        char[][] worseBoard = {
            {'B', 'W', 'B'},
            {'W', 'B', 'W'},
            {'B', 'W', 'B'}
        };
        
        YinYangChromosome betterChromosome = new YinYangChromosome(new YinYangBoard(betterBoard)); // Membuat kromosom dari board yang lebih baik
        YinYangChromosome worseChromosome = new YinYangChromosome(new YinYangBoard(worseBoard)); // Membuat kromosom dari board yang lebih buruk
        
        double betterFitness = fitnessFunction.calculate(betterChromosome); // Menghitung fitness kromosom yang lebih baik
        double worseFitness = fitnessFunction.calculate(worseChromosome); // Menghitung fitness kromosom yang lebih buruk
        
        // Better solution should have lower fitness
        assertTrue("Better solution should have lower fitness", betterFitness < worseFitness); // Memastikan solusi yang lebih baik memiliki fitness lebih rendah
    }

    @Test
    public void testOptimalSolutionCheck() {
        // Test case 1: Optimal solution
        char[][] optimalBoard = {
            {'B', 'B', 'W'},
            {'B', 'W', 'W'},
            {'W', 'W', 'W'}
        };
        YinYangChromosome optimalChromosome = new YinYangChromosome(new YinYangBoard(optimalBoard));
        assertTrue("Should identify optimal solution", fitnessFunction.isOptimalSolution(optimalChromosome));

        // Test case 2: Non-optimal solution
        char[][] nonOptimalBoard = {
            {'B', 'W', 'B'},
            {'W', 'B', 'W'},
            {'B', 'W', 'B'}
        };
        YinYangChromosome nonOptimalChromosome = new YinYangChromosome(new YinYangBoard(nonOptimalBoard));
        assertFalse("Should identify non-optimal solution", fitnessFunction.isOptimalSolution(nonOptimalChromosome));
    }

    @Test
    public void testFitnessDescription() {
        // Test case 1: Perfect board
        char[][] perfectBoard = {
            {'B', 'B', 'W'},
            {'B', 'W', 'W'},
            {'W', 'W', 'W'}
        };
        YinYangChromosome perfectChromosome = new YinYangChromosome(new YinYangBoard(perfectBoard));
        String perfectDescription = fitnessFunction.getFitnessDescription(perfectChromosome);
        assertTrue("Description should mention connectivity is OK", 
            perfectDescription.contains("Connectivity: OK"));
        assertTrue("Description should show crossing patterns", 
            perfectDescription.contains("Crossing Patterns:"));

        // Test case 2: Bad board
        char[][] badBoard = {
            {'B', 'W', 'B'},
            {'W', 'B', 'W'},
            {'B', 'W', 'B'}
        };
        YinYangChromosome badChromosome = new YinYangChromosome(new YinYangBoard(badBoard));
        String badDescription = fitnessFunction.getFitnessDescription(badChromosome);
        assertTrue("Description should mention connectivity failed", 
            badDescription.contains("Connectivity: Failed"));
    }

    @Test
    public void testFitnessWithDifferentBoardSizes() {
        // Test small board (2x2)
        char[][] smallBoard = {
            {'B', 'W'},
            {'W', 'B'}
        };
        YinYangChromosome smallChromosome = new YinYangChromosome(new YinYangBoard(smallBoard));
        double smallFitness = fitnessFunction.calculate(smallChromosome);
        assertTrue("Small board should have valid fitness", smallFitness >= 0);

        // Test medium board (4x4)
        char[][] mediumBoard = {
            {'B', 'B', 'W', 'W'},
            {'B', 'B', 'W', 'W'},
            {'W', 'W', 'B', 'B'},
            {'W', 'W', 'B', 'B'}
        };
        YinYangChromosome mediumChromosome = new YinYangChromosome(new YinYangBoard(mediumBoard));
        double mediumFitness = fitnessFunction.calculate(mediumChromosome);
        assertTrue("Medium board should have valid fitness", mediumFitness >= 0);
    }

    @Test
    public void testFitnessComponentWeights() {
        // Test board with high connectivity penalty
        char[][] disconnectedBoard = {
            {'B', 'W', 'B'},
            {'W', 'W', 'W'},
            {'B', 'W', 'B'}
        };
        YinYangChromosome disconnectedChromosome = new YinYangChromosome(new YinYangBoard(disconnectedBoard));
        double disconnectedFitness = fitnessFunction.calculate(disconnectedChromosome);
        
        // Test board with high crossing pattern penalty
        char[][] crossingBoard = {
            {'B', 'W', 'B', 'W'},
            {'W', 'B', 'W', 'B'},
            {'B', 'W', 'B', 'W'},
            {'W', 'B', 'W', 'B'}
        };
        YinYangChromosome crossingChromosome = new YinYangChromosome(new YinYangBoard(crossingBoard));
        double crossingFitness = fitnessFunction.calculate(crossingChromosome);
        
        // Test board with high mono color penalty
        char[][] monoBoard = {
            {'B', 'B', 'B', 'B'},
            {'B', 'B', 'B', 'B'},
            {'W', 'W', 'W', 'W'},
            {'W', 'W', 'W', 'W'}
        };
        YinYangChromosome monoChromosome = new YinYangChromosome(new YinYangBoard(monoBoard));
        double monoFitness = fitnessFunction.calculate(monoChromosome);
        
        // Verify that each penalty contributes appropriately to fitness
        assertTrue("Disconnected regions should have high fitness", disconnectedFitness > 50.0);
        assertTrue("Many crossing patterns should increase fitness", crossingFitness > 30.0);
        assertTrue("Many mono color patterns should increase fitness", monoFitness > 40.0);
    }

    @Test
    public void testEdgeCases() {
        // Test case 1: All black board
        char[][] allBlackBoard = {
            {'B', 'B', 'B'},
            {'B', 'B', 'B'},
            {'B', 'B', 'B'}
        };
        YinYangChromosome allBlackChromosome = new YinYangChromosome(new YinYangBoard(allBlackBoard));
        double allBlackFitness = fitnessFunction.calculate(allBlackChromosome);
        assertTrue("All black board should have very high fitness", allBlackFitness > 100.0);

        // Test case 2: Alternating pattern
        char[][] alternatingBoard = {
            {'B', 'W', 'B'},
            {'W', 'B', 'W'},
            {'B', 'W', 'B'}
        };
        YinYangChromosome alternatingChromosome = new YinYangChromosome(new YinYangBoard(alternatingBoard));
        double alternatingFitness = fitnessFunction.calculate(alternatingChromosome);
        assertTrue("Alternating pattern should have high fitness", alternatingFitness > 50.0);
    }

    @Test
    public void testFitnessComponentsIndividually() {
        // Test board with only connectivity issues (disconnected regions)
        char[][] disconnectedBoard = {
            {'B', 'B', 'W'},
            {'W', 'W', 'W'},
            {'B', 'B', 'W'}
        };
        YinYangChromosome disconnectedChromosome = new YinYangChromosome(new YinYangBoard(disconnectedBoard));
        double disconnectedFitness = fitnessFunction.calculate(disconnectedChromosome);
        System.out.println("Disconnected Board Fitness: " + disconnectedFitness);
        System.out.println(fitnessFunction.getFitnessDescription(disconnectedChromosome));

        // Test board with only crossing patterns
        char[][] crossingBoard = {
            {'B', 'W', 'B'},
            {'B', 'B', 'W'},
            {'W', 'W', 'W'}
        };
        YinYangChromosome crossingChromosome = new YinYangChromosome(new YinYangBoard(crossingBoard));
        double crossingFitness = fitnessFunction.calculate(crossingChromosome);
        System.out.println("\nCrossing Pattern Board Fitness: " + crossingFitness);
        System.out.println(fitnessFunction.getFitnessDescription(crossingChromosome));

        // Test board with only mono color patterns
        char[][] monoBoard = {
            {'B', 'B', 'B'},
            {'W', 'W', 'W'},
            {'B', 'W', 'B'}
        };
        YinYangChromosome monoChromosome = new YinYangChromosome(new YinYangBoard(monoBoard));
        double monoFitness = fitnessFunction.calculate(monoChromosome);
        System.out.println("\nMono Color Board Fitness: " + monoFitness);
        System.out.println(fitnessFunction.getFitnessDescription(monoChromosome));
    }

    @Test
    public void testOptimalSolutionThreshold() {
        // Test different thresholds for optimal solution
        char[][] almostOptimalBoard = {
            {'B', 'B', 'W'},
            {'B', 'W', 'W'},
            {'W', 'W', 'B'}
        };
        YinYangChromosome almostOptimalChromosome = new YinYangChromosome(new YinYangBoard(almostOptimalBoard));
        double almostOptimalFitness = fitnessFunction.calculate(almostOptimalChromosome);
        System.out.println("Almost Optimal Board Fitness: " + almostOptimalFitness);
        System.out.println(fitnessFunction.getFitnessDescription(almostOptimalChromosome));

        // Test with perfect solution board
        char[][] perfectBoard = {
            {'B', 'B', 'W'},
            {'B', 'W', 'W'},
            {'W', 'W', 'W'}
        };
        YinYangChromosome perfectChromosome = new YinYangChromosome(new YinYangBoard(perfectBoard));
        double perfectFitness = fitnessFunction.calculate(perfectChromosome);
        System.out.println("\nPerfect Board Fitness: " + perfectFitness);
        System.out.println(fitnessFunction.getFitnessDescription(perfectChromosome));
    }

    @Test
    public void testMonoColorPatterns() {
        // Test different mono color patterns
        char[][] allBlackBoard = {
            {'B', 'B', 'B'},
            {'B', 'B', 'B'},
            {'B', 'B', 'B'}
        };
        YinYangChromosome allBlackChromosome = new YinYangChromosome(new YinYangBoard(allBlackBoard));
        double allBlackFitness = fitnessFunction.calculate(allBlackChromosome);
        System.out.println("All Black Board Fitness: " + allBlackFitness);
        System.out.println(fitnessFunction.getFitnessDescription(allBlackChromosome));

        // Test with partial mono color pattern
        char[][] partialMonoBoard = {
            {'B', 'B', 'B'},
            {'B', 'B', 'W'},
            {'W', 'W', 'W'}
        };
        YinYangChromosome partialMonoChromosome = new YinYangChromosome(new YinYangBoard(partialMonoBoard));
        double partialMonoFitness = fitnessFunction.calculate(partialMonoChromosome);
        System.out.println("\nPartial Mono Color Board Fitness: " + partialMonoFitness);
        System.out.println(fitnessFunction.getFitnessDescription(partialMonoChromosome));
    }

    @Test
    public void testFitnessWeights() {
        // Test how weights affect fitness calculation
        char[][] mixedBoard = {
            {'B', 'W', 'B'},
            {'W', 'B', 'W'},
            {'B', 'W', 'B'}
        };
        YinYangChromosome mixedChromosome = new YinYangChromosome(new YinYangBoard(mixedBoard));
        double mixedFitness = fitnessFunction.calculate(mixedChromosome);
        System.out.println("Mixed Pattern Board Fitness: " + mixedFitness);
        System.out.println(fitnessFunction.getFitnessDescription(mixedChromosome));

        // Compare with a board having multiple issues
        char[][] multiIssueBoard = {
            {'B', 'B', 'B'},
            {'W', 'B', 'W'},
            {'B', 'W', 'B'}
        };
        YinYangChromosome multiIssueChromosome = new YinYangChromosome(new YinYangBoard(multiIssueBoard));
        double multiIssueFitness = fitnessFunction.calculate(multiIssueChromosome);
        System.out.println("\nMulti-Issue Board Fitness: " + multiIssueFitness);
        System.out.println(fitnessFunction.getFitnessDescription(multiIssueChromosome));
    }
}
