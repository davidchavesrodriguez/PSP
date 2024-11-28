package org.example;

import java.util.ArrayList;
import java.util.Scanner;

public class JavaProcessBuilder {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("How many numbers are you telling me?");
        int numberOfInputs = scanner.nextInt();

        ArrayList<Integer> listOfNumbers = new ArrayList<Integer>();
        for (int i = 0; i < numberOfInputs; i++) {
            System.out.println("Tell me a number then: ");
            listOfNumbers.add(scanner.nextInt());
        }

        int sumOfNumbers = 0;
        for (int number : listOfNumbers) {
            sumOfNumbers += number;
        }
        System.out.println("The square of the sum of the numbers is " + Math.pow(sumOfNumbers, 2));
    }
}
