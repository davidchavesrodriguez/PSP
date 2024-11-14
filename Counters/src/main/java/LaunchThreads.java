import java.util.ArrayList;
import java.util.List;

public class LaunchThreads {
    public static final int NUM_THREADS= 10;
    public static void main(String[] args) throws InterruptedException {
        Counter counter = new Counter();
        List<Thread> listOfThreads = new ArrayList<>();

        for (int i = 0; i < NUM_THREADS; i++) {
            CounterRunnable counterRunnable= new CounterRunnable(counter);
            Thread thread= new Thread(counterRunnable);
            listOfThreads.add(thread);
            thread.start();
        }

        for (int i = 0; i < NUM_THREADS; i++) {
            listOfThreads.get(i).join();
        }

        System.out.println("The final value of counter is: " + counter.getCount());
    }
}
