package tnt.aggregation.model;

import lombok.*;

import java.util.List;
import java.util.Map;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class Shipment {

    private Map<String, String> shipmentInfo;
}
