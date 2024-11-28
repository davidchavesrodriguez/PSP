package gal.classexercises.producerconsumer.mailbox;

public class Mailbox {
    private String message;
    private boolean hasMessage = false;

    public synchronized void put(String message) throws InterruptedException {
        while (hasMessage) {
            wait();
        }
        this.message = message;
        hasMessage = true;
        System.out.println("The message from " + Thread.currentThread().getName() + " is " + message);
        notifyAll();
    }

    public synchronized String get() throws InterruptedException {
        while (!hasMessage) {
            wait();
        }
        hasMessage = false;
        System.out.println("Retrieved message from" + Thread.currentThread().getName() + " is " + message);
        notifyAll();
        return this.message;
    }
}
