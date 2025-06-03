package com.example.demo.logic;

import java.util.ArrayList;
import java.util.List;

public class CalculateWinning {

    public static double calculateWinnings(int[][] grid, double betAmount, boolean freeSpin) {

        int[][] multiplier = MultiplierGenerator.initializeMultiplier(grid, freeSpin);
        double totalWinning = 0;
        List<double[]> winningMatrix = new ArrayList<>();

        for (int paylineIndex = 0; paylineIndex < MathData.paylines.length; paylineIndex++) {
            int[] payline = MathData.paylines[paylineIndex];
            double[] tempMatrix = new double[3];

            int firstSymbol = -1;
            int col = 0;
            int countWC = 0;

            while (col < payline.length) {
                int symbol = grid[payline[col]][col];
                if (symbol != 0) {
                    firstSymbol = symbol;
                    break;
                } else {
                    countWC++;
                }
                col++;
            }

            if (firstSymbol == -1) {
                firstSymbol = 0;
            }

            int matchCount = 0;
            for (int c = 0; c < payline.length; c++) {
                int symbol = grid[payline[c]][c];
                if (symbol == firstSymbol || (symbol == 0)) {
                    matchCount++;
                } else {
                    break;
                }
            }

            if (matchCount >= 3) {
                tempMatrix[0] = paylineIndex;
                tempMatrix[1] = matchCount;
                tempMatrix[2] = MathData.scores.get(MathData.character[firstSymbol])[5 - matchCount]
                        * Controller.getBetAmt();

                String symbolStr = MathData.character[firstSymbol];
                double[] rewardTable = MathData.scores.get(symbolStr);
                int rewardIndex = 5 - matchCount;
                double baseReward = rewardTable[rewardIndex];
                double reward = baseReward * betAmount;

                /* ---------Multiplier Logic-------- */
                for (int c = 0; c < matchCount; c++) {
                    int rowIndex = payline[c];
                    int colIndex = c;
                    int mult = multiplier[rowIndex][colIndex];
                    if (mult > 0) {
                        reward *= mult;
                    }
                }

                int mult = 1;
                int[] winline = MathData.paylines[(int) tempMatrix[0]];

                for (int j = 0; j < tempMatrix[1]; j++) {
                    int tempMult = multiplier[winline[j]][j];
                    mult *= tempMult;
                }
                double temp = mult * tempMatrix[2];
                tempMatrix[2] = temp;
                
                winningMatrix.add(tempMatrix);

                if (countWC == 3) {
                    totalWinning += Math.max(reward, MathData.scores.get("WC")[2] * betAmount);
                    tempMatrix[2] = Math.max(reward, MathData.scores.get("WC")[2] * betAmount);
                } else {
                    totalWinning += reward;
                }              
            }
        }

        if(freeSpin) MathData.freeWin += totalWinning;
        else MathData.baseWin += totalWinning;

        MathData.winningMatrix = winningMatrix;
        MathData.balance += totalWinning;

        return totalWinning;
    }
}
