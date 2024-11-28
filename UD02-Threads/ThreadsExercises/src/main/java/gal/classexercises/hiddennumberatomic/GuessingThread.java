package gal.classexercises.hiddennumberatomic;

import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

public class GuessingThread implements Runnable {
    private HiddenNumber hiddenNumber;
    boolean gameWon = false;

    public GuessingThread(HiddenNumber hiddenNumber) {
        this.hiddenNumber = hiddenNumber;
    }

    @Override
    public void run() {
        Random randomGenerator = new Random();
        int randomGuess;

        // Start to guess
        while (!gameWon && !hiddenNumber.getGameOver().get()) {
            randomGuess = randomGenerator.nextInt(101);

            // Call numberGuess method
            int result = hiddenNumber.numberGuess(new AtomicInteger(randomGuess));

            // Control the loop
            if (result == 1 || result == -1) {
                gameWon = true;
                System.out.println(Thread.currentThread().getName() + " has won by guessing " + randomGuess + "!");
            } else {
                System.out.println(Thread.currentThread().getName() + " has guessed with " + randomGuess);
            }
        }
    }
}
