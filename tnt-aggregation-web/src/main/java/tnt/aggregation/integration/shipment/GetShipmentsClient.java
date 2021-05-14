package tnt.aggregation.integration.shipment;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import tnt.aggregation.exceptions.enums.FunctionalErrors;
import tnt.aggregation.exceptions.handlers.InternalException;

import java.time.Duration;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;
import java.util.stream.Collectors;

@Slf4j
@Component
@RequiredArgsConstructor
public class GetShipmentsClient implements GetShipments {


    private final RestTemplate restTemplate;
    public static LocalTime timeAdded = LocalTime.now();

    @Value("${shipment.endpoint}")
    public String shipmentApiUrl;

    @Override
    public Map<String,List<String>> getShipments(List<Integer> orderNumbers) {


        BlockingQueue<Integer> blockingQueue = associateTimeWithEachOrderNumberElement(orderNumbers);

        Map<String, List<String>> listOfProducts=new ConcurrentHashMap<>();

        while(!blockingQueue.isEmpty()) {

            if (!blockingQueue.isEmpty() && blockingQueue.size() >= 5) {
                listOfProducts.putAll(callShipmentAPI(blockingQueue, 5,shipmentApiUrl));
            }
            if (!blockingQueue.isEmpty() && Duration.between(LocalTime.now(), timeAdded.plusSeconds(4)).isNegative()) { //For less than 5 seconds remaining
                listOfProducts.putAll(callShipmentAPI(blockingQueue, blockingQueue.size(),shipmentApiUrl));
            }

        }
        return listOfProducts;

    }

    private Map<String, List<String>> callShipmentAPI(BlockingQueue blockingQueue,int size,String shipmentApiUrl){

          ParameterizedTypeReference<Map<String, List<String>>> responseType =
                new ParameterizedTypeReference<Map<String, List<String>>>() {};
        ResponseEntity<Map<String, List<String>>> responseEntity;

        String listOfIds = getListOfOrderNumbersForShipmentsFromQueue(blockingQueue, size);

        try {
            responseEntity= restTemplate.exchange(shipmentApiUrl, HttpMethod.GET, null,responseType,listOfIds);
        } catch (HttpClientErrorException e) {
            throw (HttpStatus.NOT_FOUND.equals(e.getStatusCode())) ? customIntegrationNotFound() : e;
        }
        return responseEntity.getBody();


    }

    private String getListOfOrderNumbersForShipmentsFromQueue(BlockingQueue blockingQueue, int size) {
        List<Integer> arrayList=new ArrayList<Integer>();
        blockingQueue.drainTo(arrayList, size);
        String listOfIds = arrayList.stream()
                .map(String::valueOf)
                .collect(Collectors.joining(","));
        return listOfIds;
    }

    private BlockingQueue<Integer> associateTimeWithEachOrderNumberElement(List<Integer> orderNumbers) {
        BlockingQueue<Integer> blockingQueue= new LinkedBlockingDeque<Integer>();
        for (Integer element: orderNumbers) {
            blockingQueue.add(element);
            if (blockingQueue.size() == 1) timeAdded = LocalTime.now();
        }
        return blockingQueue;
    }

    private InternalException customIntegrationNotFound() {
        return new InternalException(HttpStatus.NOT_FOUND.toString(), FunctionalErrors.INTERNAL_ERROR);
    }
}
