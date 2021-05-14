package tnt.aggregation.misc;

import java.time.Instant;
import java.util.*;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.TimeUnit;

public class TestBlockingQuqeu {

    private static long EXPIRED_TIME_IN_SEC = 5l;

    public static void main(String[] args) throws InterruptedException {

        List<String> listOfCountry= Arrays.asList("TG","TY","CN","GB","FR");
        BlockingQueue<String> blockingQueue= new LinkedBlockingDeque<String>();
        for (String element:listOfCountry) {  blockingQueue.add(element);}
        System.out.println("Blocking Queue size Before Removing" +blockingQueue.size());
        List<String> listOfIds = new ArrayList<String>();

        long timestampMillis = System.currentTimeMillis();
        long timestampSeconds = TimeUnit.MILLISECONDS.toSeconds(timestampMillis);
        System.out.println("Seconds: " + timestampSeconds);

        System.out.println("Items in list are");
        listOfIds.forEach(System.out::print);

        Date currentTime=new Date();
        Date actualExpiredTime=new Date();

        actualExpiredTime.setTime(currentTime.getTime()-EXPIRED_TIME_IN_SEC * 1000l);

        BlockingQueue<Map<Double, ArrayList<Date>>> blockingQueue1=new LinkedBlockingDeque<Map<Double, ArrayList<Date>>>();


        System.out.println("actualExpiredTime = " +actualExpiredTime);
        System.out.println("Blocking Queue size After Removing"+blockingQueue.size());


        BlockingQueue<Map<String, Instant>> blockingQueue2= new LinkedBlockingDeque<>();
        Map<String,Instant> myMap=new HashMap<String,Instant>();

        blockingQueue.stream().forEach(System.out::println);
    }



}

