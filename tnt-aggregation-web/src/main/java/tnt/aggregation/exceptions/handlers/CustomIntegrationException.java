package tnt.aggregation.exceptions.handlers;

import org.springframework.http.HttpStatus;

public class CustomIntegrationException extends CustomExceptionBase {
    public CustomIntegrationException(String rejectionReason, String rejectionCode, HttpStatus result, String message) {
        super(rejectionReason, rejectionCode, result, message);
    }
}
