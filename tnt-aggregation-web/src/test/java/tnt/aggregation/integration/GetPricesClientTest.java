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
import tnt.aggregation.integration.prices.GetPricesClient;

import java.io.IOException;
import java.util.*;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
public class GetPricesClientTest {

    @InjectMocks
    private GetPricesClient getPricesClient;

    @Mock
    private RestTemplate restTemplate;


    @Before
    public void setup() {
        ReflectionTestUtils.setField(getPricesClient, "pricingApiUrl", "http://url.com");
    }

    @Test
    public void testGetPricingSuccess() throws IOException {

        ResponseEntity<Map> serviceResponse =
                new ResponseEntity<Map>(getMapForBody(), HttpStatus.OK);

        when(restTemplate.exchange(any(String.class),
                any(HttpMethod.class),
                any(),any(ParameterizedTypeReference.class),any(String.class)))
                .thenReturn(serviceResponse);

        Map<String,String> productPrices=getPricesClient.getPricing(getListOfCountry());
        assertNotNull(productPrices);

    }


    @Test(expected = InternalException.class)
    public void testGetPricingFailure() {

        when(restTemplate.exchange(any(String.class),
                any(HttpMethod.class),
                any(),any(ParameterizedTypeReference.class),any(String.class)))
                .thenThrow(InternalException.class);

        Map<String,String> productPrices=getPricesClient.getPricing(getListOfCountry());

        assertNull(productPrices);

    }

    private List getListOfCountry() {
        List<String> listOfCountry =new ArrayList<String>();
        listOfCountry.addAll(Arrays.asList("IN","UK","CN","FR","PL","Nl","US"));
        return listOfCountry;
    }

    private Map<String,String> getMapForBody() {
        Map<String,String> mapForBody=new HashMap<>();
        mapForBody.put("IN","40.55860889509707");
        mapForBody.put("UK","84.19991454361984");
        mapForBody.put("CN", "64.71328246615147");
        mapForBody.put("FR","41.48161826004061");
        mapForBody.put("PL","76.10018902345672");
        mapForBody.put("NL","76.10018902345672");
        mapForBody.put("US","76.10018902345672");
        return mapForBody;
    }


}
