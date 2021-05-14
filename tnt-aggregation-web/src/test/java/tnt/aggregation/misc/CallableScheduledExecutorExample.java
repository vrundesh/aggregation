package tnt.aggregation.misc;

import java.util.concurrent.*;

/**
 * CallableScheduledExecutorExample.java
 *
 * This program demonstrates how to schedule a Callable task to execute after
 * a given delay, and wait for the result becomes available.
 *
 * @author www.codejava.net
 */
public class CallableScheduledExecutorExample {
    public static void main(String[] args) {

        ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();

        Callable<Integer> task = new Callable<Integer>() {
            public Integer call() {
                // fake computation time
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
                return 1000000;
            }
        };
        int delay = 5;
        Future<Integer> result = scheduler.schedule(task, delay, TimeUnit.SECONDS);

        try {
            Integer value = result.get();
            System.out.println("value = " + value);

        } catch (InterruptedException | ExecutionException ex) {
            ex.printStackTrace();
        }
        scheduler.shutdown();
    }
}