package org.example.threadPool;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class CheckMultiples {
    public static void main(String[] args) {
        // Step 1: Thread pool for generating numbers
        ExecutorService numberGeneratorPool = Executors.newSingleThreadExecutor();
        List<BigInteger> numbers = new ArrayList<>();

        // Generate 50 numbers between 20 and 50 digits
        Runnable generateNumbersTask = () -> {
            Random random = new Random();
            for (int i = 0; i < 50; i++) {
                StringBuilder sb = new StringBuilder();
                sb.append(random.nextInt(9) + 1); // First digit can't be zero
                int numberOfDigits = 20 + random.nextInt(31); // 20 to 50 digits
                for (int j = 1; j < numberOfDigits; j++) {
                    sb.append(random.nextInt(10)); // Add random digits
                }
                numbers.add(new BigInteger(sb.toString()));
            }
        };
        numberGeneratorPool.execute(generateNumbersTask);
        numberGeneratorPool.shutdown();
        while (!numberGeneratorPool.isTerminated()) {
        }

        // Step 2: Thread pool for checking multiples
        ExecutorService multipleCheckerPool = Executors.newFixedThreadPool(12);

        List<Future<Void>> futures = new ArrayList<>();

        for (BigInteger number : numbers) {
            // Submit tasks for checking divisibility by 3, 5, and 11
            futures.add(multipleCheckerPool.submit(new CheckMultipleOf3(number)));
            futures.add(multipleCheckerPool.submit(new CheckMultipleOf5(number)));
            futures.add(multipleCheckerPool.submit(new CheckMultipleOf11(number)));
        }

        // Wait for all tasks to complete
        for (Future<Void> future : futures) {
            try {
                future.get();  // Get the result of the task (blocking call)
            } catch (Exception e) {
                System.err.println("Error checking multiples: " + e.getMessage());
            }
        }

        // Shutdown the thread pool
        multipleCheckerPool.shutdown();
        while (!multipleCheckerPool.isTerminated()) {
        }

        System.out.println("All checks completed.");
    }

    // Callable task to check if a number is a multiple of 3
    static class CheckMultipleOf3 implements Callable<Void> {
        private final BigInteger number;

        public CheckMultipleOf3(BigInteger number) {
            this.number = number;
        }

        @Override
        public Void call() throws Exception {
            boolean isMultiple = isMultipleOf3(number);
            if (isMultiple) { // Only print if true
                synchronized (System.out) {
                    System.out.printf("Number: %s - Multiple of 3: %b%n", number, isMultiple);
                }
            }
            return null;
        }

        private boolean isMultipleOf3(BigInteger number) {
            int sumOfDigits = number.toString().chars()
                    .map(Character::getNumericValue)
                    .sum();
            return sumOfDigits % 3 == 0;
        }
    }

    // Callable task to check if a number is a multiple of 5
    static class CheckMultipleOf5 implements Callable<Void> {
        private final BigInteger number;

        public CheckMultipleOf5(BigInteger number) {
            this.number = number;
        }

        @Override
        public Void call() throws Exception {
            boolean isMultiple = isMultipleOf5(number);
            if (isMultiple) { // Only print if true
                synchronized (System.out) {
                    System.out.printf("Number: %s - Multiple of 5: %b%n", number, isMultiple);
                }
            }
            return null;
        }

        private boolean isMultipleOf5(BigInteger number) {
            char lastDigit = number.toString().charAt(number.toString().length() - 1);
            return lastDigit == '0' || lastDigit == '5';
        }
    }

    // Callable task to check if a number is a multiple of 11
    static class CheckMultipleOf11 implements Callable<Void> {
        private final BigInteger number;

        public CheckMultipleOf11(BigInteger number) {
            this.number = number;
        }

        @Override
        public Void call() throws Exception {
            boolean isMultiple = isMultipleOf11(number);
            if (isMultiple) { // Only print if true
                synchronized (System.out) {
                    System.out.printf("Number: %s - Multiple of 11: %b%n", number, isMultiple);
                }
            }
            return null;
        }

        private boolean isMultipleOf11(BigInteger number) {
            String numStr = number.toString();
            int oddSum = 0, evenSum = 0;
            for (int i = 0; i < numStr.length(); i++) {
                int digit = Character.getNumericValue(numStr.charAt(i));
                if (i % 2 == 0) {
                    oddSum += digit;
                } else {
                    evenSum += digit;
                }
            }
            return (oddSum - evenSum) % 11 == 0;
        }
    }
}
