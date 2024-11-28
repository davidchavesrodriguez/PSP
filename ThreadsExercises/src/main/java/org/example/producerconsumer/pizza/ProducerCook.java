package org.example.producerconsumer.pizza;

public class ProducerCook implements Runnable {
    private static int idPizza = 1;
    private ResourcePizzaTray tray;

    public ProducerCook(ResourcePizzaTray tray) {
        this.tray = tray;
    }

    @Override
    public void run() {
        try {
            while (idPizza <= 100) {
                Thread.sleep((long) (500 + Math.random() * 500));
                Pizza pizza = new Pizza(idPizza++);
                tray.addPizza(pizza);
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
