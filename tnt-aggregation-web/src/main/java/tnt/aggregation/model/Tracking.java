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
public class Tracking implements Serializable {

   private Map<String, String> trackingInfo;

}
