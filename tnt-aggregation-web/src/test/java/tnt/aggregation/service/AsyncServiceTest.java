package tnt.aggregation.service;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import tnt.aggregation.integration.prices.GetPricesClient;
import tnt.aggregation.integration.shipment.GetShipmentsClient;
import tnt.aggregation.integration.tracking.GetTrackingStatusClient;

import java.util.concurrent.Executor;

@RunWith(MockitoJUnitRunner.class)
public class AsyncServiceTest {


    @Mock
    private GetPricesClient getPricesClient;

    @Mock
    private GetTrackingStatusClient getTrackingStatusClient;

    @Mock
    private GetShipmentsClient getShipmentsClient;

    @Mock
    private Executor getPricesClientExecutorService;

    @Mock
    private Executor getTrackingStatusClientExecutorService;

    @Mock
    private Executor getShipmentsClientExecutorService;


    @InjectMocks
    private TNTAsyncService service;


    @Test
    public void testSomeMethod() {
        /*// Mock the method execute to call the run method of the provided Runnable
        doAnswer(
                (InvocationOnMock invocation) -> {
                    ((Runnable) invocation.getArguments()[0]).run();
                    return null;
                }
        ).when(serviceAExecutorService).execute(any(Runnable.class));

        doAnswer(
                (InvocationOnMock invocation) -> {
                    ((Runnable) invocation.getArguments()[0]).run();
                    return null;
                }
        ).when(serviceBExecutorService).execute(any(Runnable.class));

        ServiceAResponse serviceAResponse = ... // The answer to return by service A
        // Make the mock of my service A return my answer
        when(ServiceA.retrieve(any(ServiceARequest.class))).thenReturn(
                serviceAResponse
        );
        ServiceBResponse serviceBResponse = ... // The answer to return by service B
        // Make the mock of my service B return my answer
        when(ServiceB.retrieve(any(ServiceBRequest.class))).thenReturn(
                serviceBResponse
        );

        // Execute my method
        ServiceResponse response = service.someMethod(
                new ServiceARequest(), new ServiceBRequest()
        );

        // Test the result assuming that both responses are wrapped into a POJO
        Assert.assertEquals(serviceAResponse, response.getServiceAResponse());
        Assert.assertEquals(serviceBResponse, response.getServiceBResponse());*/
    }

}
