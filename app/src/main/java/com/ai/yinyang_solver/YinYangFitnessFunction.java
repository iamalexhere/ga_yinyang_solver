package com.ai.yinyang_solver;

public class YinYangFitnessFunction implements FitnessFunction<YinYangChromosome, Double> {
    private int width;
    private int height;

    public YinYangFitnessFunction(int width, int height) {
        this.width = width;
        this.height = height;
    }

    @Override
    public Double calculate(YinYangChromosome chromosome) {
        char[] board = chromosome.getBoard();
        int[] colorComponents = countComponentsForEachColor(board);
        int blackComponents = colorComponents[0];
        int whiteComponents = colorComponents[1];
        
        double fitness = 0;
        return fitness;
    }

    private int[] countComponentsForEachColor(char[] board) {
        boolean[] visited = new boolean[board.length];
        int blackComponents = 0;
        int whiteComponents = 0;

        for(int i = 0;i < board.length;i++) {
            if(!visited[i]) {
                if(board[i] == '1' || board[i] == '3') {
                    dfs(i, board, visited, board[i]);
                    blackComponents += 1;
                }else{
                    dfs(i, board, visited, board[i]);
                    whiteComponents += 1;
                }
            }
        }

        int[] result = new int[2];
        result[0] = blackComponents;
        result[1] = whiteComponents;

        return result;
    }

    private int checkTwoByTwo() {

    }

    private void dfs(int idx, char[] board, boolean[] visited, char target) {
        if(idx < 0 || idx >= board.length || visited[idx] || board[idx] != target) {
            return;
        }

        visited[idx] = true;

        int row = idx / width;
        int col = idx % width;

        if(row > 0) dfs(idx-width, board, visited, target);
        if(row < height - 1) dfs(idx+width, board, visited, target);
        if(col > 0) dfs(idx-1, board, visited, target);
        if(col < width - 1) dfs(idx+1, board, visited, target);
    }

    private void slidingWindow() {

    }
}
