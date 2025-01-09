package com.ai.yinyang_solver;

import java.util.List;
import java.util.Random;

/**
 * Kelas {@code YinYangSolver} mengimplementasikan algoritma genetika untuk menyelesaikan puzzle Yin Yang.
 * Puzzle ini melibatkan pengisian papan dengan simbol 'B' dan 'W' sedemikian rupa sehingga tidak ada baris atau kolom
 * yang memiliki lebih dari setengah simbol yang sama, dan tidak ada tiga simbol berurutan yang sama secara horizontal atau vertikal.
 */
public class YinYangSolver {
    // Parameter yang disesuaikan untuk meningkatkan kinerja dan stabilitas algoritma.
    private static final int POPULATION_SIZE = 600; // Ukuran populasi ditingkatkan untuk mengeksplorasi lebih banyak solusi potensial.
    private static final int MAX_GENERATIONS = 3000; // Jumlah maksimum generasi ditingkatkan untuk memberikan waktu evolusi yang lebih lama.
    private static final int MAX_STAGNANT_GENERATIONS = 50; // Jumlah maksimum generasi stagnan dikurangi untuk mempercepat restart jika tidak ada kemajuan.
    private static final double PERFECT_FITNESS = 0.1; // Ambang batas fitness sempurna dilonggarkan untuk memungkinkan konvergensi yang lebih cepat.
    private static final double MUTATION_RATE = 0.3; // Tingkat mutasi ditingkatkan untuk menambah variasi genetik dalam populasi.
    private static final int TOURNAMENT_SIZE = 5; // Ukuran turnamen ditingkatkan untuk seleksi yang lebih kompetitif.
    private static final int ELITE_COUNT = 8; // Jumlah individu elit digandakan untuk mempertahankan solusi terbaik.
    private static final int LOCAL_SEARCH_ITERATIONS = 400; // Jumlah iterasi pencarian lokal ditingkatkan untuk penyempurnaan solusi yang lebih intensif.
    private static final double CROSSOVER_RATE = 0.8; // Tingkat crossover ditambahkan untuk memungkinkan pertukaran informasi genetik antar individu.

    private final YinYangBoard initialBoard;
    private final YinYangPopulation population;
    private final YinYangFitnessFunction fitnessFunction;
    private final Random random;

    private int currentGeneration;
    private int stagnantGenerations;
    private double bestFitness;
    private YinYangChromosome bestSolution;

    /**
     * Konstruktor untuk membuat instance {@code YinYangSolver}.
     *
     * @param board Papan Yin Yang awal yang akan diselesaikan.
     */
    public YinYangSolver(char[][] board) {
        this.initialBoard = new YinYangBoard(board);
        this.fitnessFunction = new YinYangFitnessFunction();
        this.population = new YinYangPopulation(fitnessFunction);
        this.random = new Random();
        this.currentGeneration = 0;
        this.stagnantGenerations = 0;
        this.bestFitness = Double.POSITIVE_INFINITY;
        this.bestSolution = null;
    }

    /**
     * Metode utama untuk memulai dan menjalankan algoritma genetika untuk menyelesaikan puzzle Yin Yang.
     *
     * @return Papan {@code YinYangBoard} yang merupakan solusi terbaik yang ditemukan.
     */
    public YinYangBoard solve() {
        population.initializePopulation(initialBoard, POPULATION_SIZE);
        int restartCount = 0;

        while (!shouldTerminate()) {
            // Mendapatkan daftar kromosom elit dari populasi saat ini.
            List<YinYangChromosome> elite = population.getTopChromosomes(ELITE_COUNT);

            // Menerapkan evolusi pada populasi.
            population.evolve(MUTATION_RATE);
            applyCrossover(); // Menerapkan crossover untuk menghasilkan individu baru.
            population.replaceWorstWithElite(elite); // Mengganti individu terburuk dengan individu elit.

            // Mendapatkan kromosom terbaik dari populasi saat ini.
            YinYangChromosome best = population.getBestChromosome();
            localSearch(best); // Menerapkan pencarian lokal untuk meningkatkan kromosom terbaik.

            updateStats(); // Memperbarui statistik generasi dan fitness terbaik.

            // Memulai ulang populasi jika algoritma terjebak dan jumlah restart belum mencapai batas.
            if (stagnantGenerations > MAX_STAGNANT_GENERATIONS / 2 && restartCount < 3) {
                population.reinitializePopulation(initialBoard, POPULATION_SIZE);
                stagnantGenerations = 0;
                restartCount++;
            }

            currentGeneration++;
        }

        // Mengembalikan papan dari solusi terbaik yang ditemukan, atau dari kromosom terbaik jika tidak ada solusi yang ditemukan.
        return bestSolution != null ? bestSolution.getBoard() : population.getBestChromosome().getBoard();
    }

    /**
     * Menerapkan operasi crossover pada populasi untuk menghasilkan variasi genetik baru.
     * Metode ini memilih dua induk melalui seleksi turnamen dan melakukan crossover dengan probabilitas tertentu.
     */
    private void applyCrossover() {
        for (int i = 0; i < POPULATION_SIZE / 2; i++) {
            if (random.nextDouble() < CROSSOVER_RATE) {
                // Memilih dua induk melalui seleksi turnamen.
                YinYangChromosome parent1 = population.selectByTournament(TOURNAMENT_SIZE);
                YinYangChromosome parent2 = population.selectByTournament(TOURNAMENT_SIZE);
                population.crossover(parent1, parent2); // Menerapkan crossover pada induk yang dipilih.
            }
        }
    }

    /**
     * Menerapkan algoritma pencarian lokal pada kromosom tertentu untuk mencoba meningkatkan fitness-nya.
     * Pencarian lokal dilakukan dengan mencoba membalik nilai setiap sel kosong dan mempertahankan perubahan jika meningkatkan fitness.
     *
     * @param chromosome Kromosom yang akan ditingkatkan melalui pencarian lokal.
     */
    private void localSearch(YinYangChromosome chromosome) {
        YinYangBoard board = chromosome.getBoard();
        double currentFitness = fitnessFunction.calculate(chromosome);
        boolean improved;

        for (int i = 0; i < LOCAL_SEARCH_ITERATIONS; i++) {
            improved = false;

            // Mencoba semua kemungkinan perubahan secara sistematis pada sel yang awalnya kosong.
            for (int row = 0; row < board.getSize(); row++) {
                for (int col = 0; col < board.getSize(); col++) {
                    if (initialBoard.getCell(row, col) == '0') {
                        char originalValue = board.getCell(row, col);
                        board.setCell(row, col, originalValue == 'B' ? 'W' : 'B'); // Membalik nilai sel.

                        // Menghitung fitness baru setelah perubahan.
                        double newFitness = fitnessFunction.calculate(chromosome);
                        if (newFitness < currentFitness) {
                            currentFitness = newFitness;
                            improved = true;
                        } else {
                            board.setCell(row, col, originalValue); // Mengembalikan nilai jika tidak ada peningkatan.
                        }
                    }
                }
            }

            if (!improved) break; // Menghentikan pencarian lokal jika tidak ada peningkatan yang ditemukan.
        }
    }

    /**
     * Menentukan apakah algoritma harus berhenti berdasarkan kondisi terminasi.
     * Kondisi terminasi meliputi mencapai fitness sempurna, mencapai jumlah generasi maksimum, atau stagnasi evolusi.
     *
     * @return {@code true} jika algoritma harus berhenti, {@code false} jika tidak.
     */
    private boolean shouldTerminate() {
        return bestFitness <= PERFECT_FITNESS ||
               currentGeneration >= MAX_GENERATIONS ||
               stagnantGenerations >= MAX_STAGNANT_GENERATIONS;
    }

    /**
     * Memperbarui statistik evolusi seperti fitness terbaik dan jumlah generasi stagnan.
     * Jika fitness saat ini lebih baik dari fitness terbaik sebelumnya, statistik diperbarui.
     * Jika tidak ada peningkatan dalam beberapa generasi, jumlah generasi stagnan bertambah.
     */
    private void updateStats() {
        YinYangChromosome currentBest = population.getBestChromosome();
        double currentFitness = fitnessFunction.calculate(currentBest);

        if (currentFitness < bestFitness) {
            bestFitness = currentFitness;
            bestSolution = currentBest.clone();
            stagnantGenerations = 0;
        } else {
            stagnantGenerations++;
        }

        // Secara periodik melakukan reinitialisasi pada individu terburuk jika algoritma terjebak.
        if (stagnantGenerations > 20) {
            population.reinitializeWorstIndividuals(POPULATION_SIZE / 10);
        }
    }

    /**
     * Metode utama untuk menjalankan {@code YinYangSolver} sebagai aplikasi konsol.
     * Metode ini membuat papan awal, menjalankan solver, dan mencetak papan awal dan solusi.
     *
     * @param args Argumen baris perintah (tidak digunakan).
     */
    public static void main(String[] args) {
        // Contoh papan 6x6 untuk dipecahkan.
        char[][] initialBoard = {
            {'0', '0', '0', 'B', '0', 'W'},
            {'0', '0', 'B', '0', 'B', '0'},
            {'0', '0', 'W', '0', '0', '0'},
            {'B', '0', '0', '0', 'W', '0'},
            {'0', 'B', '0', 'B', '0', '0'},
            {'W', '0', '0', '0', '0', '0'}
        };

        YinYangSolver solver = new YinYangSolver(initialBoard);
        YinYangBoard solution = solver.solve();

        System.out.println("\nPapan Awal:");
        System.out.println(new YinYangBoard(initialBoard).toString());
        System.out.println("\nSolusi Akhir:");
        System.out.println(solution.toString());
    }
}
