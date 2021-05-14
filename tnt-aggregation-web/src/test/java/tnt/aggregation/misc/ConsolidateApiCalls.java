package tnt.aggregation.misc;

import org.json.JSONException;

import java.time.Duration;
import java.time.LocalTime;
import java.util.concurrent.*;

public class ConsolidateApiCalls {

    public static final ConcurrentLinkedDeque<String> queue = new ConcurrentLinkedDeque<>();
    public static LocalTime timeAdded = LocalTime.now();

    public static void main(String[] args) throws JSONException, ExecutionException, InterruptedException {
        ScheduledExecutorService executor = Executors.newScheduledThreadPool(4);
        executor.scheduleAtFixedRate(() -> {
                    queue.add("NL");
                    if (queue.size() == 1) timeAdded = LocalTime.now();
                },1, 500, TimeUnit.MILLISECONDS);

        executor.scheduleAtFixedRate(() -> {
                    if (queue.size() >= 4) {
                        callAPI("size");
                    }
                },1, 200, TimeUnit.MILLISECONDS);

        executor.scheduleAtFixedRate(() -> {
                    if (Duration.between(LocalTime.now(), timeAdded.plusSeconds(4)).isNegative() && !queue.isEmpty()) {
                        callAPI("time");
                    }
                },1, 500, TimeUnit.MILLISECONDS);
    }

    public static void callAPI(String reason) {
        System.out.println("API called due to " + reason + " limit.");
        StringBuilder result = new StringBuilder();
        queue.forEach(s -> result.append(s).append(": ").append(ThreadLocalRandom.current().nextDouble()).append(", "));
        queue.clear();
        System.out.println(result);
    }}