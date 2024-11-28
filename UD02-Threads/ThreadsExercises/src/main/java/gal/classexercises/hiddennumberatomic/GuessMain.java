package gal.classexercises.hiddennumberatomic;

import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

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

        while (!pool.isTerminated()) {

        }

        System.out.println("The hidden number was " + hiddenNumber.getHiddenNumber().get());

    }
}
