package gal.classexercises.producerconsumer.bakery;

public class Clerk implements Runnable {
    private TakeANumber takeANumber;

    public Clerk(TakeANumber takeANumber) {
        this.takeANumber = takeANumber;
    }

    @Override
    public void run() {
        try {
            while (true) {
                takeANumber.serveTicket();
            }
        } catch (InterruptedException e) {
            System.out.println("Clerk has finished serving customers.");
        }
    }
}
