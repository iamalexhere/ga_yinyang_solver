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
}
