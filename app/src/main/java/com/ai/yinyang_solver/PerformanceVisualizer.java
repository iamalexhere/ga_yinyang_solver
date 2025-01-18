package com.ai.yinyang_solver;

import org.knowm.xchart.*;
import org.knowm.xchart.style.Styler;
import org.knowm.xchart.style.markers.Circle;
import org.knowm.xchart.style.lines.SeriesLines;
import java.awt.Color;
import java.io.IOException;
import java.util.*;

public class PerformanceVisualizer {
    private static final Color[] COLORS = {
        new Color(0, 102, 204),    // Deep Blue
        new Color(204, 37, 41),    // Red
        new Color(62, 150, 81),    // Forest Green
        new Color(107, 76, 154),   // Purple
        new Color(218, 124, 48),   // Orange
        new Color(146, 36, 40),    // Dark Red
        new Color(49, 130, 189),   // Light Blue
        new Color(140, 86, 75),    // Brown
        new Color(227, 119, 194),  // Pink
        new Color(127, 127, 127)   // Gray
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
            .width(1200)    // Increased width
            .height(800)    // Increased height
            .title(title)
            .xAxisTitle(xTitle)
            .yAxisTitle(yTitle)
            .build();

        // Customize chart styling
        chart.getStyler()
            .setLegendPosition(Styler.LegendPosition.OutsideE)
            .setLegendBorderColor(Color.LIGHT_GRAY)
            .setPlotBackgroundColor(Color.WHITE)
            .setChartBackgroundColor(Color.WHITE)
            .setLegendVisible(true);

        // Set axis ranges to start from 0
        chart.getStyler().setYAxisMin(0.0);
        chart.getStyler().setXAxisMin(0.0);

        return chart;
    }

    private void addSeriesToChart(XYChart chart, String seriesName, List<Double> history, Color color) {
        double[] generations = new double[history.size()];
        double[] fitness = new double[history.size()];
        
        for (int i = 0; i < history.size(); i++) {
            generations[i] = i;
            fitness[i] = history.get(i);
        }

        XYSeries series = chart.addSeries(seriesName, generations, fitness);
        series.setLineColor(color);
        series.setLineStyle(SeriesLines.SOLID);
        series.setMarker(new Circle());
        series.setLineWidth(2.5f);
    }

    private void saveChart(XYChart chart, String filename) throws IOException {
        BitmapEncoder.saveBitmap(chart, outputDir + "/" + filename, BitmapEncoder.BitmapFormat.PNG);
    }
}
