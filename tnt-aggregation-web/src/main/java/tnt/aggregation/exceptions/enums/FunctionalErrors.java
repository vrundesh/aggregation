package tnt.aggregation.exceptions.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public enum FunctionalErrors {

    INTERNAL_ERROR("Internal error occurred, please try again later", "10"),
    MALFORMED_REQUEST("Malformed request", "10"),
    THE_ORDER_TRANSACTION_ID_IS_NOT_AUTHORIZED("The OrderTransactionId is not authorized", "40001");

    private String message;

    private String code;

    public String getFormattedMessage(final String arg) {
        return String.format(message, arg);
    }
}
