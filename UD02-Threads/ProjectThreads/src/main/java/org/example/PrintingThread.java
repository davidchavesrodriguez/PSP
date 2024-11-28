package org.example;

public class PrintingThread extends Thread {
    @Override
    public void run() {
        Thread myth= Thread.currentThread();
        System.out.println("A thread printing class");
        System.out.println("Priority: " + myth.getPriority());
    }
}
