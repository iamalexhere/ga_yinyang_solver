package com.ai.yinyang_solver;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Input {
    public static void main(String[] args) throws IOException {
        try (BufferedReader in = new BufferedReader(new FileReader("D:\\Code\\AI\\Reasonable\\ga_yinyang_solver\\app\\src\\main\\java\\com\\ai\\yinyang_solver\\Input.txt"))) {
            int boardSize = in.read();
            char[] board = new char[boardSize * boardSize];
            int input;
            int i = 0;
            while ((input = in.read()) != -1) {

                board[i] = (char)input;
                System.out.print(board[i]);
                i++;
            }
            in.close();
        }
    }
}
