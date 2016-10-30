package cz.muni.fi.pa165.library.enums;


/**
 *
 * @author Bedrich Said
 */
public enum BookState {
    NEW ("New"),
    LIGHT_DAMAGE ("Lightly damaged"),
    MEDIUM_DAMAGE ("Moderately damaged"),
    HEAVY_DAMAGE ("Heavily damaged"),
    REMOVED ("Missing");

    private final String name;       

    BookState(String s) {
        name = s;
    }

    public boolean equalsName(String otherName) {
        return otherName != null && name.equals(otherName);
    }

    @Override
    public String toString() {
       return this.name;
    }
}
