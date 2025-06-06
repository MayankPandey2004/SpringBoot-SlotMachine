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
                        { "BB", "CC", "HH", "EE", "WC" },
                        { "AA", "DD", "FF", "GG", "JJ" },
                        { "KK", "LL", "BN", "BB", "CC" },
                        { "HH", "EE", "WC", "AA", "DD" },
                        { "FF", "GG", "JJ", "KK", "LL" },
                        { "BN", "BB", "CC", "HH", "EE" },
                        { "WC", "AA", "DD", "FF", "GG" },
                        { "JJ", "KK", "LL", "BN", "BB" },
                        { "CC", "HH", "EE", "WC", "AA" },
                        { "DD", "FF", "GG", "JJ", "KK" },
                        { "LL", "BN", "BB", "CC", "HH" },
                        { "EE", "WC", "AA", "DD", "FF" },
                        { "GG", "JJ", "KK", "LL", "BN" },
                        { "BB", "CC", "HH", "EE", "WC" },
                        { "AA", "DD", "FF", "GG", "JJ" },
                        { "KK", "LL", "BN", "BB", "CC" },
                        { "HH", "EE", "WC", "AA", "DD" },
                        { "FF", "GG", "JJ", "KK", "LL" },
                        { "BN", "BB", "CC", "HH", "EE" },
                        { "WC", "AA", "DD", "FF", "GG" },
                        { "JJ", "KK", "LL", "BN", "BB" },
                        { "CC", "HH", "EE", "WC", "AA" },
                        { "DD", "FF", "GG", "JJ", "KK" },
                        { "LL", "BN", "BB", "CC", "HH" },
                        { "EE", "WC", "AA", "DD", "FF" },
                        { "GG", "JJ", "KK", "LL", "BN" },
                        { "BB", "CC", "HH", "EE", "WC" },
                        { "AA", "DD", "FF", "GG", "JJ" },
                        { "KK", "LL", "BN", "BB", "CC" },
                        { "HH", "EE", "WC", "AA", "DD" },
                        { "FF", "GG", "JJ", "KK", "LL" },
                        { "BN", "BB", "CC", "HH", "EE" },
                        { "WC", "AA", "DD", "FF", "GG" },
                        { "JJ", "KK", "LL", "BN", "BB" },
                        { "CC", "HH", "EE", "WC", "AA" },
                        { "DD", "FF", "GG", "JJ", "KK" },
                        { "LL", "BN", "BB", "CC", "HH" },
                        { "EE", "WC", "AA", "DD", "FF" },
                        { "GG", "JJ", "KK", "LL", "BN" },
                        { "BB", "CC", "HH", "EE", "WC" },
                        { "AA", "DD", "FF", "GG", "JJ" },
                        { "KK", "LL", "BN", "BB", "CC" },
                        { "HH", "EE", "WC", "AA", "DD" },
                        { "FF", "GG", "JJ", "KK", "LL" },
                        { "BN", "BB", "CC", "HH", "EE" },
                        { "WC", "AA", "DD", "FF", "GG" },
                        { "JJ", "KK", "LL", "BN", "BB" },
                        { "CC", "HH", "EE", "WC", "AA" },
                        { "DD", "FF", "GG", "JJ", "KK" },
                        { "LL", "BN", "BB", "CC", "HH" },
                        { "EE", "WC", "AA", "DD", "FF" },
                        { "GG", "JJ", "KK", "LL", "BN" },
                        { "BB", "CC", "HH", "EE", "WC" },
                        { "AA", "DD", "FF", "GG", "JJ" },
                        { "KK", "LL", "BN", "BB", "CC" },
                        { "HH", "EE", "WC", "AA", "DD" },
                        { "FF", "GG", "JJ", "KK", "LL" },
                        { "BN", "BB", "CC", "HH", "EE" },
                        { "WC", "AA", "DD", "FF", "GG" },
                        { "JJ", "KK", "LL", "BN", "BB" },
                        { "CC", "HH", "EE", "WC", "AA" },
                        { "DD", "FF", "GG", "JJ", "KK" },
                        { "LL", "BN", "BB", "CC", "HH" },
                        { "EE", "WC", "AA", "DD", "FF" },
                        { "GG", "JJ", "KK", "LL", "BN" },
                        { "BB", "CC", "HH", "EE", "WC" },
                        { "AA", "DD", "FF", "GG", "JJ" },
                        { "KK", "LL", "BN", "BB", "CC" },
                        { "HH", "EE", "WC", "AA", "DD" },
                        { "FF", "GG", "JJ", "KK", "LL" },
                        { "BN", "BB", "CC", "HH", "EE" },
                        { "WC", "AA", "DD", "FF", "GG" },
                        { "JJ", "KK", "LL", "BN", "BB" },
                        { "CC", "HH", "EE", "WC", "AA" },
                        { "DD", "FF", "GG", "JJ", "KK" },
                        { "LL", "BN", "BB", "CC", "HH" },
                        { "EE", "WC", "AA", "DD", "FF" },
                        { "GG", "JJ", "KK", "LL", "BN" },
                        { "BB", "CC", "HH", "EE", "WC" },
                        { "AA", "DD", "FF", "GG", "JJ" },
                        { "KK", "LL", "BN", "BB", "CC" },
                        { "HH", "EE", "WC", "AA", "DD" },
                        { "FF", "GG", "JJ", "KK", "LL" },
                        { "BN", "BB", "CC", "HH", "EE" },
                        { "WC", "AA", "DD", "FF", "GG" },
                        { "JJ", "KK", "LL", "BN", "BB" },
                        { "CC", "HH", "EE", "WC", "AA" },
                        { "DD", "FF", "GG", "JJ", "KK" },
                        { "LL", "BN", "BB", "CC", "HH" },
                        { "EE", "WC", "AA", "DD", "FF" },
                        { "GG", "JJ", "KK", "LL", "BN" },
                        { "BB", "CC", "HH", "EE", "WC" },
                        { "AA", "DD", "FF", "GG", "JJ" },
                        { "KK", "LL", "BN", "BB", "CC" },
                        { "HH", "EE", "WC", "AA", "DD" },
                        { "FF", "GG", "JJ", "KK", "LL" },
                        { "BN", "BB", "CC", "HH", "EE" },
                        { "WC", "AA", "DD", "FF", "GG" },
                        { "JJ", "KK", "LL", "BN", "BB" },
                        { "CC", "HH", "EE", "WC", "AA" },
                        { "DD", "FF", "GG", "JJ", "KK" },
                        { "LL", "BN", "BB", "CC", "HH" },
                        { "EE", "WC", "AA", "DD", "FF" },
                        { "GG", "JJ", "KK", "LL", "BN" },
                        { "BB", "CC", "HH", "EE", "WC" },
                        { "AA", "DD", "FF", "GG", "JJ" },
                        { "KK", "LL", "BN", "BB", "CC" },
                        { "HH", "EE", "WC", "AA", "DD" },
                        { "FF", "GG", "JJ", "KK", "LL" },
                        { "BN", "BB", "CC", "HH", "EE" },
                        { "WC", "AA", "DD", "FF", "GG" },
                        { "JJ", "KK", "LL", "BN", "BB" },
                        { "CC", "HH", "EE", "WC", "AA" },
                        { "DD", "FF", "GG", "JJ", "KK" },
                        { "LL", "BN", "BB", "CC", "HH" },
                        { "EE", "WC", "AA", "DD", "FF" },
                        { "GG", "JJ", "KK", "LL", "BN" },
                        { "BB", "CC", "HH", "EE", "WC" },
                        { "AA", "DD", "FF", "GG", "JJ" },
                        { "KK", "LL", "BN", "BB", "CC" },
                        { "HH", "EE", "WC", "AA", "DD" },
                        { "FF", "GG", "JJ", "KK", "LL" },
                        { "BN", "BB", "CC", "HH", "EE" },
                        { "WC", "AA", "DD", "FF", "GG" },
                        { "JJ", "KK", "LL", "BN", "BB" },
                        { "CC", "HH", "EE", "WC", "AA" },
                        { "DD", "FF", "GG", "JJ", "KK" },
                        { "LL", "BN", "BB", "CC", "HH" },
                        { "EE", "WC", "AA", "DD", "FF" },
                        { "GG", "JJ", "KK", "LL", "BN" },
                        { "BB", "CC", "HH", "EE", "WC" },
                        { "AA", "DD", "FF", "GG", "JJ" },
                        { "KK", "LL", "BN", "BB", "CC" },
                        { "HH", "EE", "WC", "AA", "DD" },
                        { "FF", "GG", "JJ", "KK", "LL" },
                        { "BN", "BB", "CC", "HH", "EE" },
                        { "WC", "AA", "DD", "FF", "GG" },
                        { "JJ", "KK", "LL", "BN", "BB" },
                        { "CC", "HH", "EE", "WC", "AA" },
                        { "DD", "FF", "GG", "JJ", "KK" },
                        { "LL", "BN", "BB", "CC", "HH" },
                        { "EE", "WC", "AA", "DD", "FF" },
                        { "GG", "JJ", "KK", "LL", "BN" },
                        { "BB", "CC", "HH", "EE", "WC" },
                        { "AA", "DD", "FF", "GG", "JJ" },
                        { "KK", "LL", "BN", "BB", "CC" },
                        { "HH", "EE", "WC", "AA", "DD" },
                        { "FF", "GG", "JJ", "KK", "LL" },
                        { "BN", "BB", "CC", "HH", "EE" },
                        { "WC", "AA", "DD", "FF", "GG" },
                        { "JJ", "KK", "LL", "BN", "BB" },
                        { "CC", "HH", "EE", "WC", "AA" },
                        { "DD", "FF", "GG", "JJ", "KK" },
                        { "LL", "BN", "BB", "CC", "HH" },
                        { "EE", "WC", "AA", "DD", "FF" },
                        { "GG", "JJ", "KK", "LL", "BN" },
                        { "BB", "CC", "HH", "EE", "WC" },
                        { "AA", "DD", "FF", "GG", "JJ" },
                        { "KK", "LL", "BN", "BB", "CC" },
                        { "HH", "EE", "WC", "AA", "DD" },
                        { "FF", "GG", "JJ", "KK", "LL" },
                        { "BN", "BB", "CC", "HH", "EE" },
                        { "WC", "AA", "DD", "FF", "GG" },
                        { "JJ", "KK", "LL", "BN", "BB" },
                        { "CC", "HH", "EE", "WC", "AA" },
                        { "DD", "FF", "GG", "JJ", "KK" },
                        { "LL", "BN", "BB", "CC", "HH" },
                        { "EE", "WC", "AA", "DD", "FF" },
                        { "GG", "JJ", "KK", "LL", "BN" },
                        { "BB", "CC", "HH", "EE", "WC" },
                        { "AA", "DD", "FF", "GG", "JJ" },
                        { "KK", "LL", "BN", "BB", "CC" },
                        { "HH", "EE", "WC", "AA", "DD" },
                        { "FF", "GG", "JJ", "KK", "LL" },
                        { "BN", "BB", "CC", "HH", "EE" },
                        { "WC", "AA", "DD", "FF", "GG" },
                        { "JJ", "KK", "LL", "BN", "BB" },
                        { "CC", "HH", "EE", "WC", "AA" },
                        { "DD", "FF", "GG", "JJ", "KK" },
                        { "LL", "BN", "BB", "CC", "HH" },
                        { "EE", "WC", "AA", "DD", "FF" },
                        { "GG", "JJ", "KK", "LL", "BN" },
                        { "BB", "CC", "HH", "EE", "WC" },
                        { "AA", "DD", "FF", "GG", "JJ" },
                        { "KK", "LL", "BN", "BB", "CC" },
                        { "HH", "EE", "WC", "AA", "DD" },
                        { "FF", "GG", "JJ", "KK", "LL" },
                        { "BN", "BB", "CC", "HH", "EE" },
                        { "WC", "AA", "DD", "FF", "GG" },
                        { "JJ", "KK", "LL", "BN", "BB" },
                        { "CC", "HH", "EE", "WC", "AA" },
                        { "DD", "FF", "GG", "JJ", "KK" }
        };

        public static String[][] freeReel = {
                        { "HH", "FF", "EE", "CC", "WC" },
                        { "AA", "DD", "GG", "JJ", "KK" },
                        { "LL", "BB", "CC", "HH", "EE" },
                        { "WC", "AA", "DD", "FF", "GG" },
                        { "JJ", "KK", "LL", "BB", "CC" },
                        { "HH", "EE", "WC", "AA", "DD" },
                        { "FF", "GG", "JJ", "KK", "LL" },
                        { "BB", "CC", "HH", "EE", "WC" },
                        { "AA", "DD", "FF", "GG", "JJ" },
                        { "KK", "LL", "BB", "CC", "HH" },
                        { "EE", "WC", "AA", "DD", "FF" },
                        { "GG", "JJ", "KK", "LL", "BB" },
                        { "CC", "HH", "EE", "WC", "AA" },
                        { "DD", "FF", "GG", "JJ", "KK" },
                        { "LL", "BB", "CC", "HH", "EE" },
                        { "WC", "AA", "DD", "FF", "GG" },
                        { "JJ", "KK", "LL", "BB", "CC" },
                        { "HH", "EE", "WC", "AA", "DD" },
                        { "FF", "GG", "JJ", "KK", "LL" },
                        { "BB", "CC", "HH", "EE", "WC" },
                        { "AA", "DD", "FF", "GG", "JJ" },
                        { "KK", "LL", "BB", "CC", "HH" },
                        { "EE", "WC", "AA", "DD", "FF" },
                        { "GG", "JJ", "KK", "LL", "BB" },
                        { "CC", "HH", "EE", "WC", "AA" },
                        { "DD", "FF", "GG", "JJ", "KK" },
                        { "LL", "BB", "CC", "HH", "EE" },
                        { "WC", "AA", "DD", "FF", "GG" },
                        { "JJ", "KK", "LL", "BB", "CC" },
                        { "HH", "EE", "WC", "AA", "DD" },
                        { "FF", "GG", "JJ", "KK", "LL" },
                        { "BB", "CC", "HH", "EE", "WC" },
                        { "AA", "DD", "FF", "GG", "JJ" },
                        { "KK", "LL", "BB", "CC", "HH" },
                        { "EE", "WC", "AA", "DD", "FF" },
                        { "GG", "JJ", "KK", "LL", "BB" },
                        { "CC", "HH", "EE", "WC", "AA" },
                        { "DD", "FF", "GG", "JJ", "KK" },
                        { "LL", "BB", "CC", "HH", "EE" },
                        { "WC", "AA", "DD", "FF", "GG" },
                        { "JJ", "KK", "LL", "BB", "CC" },
                        { "HH", "EE", "WC", "AA", "DD" },
                        { "FF", "GG", "JJ", "KK", "LL" },
                        { "BB", "CC", "HH", "EE", "WC" },
                        { "AA", "DD", "FF", "GG", "JJ" },
                        { "KK", "LL", "BB", "CC", "HH" },
                        { "EE", "WC", "AA", "DD", "FF" },
                        { "GG", "JJ", "KK", "LL", "BB" },
                        { "CC", "HH", "EE", "WC", "AA" },
                        { "DD", "FF", "GG", "JJ", "KK" },
                        { "LL", "BB", "CC", "HH", "EE" },
                        { "WC", "AA", "DD", "FF", "GG" },
                        { "JJ", "KK", "LL", "BB", "CC" },
                        { "HH", "EE", "WC", "AA", "DD" },
                        { "FF", "GG", "JJ", "KK", "LL" },
                        { "BB", "CC", "HH", "EE", "WC" },
                        { "AA", "DD", "FF", "GG", "JJ" },
                        { "KK", "LL", "BB", "CC", "HH" },
                        { "EE", "WC", "AA", "DD", "FF" },
                        { "GG", "JJ", "KK", "LL", "BB" },
                        { "CC", "HH", "EE", "WC", "AA" },
                        { "DD", "FF", "GG", "JJ", "KK" },
                        { "LL", "BB", "CC", "HH", "EE" },
                        { "WC", "AA", "DD", "FF", "GG" },
                        { "JJ", "KK", "LL", "BB", "CC" },
                        { "HH", "EE", "WC", "AA", "DD" },
                        { "FF", "GG", "JJ", "KK", "LL" },
                        { "BB", "CC", "HH", "EE", "WC" },
                        { "AA", "DD", "FF", "GG", "JJ" },
                        { "KK", "LL", "BB", "CC", "HH" },
                        { "EE", "WC", "AA", "DD", "FF" },
                        { "GG", "JJ", "KK", "LL", "BB" },
                        { "CC", "HH", "EE", "WC", "AA" },
                        { "DD", "FF", "GG", "JJ", "KK" },
                        { "LL", "BB", "CC", "HH", "EE" },
                        { "WC", "AA", "DD", "FF", "GG" },
                        { "JJ", "KK", "LL", "BB", "CC" },
                        { "HH", "EE", "WC", "AA", "DD" },
                        { "FF", "GG", "JJ", "KK", "LL" },
                        { "BB", "CC", "HH", "EE", "WC" },
                        { "AA", "DD", "FF", "GG", "JJ" },
                        { "KK", "LL", "BB", "CC", "HH" },
                        { "EE", "WC", "AA", "DD", "FF" },
                        { "GG", "JJ", "KK", "LL", "BB" },
                        { "CC", "HH", "EE", "WC", "AA" },
                        { "DD", "FF", "GG", "JJ", "KK" },
                        { "LL", "BB", "CC", "HH", "EE" },
                        { "WC", "AA", "DD", "FF", "GG" },
                        { "JJ", "KK", "LL", "BB", "CC" },
                        { "HH", "EE", "WC", "AA", "DD" },
                        { "FF", "GG", "JJ", "KK", "LL" },
                        { "BB", "CC", "HH", "EE", "WC" },
                        { "AA", "DD", "FF", "GG", "JJ" },
                        { "KK", "LL", "BB", "CC", "HH" },
                        { "EE", "WC", "AA", "DD", "FF" },
                        { "GG", "JJ", "KK", "LL", "BB" },
                        { "CC", "HH", "EE", "WC", "AA" },
                        { "DD", "FF", "GG", "JJ", "KK" },
                        { "LL", "BB", "CC", "HH", "EE" },
                        { "WC", "AA", "DD", "FF", "GG" },
                        { "JJ", "KK", "LL", "BB", "CC" },
                        { "HH", "EE", "WC", "AA", "DD" },
                        { "FF", "GG", "JJ", "KK", "LL" },
                        { "BB", "CC", "HH", "EE", "WC" },
                        { "AA", "DD", "FF", "GG", "JJ" },
                        { "KK", "LL", "BB", "CC", "HH" },
                        { "EE", "WC", "AA", "DD", "FF" },
                        { "GG", "JJ", "KK", "LL", "BB" },
                        { "CC", "HH", "EE", "WC", "AA" },
                        { "DD", "FF", "GG", "JJ", "KK" },
                        { "LL", "BB", "CC", "HH", "EE" },
                        { "WC", "AA", "DD", "FF", "GG" },
                        { "JJ", "KK", "LL", "BB", "CC" },
                        { "HH", "EE", "WC", "AA", "DD" },
                        { "FF", "GG", "JJ", "KK", "LL" },
                        { "BB", "CC", "HH", "EE", "WC" },
                        { "AA", "DD", "FF", "GG", "JJ" },
                        { "KK", "LL", "BB", "CC", "HH" },
                        { "EE", "WC", "AA", "DD", "FF" },
                        { "GG", "JJ", "KK", "LL", "BB" },
                        { "CC", "HH", "EE", "WC", "AA" },
                        { "DD", "FF", "GG", "JJ", "KK" },
                        { "LL", "BB", "CC", "HH", "EE" },
                        { "WC", "AA", "DD", "FF", "GG" },
                        { "JJ", "KK", "LL", "BB", "CC" },
                        { "HH", "EE", "WC", "AA", "DD" },
                        { "FF", "GG", "JJ", "KK", "LL" },
                        { "BB", "CC", "HH", "EE", "WC" },
                        { "AA", "DD", "FF", "GG", "JJ" },
                        { "KK", "LL", "BB", "CC", "HH" },
                        { "EE", "WC", "AA", "DD", "FF" },
                        { "GG", "JJ", "KK", "LL", "BB" },
                        { "CC", "HH", "EE", "WC", "AA" },
                        { "DD", "FF", "GG", "JJ", "KK" },
                        { "LL", "BB", "CC", "HH", "EE" },
                        { "WC", "AA", "DD", "FF", "GG" },
                        { "JJ", "KK", "LL", "BB", "CC" },
                        { "HH", "EE", "WC", "AA", "DD" },
                        { "FF", "GG", "JJ", "KK", "LL" },
                        { "BB", "CC", "HH", "EE", "WC" },
                        { "AA", "DD", "FF", "GG", "JJ" },
                        { "KK", "LL", "BB", "CC", "HH" },
                        { "EE", "WC", "AA", "DD", "FF" },
                        { "GG", "JJ", "KK", "LL", "BB" },
                        { "CC", "HH", "EE", "WC", "AA" },
                        { "DD", "FF", "GG", "JJ", "KK" },
                        { "LL", "BB", "CC", "HH", "EE" },
                        { "WC", "AA", "DD", "FF", "GG" },
                        { "JJ", "KK", "LL", "BB", "CC" },
                        { "HH", "EE", "WC", "AA", "DD" },
                        { "FF", "GG", "JJ", "KK", "LL" },
                        { "BB", "CC", "HH", "EE", "WC" },
                        { "AA", "DD", "FF", "GG", "JJ" },
                        { "KK", "LL", "BB", "CC", "HH" },
                        { "EE", "WC", "AA", "DD", "FF" },
                        { "GG", "JJ", "KK", "LL", "BB" },
                        { "CC", "HH", "EE", "WC", "AA" },
                        { "DD", "FF", "GG", "JJ", "KK" },
                        { "LL", "BB", "CC", "HH", "EE" },
                        { "WC", "AA", "DD", "FF", "GG" },
                        { "JJ", "KK", "LL", "BB", "CC" },
                        { "HH", "EE", "WC", "AA", "DD" },
                        { "FF", "GG", "JJ", "KK", "LL" },
                        { "BB", "CC", "HH", "EE", "WC" },
                        { "AA", "DD", "FF", "GG", "JJ" },
                        { "KK", "LL", "BB", "CC", "HH" },
                        { "EE", "WC", "AA", "DD", "FF" },
                        { "GG", "JJ", "KK", "LL", "BB" },
                        { "CC", "HH", "EE", "WC", "AA" },
                        { "DD", "FF", "GG", "JJ", "KK" },
                        { "LL", "BB", "CC", "HH", "EE" }
        };
}
