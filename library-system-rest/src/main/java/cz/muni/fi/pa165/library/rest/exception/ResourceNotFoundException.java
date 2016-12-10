package cz.muni.fi.pa165.library.rest.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exception thrown when the requested resource was not found.
 *
 * @author Dávid Lupták
 * @version 9.12.2016
 */
@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "The requested resource not found.")
public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(Throwable cause) {
        super(cause);
    }
}
