package tnt.aggregation.integration.tracking;

import java.util.List;
import java.util.Map;


public interface GetTrackingStatus {

    Map<String, String> getTrackingStatus(List<Integer> orderNumbers);
}
