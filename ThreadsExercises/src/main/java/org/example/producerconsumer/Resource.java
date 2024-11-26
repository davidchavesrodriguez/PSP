package org.example.producerconsumer;

public class Resource {
    private int content;
    private boolean avaliable = false;

    // Producer
    public synchronized void put(int content) throws InterruptedException {
        while (avaliable) {
            wait();
        }
        this.content = content;
        avaliable = true;
        notifyAll();
    }

    // Consumer
    public synchronized int get() throws InterruptedException {
        while (!avaliable) {
            wait();
        }
        avaliable = false;
        notifyAll();
        return this.content;
    }
}
