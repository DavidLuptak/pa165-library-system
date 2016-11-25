package cz.muni.fi.pa165.library.exceptions;

import org.springframework.dao.DataAccessException;

/**
 * @author Martin
 * @version 25.11.2016
 */
public class LibraryDAOException extends DataAccessException {
    public LibraryDAOException(String msg) {
        super(msg);
    }

    public LibraryDAOException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
