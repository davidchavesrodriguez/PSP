package gal.classexercises.producerconsumer.mailbox;

public class LaunchMailbox {
    public static void main(String[] args) throws InterruptedException {
        Mailbox mailbox = new Mailbox();

        for (int i = 1; i <= 5; i++) {
            Thread producer = new Thread(new Producer(mailbox), "Producer-" + i);
            producer.start();
        }

        for (int i = 1; i <= 5; i++) {
            Thread consumer = new Thread(new Consumer(mailbox), "Consumer-" + i);
            consumer.start();
        }
    }
}
