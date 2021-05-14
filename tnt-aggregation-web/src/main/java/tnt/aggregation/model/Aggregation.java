package tnt.aggregation.model;


import lombok.*;

import java.io.Serializable;
import java.util.List;
import java.util.Map;


@Data
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Aggregation implements Serializable {

   Map<String, String> pricing;
   Map<String,String>  track;
   Map<String, List<String>> shipments;

}
