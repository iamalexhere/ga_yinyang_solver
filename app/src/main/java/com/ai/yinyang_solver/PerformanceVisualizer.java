package com.ai.yinyang_solver;

import org.knowm.xchart.*;
import org.knowm.xchart.style.Styler;
import java.awt.Color;
import java.io.IOException;
import java.util.*;

public class PerformanceVisualizer {
    private static final Color[] COLORS = {
        Color.BLUE, Color.RED, Color.GREEN, Color.ORANGE, Color.MAGENTA,
        Color.CYAN, Color.PINK, Color.YELLOW, Color.GRAY, Color.DARK_GRAY
    };

    private final String outputDir;
    private Map<Integer, List<Double>> populationSizeHistories = new HashMap<>();
    private Map<Double, List<Double>> mutationRateHistories = new HashMap<>();
    private Map<Double, List<Double>> crossoverRateHistories = new HashMap<>();

    public PerformanceVisualizer(String outputDir) {
        this.outputDir = outputDir;
    }

    public void addPopulationSizeHistory(int popSize, List<Double> history) {
        populationSizeHistories.put(popSize, new ArrayList<>(history));
    }

    public void addMutationRateHistory(double rate, List<Double> history) {
        mutationRateHistories.put(rate, new ArrayList<>(history));
    }

    public void addCrossoverRateHistory(double rate, List<Double> history) {
        crossoverRateHistories.put(rate, new ArrayList<>(history));
    }

    public void plotPopulationSizeComparison() {
        try {
            XYChart chart = createBaseChart("Population Size Comparison", "Generation", "Fitness");
            int colorIndex = 0;
            
            for (Map.Entry<Integer, List<Double>> entry : populationSizeHistories.entrySet()) {
                addSeriesToChart(chart, "Population: " + entry.getKey(), 
                               entry.getValue(), COLORS[colorIndex++ % COLORS.length]);
            }

            saveChart(chart, "population_size_comparison");
        } catch (IOException e) {
            System.err.println("Error saving population size comparison plot: " + e.getMessage());
        }
    }

    public void plotMutationRateComparison() {
        try {
            XYChart chart = createBaseChart("Mutation Rate Comparison", "Generation", "Fitness");
            int colorIndex = 0;
            
            for (Map.Entry<Double, List<Double>> entry : mutationRateHistories.entrySet()) {
                addSeriesToChart(chart, String.format("Mutation Rate: %.2f", entry.getKey()), 
                               entry.getValue(), COLORS[colorIndex++ % COLORS.length]);
            }

            saveChart(chart, "mutation_rate_comparison");
        } catch (IOException e) {
            System.err.println("Error saving mutation rate comparison plot: " + e.getMessage());
        }
    }

    public void plotCrossoverRateComparison() {
        try {
            XYChart chart = createBaseChart("Crossover Rate Comparison", "Generation", "Fitness");
            int colorIndex = 0;
            
            for (Map.Entry<Double, List<Double>> entry : crossoverRateHistories.entrySet()) {
                addSeriesToChart(chart, String.format("Crossover Rate: %.2f", entry.getKey()), 
                               entry.getValue(), COLORS[colorIndex++ % COLORS.length]);
            }

            saveChart(chart, "crossover_rate_comparison");
        } catch (IOException e) {
            System.err.println("Error saving crossover rate comparison plot: " + e.getMessage());
        }
    }

    private XYChart createBaseChart(String title, String xTitle, String yTitle) {
        XYChart chart = new XYChartBuilder()
            .width(800)
            .height(600)
            .title(title)
            .xAxisTitle(xTitle)
            .yAxisTitle(yTitle)
            .build();

        chart.getStyler().setLegendPosition(Styler.LegendPosition.InsideNE);
        chart.getStyler().setDefaultSeriesRenderStyle(XYSeries.XYSeriesRenderStyle.Line);
        chart.getStyler().setPlotMargin(0);
        chart.getStyler().setPlotGridLinesVisible(true);

        return chart;
    }

    private void addSeriesToChart(XYChart chart, String seriesName, List<Double> history, Color color) {
        double[] generations = new double[history.size()];
        double[] fitness = new double[history.size()];
        
        for (int i = 0; i < history.size(); i++) {
            generations[i] = i;
            fitness[i] = history.get(i);
        }

        chart.addSeries(seriesName, generations, fitness).setLineColor(color);
    }

    private void saveChart(XYChart chart, String filename) throws IOException {
        BitmapEncoder.saveBitmap(chart, outputDir + "/" + filename, BitmapEncoder.BitmapFormat.PNG);
    }
}
