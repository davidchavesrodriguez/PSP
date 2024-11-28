package org.example.review;

import java.util.Scanner;

public class BirthdayArgs {
    public static void main(String[] args) {
        System.out.println("How many cases are you sending?");
        Scanner scanner = new Scanner(System.in);
        int numCases = scanner.nextInt();

        for (int i = 0; i < numCases; i++) {
            System.out.println("What´s yout age in seconds?");
            int ageInSeconds = scanner.nextInt();

            String ageInBinary = Integer.toBinaryString(ageInSeconds);
            int candleCounter = 0;

            for (char character : ageInBinary.toCharArray()) {
                if (character == '1') {
                    candleCounter++;
                }
            }

            System.out.println("For your birthday of " + ageInSeconds + " seconds you will need "
                    + candleCounter + " candles");

        }
    }
}