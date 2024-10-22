package org.example;

import java.util.Scanner;

public class AddEvenNumbersBetweenTwo {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Tell me the first number:");
        int firstNumber = sc.nextInt();
        System.out.println("Tell me the second number:");
        int secondNumber = sc.nextInt();
        int counter = 0;

        for (int i = firstNumber; i <= secondNumber; i++) {
            if (i % 2 == 0) {
                counter += i;
            }
        }
        System.out.println(counter);
    }
}
