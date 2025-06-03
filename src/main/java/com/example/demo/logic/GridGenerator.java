package com.example.demo.logic;

import java.util.random.RandomGenerator;
import java.util.random.RandomGeneratorFactory;

import com.example.demo.controllers.SpinController;

public class GridGenerator {
    public static RandomGenerator rng = RandomGeneratorFactory.getDefault().create();

    public static int[][] gridGenerator() {

        int rows = MathData.rows;
        int cols = MathData.cols;

        int grid[][] = new int[rows][cols];

        String[][] currentReel = (MathData.isFree) ? MathData.freeReel : MathData.baseReel;

        int countBN = 0;

        if (SpinController.isBuyFeature && !MathData.isFree) {
            int random = rng.nextInt(0, 3);
            ForceTool.buyFeatureDetected(random);
            MathData.balance -= (MathData.betAmount * 125);
        }

        for (int j = 0; j < cols; j++) {
            if ((j >= (cols - MathData.stickyWilds)) && MathData.isFree) {
                for (int i = 0; i < rows; i++) {
                    grid[i][j] = 0;
                }
                continue;
            }
            int index = rng.nextInt(0, currentReel.length);
            if (ForceTool.forceSet) {
                index = ForceTool.handle(j);
            }
            for (int i = 0; i < rows; i++) {
                grid[i][j] = java.util.Arrays.asList(MathData.character)
                        .indexOf(currentReel[(index + i) % currentReel.length][j]);
                if (grid[i][j] == 12)
                    countBN++;
            }

        }

        Controller.scatterFound(countBN);

        /* ---------TESTING-------- */

        // if(MathData.iteration==0){
        // grid[0][0] = 0;
        // grid[0][1] = 0;
        // grid[0][2] = 12;
        // grid[0][3] = 12;
        // grid[0][4] = 12;
        // MathData.iteration++;
        // }

        // System.out.println("Slot Grid:");
        // for (int i = 0; i < rows; i++) {
        // for (int j = 0; j < cols; j++) {
        // System.out.print(MathData.character[grid[i][j]] + " ");
        // }
        // System.out.println();
        // }
        // System.out.println();

        return grid;
    }
}
