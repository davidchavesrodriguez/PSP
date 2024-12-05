package gal.classexercises.producerconsumer.bakery;

public class Customer implements Runnable {
    private TakeANumber takeANumber;

    public Customer(TakeANumber takeANumber) {
        this.takeANumber = takeANumber;
    }

    @Override
    public void run() {
        try {
            takeANumber.takeTicket();
        } catch (InterruptedException e) {
            System.out.println("Customer has been interrupted.");
        }
    }
}
