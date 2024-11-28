package gal.classexercises;

import java.util.Random;

public class ThreadsCollaboration implements Runnable {
    public static final int NUM_THREADS = 10;
    public static final int CHUNK_SIZE = 365;
    public static final int TEN_YEARS = 3650;
    public static int[] temperatures = new int[TEN_YEARS];
    public static final int[] partialSums = new int[NUM_THREADS];
    public final int start;
    public final int end;

    public ThreadsCollaboration(int start, int end) {
        this.start = start;
        this.end = end;
    }

    @Override
    public void run() {
        int sum = 0;
        for (int i = start; i < end; i++) {
            sum += temperatures[i];
        }
        partialSums[start / CHUNK_SIZE] = sum;
//        System.out.println("Sum of thread" + start/CHUNK_SIZE + "= " + sum);
    }


    public static void main(String[] args) {
        Random random = new Random();

        for (int i = 0; i < temperatures.length; i++) {
            temperatures[i] = random.nextInt(-30, 51);
        }

        Thread[] threads = new Thread[NUM_THREADS];
        for (int i = 0; i < NUM_THREADS; i++) {
            int start = i * CHUNK_SIZE;
            int end = start + CHUNK_SIZE;
            threads[i] = new Thread(new ThreadsCollaboration(start, end));
            threads[i].start();
        }

        for (Thread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        int totalSum = 0;
        for (int sum : partialSums) {
            totalSum += sum;
        }

        // Without Threads
        double sumWithoutThreads= 0;
        for (int i = 0; i < temperatures.length; i++) {
            sumWithoutThreads+= temperatures[i];
        }

        double averageTemperature = totalSum / (double) TEN_YEARS;
        System.out.println("Average temperature is: " + averageTemperature);
        System.out.println("Average without threads: " + sumWithoutThreads/TEN_YEARS);
    }
}