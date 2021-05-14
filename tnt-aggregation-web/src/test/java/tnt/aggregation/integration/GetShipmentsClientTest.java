package tnt.aggregation.integration;


import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.RestTemplate;
import tnt.aggregation.exceptions.handlers.InternalException;
import tnt.aggregation.integration.shipment.GetShipmentsClient;

import java.io.IOException;
import java.util.*;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
public class GetShipmentsClientTest {


    @InjectMocks
    private GetShipmentsClient getShipmentsClient;

    @Mock
    private RestTemplate restTemplate;


    @Before
    public void setup() {
        ReflectionTestUtils.setField(getShipmentsClient, "shipmentApiUrl", "http://url.com");
    }


    @Test
    public void testGetShipmentsSuccess() throws IOException {

        ResponseEntity<Map> serviceResponse =
                new ResponseEntity<Map>(getMapForBody(), HttpStatus.OK);

        when(restTemplate.exchange(any(String.class),
                any(HttpMethod.class),
                any(),any(ParameterizedTypeReference.class),any(String.class)))
                .thenReturn(serviceResponse);

        Map<String,String> productPrices=getShipmentsClient.getShipments(getListOfOrders());
        assertNotNull(productPrices);

    }

    @Test(expected = InternalException.class)
    public void testGetShipmentsFailure() {

        when(restTemplate.exchange(any(String.class),
                any(HttpMethod.class),
                any(),any(ParameterizedTypeReference.class),any(String.class)))
                .thenThrow(InternalException.class);

        Map<String,String> productPrices=getShipmentsClient.getShipments(getListOfOrders());

        assertNull(productPrices);

    }


    private List getListOfOrders() {
        List<Integer> ListOfOrder =new ArrayList<Integer>();
        ListOfOrder.addAll(Arrays.asList(109347263,12345689));
        return ListOfOrder;
    }

    private Map<String,List<String>> getMapForBody() {
        Map<String,List<String>> mapForBody=new HashMap<>();

        List<String> listOfShipmentsForFirstOrder=new ArrayList<>();
        listOfShipmentsForFirstOrder.addAll(Arrays.asList("box","envelop"));

        List<String> listOfShipmentsForSecondOrder=new ArrayList<>();
        listOfShipmentsForSecondOrder.addAll(Arrays.asList("box"));

        mapForBody.put("109347263",listOfShipmentsForFirstOrder);
        mapForBody.put("123456891",listOfShipmentsForSecondOrder);

        return mapForBody;
    }



}
