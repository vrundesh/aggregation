package tnt.aggregation.exceptions.handlers;

import lombok.Getter;
import tnt.aggregation.exceptions.enums.FunctionalErrors;


@Getter
public class IntegrationException extends RuntimeException {

    private final String code;

    public IntegrationException(final FunctionalErrors error) {
        super(error.getMessage());
        this.code = error.getCode();
    }
}

