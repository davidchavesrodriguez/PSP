package gal.classexercises.producerconsumer.birds;

public class LaunchBirds {
    public static void main(String[] args) {
        Cage cage = new Cage();
        int numBirds = 10;

        for (int i = 1; i <= numBirds; i++) {
            Thread birdThread = new Thread(new Bird(i, cage));
            birdThread.start();
        }

    }
}
