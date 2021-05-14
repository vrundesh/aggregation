package tnt.aggregation.misc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.*;

public class TestExecutorScheduled {

    public static void main(String args[]) throws ExecutionException, InterruptedException {

        List<String> listOfCountry= Arrays.asList("TG","TY","CN");
        BlockingQueue<String> blockingQueue= new LinkedBlockingDeque<String>();
        for (String element:listOfCountry) {  blockingQueue.add(element);}
        ScheduledFuture scheduledFuture=getListToSent(blockingQueue);
        System.out.println(scheduledFuture.get());
    }

    public static ScheduledFuture getListToSent(BlockingQueue<String> blockingQueue) throws ExecutionException, InterruptedException {
        ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(5);
        List arrayList=new ArrayList<String>();
        ScheduledFuture scheduledFuture = scheduledExecutorService.schedule((Callable) () -> {
            if(blockingQueue.size()>=5){
                System.out.println("Size is greater than 5!");
                blockingQueue.drainTo(arrayList,5);
            }else{
                System.out.println("Size is less than 5!");
                blockingQueue.drainTo(arrayList);
                scheduledExecutorService.shutdown();
            }
            return arrayList;
        },5, TimeUnit.SECONDS);
        return scheduledFuture;
    }
}
