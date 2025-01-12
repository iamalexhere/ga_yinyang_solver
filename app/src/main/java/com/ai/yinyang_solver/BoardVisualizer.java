package com.ai.yinyang_solver;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class BoardVisualizer {
    private static final int CELL_SIZE = 50;
    private static final int PADDING = 10;
    private static final Color BACKGROUND_COLOR = Color.WHITE;
    private static final Color GRID_COLOR = Color.BLACK;
    private static final Color BLACK_PIECE = Color.BLACK;
    private static final Color WHITE_PIECE = Color.WHITE;
    
    public static void saveBoard(YinYangBoard board, String outputDir, String prefix) {
        int width = board.getSize() * CELL_SIZE + 2 * PADDING;
        int height = board.getSize() * CELL_SIZE + 2 * PADDING;
        
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2d = image.createGraphics();
        
        // Set rendering hints for better quality
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        
        // Fill background
        g2d.setColor(BACKGROUND_COLOR);
        g2d.fillRect(0, 0, width, height);
        
        // Draw grid and pieces
        for (int i = 0; i < board.getSize(); i++) {
            for (int j = 0; j < board.getSize(); j++) {
                int x = PADDING + j * CELL_SIZE;
                int y = PADDING + i * CELL_SIZE;
                
                // Draw cell border
                g2d.setColor(GRID_COLOR);
                g2d.drawRect(x, y, CELL_SIZE, CELL_SIZE);
                
                // Draw piece if present
                char piece = board.getCell(i, j);
                if (piece != '0') {
                    g2d.setColor(piece == 'B' ? BLACK_PIECE : WHITE_PIECE);
                    g2d.fillOval(x + 5, y + 5, CELL_SIZE - 10, CELL_SIZE - 10);
                    
                    // Draw border for white pieces to distinguish them
                    if (piece == 'W') {
                        g2d.setColor(GRID_COLOR);
                        g2d.drawOval(x + 5, y + 5, CELL_SIZE - 10, CELL_SIZE - 10);
                    }
                }
            }
        }
        
        g2d.dispose();
        
        // Create output directory if it doesn't exist
        File outputDirFile = new File(outputDir);
        if (!outputDirFile.exists()) {
            outputDirFile.mkdirs();
        }
        
        // Generate unique filename with timestamp
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
        String filename = String.format("%s/%s_%s.png", outputDir, prefix, timestamp);
        
        try {
            ImageIO.write(image, "png", new File(filename));
            System.out.println("Board image saved as: " + filename);
        } catch (IOException e) {
            System.err.println("Error saving board image: " + e.getMessage());
        }
    }
}
