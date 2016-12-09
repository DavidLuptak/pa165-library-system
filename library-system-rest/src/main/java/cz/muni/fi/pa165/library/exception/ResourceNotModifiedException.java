package cz.muni.fi.pa165.library.exception;

/**
 * @author Dávid Lupták
 * @version 9.12.2016
 */
public class ResourceNotModifiedException extends RuntimeException {
    public ResourceNotModifiedException(Throwable cause) {
        super(cause);
    }
}
