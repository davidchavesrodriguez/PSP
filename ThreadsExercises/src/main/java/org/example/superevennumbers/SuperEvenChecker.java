package org.example.superevennumbers;

import java.util.concurrent.Callable;

public class SuperEvenChecker implements Callable<Boolean> {
    private final String numberString;

    public SuperEvenChecker(String numberString) {
        this.numberString = numberString;
    }

    @Override
    public Boolean call() throws Exception {
        for (int i = 0; i < numberString.length(); i++) {
            if (!(numberString.charAt(i) % 2 == 0)) {
                return false;
            }
        }
        return true;
    }
}
