package org.example;

public class InfoRunnable implements Runnable {

    @Override
    public void run() {
        System.out.println("Hello, my thread " + Thread.currentThread().getName());
    }
}
