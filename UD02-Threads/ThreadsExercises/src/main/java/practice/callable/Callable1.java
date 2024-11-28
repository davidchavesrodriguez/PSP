package practice.callable;

import java.util.Random;
import java.util.concurrent.*;

public class Callable1 implements Callable<String> {
    @Override
    public String call() throws Exception {
        String threadName= Thread.currentThread().getName();
        System.out.println("Welcome, " + threadName);

        Random random= new Random();
        for (int i = 0; i < 5; i++) {
            int randomNumber= random.nextInt(10, 500);
            Thread.sleep(randomNumber);
        }
        System.out.println("Bye, " + threadName);
        return "--- Thread " + threadName + " finished ---";
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ExecutorService pool= Executors.newFixedThreadPool(2);

        Callable1 firstCallable= new Callable1();
        Callable1 secondCallable= new Callable1();

        Future<String> firstResult= pool.submit(firstCallable);
        Future<String> secondResult= pool.submit(secondCallable);

        System.out.println(firstResult.get());
        System.out.println(secondResult.get());

        pool.shutdown();
    }
}
