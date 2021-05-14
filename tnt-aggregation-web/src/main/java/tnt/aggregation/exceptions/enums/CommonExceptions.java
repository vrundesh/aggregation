package tnt.aggregation.exceptions.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public enum CommonExceptions {

    INTERNAL_ERROR("10", "Internal error occurred, please try again later"),
    MALFORMED_REQUEST("10", "Malformed request");

    private String code;

    private String message;

}
