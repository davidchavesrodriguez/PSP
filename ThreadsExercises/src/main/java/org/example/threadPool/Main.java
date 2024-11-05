package org.example.threadPool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {
    public static final int NUM_THREADS = 6;

    public static void main(String[] args) {
        String[] names = {"Ana", "Manuel", "Elena", "Luis", "Teresa", "Andrés"};

        ExecutorService pool = Executors.newFixedThreadPool(NUM_THREADS);
        for (int i = 0; i < NUM_THREADS; i++) {
            Runnable runnableGreet = new RunnableGreet(names[i]);
            pool.execute(runnableGreet);
        }
        pool.shutdown();
    }
}
