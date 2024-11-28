package gal.classexercises.producerconsumer.pizza;

import java.util.LinkedList;

public class ResourcePizzaTray {
    private LinkedList<Pizza> pizzas = new LinkedList<>();
    private final int MAX_CAPACITY = 5;

    public synchronized void addPizza(Pizza pizza) throws InterruptedException {
        while (pizzas.size() == MAX_CAPACITY) {
            wait();
        }
        pizzas.add(pizza);
        System.out.println("Cook placed pizza number " + pizza.getId()
                + " in the tray (" + pizza.getName() + ", " + pizza.getPrice() + "€)");
        notifyAll();
    }

    public synchronized Pizza takePizza() throws InterruptedException {
        while (pizzas.isEmpty()) {
            wait();
        }
        Pizza pizza = pizzas.removeFirst();
        System.out.println("Delivered pizza " + pizza.getId());
        notifyAll();
        return pizza;
    }
}
