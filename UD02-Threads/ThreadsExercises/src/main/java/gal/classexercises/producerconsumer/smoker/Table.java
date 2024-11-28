package gal.classexercises.producerconsumer.smoker;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Table {
    List<String> ingredients = Arrays.asList("Tobacco", "Paper", "Lighter");
    List<String> ingredientsAvailable = new ArrayList<>();

    public synchronized void addIngredients(String firstIngredient, String secondIngredient) throws InterruptedException {
        while (!ingredientsAvailable.isEmpty()) {
            wait();
        }

        ingredientsAvailable.add(firstIngredient);
        ingredientsAvailable.add(secondIngredient);

        System.out.println("Tobacconist placed: " + firstIngredient + " and " + secondIngredient);
        notifyAll();
    }


    public synchronized boolean canSmoke(String smokerIngredient) {
        return ingredientsAvailable.containsAll(ingredients.stream()
                .filter(ingredient -> !ingredient.equals(smokerIngredient)).toList());
    }


}
