package cz.muni.fi.pa165.library.api.exception;

/**
 * Exception thrown when requested entity not found.
 *
 * @author Dávid Lupták
 * @version 15.11.2016
 */
public class NoEntityFoundException extends RuntimeException {

    public NoEntityFoundException(String message) {
        super(message);
    }

    public NoEntityFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
