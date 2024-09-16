package org.example;

import java.util.Scanner;


public class Main {
    public static void main(String[] args) {

        int numberOfAges;
        Scanner myScanner = new Scanner(System.in);
        System.out.print("How many ages are you telling me? ");
        numberOfAges = myScanner.nextInt();
        System.out.println("Ok, now tell me the " + numberOfAges + " ages and I will calculate how many candles you need: ");
        for (int i = 0; i < numberOfAges; i++) {
            int ageInput = myScanner.nextInt();
            String binaryNumber = calculateCandles(ageInput);
            int candleCounter = 0;
            for (int j = 0; j < binaryNumber.length(); j++) {
                if (binaryNumber.charAt(j) == '1') {
                    candleCounter++;
                }
            }
            if (candleCounter == 0) {
                System.out.println("You won´t need any candle.");
            } else if (candleCounter == 1) {
                System.out.println("You will need 1 candle.");
            } else {
                System.out.println("You will need " + candleCounter + " candles.");
            }
        }
    }

    public static String calculateCandles(Integer nextAge) {
        return Integer.toBinaryString(nextAge);

    }
}