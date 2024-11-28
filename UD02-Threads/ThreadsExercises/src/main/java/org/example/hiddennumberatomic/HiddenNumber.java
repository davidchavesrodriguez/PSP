package org.example.hiddennumberatomic;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

public class HiddenNumber {
    private AtomicInteger hiddenNumber;
    private AtomicBoolean gameOver;

    public HiddenNumber() {
    }

    public HiddenNumber(int hiddenNumber) {
        this.hiddenNumber = new AtomicInteger(hiddenNumber);
        this.gameOver = new AtomicBoolean(false);
    }

    public AtomicInteger getHiddenNumber() {
        return hiddenNumber;
    }

    public void setHiddenNumber(AtomicInteger hiddenNumber) {
        this.hiddenNumber = hiddenNumber;
    }

    public AtomicBoolean getGameOver() {
        return gameOver;
    }

    public void setGameOver(AtomicBoolean gameOver) {
        this.gameOver = gameOver;
    }

    public int numberGuess(AtomicInteger guessedNumber) {
        if (gameOver.get()) {
            return -1; // Game finished
        } else if (hiddenNumber.get() == guessedNumber.get()) {
            gameOver.set(true); // Thread guessed right
            return 1;
        } else {
            return 0;
        }
    }
}
