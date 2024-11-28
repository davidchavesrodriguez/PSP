package org.example;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) throws InterruptedException {
        InfoRunnable ir = new InfoRunnable();
        Thread t1 = new Thread(ir);
        Thread t2 = new Thread(ir);


        t1.start();
        t2.start();

        Thread t3 = new Thread(ir, "Thread Pepe");
        t3.start();

        Thread t4 = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("Runnable anonymous");
            }

        });

        t4.start();
        while (t4.isAlive()) {
            Thread.sleep(500L);
        }


//        Thread t5= new Thread(runnable);


        Thread t6 = new PrintingThread();
        t6.start();
        t6.join();

    }
}