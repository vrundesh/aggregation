package tnt.aggregation.integration.shipment;

import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

public interface GetShipments {

    Map<String, List<String>> getShipments(List<Integer> shipmentOrderNrs);
}
