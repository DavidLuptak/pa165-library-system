package cz.muni.fi.pa165.library.rest.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exception thrown when the requested resource already exists.
 *
 * @author Dávid Lupták
 * @version 9.12.2016
 */
@ResponseStatus(value = HttpStatus.UNPROCESSABLE_ENTITY, reason = "The requested resource already exists.")
public class ResourceAlreadyExistingException extends RuntimeException {
    public ResourceAlreadyExistingException(Throwable cause) {
        super(cause);
    }
}
