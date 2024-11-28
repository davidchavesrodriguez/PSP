package gal.classexercises.hiddennumbersynchronized;

import java.util.Random;

public class GuessingThread implements Runnable{
    private HiddenNumber hiddenNumber;

    public GuessingThread(HiddenNumber hiddenNumber) {
        this.hiddenNumber = hiddenNumber;
    }

    @Override
    public void run() {
        Random randomGenerator= new Random();
        int randomGuess;

        while (!hiddenNumber.isGameOver()){
            randomGuess= randomGenerator.nextInt(101);

            int result= hiddenNumber.numberGuess(randomGuess);

            if (result==1){
                System.out.println(Thread.currentThread().getName() + " has won by guessing " + randomGuess);
            } else {
                System.out.println(Thread.currentThread().getName() + " guessed " + randomGuess);
            }
        }
    }
}
