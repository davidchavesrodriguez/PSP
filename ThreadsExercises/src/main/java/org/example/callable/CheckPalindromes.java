package org.example.callable;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

public class CheckPalindromes implements Callable<Boolean> {
    private final String word;
    private final HashSet<String> palindromes;

    public CheckPalindromes(String word, HashSet<String> palindromes) {
        this.word = word;
        this.palindromes = palindromes;
    }

    public static void main(String[] args) {
        String[] words = {"kayak", "hello", "deified", "world", "level", "java", "noon", "python", "civic", "racecar"};

        HashSet<String> palindromes = new HashSet<>();

        List<FutureTask<Boolean>> tasks = new ArrayList<>();

        for (String word : words) {
            CheckPalindromes task = new CheckPalindromes(word, palindromes);

            FutureTask<Boolean> futureTask = new FutureTask<>(task);

            // Create a thread to execute the FutureTask
            Thread thread = new Thread(futureTask);
            thread.start();

            tasks.add(futureTask);
        }

        // Wait for all tasks to complete and print the results
        for (int i = 0; i < words.length; i++) {
            FutureTask<Boolean> futureTask = tasks.get(i);
            String word = words[i];

            try {
                if (futureTask.get()) {
                    System.out.println("Word \"" + word + "\" is a palindrome");
                } else {
                    System.out.println("Word \"" + word + "\" is NOT a palindrome");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        System.out.println("Palindromes: " + palindromes);
    }

    @Override
    public Boolean call() {
        // Reverse the word and check if it is the same as the original
        String reversedWord = new StringBuilder(word).reverse().toString();
        if (!word.equals(reversedWord)) {
            return false;
        }

        // If it is a palindrome, add it to the HashSet
        palindromes.add(word);
        return true;
    }
}
