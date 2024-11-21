package org.example.hiddennumbersynchronized;

import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class GuessMain {
    public static final int NUM_THREADS = 10;

    public static void main(String[] args) {
        Random randomGenerator = new Random();

        HiddenNumber hiddenNumber = new HiddenNumber(randomGenerator.nextInt(101));

        ExecutorService pool = Executors.newFixedThreadPool(NUM_THREADS);

        for (int i = 0; i < NUM_THREADS; i++) {
            pool.submit(new GuessingThread(hiddenNumber));
        }

        pool.shutdown();

        try {
            // Wait until the threads end or 60 seconds
            if (!pool.awaitTermination(60, TimeUnit.SECONDS)) {
                pool.shutdownNow(); // We can shut them if they do not finish in 60 seconds
            }
        } catch (InterruptedException e) {
            pool.shutdownNow();
        }

        System.out.println("The hidden number was " + hiddenNumber.getHiddenNumber());
    }
}
