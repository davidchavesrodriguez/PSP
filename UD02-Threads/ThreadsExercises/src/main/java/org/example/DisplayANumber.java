package org.example;

import java.util.Random;

public class DisplayANumber implements Runnable {

    @Override
    public void run() {
        System.out.println("Greetings, " + Thread.currentThread().getName() + "!");

        Random random = new Random();
        for (int i = 0; i < 5; i++) {
            int randomNumber = random.nextInt(500) + 10;
            try {
                Thread.sleep(randomNumber);
//                System.out.println("Stopped at " + randomNumber + " miliseconds");
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        System.out.println("See you next time, " + Thread.currentThread().getName() + "!");

    }

    public static void main(String[] args) throws InterruptedException {
        DisplayANumber displayANumber= new DisplayANumber();
        Thread firstThread= new Thread(displayANumber);
        Thread secondThread= new Thread(displayANumber);

        firstThread.start();
        secondThread.start();

        firstThread.join();
        secondThread.join();

        System.out.println("All threads have finished working");
    }
}
