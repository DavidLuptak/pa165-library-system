package cz.muni.fi.pa165.library.web.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 *
 * @author Bedrich Said
 */
@ResponseStatus(value=HttpStatus.FORBIDDEN)
public class WebSecurityException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public WebSecurityException() {
    }

    public WebSecurityException(String message) {
        super(message);
    }

    public WebSecurityException(Throwable cause) {
        super(cause);
    }

    public WebSecurityException(String message, Throwable cause) {
        super(message, cause);
    }
}
