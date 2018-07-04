package exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.FORBIDDEN, reason = "Service is currently busy monitoring. Please stop the service first.")
public class ServiceBusyException extends RuntimeException {

    public ServiceBusyException() {
    }

    public ServiceBusyException(String message) {
        super(message);
    }
}
