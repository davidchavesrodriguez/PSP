package gal.classexercises.producerconsumer.birds;

import java.util.Random;

public class Bird implements Runnable {
    private final int birdId;
    private final Cage cage;
    Random random = new Random();

    public Bird(int birdId, Cage cage) {
        this.birdId = birdId;
        this.cage = cage;
    }

    @Override
    public void run() {
        try {
            while (true) {
                // Eating
                cage.startEating(birdId);
                Thread.sleep(random.nextInt(500, 1000));
                cage.stopEating(birdId);

                // Swinging
                cage.startSwinging(birdId);
                Thread.sleep(random.nextInt(500, 2000));
                cage.stopSwinging(birdId);
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
