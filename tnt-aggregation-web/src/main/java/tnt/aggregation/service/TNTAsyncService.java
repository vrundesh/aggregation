package tnt.aggregation.service;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import tnt.aggregation.integration.prices.GetPricesClient;
import tnt.aggregation.integration.shipment.GetShipmentsClient;
import tnt.aggregation.integration.tracking.GetTrackingStatusClient;
import tnt.aggregation.model.Aggregation;

import java.util.List;
import java.util.concurrent.CompletableFuture;


@Slf4j
@Service
@RequiredArgsConstructor
public class TNTAsyncService {

	private final GetPricesClient getPricesClient;
	private final GetTrackingStatusClient getTrackingStatusClient;
	private final GetShipmentsClient getShipmentsClient;

	public Aggregation getAllDataAggregated(List<String> pricing , List<Integer> trackingOrderNrs, List<Integer> shipmentOrderNrs ) {

		Aggregation aggregation=new Aggregation();
		CompletableFuture.allOf(
				CompletableFuture
						.supplyAsync( ()-> getPricesClient.getPricing(pricing))
						.thenAccept(pricingStringMap -> aggregation.setPricing(pricingStringMap))
				        .handle((t, ex) -> {log.info("Exception Occur while getting Prices");	return null;})
				,CompletableFuture
				        .supplyAsync(() -> getTrackingStatusClient.getTrackingStatus(trackingOrderNrs))
				        .thenAccept(trackingStringMap -> aggregation.setTrack(trackingStringMap))
						.handle((t, ex) -> {log.info("Exception Occur while getting Tracking Status");	return null;})
				,CompletableFuture
				        .supplyAsync(() -> getShipmentsClient.getShipments(shipmentOrderNrs))
				        .thenAccept(shipmentsStringMap -> aggregation.setShipments(shipmentsStringMap))
				        .handle((t, ex) -> {log.info("Exception Occur while getting Shipment Info");	return null;})
		).join();
		return aggregation;
	}
}
