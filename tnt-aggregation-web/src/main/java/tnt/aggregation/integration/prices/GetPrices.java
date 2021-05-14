package tnt.aggregation.integration.prices;

import tnt.aggregation.model.Price;
import tnt.aggregation.model.Shipment;
import tnt.aggregation.model.Tracking;

import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public interface GetPrices {

      Map<String, String> getPricing(List<String> listOfCountry) throws ExecutionException, InterruptedException;
}
