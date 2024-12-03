package gal.classexercises.producerconsumer.birds;

public class Cage {
    private int birdsEating = 0;
    private final int MAX_EATING = 3;
    private boolean isSwingOccupied = false;

    // Control eating
    public synchronized void startEating(int birdId) throws InterruptedException {
        while (birdsEating == MAX_EATING) {
            wait();
        }
        birdsEating++;
        System.out.println("Bird " + birdId + " starts eating \uD83E\uDED0");
    }

    public synchronized void stopEating(int birdId) {
        birdsEating--;
        System.out.println("Bird " + birdId + " stops eating \uD83E\uDED0❌");
        notifyAll();
    }

    // Control swing
    public synchronized void startSwinging(int birdId) throws InterruptedException {
        while (isSwingOccupied) {
            wait();
        }
        isSwingOccupied = true;
        System.out.println("Bird " + birdId + " is swinging \uD83E\uDEBD");
    }

    public synchronized void stopSwinging(int birdId) {
        isSwingOccupied = false;
        System.out.println("Bird " + birdId + " is not swinging anymore \uD83E\uDEBD❌");
        notifyAll();
    }
}
