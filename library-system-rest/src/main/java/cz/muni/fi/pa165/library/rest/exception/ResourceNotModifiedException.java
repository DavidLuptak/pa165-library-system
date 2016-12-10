package cz.muni.fi.pa165.library.rest.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exception thrown when the requested resource cannot be modified.
 *
 * @author Dávid Lupták
 * @version 9.12.2016
 */
@ResponseStatus(value = HttpStatus.NOT_MODIFIED, reason = "The requested resource cannot be modified.")
public class ResourceNotModifiedException extends RuntimeException {
    public ResourceNotModifiedException(Throwable cause) {
        super(cause);
    }
}
