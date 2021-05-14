package tnt.aggregation.exceptions.handlers;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.beans.ConstructorProperties;

@Getter
@Setter
public class CustomExceptionBase extends RuntimeException {
    protected final String rejectionReason;
    protected final String rejectionCode;
    protected final HttpStatus result;
    protected final String message;

    @ConstructorProperties({"rejectionReason", "rejectionCode", "result", "message"})
    public CustomExceptionBase(String rejectionReason, String rejectionCode, HttpStatus result, String message) {
        this.rejectionReason = rejectionReason;
        this.rejectionCode = rejectionCode;
        this.result = result;
        this.message = message;
    }
}