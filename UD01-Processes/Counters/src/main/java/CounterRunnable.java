public class CounterRunnable implements Runnable {
    private static final int NUM_OPERATIONS = 500;
    private Counter counter;

    public CounterRunnable(Counter counter) {
        this.counter = counter;
    }

    @Override
    public void run() {
        for (int i = 0; i < NUM_OPERATIONS; i++) {
            counter.increase();
            System.out.println(Thread.currentThread().getName() + " " + counter.getCount());
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
