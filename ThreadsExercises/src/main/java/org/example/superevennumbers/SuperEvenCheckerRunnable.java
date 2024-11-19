package org.example.superevennumbers;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

public class SuperEvenCheckerRunnable implements Runnable {
    private Number number;
    AtomicBoolean isSuperEven = new AtomicBoolean(true);

    public SuperEvenCheckerRunnable(Number number) {
        this.number = number;
    }

    @Override
    public void run() {
        String numStr = String.valueOf(number.getValue());
        ExecutorService executor = Executors.newFixedThreadPool(2);
        AtomicBoolean isSuperEven = new AtomicBoolean(true);

        for (char digit : numStr.toCharArray()) {
            DigitCheckerRunnable digitCheckerRunnable = new DigitCheckerRunnable(digit, number, isSuperEven);
            executor.submit(digitCheckerRunnable);
        }

        executor.shutdown();

        try {
            if (!executor.awaitTermination(60, TimeUnit.SECONDS)) {
                executor.shutdownNow();
            }
        } catch (InterruptedException e) {
            executor.shutdownNow();
            Thread.currentThread().interrupt();
        }

        if (isSuperEven.get()) {
            System.out.println("Number " + number.getValue() + " is super even.");
        }
//        } else {
//            System.out.println("Number " + number.getValue() + " is not super even.");
//        }

    }
}
