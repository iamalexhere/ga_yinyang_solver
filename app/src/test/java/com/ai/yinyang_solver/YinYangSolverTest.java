// package com.ai.yinyang_solver;

// import static org.junit.Assert.*;
// import org.junit.Before;
// import org.junit.Test;

// public class YinYangSolverTest {
//     private YinYangFitnessFunction fitnessFunction;
    
//     @Before
//     public void setUp() {
//         fitnessFunction = new YinYangFitnessFunction();
//     }
    
//     @Test
//     public void testSolveSimplePuzzle() {
//         // Simple 3x3 puzzle with known solution pattern
//         char[][] board = {
//             {'B', '0', 'W'},
//             {'0', '0', '0'},
//             {'W', '0', 'B'}
//         };
        
//         YinYangSolver solver = new YinYangSolver(board);
//         YinYangBoard solution = solver.solve();
        
//         // Verify solution properties
//         assertTrue("All regions should be connected", solution.isAllRegionsConnected());
//         assertEquals("Should not have crossing patterns", 0, solution.slidingWindowForColorCross());
//         assertEquals("Should not have mono-color patterns", 0, solution.slidingWindowForMonoColor());
        
//         // Verify fixed cells remain unchanged
//         assertEquals("Fixed cell (0,0) should remain 'B'", 'B', solution.getCell(0, 0));
//         assertEquals("Fixed cell (0,2) should remain 'W'", 'W', solution.getCell(0, 2));
//         assertEquals("Fixed cell (2,0) should remain 'W'", 'W', solution.getCell(2, 0));
//         assertEquals("Fixed cell (2,2) should remain 'B'", 'B', solution.getCell(2, 2));
        
//         // Check fitness score
//         double fitness = fitnessFunction.calculate(new YinYangChromosome(solution));
//         assertTrue("Fitness should be close to optimal (<=0.1)", fitness <= 0.1);
//     }
    
//     @Test
//     public void testSolveMediumPuzzle() {
//         // Medium 4x4 puzzle with diagonal pattern
//         char[][] board = {
//             {'B', '0', '0', 'W'},
//             {'0', 'B', 'W', '0'},
//             {'0', 'W', 'B', '0'},
//             {'W', '0', '0', 'B'}
//         };
        
//         YinYangSolver solver = new YinYangSolver(board);
//         YinYangBoard solution = solver.solve();
        
//         // Verify solution properties
//         assertTrue("All regions should be connected", solution.isAllRegionsConnected());
//         assertEquals("Should not have crossing patterns", 0, solution.slidingWindowForColorCross());
//         assertEquals("Should not have mono-color patterns", 0, solution.slidingWindowForMonoColor());
        
//         // Verify fixed cells
//         assertEquals("Fixed cell (0,0) should remain 'B'", 'B', solution.getCell(0, 0));
//         assertEquals("Fixed cell (0,3) should remain 'W'", 'W', solution.getCell(0, 3));
//         assertEquals("Fixed cell (3,0) should remain 'W'", 'W', solution.getCell(3, 0));
//         assertEquals("Fixed cell (3,3) should remain 'B'", 'B', solution.getCell(3, 3));
        
//         // Check fitness score
//         double fitness = fitnessFunction.calculate(new YinYangChromosome(solution));
//         assertTrue("Fitness should be close to optimal (<=0.1)", fitness <= 0.1);
//     }
    
//     @Test
//     public void testSolveEmptyPuzzle() {
//         // Empty 3x3 puzzle (only corners fixed)
//         char[][] board = {
//             {'B', '0', 'W'},
//             {'0', '0', '0'},
//             {'W', '0', 'B'}
//         };
        
//         YinYangSolver solver = new YinYangSolver(board);
//         YinYangBoard solution = solver.solve();
        
//         // Verify basic solution properties
//         assertTrue("All regions should be connected", solution.isAllRegionsConnected());
//         assertEquals("Should not have crossing patterns", 0, solution.slidingWindowForColorCross());
//         assertEquals("Should not have mono-color patterns", 0, solution.slidingWindowForMonoColor());
        
//         // Check fitness score
//         double fitness = fitnessFunction.calculate(new YinYangChromosome(solution));
//         assertTrue("Fitness should be close to optimal (<=0.1)", fitness <= 0.1);
//     }
    
//     @Test
//     public void testSolveComplexPuzzle() {
//         // Complex 5x5 puzzle with more fixed cells
//         char[][] board = {
//             {'B', '0', 'W', '0', 'B'},
//             {'0', 'B', '0', 'W', '0'},
//             {'W', '0', '0', '0', 'W'},
//             {'0', 'W', '0', 'B', '0'},
//             {'B', '0', 'W', '0', 'B'}
//         };
        
//         YinYangSolver solver = new YinYangSolver(board);
//         YinYangBoard solution = solver.solve();
        
//         // Verify solution properties
//         assertTrue("All regions should be connected", solution.isAllRegionsConnected());
//         assertEquals("Should not have crossing patterns", 0, solution.slidingWindowForColorCross());
//         assertEquals("Should not have mono-color patterns", 0, solution.slidingWindowForMonoColor());
        
//         // Check fitness score
//         double fitness = fitnessFunction.calculate(new YinYangChromosome(solution));
//         assertTrue("Fitness should be close to optimal (<=0.1)", fitness <= 0.1);
        
//         // Verify some key fixed cells
//         assertEquals("Fixed cell (0,0) should remain 'B'", 'B', solution.getCell(0, 0));
//         assertEquals("Fixed cell (0,2) should remain 'W'", 'W', solution.getCell(0, 2));
//         assertEquals("Fixed cell (2,0) should remain 'W'", 'W', solution.getCell(2, 0));
//         assertEquals("Fixed cell (4,4) should remain 'B'", 'B', solution.getCell(4, 4));
//     }
    
//     @Test
//     public void testSolveWithConstraints() {
//         // Test puzzle with specific constraints
//         char[][] board = {
//             {'B', 'W', 'B'},
//             {'0', '0', '0'},
//             {'W', 'B', 'W'}
//         };
        
//         YinYangSolver solver = new YinYangSolver(board);
//         YinYangBoard solution = solver.solve();
        
//         // Verify all fixed cells remain unchanged
//         assertEquals("Fixed cell (0,0) should remain 'B'", 'B', solution.getCell(0, 0));
//         assertEquals("Fixed cell (0,1) should remain 'W'", 'W', solution.getCell(0, 1));
//         assertEquals("Fixed cell (0,2) should remain 'B'", 'B', solution.getCell(0, 2));
//         assertEquals("Fixed cell (2,0) should remain 'W'", 'W', solution.getCell(2, 0));
//         assertEquals("Fixed cell (2,1) should remain 'B'", 'B', solution.getCell(2, 1));
//         assertEquals("Fixed cell (2,2) should remain 'W'", 'W', solution.getCell(2, 2));
        
//         // Verify solution properties
//         assertTrue("All regions should be connected", solution.isAllRegionsConnected());
//         assertEquals("Should not have crossing patterns", 0, solution.slidingWindowForColorCross());
//         assertEquals("Should not have mono-color patterns", 0, solution.slidingWindowForMonoColor());
        
//         // Check fitness score
//         double fitness = fitnessFunction.calculate(new YinYangChromosome(solution));
//         assertTrue("Fitness should be close to optimal (<=0.1)", fitness <= 0.1);
//     }
// }
