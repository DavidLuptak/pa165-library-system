package cz.muni.fi.pa165.library.persistence.enums;

import cz.muni.fi.pa165.library.persistence.constants.BookStateConstants;

/**
 *
 * @author Bedrich Said
 */
public enum BookState {
    NEW(BookStateConstants.NEW),
    LIGHT_DAMAGE(BookStateConstants.LIGHT_DAMAGE),
    MEDIUM_DAMAGE(BookStateConstants.MEDIUM_DAMAGE),
    HEAVY_DAMAGE(BookStateConstants.HEAVY_DAMAGE),
    REMOVED(BookStateConstants.REMOVED);

    private final String value;

    BookState(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
