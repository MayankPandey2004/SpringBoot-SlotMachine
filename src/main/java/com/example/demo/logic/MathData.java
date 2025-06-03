package com.example.demo.logic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MathData {

        public static int rows = 3;
        public static int cols = 5;

        public static int iteration = 0;
        public static double betAmount = 2;
        public static double balance = 2000_000_000;

        public static double freeWin = 0;
        public static double baseWin = 0;

        public static boolean isFree = false;
        public static int stickyWilds = 0;
        public static int freeSpin = 0;
        static int totalFreePlayTriggers = 0;

        public static final String character[] = { "WC", "AA", "BB", "CC", "DD", "EE", "FF", "GG", "HH", "JJ", "KK",
                        "LL", "BN", };

        public static List<double[]> winningMatrix = new ArrayList<>();

        public static int[][] paylines = {
                        { 1, 1, 1, 1, 1 }, // 0
                        { 0, 0, 0, 0, 0 }, // 1
                        { 2, 2, 2, 2, 2 }, // 2
                        { 0, 1, 2, 1, 0 }, // 3
                        { 2, 1, 0, 1, 2 }, // 4
                        { 1, 0, 0, 0, 1 }, // 5
                        { 1, 2, 2, 2, 1 }, // 6
                        { 0, 1, 0, 1, 0 }, // 7
                        { 2, 1, 2, 1, 2 }, // 8
                        { 1, 1, 0, 1, 1 }, // 9
                        { 1, 1, 2, 1, 1 }, // 10
                        { 0, 1, 1, 1, 0 }, // 11
                        { 2, 1, 1, 1, 2 }, // 12
                        { 0, 1, 2, 2, 2 }, // 13
                        { 2, 1, 0, 0, 0 }, // 14
                        { 0, 2, 0, 2, 0 }, // 15
                        { 2, 0, 2, 0, 2 }, // 16
                        { 0, 2, 2, 2, 0 }, // 17
                        { 2, 0, 0, 0, 2 }, // 18
                        { 0, 0, 2, 0, 0 } // 19
        };

        public static Map<String, double[]> scores = new HashMap<>();

        public static void initializeMap() {
                scores.put("WC", new double[] { 15.0, 10.0, 5.0, 0, 0 });
                scores.put("AA", new double[] { 10.0, 6.25, 3.75, 0, 0 });
                scores.put("BB", new double[] { 7.5, 4.5, 2.5, 0, 0 });
                scores.put("CC", new double[] { 6.25, 3.75, 1.5, 0, 0 });
                scores.put("DD", new double[] { 5.0, 2.5, 1.25, 0, 0 });
                scores.put("EE", new double[] { 3.75, 1.5, 1.0, 0, 0 });
                scores.put("FF", new double[] { 2.5, 1.25, 0.75, 0, 0 });
                scores.put("GG", new double[] { 2.5, 1.25, 0.75, 0, 0 });
                scores.put("HH", new double[] { 1.5, 0.75, 0.5, 0, 0 });
                scores.put("JJ", new double[] { 1.5, 0.75, 0.5, 0, 0 });
                scores.put("KK", new double[] { 1.0, 0.5, 0.25, 0, 0 });
                scores.put("LL", new double[] { 1.0, 0.5, 0.25, 0, 0 });
                scores.put("BN", new double[] { 0, 0, 0, 0, 0 });
        }

        public static String[][] baseReel = {
                        { "BB", "CC", "HH", "HH", "EE" },
                        { "WC", "WC", "WC", "WC", "WC" },
                        { "LL", "LL", "LL", "LL", "LL" },
                        { "JJ", "CC", "HH", "HH", "EE" },
                        { "CC", "JJ", "JJ", "FF", "JJ" },
                        { "GG", "GG", "AA", "EE", "KK" },
                        { "LL", "LL", "LL", "LL", "LL" },
                        { "DD", "DD", "FF", "CC", "GG" },
                        { "FF", "HH", "JJ", "HH", "KK" },
                        { "GG", "GG", "KK", "GG", "HH" },
                        { "KK", "KK", "HH", "KK", "EE" },
                        { "BN", "BN", "AA", "BN", "BN" },
                        { "HH", "AA", "GG", "JJ", "FF" },
                        { "AA", "JJ", "LL", "KK", "EE" },
                        { "LL", "LL", "AA", "LL", "LL" },
                        { "FF", "GG", "JJ", "JJ", "HH" },
                        { "KK", "KK", "GG", "GG", "GG" },
                        { "WC", "WC", "WC", "WC", "WC" },
                        { "FF", "HH", "JJ", "EE", "CC" },
                        { "AA", "BB", "BB", "BB", "EE" },
                        { "KK", "DD", "HH", "CC", "HH" },
                        { "EE", "GG", "GG", "DD", "DD" },
                        { "HH", "AA", "BB", "BB", "CC" },
                        { "BB", "DD", "JJ", "HH", "JJ" },
                        { "JJ", "EE", "BN", "GG", "BB" },
                        { "FF", "FF", "FF", "DD", "GG" },
                        { "GG", "KK", "JJ", "FF", "JJ" },
                        { "JJ", "EE", "DD", "JJ", "KK" },
                        { "LL", "LL", "LL", "LL", "LL" },
                        { "AA", "DD", "AA", "CC", "JJ" },
                        { "FF", "EE", "HH", "JJ", "KK" },
                        { "GG", "KK", "KK", "DD", "GG" },
                        { "BB", "FF", "FF", "CC", "HH" },
                        { "BB", "CC", "EE", "EE", "BB" },
                        { "EE", "HH", "KK", "HH", "HH" },
                        { "JJ", "FF", "CC", "GG", "JJ" },
                        { "LL", "LL", "LL", "LL", "LL" },
                        { "HH", "GG", "FF", "HH", "GG" },
                        { "CC", "JJ", "GG", "EE", "HH" },
                        { "JJ", "HH", "BB", "BB", "AA" },
                        { "BN", "AA", "EE", "DD", "GG" },
                        { "GG", "KK", "GG", "BN", "KK" },
                        { "DD", "BB", "CC", "FF", "HH" },
                        { "GG", "CC", "EE", "HH", "GG" },
                        { "JJ", "HH", "AA", "KK", "HH" },
                        { "EE", "EE", "JJ", "DD", "CC" },
                        { "GG", "LL", "GG", "GG", "GG" },
                        { "HH", "HH", "HH", "HH", "KK" },
                        { "CC", "GG", "BB", "AA", "GG" },
                        { "DD", "KK", "FF", "EE", "FF" },
                        { "KK", "HH", "GG", "BB", "DD" },
                        { "BB", "JJ", "BN", "EE", "JJ" },
                        { "HH", "BB", "FF", "DD", "GG" },
                        { "KK", "GG", "GG", "BB", "KK" },
                        { "GG", "AA", "KK", "HH", "AA" },
                        { "JJ", "DD", "HH", "DD", "DD" },
                        { "EE", "JJ", "GG", "FF", "BB" },
                        { "HH", "KK", "BB", "BN", "HH" },
                        { "FF", "HH", "KK", "HH", "CC" },
                        { "KK", "GG", "FF", "DD", "FF" },
                        { "HH", "AA", "EE", "EE", "BB" },
                        { "EE", "HH", "KK", "HH", "HH" },
                        { "JJ", "FF", "CC", "GG", "AA" },
                        { "LL", "LL", "LL", "LL", "LL" },
                        { "HH", "GG", "FF", "HH", "GG" },
                        { "CC", "JJ", "GG", "EE", "HH" },
                        { "JJ", "HH", "DD", "FF", "AA" },
                        { "BN", "AA", "EE", "DD", "GG" },
                        { "GG", "KK", "GG", "AA", "KK" },
                        { "DD", "BB", "CC", "FF", "HH" },
                        { "AA", "FF", "EE", "EE", "GG" },
                        { "GG", "DD", "JJ", "HH", "JJ" },
                        { "JJ", "EE", "BN", "GG", "BB" },
                        { "FF", "AA", "FF", "AA", "GG" },
                        { "GG", "KK", "JJ", "FF", "JJ" },
                        { "JJ", "EE", "DD", "JJ", "KK" },
                        { "LL", "LL", "LL", "LL", "LL" },
                        { "AA", "DD", "FF", "CC", "JJ" },
                        { "FF", "EE", "HH", "JJ", "KK" },
                        { "GG", "KK", "KK", "DD", "GG" },
                        { "HH", "FF", "FF", "CC", "HH" },
                        { "HH", "CC", "EE", "EE", "BB" },
                        { "EE", "HH", "KK", "HH", "HH" },
                        { "JJ", "FF", "CC", "GG", "JJ" },
                        { "LL", "LL", "LL", "LL", "LL" },
                        { "HH", "GG", "FF", "HH", "GG" },
                        { "CC", "JJ", "GG", "EE", "HH" },
                        { "JJ", "HH", "DD", "FF", "AA" },
                        { "FF", "BN", "EE", "DD", "GG" },
                        { "GG", "KK", "GG", "BN", "KK" },
                        { "DD", "BB", "CC", "FF", "HH" },
                        { "HH", "FF", "EE", "EE", "GG" },
                        { "GG", "DD", "JJ", "HH", "JJ" },
                        { "JJ", "EE", "BN", "GG", "BB" },
                        { "FF", "FF", "FF", "DD", "GG" },
                        { "GG", "KK", "JJ", "FF", "JJ" },
                        { "JJ", "EE", "DD", "JJ", "KK" },
                        { "LL", "LL", "LL", "LL", "LL" },
                        { "AA", "DD", "FF", "CC", "JJ" },
                        { "FF", "EE", "HH", "JJ", "KK" },
                        { "GG", "KK", "KK", "DD", "GG" },
                        { "HH", "FF", "FF", "CC", "HH" },
                        { "GG", "KK", "JJ", "FF", "JJ" },
                        { "JJ", "EE", "DD", "JJ", "KK" },
                        { "KK", "HH", "EE", "KK", "KK" },
                        { "GG", "GG", "GG", "AA", "GG" }
        };

        public static String[][] freeReel = {
                        { "HH", "FF", "EE", "EE", "EE" },
                        { "DD", "HH", "AA", "CC", "FF" },
                        { "LL", "LL", "LL", "LL", "LL" },
                        { "JJ", "CC", "HH", "HH", "EE" },
                        { "CC", "JJ", "JJ", "FF", "JJ" },
                        { "BB", "EE", "WC", "GG", "KK" },
                        { "LL", "LL", "LL", "LL", "LL" },
                        { "JJ", "DD", "FF", "CC", "GG" },
                        { "FF", "HH", "JJ", "HH", "KK" },
                        { "GG", "GG", "KK", "GG", "HH" },
                        { "KK", "KK", "HH", "KK", "EE" },
                        { "DD", "AA", "CC", "HH", "CC" },
                        { "HH", "BB", "GG", "JJ", "FF" },
                        { "BB", "JJ", "HH", "KK", "EE" },
                        { "LL", "LL", "LL", "LL", "LL" },
                        { "FF", "GG", "JJ", "JJ", "HH" },
                        { "KK", "KK", "GG", "GG", "GG" },
                        { "HH", "JJ", "HH", "HH", "KK" },
                        { "FF", "HH", "JJ", "EE", "CC" },
                        { "AA", "BB", "FF", "KK", "EE" },
                        { "KK", "DD", "HH", "AA", "HH" },
                        { "EE", "GG", "GG", "DD", "DD" },
                        { "HH", "AA", "BB", "BB", "CC" },
                        { "GG", "CC", "EE", "HH", "GG" },
                        { "JJ", "EE", "AA", "GG", "HH" },
                        { "EE", "FF", "JJ", "DD", "CC" },
                        { "LL", "LL", "LL", "LL", "LL" },
                        { "FF", "HH", "HH", "HH", "KK" },
                        { "CC", "GG", "BB", "AA", "GG" },
                        { "DD", "KK", "FF", "EE", "FF" },
                        { "KK", "HH", "GG", "BB", "DD" },
                        { "GG", "JJ", "AA", "AA", "JJ" },
                        { "HH", "BB", "FF", "DD", "GG" },
                        { "KK", "GG", "GG", "BB", "KK" },
                        { "GG", "AA", "KK", "HH", "AA" },
                        { "JJ", "DD", "HH", "DD", "DD" },
                        { "EE", "JJ", "GG", "FF", "BB" },
                        { "HH", "KK", "BB", "AA", "HH" },
                        { "FF", "HH", "KK", "HH", "CC" },
                        { "KK", "GG", "AA", "DD", "FF" },
                        { "HH", "CC", "EE", "EE", "BB" },
                        { "EE", "HH", "KK", "HH", "HH" },
                        { "JJ", "FF", "CC", "GG", "JJ" },
                        { "LL", "LL", "LL", "LL", "LL" },
                        { "HH", "GG", "FF", "HH", "GG" },
                        { "CC", "JJ", "GG", "EE", "HH" },
                        { "JJ", "HH", "DD", "FF", "AA" },
                        { "FF", "AA", "EE", "DD", "GG" },
                        { "GG", "KK", "GG", "AA", "KK" },
                        { "DD", "BB", "CC", "FF", "HH" },
                        { "HH", "FF", "EE", "EE", "GG" },
                        { "GG", "DD", "JJ", "HH", "JJ" },
                        { "JJ", "EE", "AA", "GG", "BB" },
                        { "FF", "FF", "FF", "DD", "GG" },
                        { "GG", "KK", "JJ", "FF", "JJ" },
                        { "JJ", "EE", "DD", "JJ", "KK" },
                        { "LL", "LL", "LL", "LL", "LL" },
                        { "WC", "WC", "WC", "WC", "JJ" },
                        { "FF", "EE", "HH", "JJ", "KK" },
                        { "GG", "KK", "KK", "DD", "GG" },
                        { "HH", "FF", "FF", "CC", "HH" },
                        { "KK", "HH", "EE", "KK", "KK" },
                        { "GG", "GG", "GG", "AA", "GG" }
        };
}
