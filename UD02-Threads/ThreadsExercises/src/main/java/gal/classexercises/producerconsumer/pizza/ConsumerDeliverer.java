package gal.classexercises.producerconsumer.pizza;

public class ConsumerDeliverer implements Runnable {
    private ResourcePizzaTray tray;
    private static int pizzasDelivered = 0;

    public ConsumerDeliverer(ResourcePizzaTray tray) {
        this.tray = tray;
    }

    @Override
    public void run() {
        try {
            while (pizzasDelivered < 100) {
                Thread.sleep((long) (1000 + Math.random() * 1000));
                tray.takePizza();
                pizzasDelivered++;
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
