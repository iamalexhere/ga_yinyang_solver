package com.ai.yinyang_solver;

import org.junit.Test;
import static org.junit.Assert.*;

public class YinYangSolverTest {

    @Test
    public void testSolve() {
        char[][] initialBoardConfig = {
            {'0', '0', '1', '0', '0', '0'},
            {'0', '0', '0', '2', '0', '0'},
            {'0', '1', '0', '2', '0', '0'},
            {'1', '0', '0', '0', '0', '2'},
            {'0', '1', '0', '1', '2', '0'},
            {'0', '0', '0', '0', '0', '0'}
        };
        YinYangBoard initialBoard = new YinYangBoard(initialBoardConfig);
        int populationSize = 100;
        int maxIterations = 100;
        double mutationRate = 0.1;
        int eliteCount = 10;
        int reinitializeCount = 10;
        int tournamentSize = 5;

        YinYangSolver solver = new YinYangSolver(initialBoard, populationSize, maxIterations, mutationRate, eliteCount, reinitializeCount, tournamentSize);
        YinYangChromosome solution = solver.solve();

        assertNotNull(solution);
        assertNotNull(solution.getBoard());
    }

    @Test
    public void testSolve_board1() {
        char[][] initialBoardConfig = {
            {'0', '0', '0', '0', '0', '0'},
            {'0', '0', 'B', '0', '0', '0'},
            {'0', 'W', '0', 'B', 'W', '0'},
            {'0', 'W', 'W', 'W', '0', '0'},
            {'0', 'W', '0', '0', 'W', '0'},
            {'0', '0', 'B', '0', '0', 'B'},
        };
        YinYangBoard initialBoard = new YinYangBoard(initialBoardConfig);
        int populationSize = 100;
        int maxIterations = 100;
        double mutationRate = 0.1;
        int eliteCount = 10;
        int reinitializeCount = 10;
        int tournamentSize = 5;

        YinYangSolver solver = new YinYangSolver(initialBoard, populationSize, maxIterations, mutationRate, eliteCount, reinitializeCount, tournamentSize);
        YinYangChromosome solution = solver.solve();

        assertNotNull(solution);
        assertNotNull(solution.getBoard());
    }

    @Test
    public void testSolve_board2() {
        char[][] initialBoardConfig = {
            {'0', '0', '0', '0', '0', '0'},
            {'0', '0', 'W', 'B', 'B', '0'},
            {'0', 'B', '0', '0', 'B', '0'},
            {'0', '0', 'B', '0', '0', '0'},
            {'0', '0', 'W', 'B', '0', 'B'},
            {'0', '0', '0', '0', '0', 'W'},
        };
        YinYangBoard initialBoard = new YinYangBoard(initialBoardConfig);
        int populationSize = 100;
        int maxIterations = 100;
        double mutationRate = 0.1;
        int eliteCount = 10;
        int reinitializeCount = 10;
        int tournamentSize = 5;

        YinYangSolver solver = new YinYangSolver(initialBoard, populationSize, maxIterations, mutationRate, eliteCount, reinitializeCount, tournamentSize);
        YinYangChromosome solution = solver.solve();

        assertNotNull(solution);
        assertNotNull(solution.getBoard());
    }

    @Test
    public void testSolve_board3() {
        char[][] initialBoardConfig = {
            {'0', '0', '0', '0', '0', 'W'},
            {'0', '0', 'B', '0', '0', 'B'},
            {'0', '0', '0', '0', 'B', '0'},
            {'0', 'W', '0', 'B', '0', '0'},
            {'0', '0', 'B', '0', 'W', '0'},
            {'0', '0', '0', '0', '0', 'W'},
        };
        YinYangBoard initialBoard = new YinYangBoard(initialBoardConfig);
        int populationSize = 100;
        int maxIterations = 100;
        double mutationRate = 0.1;
        int eliteCount = 10;
        int reinitializeCount = 10;
        int tournamentSize = 5;

        YinYangSolver solver = new YinYangSolver(initialBoard, populationSize, maxIterations, mutationRate, eliteCount, reinitializeCount, tournamentSize);
        YinYangChromosome solution = solver.solve();

        assertNotNull(solution);
        assertNotNull(solution.getBoard());
    }

    @Test
    public void testSolve_board4() {
        char[][] initialBoardConfig = {
            {'0', '0', '0', '0', '0', '0'},
            {'0', '0', 'B', 'B', 'B', 'W'},
            {'0', 'B', '0', 'W', '0', '0'},
            {'B', '0', 'W', 'B', 'W', '0'},
            {'0', '0', '0', 'B', '0', '0'},
            {'0', '0', '0', '0', '0', '0'},
        };
        YinYangBoard initialBoard = new YinYangBoard(initialBoardConfig);
        int populationSize = 100;
        int maxIterations = 100;
        double mutationRate = 0.1;
        int eliteCount = 10;
        int reinitializeCount = 10;
        int tournamentSize = 5;

        YinYangSolver solver = new YinYangSolver(initialBoard, populationSize, maxIterations, mutationRate, eliteCount, reinitializeCount, tournamentSize);
        YinYangChromosome solution = solver.solve();

        assertNotNull(solution);
        assertNotNull(solution.getBoard());
    }

    @Test
    public void testSolve_board5() {
        char[][] initialBoardConfig = {
            {'0', 'W', '0', '0', '0', '0'},
            {'0', 'B', 'W', 'W', '0', 'B'},
            {'0', '0', 'B', 'B', 'B', '0'},
            {'0', 'B', '0', '0', 'B', '0'},
            {'0', '0', '0', 'W', '0', '0'},
            {'0', '0', '0', '0', '0', '0'},
        };
        YinYangBoard initialBoard = new YinYangBoard(initialBoardConfig);
        int populationSize = 100;
        int maxIterations = 100;
        double mutationRate = 0.1;
        int eliteCount = 10;
        int reinitializeCount = 10;
        int tournamentSize = 5;

        YinYangSolver solver = new YinYangSolver(initialBoard, populationSize, maxIterations, mutationRate, eliteCount, reinitializeCount, tournamentSize);
        YinYangChromosome solution = solver.solve();

        assertNotNull(solution);
        assertNotNull(solution.getBoard());
    }

    @Test
    public void testSolve_board6() {
        char[][] initialBoardConfig = {
            {'0', '0', '0', '0', '0', '0'},
            {'0', '0', '0', 'W', '0', '0'},
            {'0', '0', 'B', 'W', 'B', '0'},
            {'0', '0', '0', 'B', '0', 'B'},
            {'W', 'W', 'W', 'W', '0', '0'},
            {'B', '0', '0', '0', '0', '0'},
        };
        YinYangBoard initialBoard = new YinYangBoard(initialBoardConfig);
        int populationSize = 100;
        int maxIterations = 100;
        double mutationRate = 0.1;
        int eliteCount = 10;
        int reinitializeCount = 10;
        int tournamentSize = 5;

        YinYangSolver solver = new YinYangSolver(initialBoard, populationSize, maxIterations, mutationRate, eliteCount, reinitializeCount, tournamentSize);
        YinYangChromosome solution = solver.solve();

        assertNotNull(solution);
        assertNotNull(solution.getBoard());
    }

    @Test
    public void testSolve_board7() {
        char[][] initialBoardConfig = {
            {'0', '0', '0', '0', '0', '0'},
            {'0', '0', '0', '0', '0', 'W'},
            {'0', '0', 'B', '0', '0', '0'},
            {'W', '0', 'W', '0', 'B', '0'},
            {'0', 'W', 'W', 'W', '0', 'B'},
            {'B', '0', '0', '0', '0', '0'},
        };
        YinYangBoard initialBoard = new YinYangBoard(initialBoardConfig);
        int populationSize = 100;
        int maxIterations = 100;
        double mutationRate = 0.1;
        int eliteCount = 10;
        int reinitializeCount = 10;
        int tournamentSize = 5;

        YinYangSolver solver = new YinYangSolver(initialBoard, populationSize, maxIterations, mutationRate, eliteCount, reinitializeCount, tournamentSize);
        YinYangChromosome solution = solver.solve();

        assertNotNull(solution);
        assertNotNull(solution.getBoard());
    }

    @Test
    public void testSolve_board8() {
        char[][] initialBoardConfig = {
            {'0', '0', 'W', '0', '0', '0'},
            {'0', '0', '0', 'W', '0', '0'},
            {'0', '0', '0', 'B', 'B', '0'},
            {'0', '0', 'B', 'W', '0', '0'},
            {'0', '0', '0', '0', 'W', '0'},
            {'0', '0', '0', '0', 'W', 'B'},
        };
        YinYangBoard initialBoard = new YinYangBoard(initialBoardConfig);
        int populationSize = 100;
        int maxIterations = 100;
        double mutationRate = 0.1;
        int eliteCount = 10;
        int reinitializeCount = 10;
        int tournamentSize = 5;

        YinYangSolver solver = new YinYangSolver(initialBoard, populationSize, maxIterations, mutationRate, eliteCount, reinitializeCount, tournamentSize);
        YinYangChromosome solution = solver.solve();

        assertNotNull(solution);
        assertNotNull(solution.getBoard());
    }

    @Test
    public void testSolve_board9() {
        char[][] initialBoardConfig = {
            {'0', '0', '0', 'B', '0', 'B'},
            {'0', '0', '0', 'W', 'B', '0'},
            {'0', '0', 'W', '0', 'W', '0'},
            {'0', 'W', '0', '0', 'W', '0'},
            {'0', '0', 'B', '0', '0', '0'},
            {'0', '0', '0', '0', '0', '0'},
        };
        YinYangBoard initialBoard = new YinYangBoard(initialBoardConfig);
        int populationSize = 100;
        int maxIterations = 100;
        double mutationRate = 0.1;
        int eliteCount = 10;
        int reinitializeCount = 10;
        int tournamentSize = 5;

        YinYangSolver solver = new YinYangSolver(initialBoard, populationSize, maxIterations, mutationRate, eliteCount, reinitializeCount, tournamentSize);
        YinYangChromosome solution = solver.solve();

        assertNotNull(solution);
        assertNotNull(solution.getBoard());
    }

    @Test
    public void testSolve_board10() {
        char[][] initialBoardConfig = {
            {'W', '0', '0', '0', '0', 'B'},
            {'0', '0', 'B', '0', 'W', 'W'},
            {'0', '0', '0', 'B', '0', '0'},
            {'0', '0', 'B', '0', 'W', '0'},
            {'0', '0', 'B', 'B', '0', '0'},
            {'0', '0', '0', '0', '0', '0'},
        };
        YinYangBoard initialBoard = new YinYangBoard(initialBoardConfig);
        int populationSize = 100;
        int maxIterations = 100;
        double mutationRate = 0.1;
        int eliteCount = 10;
        int reinitializeCount = 10;
        int tournamentSize = 5;

        YinYangSolver solver = new YinYangSolver(initialBoard, populationSize, maxIterations, mutationRate, eliteCount, reinitializeCount, tournamentSize);
        YinYangChromosome solution = solver.solve();

        assertNotNull(solution);
        assertNotNull(solution.getBoard());
    }

    @Test
    public void testSolve_board11() {
        char[][] initialBoardConfig = {
            {'0', '0', '0', '0', '0', 'B'},
            {'0', '0', 'B', '0', '0', '0'},
            {'0', 'W', 'B', '0', 'W', '0'},
            {'0', 'W', '0', 'B', '0', '0'},
            {'0', '0', 'W', 'W', 'W', 'W'},
            {'0', '0', '0', '0', '0', 'B'},
        };
        YinYangBoard initialBoard = new YinYangBoard(initialBoardConfig);
        int populationSize = 100;
        int maxIterations = 100;
        double mutationRate = 0.1;
        int eliteCount = 10;
        int reinitializeCount = 10;
        int tournamentSize = 5;

        YinYangSolver solver = new YinYangSolver(initialBoard, populationSize, maxIterations, mutationRate, eliteCount, reinitializeCount, tournamentSize);
        YinYangChromosome solution = solver.solve();

        assertNotNull(solution);
        assertNotNull(solution.getBoard());
    }

    @Test
    public void testSolve_board12() {
        char[][] initialBoardConfig = {
            {'0', '0', 'B', 'W', '0', 'B'},
            {'0', '0', '0', '0', '0', '0'},
            {'0', 'B', '0', '0', '0', '0'},
            {'0', '0', '0', '0', 'W', '0'},
            {'0', '0', '0', '0', '0', '0'},
            {'0', '0', '0', '0', '0', '0'},
        };
        YinYangBoard initialBoard = new YinYangBoard(initialBoardConfig);
        int populationSize = 100;
        int maxIterations = 100;
        double mutationRate = 0.1;
        int eliteCount = 10;
        int reinitializeCount = 10;
        int tournamentSize = 5;

        YinYangSolver solver = new YinYangSolver(initialBoard, populationSize, maxIterations, mutationRate, eliteCount, reinitializeCount, tournamentSize);
        YinYangChromosome solution = solver.solve();

        assertNotNull(solution);
        assertNotNull(solution.getBoard());
    }

    @Test
    public void testSolve_board13() {
         char[][] initialBoardConfig = {
            {'0', '0', '0', '0', '0', '0', '0', '0', '0', '0'},
            {'0', '0', '0', '0', '0', '0', '0', '0', '0', '0'},
            {'0', 'B', '0', 'W', 'W', 'W', '0', 'W', 'W', '0'},
            {'0', 'B', '0', '0', '0', '0', 'W', '0', '0', '0'},
            {'0', 'W', 'W', 'W', 'W', 'W', 'W', 'B', '0', '0'},
            {'0', '0', '0', 'B', 'B', '0', 'B', '0', '0', '0'},
            {'0', 'B', '0', '0', 'W', '0', '0', 'B', '0', '0'},
            {'0', '0', 'W', '0', '0', '0', 'B', '0', '0', '0'},
            {'0', '0', '0', '0', 'B', '0', '0', '0', 'B', '0'},
            {'0', '0', '0', '0', '0', '0', '0', '0', 'B', 'W'},
        };
        YinYangBoard initialBoard = new YinYangBoard(initialBoardConfig);
        int populationSize = 100;
        int maxIterations = 100;
        double mutationRate = 0.1;
        int eliteCount = 10;
        int reinitializeCount = 10;
        int tournamentSize = 5;

        YinYangSolver solver = new YinYangSolver(initialBoard, populationSize, maxIterations, mutationRate, eliteCount, reinitializeCount, tournamentSize);
        YinYangChromosome solution = solver.solve();

        assertNotNull(solution);
        assertNotNull(solution.getBoard());
    }

    @Test
    public void testSolve_board14() {
        char[][] initialBoardConfig = {
            {'0', 'B', '0', '0', '0', '0', '0', '0', '0', '0'},
            {'0', '0', '0', 'B', 'B', '0', '0', '0', '0', '0'},
            {'0', '0', 'B', '0', 'W', 'W', 'W', 'W', 'W', '0'},
            {'0', '0', '0', '0', '0', '0', '0', '0', '0', '0'},
            {'0', '0', '0', 'W', '0', 'W', '0', 'W', '0', '0'},
            {'0', 'W', '0', 'W', '0', '0', 'W', '0', '0', '0'},
            {'0', 'W', '0', 'W', '0', 'B', '0', 'W', '0', '0'},
            {'0', '0', '0', 'W', 'B', '0', '0', '0', 'W', '0'},
            {'W', '0', '0', 'W', '0', 'B', '0', '0', '0', '0'},
            {'0', '0', '0', '0', '0', '0', '0', '0', '0', '0'},
        };
        YinYangBoard initialBoard = new YinYangBoard(initialBoardConfig);
        int populationSize = 100;
        int maxIterations = 100;
        double mutationRate = 0.1;
        int eliteCount = 10;
        int reinitializeCount = 10;
        int tournamentSize = 5;

        YinYangSolver solver = new YinYangSolver(initialBoard, populationSize, maxIterations, mutationRate, eliteCount, reinitializeCount, tournamentSize);
        YinYangChromosome solution = solver.solve();

        assertNotNull(solution);
        assertNotNull(solution.getBoard());
    }

    @Test
    public void testSolve_board15() {
        char[][] initialBoardConfig = {
            {'0', '0', '0', 'B', '0', '0'},
            {'0', 'B', '0', '0', '0', '0'},
            {'0', 'B', '0', 'B', 'B', '0'},
            {'0', 'B', '0', '0', '0', '0'},
            {'0', '0', 'B', '0', '0', '0'},
            {'0', '0', '0', '0', 'B', '0'},
        };
        YinYangBoard initialBoard = new YinYangBoard(initialBoardConfig);
        int populationSize = 100;
        int maxIterations = 100;
        double mutationRate = 0.1;
        int eliteCount = 10;
        int reinitializeCount = 10;
        int tournamentSize = 5;

        YinYangSolver solver = new YinYangSolver(initialBoard, populationSize, maxIterations, mutationRate, eliteCount, reinitializeCount, tournamentSize);
        YinYangChromosome solution = solver.solve();

        assertNotNull(solution);
        assertNotNull(solution.getBoard());
    }

    @Test
    public void testSolve_board16() {
        char[][] initialBoardConfig = {
            {'B', '0', 'W', '0', '0', '0', '0', '0', '0', '0'},
            {'0', '0', '0', '0', 'B', 'B', 'B', '0', '0', '0'},
            {'0', '0', 'B', '0', '0', '0', 'B', '0', 'W', '0'},
            {'0', '0', 'B', '0', '0', 'B', '0', '0', '0', '0'},
            {'0', '0', 'B', '0', 'B', '0', '0', '0', 'W', '0'},
            {'0', '0', 'B', '0', '0', 'B', '0', 'B', '0', '0'},
            {'0', 'B', '0', 'B', '0', '0', 'B', '0', 'B', '0'},
            {'B', '0', '0', '0', '0', '0', '0', '0', '0', '0'},
            {'0', 'B', 'B', 'B', 'B', 'B', 'B', 'B', '0', '0'},
            {'W', '0', '0', '0', '0', '0', '0', '0', '0', '0'},
        };
        YinYangBoard initialBoard = new YinYangBoard(initialBoardConfig);
        int populationSize = 100;
        int maxIterations = 100;
        double mutationRate = 0.1;
        int eliteCount = 10;
        int reinitializeCount = 10;
        int tournamentSize = 5;

        YinYangSolver solver = new YinYangSolver(initialBoard, populationSize, maxIterations, mutationRate, eliteCount, reinitializeCount, tournamentSize);
        YinYangChromosome solution = solver.solve();

        assertNotNull(solution);
        assertNotNull(solution.getBoard());
    }
}
