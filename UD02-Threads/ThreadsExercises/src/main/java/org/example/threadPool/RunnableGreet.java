package org.example.threadPool;

public class RunnableGreet implements Runnable {
    private final String name;

    public RunnableGreet(String name) {
        this.name = name;
    }

    @Override
    public void run() {
        System.out.println("How are you, " + name + "?");
    }
}
