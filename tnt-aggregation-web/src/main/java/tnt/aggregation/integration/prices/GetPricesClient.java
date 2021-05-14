package tnt.aggregation.integration.prices;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
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
public class GetPricesClient implements GetPrices {

    private final RestTemplate restTemplate;
    public static LocalTime timeAdded = LocalTime.now();


    @Value("${pricing.endpoint}")
    public String pricingApiUrl;

    @Override
    public Map<String, String> getPricing(List<String> listOfCountry) {

        BlockingQueue<String> blockingQueue = associateTimeWithEachCountryElement(listOfCountry);
        Map<String, String> productPrices=new ConcurrentHashMap<>();

        while(!blockingQueue.isEmpty()) {
           if (!blockingQueue.isEmpty() && blockingQueue.size() >= 5) {
               productPrices.putAll(callPricingAPI(blockingQueue, 5,pricingApiUrl));
           }
           if (!blockingQueue.isEmpty() && Duration.between(LocalTime.now(), timeAdded.plusSeconds(4)).isNegative()) { //For less than 5 seconds remaining
               productPrices.putAll(callPricingAPI(blockingQueue, blockingQueue.size(),pricingApiUrl));
           }
        }
        return productPrices;
    }

    private BlockingQueue<String> associateTimeWithEachCountryElement(List<String> listOfCountry) {
        BlockingQueue<String> blockingQueue= new LinkedBlockingDeque<String>();
        for (String element: listOfCountry) {
            blockingQueue.add(element);
            if (blockingQueue.size() == 1) timeAdded = LocalTime.now();
        }
        return blockingQueue;
    }


    private Map<String, String> callPricingAPI(BlockingQueue blockingQueue,int size,String pricingApiUrl) {

        ParameterizedTypeReference<Map<String, String>> responseType =
                new ParameterizedTypeReference<Map<String, String>>() { };

        String listOfIds = getListOfCountriesFromQueue(blockingQueue, size);
        Map<String, String> responseEntity;

        try {
            responseEntity = restTemplate.exchange(pricingApiUrl,
                    HttpMethod.GET, null, responseType, listOfIds).getBody();
        } catch (HttpClientErrorException e) {
            throw (HttpStatus.NOT_FOUND.equals(e.getStatusCode())) ? customIntegrationNotFound() : e;
        }

        return responseEntity;
    }

    private String getListOfCountriesFromQueue(BlockingQueue blockingQueue, int size) {
        Map<String, String> responseEntity = null;
        List<String> arrayList=new ArrayList<String>();
        blockingQueue.drainTo(arrayList, size);
        String listOfIds = arrayList.stream()
                .map(String::valueOf)
                .collect(Collectors.joining(","));
        return listOfIds;
    }


    private InternalException customIntegrationNotFound() {
        return new InternalException(HttpStatus.NOT_FOUND.toString(), FunctionalErrors.INTERNAL_ERROR);
    }

}
