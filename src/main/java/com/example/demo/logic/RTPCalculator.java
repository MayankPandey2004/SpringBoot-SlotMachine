package com.example.demo.logic;

import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;

import com.example.demo.controllers.SpinController;

public class RTPCalculator {

    private final double betAmount;
    private final int numberOfSpins;

    public RTPCalculator(double betAmount, int numberOfSpins) {
        this.betAmount = betAmount;
        this.numberOfSpins = numberOfSpins;
    }

    @SuppressWarnings("CallToPrintStackTrace")
    public void calculate() {
        double totalBets = 0;

        for (int i = 1; i <= numberOfSpins; i++) {
            if (MathData.balance >= MathData.betAmount) {
                SpinController.newSpin();
                totalBets += betAmount;
            } else {
                System.out.println(MathData.balance);
                break;
            }
            if (i % 100_000_000 == 0 || i == numberOfSpins) {
                double baseRTP = (MathData.baseWin / totalBets) * 100;
                double freeRTP = (MathData.freeWin / totalBets) * 100;
                double totalRTP = baseRTP + freeRTP;
                double odds = MathData.totalFreePlayTriggers > 0 ? (double) i / MathData.totalFreePlayTriggers : 0;
                double avgFreePlayValue = (odds * freeRTP) / 100;

                DecimalFormat df = new DecimalFormat("#.###");

                String output = "Iteration: " + i / 100000000 + "00M\n"
                        + "Total Bets: " + totalBets / 1000000 + "M\n"
                        + "Base RTP: " + df.format(baseRTP) + "%\n"
                        + "Free RTP: " + df.format(freeRTP) + "%\n"
                        + "Total RTP: " + df.format(totalRTP) + "%\n"
                        + "Odds: " + df.format(odds) + "\n"
                        + "Avg per free play: " + df.format(avgFreePlayValue) + "\n"
                        + "Balance: " + df.format(MathData.balance) + "\n";

                try (FileWriter writer = new FileWriter("RTPResults.txt")) {
                    writer.write(output);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
