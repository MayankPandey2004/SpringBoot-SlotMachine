package com.example.demo.logic;

public class Controller {
    public static void setBetAmt(double amount) {
        MathData.betAmount = amount;
    }

    public static double getBetAmt() {
        return MathData.betAmount;
    }

    public static double getBalance() {
        return MathData.balance;
    }

    public static boolean balanceLeft() {
        return MathData.balance >= MathData.betAmount;
    }

    public static void scatterFound(int countBN) {
        switch (countBN) {
            case 3 -> {
                MathData.freeSpin = 6;
                MathData.stickyWilds = 1;
                MathData.totalFreePlayTriggers++;
            }
            case 4 -> {
                MathData.freeSpin = 8;
                MathData.stickyWilds = 2;
                MathData.totalFreePlayTriggers++;
            }
            case 5 -> {
                MathData.freeSpin = 10;
                MathData.stickyWilds = 3;
                MathData.totalFreePlayTriggers++;
            }
            default -> {
                return;
            }
        }
    }
}
