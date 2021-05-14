package tnt.aggregation.exceptions.handlers;

import lombok.Getter;
import tnt.aggregation.exceptions.enums.FunctionalErrors;

@Getter
public class InternalException extends RuntimeException {

    private FunctionalErrors error;

    public InternalException(String message, FunctionalErrors error) {
        super(message);
        this.error = error;
    }

    public InternalException(FunctionalErrors error) {
        super(error.getMessage());
        this.error = error;
    }

    public InternalException(Throwable cause, FunctionalErrors functionalErrors) {
        super(cause);
        this.error = functionalErrors;
    }

}
