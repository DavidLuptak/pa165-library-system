package cz.muni.fi.pa165.library.rest.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exception thrown when the requested resource cannot be deleted
 *
 * @author Dávid Lupták
 * @version 18.12.2016
 */
@ResponseStatus(value = HttpStatus.CONFLICT, reason = "The requested resource cannot be deleted.")
public class ResourceNotDeletableException extends RuntimeException {
    public ResourceNotDeletableException(Throwable cause) {
        super(cause);
    }
}
