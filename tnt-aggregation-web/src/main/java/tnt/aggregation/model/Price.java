package tnt.aggregation.model;

import lombok.*;

import java.io.Serializable;
import java.util.Map;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class Price implements Serializable {

    private Map<String, String> priceInfo;
}
