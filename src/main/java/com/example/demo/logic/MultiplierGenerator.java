package com.example.demo.logic;

public class MultiplierGenerator {
    public static int[][] initializeMultiplier(int[][] grid, boolean freeSpin) {

        int [][] multiplier = new int[3][5];

        // Process all WC symbols
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 5; j++) {
                if (MathData.character[grid[i][j]].equals("WC")) {

                    // Vertical
                    for (int k = 0; k < 3; k++) {
                        if (k != i && !MathData.character[grid[k][j]].equals("WC")) {
                            multiplier[k][j] += 2;
                        }
                    }

                    // Horizontal
                    for (int k = 0; k < 5; k++) {
                        if (k != j && !MathData.character[grid[i][k]].equals("WC")) {
                            multiplier[i][k] += 2;
                        }
                    }

                    // FREE SPIN LOGIC
                    
                    if (freeSpin) {
                        
                        // Top-left to bottom-right
                        int x = i - 1, y = j - 1;
                        while (x >= 0 && y >= 0) {
                            if (!MathData.character[grid[x][y]].equals("WC") && !MathData.character[grid[x][y]].equals("")) {
                                multiplier[x][y] += 2;
                            }
                            x--;
                            y--;
                        }

                        x = i + 1;
                        y = j + 1;
                        while (x < 3 && y < 5) {
                            if (!MathData.character[grid[x][y]].equals("WC") && !MathData.character[grid[x][y]].equals("")) {
                                multiplier[x][y] += 2;
                            }
                            x++;
                            y++;
                        }

                        // Top-right to bottom-left
                        x = i - 1;
                        y = j + 1;
                        while (x >= 0 && y < 5) {
                            if (!MathData.character[grid[x][y]].equals("WC") && !MathData.character[grid[x][y]].equals("")) {
                                multiplier[x][y] += 2;
                            }
                            x--;
                            y++;
                        }

                        x = i + 1;
                        y = j - 1;
                        while (x < 3 && y >= 0) {
                            if (!MathData.character[grid[x][y]].equals("WC") && !MathData.character[grid[x][y]].equals("")) {
                                multiplier[x][y] += 2;
                            }
                            x++;
                            y--;
                        }
                    }
                }
            }
        }

        // System.out.println("Multiplier Grid:");
        // for (int i = 0; i < 3; i++) {
        //     for (int j = 0; j < 5; j++) {
        //         System.out.print(multiplier[i][j] + " ");
        //     }
        //     System.out.println();
        // }
        // System.out.println();

        return multiplier;
    }
}
