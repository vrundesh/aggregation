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
import tnt.aggregation.integration.tracking.GetTrackingStatusClient;

import java.io.IOException;
import java.util.*;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;


@RunWith(SpringRunner.class)
public class GetTrackingStatusClientTest {



    @InjectMocks
    private GetTrackingStatusClient getTrackingStatusClient;

    @Mock
    private RestTemplate restTemplate;


    @Before
    public void setup() {
        ReflectionTestUtils.setField(getTrackingStatusClient, "trackingApiUrl", "http://url.com");
    }

    @Test
    public void testGetPricingSuccess() throws IOException {

        ResponseEntity<Map> serviceResponse =
                new ResponseEntity<Map>(getMapForBody(), HttpStatus.OK);

        when(restTemplate.exchange(any(String.class),
                any(HttpMethod.class),
                any(),any(ParameterizedTypeReference.class),any(String.class)))
                .thenReturn(serviceResponse);

        Map<String,String> productPrices=getTrackingStatusClient.getTrackingStatus(getListOfOrders());
        assertNotNull(productPrices);

    }


    @Test(expected = InternalException.class)
    public void testGetPricingFailure() {

        when(restTemplate.exchange(any(String.class),
                any(HttpMethod.class),
                any(),any(ParameterizedTypeReference.class),any(String.class)))
                .thenThrow(InternalException.class);

        Map<String,String> productPrices=getTrackingStatusClient.getTrackingStatus(getListOfOrders());

        assertNull(productPrices);

    }



    private List getListOfOrders() {
        List<Integer> ListOfOrder =new ArrayList<Integer>();
        ListOfOrder.addAll(Arrays.asList(109347263,12345689));
        return ListOfOrder;
    }

    private Map<String,String> getMapForBody() {

        Map<String,String> mapForBody=new HashMap<>();
        mapForBody.put("109347263","COLLECTED");
        mapForBody.put("123456891","DELIVERED");

        return mapForBody;
    }
}
