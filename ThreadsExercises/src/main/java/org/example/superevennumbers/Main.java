package org.example.superevennumbers;

import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {
    public static final int NUM_THREADS= 4;
    public static void main(String[] args) {
        Random randomGenerator = new Random();
        int firstNumber = randomGenerator.nextInt(1, 10000);
        int secondNumber = randomGenerator.nextInt(1, 10000);

        if (firstNumber == secondNumber) {
            System.out.println("Almost imposible but could happen");
            return;
        }

        int lowNumber = Math.min(firstNumber, secondNumber);
        int highNumber = Math.max(firstNumber, secondNumber);

        int range = highNumber - lowNumber;

        System.out.println("Low number: " + lowNumber);
        System.out.println("High number: " + highNumber);
        System.out.println("Range: " + range);


        ExecutorService pool= Executors.newFixedThreadPool(NUM_THREADS);

    }
}
