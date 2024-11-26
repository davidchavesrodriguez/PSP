package org.example.producerconsumer;

import java.util.Random;

public class Producer implements Runnable {

    private Resource resource;

    public Producer(Resource resource) {
        this.resource = resource;
    }

    @Override
    public void run() {
        Random random = new Random();
        for (int i = 0; i < 20; i++) {
            try {
                int num = random.nextInt(10);
                System.out.println("\nProducer produced: " + num);
                resource.put(num);
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
