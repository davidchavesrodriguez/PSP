package org.example.superevennumbers;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainRunnable {
    public static final int NUM_THREADS= 4;
    public static void main(String[] args) {
        Random randomGenerator= new Random();

        int firstNumber= randomGenerator.nextInt(1, 10000);
        int secondNumber= randomGenerator.nextInt(1, 10000);

        if (firstNumber == secondNumber) {
            System.out.println("Almost imposible but could happen");
            return;
        }

        int lowNumber= Math.min(firstNumber, secondNumber);
        int highNumber= Math.max(firstNumber, secondNumber);

        int range= highNumber - lowNumber;

        System.out.println("Low number: " + lowNumber);
        System.out.println("High number: " + highNumber);
        System.out.println("Range: " + range);

        ExecutorService pool= Executors.newFixedThreadPool(NUM_THREADS);
        List<SuperEvenCheckerRunnable> tasks= new ArrayList<>();

        for (int i = lowNumber; i <= highNumber ; i++) {
            SuperEvenCheckerRunnable runnable= new SuperEvenCheckerRunnable(String.valueOf(i));
            tasks.add(runnable);
            pool.submit(runnable);
        }
        pool.shutdown();

        for (int i = lowNumber, index = 0; i <= highNumber; i++, index++) {
            boolean isSuperEven=
            SuperEvenCheckerRunnable task = tasks.get(index);
            System.out.println(i + (task.isSuperEven() ? " IS SUPER EVEN!" : " is not super even."));
        }

    }
}
