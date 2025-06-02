package com.example.demo.logic;

import java.util.Map;

public class ForceTool {
    private static boolean trigger3Scatters = false;
    private static boolean trigger4Scatters = false;
    private static boolean trigger5Scatters = false;
    private static boolean triggerWild = false;
    private static boolean triggerBigWin = false;
    private static boolean triggerHugeWin = false;
    private static boolean triggerMegaWin = false;
    public static boolean forceSet = false;

    public static void reset() {
        trigger3Scatters = false;
        trigger4Scatters = false;
        trigger5Scatters = false;
        triggerWild = false;
        triggerBigWin = false;
        triggerHugeWin = false;
        triggerMegaWin = false;
        forceSet = false;
    }

    @SuppressWarnings("unchecked")
    public static void initialize(Map<String, Object> body){
        Map<String, Boolean> forceMap = (Map<String, Boolean>)body.get("forceTool");
        trigger3Scatters = forceMap.get("trigger3Scatters");
        trigger4Scatters = forceMap.get("trigger4Scatters");
        trigger5Scatters = forceMap.get("trigger5Scatters");
        triggerWild = forceMap.get("triggerWild");
        triggerBigWin = forceMap.get("triggerBigWin");
        triggerHugeWin = forceMap.get("triggerHugeWin");
        triggerMegaWin = forceMap.get("triggerMegaWin");
        for (boolean value : forceMap.values()) {
            if (value) {
                forceSet = true;
            }
        }
    }

    public static int handle(int col) {
        if (trigger3Scatters) {
            switch (col) {
                case 0 -> {
                    return 0;
                }
                default -> {
                    return 10;
                }
            }
        } else if (trigger4Scatters) {
            return 10;
        } else if (trigger5Scatters) {
            switch (col) {
                case 2 -> {
                    return 24;
                }
                default -> {
                    return 10;
                }
            }
        } else if (triggerWild) {
            switch (col) {
                case 0 -> {
                    return 1;
                }
                case 4 -> {
                    return 0;
                }
                default -> {
                    return 5;
                }
            }
        } else if (triggerBigWin) {
            switch (col) {
                case 0 -> {
                    return 1;
                }
                case 2 -> {
                    return 27;
                }
                case 4 -> {
                    return 54;
                }
                default -> {
                    return 55;
                }
            }
        } else if (triggerHugeWin) {
            switch (col) {
                case 0 -> {
                    return 1;
                }
                case 1 -> {
                    return 3;
                }
                case 2 -> {
                    return 35;
                }
                case 3 -> {
                    return 7;
                }
                default -> {
                    return 2;
                }
            }
        } else if (triggerMegaWin) {
            switch (col) {
                case 0 -> {
                    return 1;
                }
                case 2 -> {
                    return 27;
                }
                default -> {
                    return 55;
                }
            }
        } else {
            return 0;
        }
    }

    public static void buyFeatureDetected(int random) {
        forceSet = true;
        switch (random) {
            case 0 -> {
                trigger3Scatters = true;
            }
            case 1 -> {
                trigger4Scatters = true;
            }
            case 2 -> {
                trigger5Scatters = true;
            }
        }
    }
}
