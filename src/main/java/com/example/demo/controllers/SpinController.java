package com.example.demo.controllers;

import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.example.demo.logic.GridGenerator;
import com.example.demo.logic.CalculateWinning;
import com.example.demo.logic.Controller;
import com.example.demo.logic.ForceTool;
import com.example.demo.logic.MathData;
import com.example.demo.logic.MultiplierGenerator;

@RestController
public
class SpinController{
    private static String[][] currentMatrix;
    private static List<double[]> currentResultMatrix;
    private static int[][] currentMultiplierMatrix;
    private static String[][] baseMatrix;
    private static List<double[]> baseResults;
    private static int[][] baseMults;
    private static List<String[][]> freeGrids;
    private static List<List<double[]>> freeResults;
    private static List<int[][]> freeMults;
    private static boolean freeSpinOccurred;

    public static boolean isBuyFeature = false;

    public static void newSpin(){
        MathData.initializeMap();
        if (Controller.balanceLeft()){
            MathData.isFree = false;
            freeSpinOccurred = false;
            resetFree();
            startSpin();
            deductBet();
            ForceTool.reset();
            baseMatrix = currentMatrix;
            baseResults = currentResultMatrix;
            baseMults = currentMultiplierMatrix;
            if (MathData.freeSpin > 0) {
                freeSpinOccurred = true;
                MathData.isFree = true;
                while (MathData.freeSpin > 0) {
                    startSpin(); 
                    appendFreeVars();
                    MathData.freeSpin--; 
                }
                MathData.stickyWilds = 0;
            }
        }

    }

    private static void deductBet(){
        MathData.balance -= MathData.betAmount;
    }

    private static void appendFreeVars() {
        freeGrids.add(currentMatrix);
        freeResults.add(currentResultMatrix);
        freeMults.add(currentMultiplierMatrix);
    }

    private static void resetFree() {
        freeGrids = new ArrayList<>();
        freeResults = new ArrayList<>();
        freeMults = new ArrayList<>();
    }

    private static String[][] convertMatrix(int[][] grid){
        String[][] newGrid = new String[MathData.rows][MathData.cols];
        for(int i=0; i<MathData.rows; i++){
            for(int j=0; j<MathData.cols; j++){
                newGrid[i][j] = MathData.character[grid[i][j]];
            }
        }
        return newGrid;
    } 

    public static void startSpin() {
        int[][] tempMatrix = GridGenerator.gridGenerator();
        currentMatrix = convertMatrix(tempMatrix);
        currentMultiplierMatrix = MultiplierGenerator.initializeMultiplier(tempMatrix, freeSpinOccurred);
        CalculateWinning.calculateWinnings(tempMatrix, Controller.getBetAmt(), freeSpinOccurred);
        currentResultMatrix = MathData.winningMatrix;
    }
       
    @PostMapping("/api/spin")   
    public ResponseEntity<Map<String, Object>> Spin(@RequestBody Map<String, Object> body){
        ForceTool.initialize(body);
        Controller.setBetAmt((int) body.get("betAmt"));
        isBuyFeature = (boolean) body.get("isBuyFeature");

        newSpin();

        Map<String, Object> response = new HashMap<>();
        response.put("symbolMatrix", baseMatrix);
        response.put("resultMatrix", baseResults);
        response.put("multiplierMatrix", baseMults);
        response.put("freeSpinOccurred", freeSpinOccurred);
        response.put("balance", Controller.getBalance());
        if (freeSpinOccurred) {
            response.put("freeGrids", freeGrids);
            response.put("freeResults", freeResults);
            response.put("freeMults", freeMults);
        }

        return ResponseEntity.ok(response);
    }
}