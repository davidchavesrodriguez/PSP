package org.example;

import java.util.ArrayList;
import java.util.Scanner;

public class JavaProcessBuilder2 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("How many numbers are you telling me?");
        int numberOfInputs = scanner.nextInt();

        ArrayList<Double> listOfNumbers = new ArrayList<Double>();
        for (int i = 0; i < numberOfInputs; i++) {
            System.out.println("Tell me a number then: ");
            double squaredNumber = Math.pow(scanner.nextInt(), 2);
            listOfNumbers.add(squaredNumber);
        }

        int sumOfNumbers = 0;
        for (double number : listOfNumbers) {
            sumOfNumbers += (int) number;
        }
        System.out.println("The sum of the square of the numbers is " + sumOfNumbers);
    }
}
