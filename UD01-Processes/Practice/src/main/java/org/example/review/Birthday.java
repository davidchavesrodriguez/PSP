package org.example.review;

import java.util.Scanner;

/**
 * The Birthday program calculates the number of candles required for birthdays
 * based on the age in seconds provided by the user.
 * <p>
 * The program prompts the user for the number of cases (i.e., different ages in seconds),
 * and for each case, converts the age in seconds into its binary representation.
 * It then counts the number of `1`s in the binary representation, which corresponds
 * to the number of candles needed for the birthday.
 * </p>
 *
 * <p>
 * Example:
 * If the age in seconds is 5, its binary representation is `101`,
 * which contains two `1`s. Therefore, the user would need 2 candles.
 * </p>
 *
 * Usage:
 * The user inputs:
 * 1. The number of cases.
 * 2. For each case, the age in seconds.
 * The program outputs the number of candles needed for each case.
 *
 * @author David Chaves Rodríguez
 * @version 1.0
 */

public class Birthday {
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
