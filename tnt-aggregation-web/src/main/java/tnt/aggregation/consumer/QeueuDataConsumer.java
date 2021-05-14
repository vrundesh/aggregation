package tnt.aggregation.consumer;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;


public class QeueuDataConsumer {

    public static ScheduledFuture getListToSent(BlockingQueue<String> blockingQueue)  {
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
