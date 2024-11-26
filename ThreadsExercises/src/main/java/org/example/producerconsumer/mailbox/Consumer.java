package org.example.producerconsumer.mailbox;

import java.util.Random;

public class Consumer implements Runnable {
    private Mailbox mailbox;
    private final Random random = new Random();


    public Consumer(Mailbox mailbox) {
        this.mailbox = mailbox;
    }

    @Override
    public void run() {
        try {
            while (true) {
                mailbox.get();
                Thread.sleep(random.nextInt(500, 2000));
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
