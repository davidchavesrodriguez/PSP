package practice.runnable;

import java.util.Random;

public class Runnable1 implements Runnable {

    @Override
    public void run() {
        String threadName = Thread.currentThread().getName();
        System.out.println("Welcome, " + threadName);

        Random random = new Random();
        for (int i = 0; i < 5; i++) {
            int randomNumber = random.nextInt(10, 500);
            try {
                Thread.sleep(randomNumber);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        System.out.println("Bye, " + threadName);
    }

    public static void main(String[] args) throws InterruptedException {
        Runnable1 runnable1 = new Runnable1();
        Thread firstThread = new Thread(runnable1);
        Thread secondThread = new Thread(runnable1);

        firstThread.start();
        secondThread.start();

        firstThread.join();
        secondThread.join();

        System.out.println("Execution finished");

    }
}
