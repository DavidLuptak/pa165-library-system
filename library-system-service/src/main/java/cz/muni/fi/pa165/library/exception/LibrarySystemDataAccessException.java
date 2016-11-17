package cz.muni.fi.pa165.library.exception;

import org.springframework.dao.DataAccessException;

/**
 * Exception thrown when data access error for the requested
 * entity occurs.
 *
 * @author Dávid Lupták
 * @version 15.11.2016
 */
public class LibrarySystemDataAccessException extends DataAccessException {

    public LibrarySystemDataAccessException(String message) {
        super(message);
    }

    public LibrarySystemDataAccessException(String message, Throwable cause) {
        super(message, cause);

    }
}
