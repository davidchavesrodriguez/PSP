package gal.classexercises.producerconsumer.bakery;


import java.util.Random;

public class TakeANumber {
    private int nextTicket = 1;
    private int servedTicket = 1;
    Random random = new Random();

    public synchronized void takeTicket() throws InterruptedException {
        Thread.sleep(random.nextInt(2000) + 2000);
        System.out.println("A wild customer appeared with ticket number " + nextTicket);
        notify();
        nextTicket++;
    }

    public synchronized void serveTicket() throws InterruptedException {
        while (nextTicket == servedTicket) {
            System.out.println("Clerk's waiting. There are no customers...");
            wait();
        }
        System.out.println("Clerk started working on ticket #" + servedTicket);
        Thread.sleep(random.nextInt(1000));
        System.out.println("Customer " + servedTicket + " got served");
        servedTicket++;
    }
}
