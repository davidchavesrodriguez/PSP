package org.example.hiddennumbersynchronized;

public class HiddenNumber {
    private int hiddenNumber;
    private boolean gameOver;

    public synchronized boolean isGameOver() {
        return gameOver;
    }

    public synchronized void setGameOver(boolean gameOver) {
        this.gameOver = gameOver;
    }

    public synchronized int getHiddenNumber() {
        return hiddenNumber;
    }

    public synchronized void setHiddenNumber(int hiddenNumber) {
        this.hiddenNumber = hiddenNumber;
    }

    public HiddenNumber(int hiddenNumber) {
        this.hiddenNumber = hiddenNumber;
        this.gameOver = false;
    }

    public synchronized int numberGuess(int guessedNumber) {
        if (gameOver) {
            return -1;
        } else if (hiddenNumber == guessedNumber) {
            gameOver = true;
            return 1;
        } else {
            return 0;
        }
    }
}
