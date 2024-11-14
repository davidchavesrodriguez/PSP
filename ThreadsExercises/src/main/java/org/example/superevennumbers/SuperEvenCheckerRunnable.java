package org.example.superevennumbers;

public class SuperEvenCheckerRunnable implements Runnable {
    public String numberString;
    public boolean isSuperEven = true;

    public SuperEvenCheckerRunnable(String numberString) {
        this.numberString = numberString;
    }



    @Override
    public void run() {
        for (int i = 0; i < numberString.length(); i++) {
            if (numberString.charAt(i) % 2 == 0) {
                isSuperEven = false;
                break;
            }
        }
    }
}
