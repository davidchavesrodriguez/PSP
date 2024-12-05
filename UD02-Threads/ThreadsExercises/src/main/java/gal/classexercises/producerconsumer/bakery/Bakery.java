package gal.classexercises.producerconsumer.bakery;

public class Bakery {
    public static void main(String[] args) {
        TakeANumber takeANumber = new TakeANumber();

        Clerk clerk = new Clerk(takeANumber);
        Thread clerkThread = new Thread(clerk);

        clerkThread.start();

        int numCustomers = 100;
        for (int i = 0; i < numCustomers; i++) {
            Customer customer = new Customer(takeANumber);
            Thread customerThread = new Thread(customer);
            customerThread.start();
        }

        try {
            clerkThread.join();
        } catch (InterruptedException e) {
            System.out.println("Main thread interrupted.");
        }

        System.out.println("Simulation ended: All customers have been served.");
    }
}
