package org.example.superevennumbers;

import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainRunnable implements Runnable {

    @Override
    public void run() {
        Random random = new Random();
        int lower = random.nextInt(10000) + 1;
        int upper = random.nextInt(10000) + 1;
        int min = Math.min(lower, upper);
        int max = Math.max(lower, upper);

        System.out.println("Checking numbers between " + min + " and " + max);

        ExecutorService executor = Executors.newFixedThreadPool(4);

        for (int i = min; i <= max; i++) {
            Number number = new Number(i);
            SuperEvenCheckerRunnable superEvenCheckerRunnable = new SuperEvenCheckerRunnable(number);
            executor.submit(superEvenCheckerRunnable);
        }

        executor.shutdown();
    }

    public static void main(String[] args) {
        MainRunnable mainRunnable = new MainRunnable();
        Thread mainThread = new Thread(mainRunnable);
        mainThread.start();
    }
}
