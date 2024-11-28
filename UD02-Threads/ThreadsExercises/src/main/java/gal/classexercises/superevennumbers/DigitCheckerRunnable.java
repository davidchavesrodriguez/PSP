package gal.classexercises.superevennumbers;

import java.util.concurrent.atomic.AtomicBoolean;

public class DigitCheckerRunnable implements Runnable {
    private char digit;
    private Number number;
    AtomicBoolean isSuperEven = new AtomicBoolean(true);

    public DigitCheckerRunnable(char digit, Number number, AtomicBoolean isSuperEven) {
        this.digit = digit;
        this.number = number;
        this.isSuperEven = isSuperEven;
    }

    @Override
    public void run() {
        if (digit != '0' && digit != '2' && digit != '4' && digit != '6' && digit != '8') {
            isSuperEven.set(false);
        }
    }
}
