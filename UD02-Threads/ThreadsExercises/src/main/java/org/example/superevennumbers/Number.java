package org.example.superevennumbers;

import java.util.concurrent.atomic.AtomicBoolean;

public class Number {
    private int value;
    private AtomicBoolean isSuperEven;

    public Number(int value) {
        this.value = value;
        this.isSuperEven = new AtomicBoolean(true);
    }

    public int getValue() {
        return value;
    }

    public AtomicBoolean getIsSuperEven() {
        return isSuperEven;
    }

    public void setIsSuperEven(boolean isSuperEven) {
        this.isSuperEven.set(isSuperEven);
    }
}
